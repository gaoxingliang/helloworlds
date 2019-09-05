import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

class DistributeTransaction {

    private Properties props;
    private String propertyfile = "jdbc.properties";

    private String sql_1 = "update banks set money=money-5 where user_id=1";
    private String sql_2 = "insert into orders values(17, 77)";


    DistributeTransaction() throws ClassNotFoundException {

        Connection connection_1 = null;
        Connection connection_2 = null;
        DruidXADataSource xaDataSource_1 = null;
        DruidXADataSource xaDataSource_2 = null;
        Xid xid_1 = null;
        Xid xid_2 = null;
        XAConnection xaConnection_1 = null;
        XAConnection xaConnection_2 = null;
        XAResource xaResource_1 = null;
        XAResource xaResource_2 = null;

        try {
            props = new Properties();
            props.load(getClass().getResourceAsStream(propertyfile));
        }
        catch (IOException io) {
            System.err.println("Error while accessing the properties file (" + propertyfile + "). Abort.");
            System.exit(1);
        }

        DruidXADataSource[] xaDataSources = initXADataSource();
        xaDataSource_1 = xaDataSources[0];
        xaDataSource_2 = xaDataSources[1];

        XAConnection[] xaConnections = initXAConnection(xaDataSource_1, xaDataSource_2);
        xaConnection_1 = xaConnections[0];
        xaConnection_2 = xaConnections[1];

        xaResource_1 = initXAResource(xaConnection_1);
        xaResource_2 = initXAResource(xaConnection_2);

        connection_1 = getDatabaseConnection(xaConnection_1);
        connection_2 = getDatabaseConnection(xaConnection_2);

        // create a separate branch for a common transaction
        Xid[] xids = createXID();
        xid_1 = xids[0];
        xid_2 = xids[1];

        try {
            execBranch(connection_1, xaResource_1, xid_1, sql_1);
            execBranch(connection_2, xaResource_2, xid_2, sql_2);

            if (prepareCommit(xaResource_1, xid_1) == XAResource.XA_OK &&
                    prepareCommit(xaResource_2, xid_2) == XAResource.XA_OK) {
                commitBranch(xaResource_1, xid_1);
                commitBranch(xaResource_2, xid_2);
                System.out.println("All trans finished success !!!");
            }
            else {
                throw new RuntimeException("Some branch failed");
            }
        }
        catch (Exception e) {
            System.out.println("Failed run all trans - ");
            e.printStackTrace();
            rollbackBranch(xaResource_1, xid_1);
            rollbackBranch(xaResource_2, xid_2);
            System.out.println("All trans rollback!!!");
        }
    }

    DruidXADataSource[] initXADataSource() {
        System.out.print("Create a XADataSource_1 data source: ");
        DruidXADataSource xaDataSource_1 = new DruidXADataSource();
        xaDataSource_1.setDbType(props.getProperty("db1.dbtype"));
        xaDataSource_1.setUrl(props.getProperty("db1.url"));
        xaDataSource_1.setUsername(props.getProperty("db1.username"));
        xaDataSource_1.setPassword(props.getProperty("db1.password"));
        xaDataSource_1.setDriverClassName(props.getProperty("db1.driver"));
        System.out.println("XA ds 1 Okay.");

        System.out.print("Create a XADataSource_2 data source: ");
        DruidXADataSource xaDataSource_2 = new DruidXADataSource();
        xaDataSource_2.setDbType(props.getProperty("db2.dbtype"));
        xaDataSource_2.setUrl(props.getProperty("db2.url"));
        xaDataSource_2.setUsername(props.getProperty("db2.username"));
        xaDataSource_2.setPassword(props.getProperty("db2.password"));
        xaDataSource_2.setDriverClassName(props.getProperty("db2.driver"));
        System.out.println("XA ds 2 Okay.");
        return new DruidXADataSource[]{xaDataSource_1, xaDataSource_2};
    }

    XAConnection[] initXAConnection(DruidXADataSource xaDataSource_1, DruidXADataSource xaDataSource_2) {
        XAConnection xaconn_1 = null;
        XAConnection xaconn_2 = null;
        try {
            System.out.print("Set up DB_1 XA connection: ");
            xaconn_1 = xaDataSource_1.getXAConnection();
            System.out.println("Okay.");

            System.out.print("Set up DB_2 XA connection: ");
            xaconn_2 = xaDataSource_2.getXAConnection();
            System.out.println("Okay.");
        }
        catch (SQLException e) {
            sqlerr(e);
        }
        return new XAConnection[]{xaconn_1, xaconn_2};
    }

