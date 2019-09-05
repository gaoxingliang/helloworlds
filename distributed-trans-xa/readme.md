# 分布式事务XA实现
https://blog.csdn.net/wuzhiwei549/article/details/79925618
# 准备
需要对应的mysql数据库资源. 具体配置查看[jdbc.properties](./src/main/resources/jdbc.properties)
```
create database testxa_db1;
use testxa_db1;
create table orders (order_id int primary key, order_num int);
insert into orders values (12, 23);
insert into orders values (13, 34);
insert into orders values (14, 45);

create database testxa_db2;
use testxa_db2;
create table banks (user_id int primary key, money int);
insert into banks values(1, 100);
```

# 注意
第二次运行可能会失败, 因为[DistributeTransaction](src/main/java/DistributeTransaction.java)中`private String sql_1 = "insert into orders values(17, 77)";`这里有一个主键限制. 改一下就可以知道了.
