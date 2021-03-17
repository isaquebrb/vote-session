# vote-session
Project to handle with voting sessions

## Solution
This solution was built with Java 11 + Spring Boot 2.4 and others tools like OpenFeign, Caffeine Cache, JPA, PostgreSQL, Lombok, JUnit 5, Spring Validation, Swagger2.

### Requirements

- Install Java 11.
- Install Git.
- Install Gradle (or use gradle wrapper). 
- Install Docker Compose (or PostgreSQL*).

Note: If you already have PostgreSQL running, you can use it. Just change de database configs in **application.properties**.

#### Executing the Project
- Clone the [Project](https://github.com/isaquebrb/vote-session.git) and enter in its folder.
- Open terminal or cmd(windows) and execute: **docker-compose up***.
- Execute ./gradlew clean build.
- Execute the VoteSessionApplication.java in project or execute gradle bootRun.

Note: Docker-compose up will a create postgreSQL database and start a pgadmin service, it can be access within the config in **docker-compose.yml**.
## Api Documentation

Heroku: https://vote-session.herokuapp.com/swagger-ui/

Running Local: http://localhost:8080/swagger-ui/

## Postman Collection
[Download Collection](https://www.getpostman.com/collections/7f535a921347af908be1)