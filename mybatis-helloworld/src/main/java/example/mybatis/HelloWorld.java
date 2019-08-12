package example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis/exampleConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.获取sqlSession实例，能直接执行已经映射的sql语句
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            //1.唯一标识符（namespace + id）
            //2.执行sql需要用到的参数
            User user = openSession.selectOne("UserMapperNameSpace.selectUser", "hello");
            System.out.println(user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
