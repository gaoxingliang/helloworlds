package com.example.db;

import com.example.db.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class DbApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
		System.out.println("Create database");
    	jdbcTemplate.execute("create database if not exists test ");
		System.out.println("Create table");
		jdbcTemplate.execute("drop table if exists test.customers");
		jdbcTemplate.execute("create table test.customers(id int primary key auto_increment, first_name varchar(32), last_name varchar(32));");
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());
		AtomicInteger r = new AtomicInteger(0);
		jdbcTemplate.batchUpdate("insert into test.customers (first_name, last_name) values(?, ?)", splitUpNames);
		jdbcTemplate.query("select id, first_name as f, last_name as l " +
				"from test.customers where first_name=?", new Object[]{"Josh"}, (rs, rowNum) ->
				Customer.builder().id(rs.getLong("id")).firstName(rs.getString("f")).lastName(rs.getString("l")).build()
		).stream().forEach(c -> System.out.println(c));

		System.out.println("test the stream imple:");
		jdbcTemplate.query("select id, first_name as f, last_name as l " +
				"from test.customers", new Object[0], (rs, rowNum) ->
				Customer.builder().createNum(r.incrementAndGet()).id(rs.getLong("id")).firstName(rs.getString("f")).lastName(rs.getString("l")).build()
		).stream().skip(3).forEach(c -> System.out.println(c));

		// 结论还是查询了所有
		System.out.println("After for each - " + r.get());
	}

}
