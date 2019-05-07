This text file is the readme for the project Code Coverage.

Following are the instructions to run the project.

Open the project and run the following commands

Step 1: mvn clean

This command will clean the project's working directory.

Step 2: mvn install

This command will install the built artifact into the repository.


Now to run this tool on other projects. Open the pom.xml file of that project and add the following code in that.


Step 3:  
a. add the dependency of the code coverage tool.

<dependency>
    <groupId>edu.utdallas</groupId>
    <artifactId>code-coverage</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>


b. add the following plugins

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <version>2.3</version>
        <executions>
	        <execution>
                    <goals>
                        <goal>properties</goal>
                    </goals>
                </execution>
         </executions>
</plugin>

<plugin>
	<groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.19.1</version>
        <configuration>
     	   <argLine>-javaagent:${edu.utdallas:code-coverage:jar}=${project.groupId}</argLine>
           <properties>
           	<property>
        	        <name>listener</name>
                        <value>edu.utdallas.JUnitListener</value>
                </property>
           </properties>
        </configuration>
</plugin>


Run the project with following command

Step 4: mvn test


the statemet_coverage.txt and variable.txt will be created in the target folder of the project.


----------------------------------------------------------------------------------------------------------------------------------------------

NOTE:  

comment the whole visitCode() method of MethodTransformVisitor class to get the line numbers accurately and precisely in statement_coverage.txt


----------------------------------------------------------------------------------------------------------------------------------------------
