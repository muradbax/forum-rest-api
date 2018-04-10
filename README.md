# 1 Sample Forum REST

Sample Forum web application exposing REST web services API build on Spring Boot framework. 

## 1.1 Profiles

There are 2 different build profiles configured for web application for development and production environments. Main properties file **src/main/resources/application.properties** defines which configuration file will be used for packaging web application.

`spring.profiles.active=dev | prod`

Corresponding properties file will be further used based on provided value, either **src/main/resources/application-dev.properties** or **src/main/resources/application-prod.properties** 

Value is currently read from Maven build file which is passed in with a `-P` flag during build commands.

## 1.2 Persistence API implementations

There are 2 different persistence services implemented to work with database, Spring JDBC and Spring Data JPA/Hibernate. To use either one of the libraries following key should be configured in configuration file *application-{profile}.properties*

`spring.data.persistence=jpa | jdbc`

Corresponding service implementing defined persistence services will be used for building and running web application.

# 2 Deploying

## 2.1 Locally

### 2.1.1 Software required

To run application following standard software components should be installed on a machine:

1. Java 8
2. MySQL database

### 2.1.2 Configure database access

Correct values for database access should be specified in configuration file of any given profile with following keys

`spring.datasource.*`

### 2.1.3 Build and run

Note that Maven is not required as Maven wrappers will be used for build management.

There are two ways to run an application. 

1. Build an artifact, i.e. runnable jar with embedded application server and run it. While in root directory run following commands:

Windows
```
mvnw.cmd package
java jar target/forum-api-{version}.jar
```
Linux
```
./mvnw package
java jar target/forum-api-{version}.jar
```

2. Use Maven plugin run goal to directly compile class files and run application in exploded form with no artifact generated.
Windows
```
mvnw.cmd spring-boot:run -P{profile}
java jar target/forum-api-{version}.jar
```
Linux
```
./mvnw spring-boot:run -P{profile}
```

- **{profile}** should be substituted with corresponding value, either **dev** or **prod** as discussed in [Profiles](#11-profiles) section.

- **{version}** should be substituted with corresponding version which can be obtained from **pom.xml** build file or manually after application is packaged
