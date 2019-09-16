# elastic-demo
使用spring-boot集成es，使用spring-data-elasticsearch实现简单的增删改查(curd)

## 1、资料
1. [elasticsearch中文教程](https://es.xiaoleilu.com/010_Intro/05_What_is_it.html "中文教程")   
2. es6以后，text类型的属性索引不会自动编入索引[官方说明](https://www.elastic.co/guide/en/elasticsearch/reference/6.2/fielddata.html)  
3. 默认配置依然存在很多问题，注解不能完全解决，这里需要通过*elasticsearchTemple*或者*transportClient*进行自定义操作[参考](https://www.cnblogs.com/sxdcgaq8080/p/10411423.html)  

## 2、http模块
本项目提供restful接口来操作es，具体操作案例[参考](src/test/EsDemoApplication.http)

##  3、索引操作流程
1. 在es中创建索引
2. 实体中对应使用注解增删改查

## 4.潜在bug
* 如果按照3、中的步骤操作，会导致分页查询的时候报错 提示 text类型默认不启用fielddata，由于text类型会过多占用内存，一旦加入到内存中，将永不销毁，可能存在内存溢出的风险   
* es6.0+中不支持一个index对应多个type，写@document注解时需要注意，避免该问题（es7.0+中去掉了type[官方描述](https://www.elastic.co/guide/en/elasticsearch/reference/7.3/removal-of-types.html)）  
* es中时间存储的是long类型时间戳，DateFormat 设置的是可视化格式，所以在查询的时候需要将时间设置为时间戳进行查询
* **LessThanEqual**的方法后缀并不会包含最大时间
