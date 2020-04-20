package com.example.db;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;

@Component
public class DBConfig {

    @Bean
    public DataSource getDatasource() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final String driverClassName = "com.mysql.jdbc.Driver";
        final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306";
        final String username = "root";
        final String password = "changeit";
        // Build dataSource manually:
        final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
        final Driver driver = (Driver) ClassUtils.getConstructorIfAvailable(driverClass).newInstance();
        final DataSource dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);
        // or using DataSourceBuilder:
        //final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(jdbcUrl).username(username).password(password).build();

        return dataSource;
    }
}
