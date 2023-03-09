# MATERA_TALENT_POOL
Rest Server for the matera talent pool evaluation.
The project consists into a web application that exposes REST operations for employees.

## Getting Started
Here are a few things you need to know about this application.

### Initial Employee Data
When the application is started, it will ingest the json file located [here](https://github.com/leandro-schmidt/MATERA_TALENT_POOL/blob/master/src/main/resources/employees.json) as a initial load of employees to the in memory database. 
If you want to add more employees, or remove some, change this file and then build the application again.

## Building and Running
Since this application uses Spring Boot and Gradle, you just need to build it with this command from the root directory:
```
gradle build
```
A jar will be generated in the build/libs directory.

The jar can be executed with the following command:
```
java -jar MATERA_TALENT_POOL-0.0.1-SNAPSHOT.jar
```

## Other important info
Here are some useful info.

### Json Examples
In the [resources](https://github.com/leandro-schmidt/MATERA_TALENT_POOL/blob/master/src/main/resources) directory there are two json files that are examples of the ones used in the post requests:
```
{
"firstName" : "Leandro" ,
"middleName" : "Costa" ,
"lastName" : "Schmidt" ,
"dateOfBirth" : "2023-01-10T00:00Z" ,
"dateOfEmployment" : "2023-01-10T12:56Z",
"status" : "ACTIVE"
}
```
