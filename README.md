# elastic-demo
使用spring-boot集成es，使用spring-data-elasticsearch实现简单的增删改查(curd)

## 1、资料
1. [elasticsearch中文教程](https://es.xiaoleilu.com/010_Intro/05_What_is_it.html "中文教程")   
2. es6以后，text类型的属性索引不会自动编入索引[官方说明](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/fielddata.html)  

## 2、http模块
本项目提供restful接口来操作es，具体操作案例[参考](src/test/EsDemoApplication.http)

##  3、索引操作流程
1. 在es中创建索引
2. 实体中对应使用注解增删改查

## 潜在bug
如果按照3、中的步骤操作，会导致分页查询的时候报错 提示 text类型默认不索引问题