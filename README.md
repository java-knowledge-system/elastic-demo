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

## 5、es搜索环境搭建

* es单点环境 docker  
````bash
docker run  --name es  -d -p 9200:9200 -p 9300:9300    -e "discovery.type=single-node" elasticsearch:6.4.3
```` 
* es跨域配置
```bash
docker exec -it es bash
cd config
yum install -y vim
vim elasticsearch.yml
```
```yaml
# elasticsearch.yml 添加配置
# 是否支持跨域
http.cors.enabled: true
# *表示支持所有域名
http.cors.allow-origin: "*"
```
* 配置管理界面
```bash
docker  run --name stoic_diffie -d -p 9100:9100 docker.io/mobz/elasticsearch-head:5
#解决406 问题
docker exec -it stoic_diffie bash 
mv -f /etc/apt/sources.list /etc/apt/sources.list.bak
echo "deb http://mirrors.163.com/debian/ jessie main non-free contrib" >> /etc/apt/sources.list
echo "deb http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list
echo "deb-src http://mirrors.163.com/debian/ jessie main non-free contrib" >>/etc/apt/sources.list
echo "deb-src http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list
apt-get update
apt-get install -y vim
vim _site/vendor.js
```
```js
// 找到contentType 替换value为application/json;charset=UTF-8
//...
ajaxSettings:{
    // ...
    contentType: "application/json;charset=UTF-8"
    // ...
}
//...
var inspectData = s.contentType === "application/json;charset=UTF-8"
```
* 添加es添加ik分词器
```bash
git clone https://github.com/medcl/elasticsearch-analysis-ik.git
cd elasticsearch-analysis-ik
git checkout v6.4.3
vim pom.xml
```
```text
把 <elasticsearch.version>6.2.2</elasticsearch.version> 替换成 <elasticsearch.version>6.4.3</elasticsearch.version>
```
```bash 
docker exec -it es bash
cd plugins
mkdir ik
exit
mvn clean package -Dskip.test=true
docker cp target\releases\elasticsearch-analysis-ik-6.4.3.zip es:/usr/share/elasticsearch/plugins/ik
docker exec -it es bash
cd plugins/ik
unzip -X elasticsearch-analysis-ik-6.4.3.zip
rm -rf elasticsearch-analysis-ik-6.4.3.zip
mv elasticsearch/* .
rm -rf elasticsearch
exit
docker restart es 
```