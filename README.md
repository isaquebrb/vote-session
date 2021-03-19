# vote-session
Project to handle voting sessions

## Tasks
- Register a new topic
- Open a voting session in a topic (with 1 minute duration default or parameterizable)
- Receive associates votes in sessions (The votes must be 'sim' ou 'não'. And each associate can vote just once per session)
- Count the votes and give the topic voting result

## Solution
```https://vote-session.herokuapp.com/ ```

This solution was built with Java 11 + Spring Boot 2.4 and others tools like OpenFeign, Caffeine Cache, JPA, PostgreSQL, Lombok, JUnit 5, Spring Validation, Swagger2, Docker Compose, Kafka.

#### Requirements

- Install Java 11.
- Install Git.
- Install Gradle (or use gradle wrapper). 
- Install Docker Compose (or PostgreSQL*).

Note: If you already have PostgreSQL running, you can use it. Just change de database configs in **application.properties**.

#### Executing the Project
- Clone the [Project](https://github.com/isaquebrb/vote-session.git) and enter in its folder.
- Open terminal or cmd (windows) and execute: **docker-compose up***.
- Execute ./gradle clean build.
- Execute the VoteSessionApplication.java in project or execute ./gradle bootRun.

Note: Docker-compose up will a create postgreSQL database and start a pgadmin service, it can be access within the config in **docker-compose.yml**.
## Api Documentation

Heroku: https://vote-session.herokuapp.com/swagger-ui/

Running Local: http://localhost:8080/swagger-ui/

## Postman Collection
[Download Collection](https://www.getpostman.com/collections/7f535a921347af908be1)

## Happy Flow
- Create an associate
- Create a topic
- Start a session
- Vote in session*
- Wait the session to finish (1 minute default)*
- Find result in Topic

Notes:
- There is an integration that randomly allow the document to vote or not
- You can update the value of SESSION_DURATION_MINUTES parameter, to change the duration time

## Kafka

This solution uses [CloudKarafka](https://www.cloudkarafka.com/docs/index.html), this feature provides Apache Kafka as service on cloud. So when the voting is done, a message will be sent to the CloudKarafka topic.
In the **application.properties** you can see these configs, like topic name, authentication to cloudkarafka, bootstrap servers and group id name. Spring boot simple doc: https://www.cloudkarafka.com/docs/spring.html 