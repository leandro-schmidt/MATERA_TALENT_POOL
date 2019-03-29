# MATERA_TALENT_POOL
Rest Server for the matera talent pool evaluation.
The project consists into a web application that exposes REST operations for employees.
A more detailed documentation about the operations can be found [here](https://app.swaggerhub.com/apis-docs/leandro-schmidt/MATERA_TALENT_POOL/1.0.0)

## Getting Started
Here are a few things you need to know about this application.

### Initial Employee Data
When the application is started, it will use the json file located [here](https://github.com/leandro-schmidt/MATERA_TALENT_POOL/blob/master/src/main/resources/employees.json) as a initial load of employees to the memory database. 
If you want to add more employees, or remove some, change this file and then build the application again.

### Other info
This app runs on port 8080 as default.
