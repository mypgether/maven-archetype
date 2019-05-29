# maven-archetype
	
	maven archetype of multi module in a project

# deploy to local maven repository

	mvn clean install -Dmaven.test.skip=true

# build project
	
	mvn archetype:generate -DgroupId=com.gether.me -DartifactId=demo -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.gether.me -DarchetypeArtifactId=maven-archetype -DarchetypeVersion=1.0-SNAPSHOT -X -DarchetypeCatalog=local