# Spring Boot Confluent kafka Publisher

## Overview

This project demonstrates how to use Spring Boot to push avro events to a Confluent Kafka topic with avro schema validation. The setup includes running a Kafka server and schema registry using Docker. Below are the steps to set up the environment, create the necessary Kafka topic and schema, and run the application to push events.

## Prerequisites

- java 17
- Docker and Docker Compose installed
- Maven for building the project
- Postman or a similar tool for creating avro schema

## Setup Instructions

1. **Clone the Repository**

```sh
git clone <repository-url>
cd <repository-directory>
```

2. **Build the Project**

Build the project using Maven:

For Maven:
```sh
mvn clean install
```

3. **Start Kafka and Schema Registry**

Navigate to the project directory and start Kafka and the schema registry using Docker Compose:

```sh
docker-compose up -d
```

Wait for about 10 seconds, then check the status of the containers:

```sh
docker ps
```

4. **Create Kafka Topic**

When kafka and schema registry containers are up, create the Kafka topic `source_topic` using the following command (replace `container-id` with your Kafka container ID):

```sh
docker exec container-id kafka-topics --create --topic source_topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
```

5. **Register Schema**

Use the following curl command in Postman to create the schema (make sure the schema registry is running on `localhost:8081`):

```sh
curl --location --request POST 'http://localhost:8081/subjects/com.student.tracker.avro.StudentEvent/versions' \
--header 'Content-Type: application/vnd.schemaregistry.v1+json' \
--data-raw '{
    "schema": "{\"type\":\"record\",\"name\":\"StudentEvent\",\"namespace\":\"com.student.tracker.avro\",\"fields\":[{\"name\":\"students\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Student\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"school\",\"type\":{\"type\":\"record\",\"name\":\"School\",\"fields\":[{\"name\":\"schoolId\",\"type\":\"int\"},{\"name\":\"schoolName\",\"type\":\"string\"},{\"name\":\"location\",\"type\":\"string\"}]}}]}}]}]}"
}'
```

6. **Run the Spring Boot Application**

Start the application by running the main method in your IDE or use the following command:

```sh
mvn spring-boot:run
```

7. **Verify Event Pushing**

To verify if the event is pushed to the topic, use the following command (replace `container-id` with your Kafka container ID):

```sh
docker exec -it container-id kafka-console-consumer --bootstrap-server localhost:9092 --topic source_topic --from-beginning --max-messages 1
```

Alternatively, you can use a subscriber application to check the events.

## Repository Link

[Subscriber Application Repository](<subscriber-repo-url>)

## Conclusion

Following the above steps will help you set up the environment and push events to a Kafka topic using Spring Boot. 
If you encounter any issues or have any questions, please refer to the project's documentation or raise an issue in the repository.