    XAResource initXAResource(XAConnection xacon) {
        XAResource xares = null;
        try {
            System.out.print("Setting up a XA resource: ");
            xares = xacon.getXAResource();
            System.out.println("Okay.");
        }
        catch (SQLException e) {
            sqlerr(e);
        }
        return xares;
    }

    Connection getDatabaseConnection(XAConnection xacon) {
        Connection con = null;
        try {
            System.out.print("Establish database connection: ");
            con = xacon.getConnection();
            con.setAutoCommit(false);
            System.out.println("Okay.");
        }
        catch (SQLException e) {
            sqlerr(e);
        }
        return con;
    }

    Xid[] createXID() {
        Xid xid_1 = null;

        byte [] gid= UUID.randomUUID().toString().getBytes();
        byte [] bid = UUID.randomUUID().toString().getBytes();

        xid_1 = new MysqlXid(gid, bid, 0);
        System.out.println(String.format("Xid  1 global %s, branch %s ", Arrays.toString(gid), Arrays.toString(bid)));

        byte [] bid2 = UUID.randomUUID().toString().getBytes();

        Xid xid_2 = null;
        xid_2 = new MysqlXid(gid, bid2, 0);
        System.out.println("Okay.");
        System.out.println(String.format("Xid 2 global %s, branch %s ", Arrays.toString(gid), Arrays.toString(bid2)));
        return new Xid[]{xid_1, xid_2};
    }

    void execBranch(Connection con, XAResource xares, Xid xid, String sql) {
        try {
            xares.start(xid, XAResource.TMNOFLAGS);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            xares.end(xid, XAResource.TMSUCCESS);
        }
        catch (XAException e) {
            System.err.println("XA exception caught:");
            System.err.println("Cause  : " + e.getCause());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            sqlerr(e);
            throw new RuntimeException(e);
        }
    }

    int prepareCommit(XAResource xares, Xid xid) {
        int rc = 0;
        System.out.print("Prepare XA branch (" +
                Byte.toString((xid.getGlobalTransactionId())[0]) + ", " +
                Byte.toString((xid.getBranchQualifier())[0]) + "): ");
        try {
            xares.prepare(xid);
        }
        catch (XAException e) {
            xaerr(e);
            throw new RuntimeException(e);
        }
        System.out.println("Okay.");
        return rc;
    }

    void commitBranch(XAResource xares, Xid xid) {
        System.out.print("Commit XA branch (" +
                Byte.toString((xid.getGlobalTransactionId())[0]) + ", " +
                Byte.toString((xid.getBranchQualifier())[0]) + "): ");
        try {
            // second parameter is 'false' since we have a two phase commit
            xares.commit(xid, false);
        }
        catch (XAException e) {
            xaerr(e);
            throw new RuntimeException(e);
        }
        System.out.println("Okay.");
    }

    void rollbackBranch(XAResource xares, Xid xid) {
        System.out.print("Rollback XA branch (" +
                Byte.toString((xid.getGlobalTransactionId())[0]) + ", " +
                Byte.toString((xid.getBranchQualifier())[0]) + "): ");
        try {
            xares.rollback(xid);
        }
        catch (XAException e) {
            xaerr(e);
            throw new RuntimeException(e);
        }
        System.out.println("Okay.");
    }

    void sqlerr(SQLException exception) {
        System.err.println("FAILED.");
        while (exception != null) {
            System.err.println("==> SQL Exception caught");
            System.err.println("--> SQLCODE : " + exception.getErrorCode());
            System.err.println("--> SQLSTATE: " + exception.getSQLState());
            System.err.println("--> Message : " + exception.getMessage());
            exception = exception.getNextException();
        }
    }

    void xaerr(XAException exception) {
        System.err.println("FAILED.");
        System.err.println("==> XA Exception caught");
        System.err.println("--> Cause  : " + exception.getCause());
        System.err.println("--> Message: " + exception.getMessage());
        exception.printStackTrace();
    }

    public static void main(String args[]) throws Exception {
        new DistributeTransaction();
    }

}