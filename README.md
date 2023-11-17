
![Vacancy-Tracker.png](https://postimg.cc/VJ6YwtDs)


### VacancyTracker

VacancyTracker is an app I created for my personal development needs to get my first job in IT. I tried to implement technologies in it that are used in the world of commercial projects.

The application downloads job offers from a public external http server. To stay up to date, the scheduler fetches job offers every 3 hours and saves them to the database if they are not already there(URL of site determines it). While the user performs the first query for job offers, the offers are fetched from the MongoDB database and cached in Redis at the same time. If, within 60 minutes, the user makes subsequent queries for job offers, the offers are just fetched from Redis which reduces the query time from approx. 150ms to 5 ms. Also, the user has to log in and get a JWT to see the job offers. The user can add jobs to the app himself. As the application does not have a frontend, Swagger and Redis-Commander were connected to the project. The application is containerised in Docker. I also covered the project with unit and integration tests.

The system architecture as I used in this project is a modular monolith (which could be transformed into a microservice). The application architecture, on the other hand, uses elements of hexagonal architecture and facade design pattern ;)



## Tech Stack

**Core:**  Java 17, Apache Maven, Spring Boot, MongoDB, Docker, Redis

**Tests:** JUnit5, AssertJ, Mockito, Testcontainers, MockMvc

**Tools&Libraries:** IntelliJ IDEA Ultimate, Git, GitHub, Log4j2, Lombok, Awaitility, Wiremock, RestTemplate, MongoExpress, Redis-Commander, Swagger, Spring Scheduler


## C3 diagram
This is a fairly simple c3 diagram that allows a quick overview of the application structure.

![App Screenshot](https://i.postimg.cc/W1ZpY5rz/c3diagram.jpg)


## API Reference



```http
```

| ENDPOINT | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `/offers` | `GET` | gets all offers in JSON format


```http
```
| ENDPOINT | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `/offers/{id}`| `POST` | gets offer by ID in JSON format|

```http
```

| ENDPOINT | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `/offers`      | `POST` | posts offer to database in JSON format|

```http
```

| ENDPOINT | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `/register` | `POST` | saves user to database

```http
```

| ENDPOINT | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `/token` | `POST` | authenticates and generates token



## Installation and Run
### Prerequisites
Make sure you have Docker installed on your machine. You can download it [here](https://www.docker.com/get-started).
### Getting started
Clone the project

```bash
  git clone https://github.com/krzysieknowak/VacancyTracker.git
```

Go to the project directory

```bash
  cd VacancyTracker
```

Build and run the project using Docker Compose:

```bash
  docker-compose up
```
To test application it is recommended to use Swagger with link below or Postman :)
```bash
  http://localhost:8080/swagger-ui/index.html#/
```
To use Redis-Commmander go to:
```bash
  http://localhost:8082/
```
To use MongoExpress go to:
```bash
  http://localhost:8081/
```

### Stopping the Application
To stop the application and shut down the services, you can use the following command:
```bash
  docker-compose down
```
