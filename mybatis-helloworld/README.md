# hello world for mybatis
## 需求
1. 安装mysql, 并对应修改[exampleConfig.xml](src/main/resources/mybatis/exampleConfig.xml)
2. 创建数据库和表
```
create database mybatis_example;
use mybatis_example;
create table users (
    username varchar(32) primary key,
    password varchar(64)
);
insert into users values ("hello", "world"), ("mybatis", "yes");
```

## 运行测试代码
