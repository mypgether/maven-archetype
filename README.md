# Introduction

maven-archetype is a framwork that generate the archetype of multi module in a project

## Integration of components

* Springboot
* Springcloud Sleuth
* Mybatis
* Mybatis Generator
* Redis --> Lettuce
* Kafka

## Related Software version information

Software Name | Version
--- | ---
[springboot](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/) | 2.1.5.RELEASE
[springboot-dubbo](https://github.com/apache/dubbo-spring-boot-project) | 2.7.1
[springboot-mybatis](https://github.com/mybatis/spring-boot-starter) | 1.3.2 
[dubbo](https://github.com/apache/dubbo) | 2.7.1
fastjson | 1.2.58
guava | 17.0
[netflix-archaius](https://github.com/Netflix/archaius) | 0.6.6
prometheus | 0.6.0
httclient | 4.5.5
lombok | 1.16.18
commons-beanutils | 1.8.3
commons-codec | 1.7
commons-collections | 3.2.1
commons-lang | 3.8.1
commons-io | 2.4
commons-pool | 2.6.0
commons-dbcp | 1.4
commons-configuration | 1.10
--- | ---
jmh | 1.17.5
mockito | 2.8.9
powermock | 1.7.1
h2db | 1.4.197

# Usage

## fetch source code

```
git clone https://github.com/mypgether/maven-archetype.git && cd maven-archetype
```

## modify pom.xml

```
vim pom.xml
replace "your nexus releases repository url" with your url
replace "your nexus snapshot repository url" with your url
```

## deploy to maven repository

```
mvn clean deploy -Dmaven.test.skip=true
```

## build your project

As Command Line below 

* "com.gether.me" is your project packagename
* "demo" is your project name
* "1.0.0-SNAPSHOT" is your project version

```
mvn archetype:generate -DgroupId=com.gether.me -DartifactId=demo -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.gether.me -DarchetypeArtifactId=maven-archetype -DarchetypeVersion=1.0-SNAPSHOT -X -DarchetypeCatalog=local
```

# Module Description

ModuleName | Function
--- | ---
xxx-model | 实体对象模块
xxx-dao | db操作模块,mybatis的mapper文件
xxx-core | 核心业务处理模块
xxx-facade | 对外暴露的interface接口模块(提供给第三方的dubbo jar)
xxx-api | facade模块的实现(dubbo实现,controller实现)
xxx-web | 容器启动模块(配置信息、拦截器等)