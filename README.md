一个基于sql生成HTTP接口的工具模块，主要是为了演示自定义spring-boot-starter的实现方式，详细参考以下博文

[Spring Boot Starter介绍](https://www.chengpei.top/archives/spring-boot-starter)

[实现一个自己的spring-boot-starter，基于SQL生成HTTP接口](https://www.chengpei.top/archives/sqlapi-spring-boot-starter)

# 功能介绍
项目中引入该模块后，启动你的项目代码，如果你的项目配置了MySql数据库及Spring-jdbc依赖，则模块会自动创建一张表``t_api_generate_config``，并初始化一条数据，启动完成后，项目会具有如下两个接口：
1. http://localhost:8085/pageQuery/test?current=1&size=10
2. http://localhost:8085/queryOne/test

接口最后的这个/test是接口后缀，是表``t_api_generate_config``的字段``api_postfix``的配置
第一个接口是分页接口，可以执行表里``statement_content``配置的sql分页返回
第二个接口是单条数据查询接口，要求表里``statement_content``配置的sql只能返回一条数据，查询效果如下：
<table>
<tr>
<td><image src="https://chengpei.top/upload/apisql-page.png"/></td>
<td><image src="https://chengpei.top/upload/sqlapi-one.png"/></td>
</tr>
</table>

同时sql配置支持动态参数，基于freemarker标签配置动态条件，例如``statement_content``里配置如下SQL：
```
select * from t_api_generate_config a where 1=1  
<#if apiPostfix?? && apiPostfix != "">  
    and a.api_postfix = '${apiPostfix}'  
</#if>
```
这样就可以如下方式传参：
http://localhost:8085/pageQuery/test2?current=1&size=10&apiPostfix=test
和分页参数一样放在GET请求地址后即可
