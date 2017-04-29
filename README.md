# spring-boot-quartz-cluster

springboot 整合quartz 可部署多个节点 同一个job只能在一个节点上运行，节点之间自动故障迁移

1.创建一个testdb的数据库

2.执行src/main/resources/ddl/tables_mysql.sql语句

3.执行QuartzClusterDemoApplication

【注意如果要运行多个节点请修改 application.properties】