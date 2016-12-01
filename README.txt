Requirement: 
	JDK 1.8+
	Maven
	
Compiling:
	Go to root folder where pom.xml resides
	> mvn clean install
	> cd target
	For K mean, i is seed id
		> java -jar Assignment2.Cluster-1.0-SNAPSHOT.jar -i 7,8,10,11 --action kmean
	For PAM, i is seed id
		> java -jar Assignment2.Cluster-1.0-SNAPSHOT.jar -i 7,8,10,11 --action pam
	The prediction will be in the target folder