# Congestion Tax Calculator


This repository contains the implementation of a Congestion Tax Calculator that calculates the fees for vehicles within the Gothenburg area based on the given rules. The application is developed with Spring Boot and uses an H2 database.

### Used Technologies
The following technologies were used in the development of this application:

- Java
- Spring Boot
- H2 Database
- Maven
- Docker
- Docker Composeq
- 

### How to run the Spring Boot project

To run the project, you need to have Java and Maven installed on your machine. Follow the below steps:

- Open zip file
- Navigate to the project directory:
```
$ cd tax
```
- Build the project:
```
$ mvn clean install
```
- Run the project:
```
$ mvn spring-boot:run
```
The application will start running at http://localhost:8080.

### How to run with docker-compose command
To run the project using Docker, you need to have Docker and docker-compose installed on your machine. Follow the below steps:
- Open zip file
- Navigate to the project directory:
```
$ cd tax
```
- Build the Docker image:
```
$ docker build -t tax .
```
- Run the project using docker-compose command:
```
$ docker-compose up
```
The application will start running at http://localhost:8080.


## API Endpoints
Once the application is running, you can use the following API endpoints to call the calculation with different inputs:

- `/calculate`: Calculates the congestion tax fee for a list of dates for a specific vehicle type and city.

Method: POST

Request Body:
```
{
   "vehicleType": "car",
   "cityName": "Gothenburg",
   "dates": [
       "2013-02-08 06:15:00",
       "2013-02-08 06:45:00"
   ]
}
```
Response Body:
```
{
"totalFee": 20
}
```
Note: The `vehicleType` can be one of the following: `car`, `bus`, `motorcycle`, `emergency`, `diplomat`, `foreign`, `military`. 
The `cityName` can be either `gothenburg` or `stockholm`.

- `/swagger-ui.html`: Provides the Swagger UI for API documentation.

#### Access swagger ui

http://localhost:8080/swagger-ui/index.html


#### Access h2 database console
http://localhost:8080/h2-console/


## Bonus Scenario
The parameters used by the application have been moved to an outside data store, which can be read during runtime by the application. This means that different content editors for different cities can be in charge of keeping the parameters up to date.

## Future Improvements
If given more time, the following improvements could be made:
- Add more test cases to ensure the correctness of the calculation.
- Refactor the code to improve its readability, maintainability, and testability.
- Add input validation to the API endpoints.
- Add cache
