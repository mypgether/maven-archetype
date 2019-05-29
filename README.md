# Introduction
	
	maven-archetype is a framwork that generate the archetype of multi module in a project

# Usage

## fetch source code
```
git clone https://github.com/mypgether/maven-archetype.git && cd maven-archetype
```

## 


# deploy to local maven repository

	mvn clean install -Dmaven.test.skip=true

# build project
	
	mvn archetype:generate -DgroupId=com.gether.me -DartifactId=demo -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.gether.me -DarchetypeArtifactId=maven-archetype -DarchetypeVersion=1.0-SNAPSHOT -X -DarchetypeCatalog=local

模块名 | 功能
--- | ---
fms-api | 向外提供api的业务逻辑
fms-core | 核心业务书写
fms-dao | db层的操作
fms-facede | 对fms-api包提供提供的api进一步外包
fms-model | 实体对象
fms-web | 启动容器层，配置信息块