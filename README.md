## Payment Aggregator Service
Version: 1.1.0-alpha-3 (dev)

Payment aggregator service to support the payment operations through providing various payment gateway (pgw) APIs to Toffee.

### Table of Contents
- [Prerequisites](#prerequisites)
- [Start the Application Using Docker](#start-the-application-using-docker)
- [Running the Application Locally (Not Recommended)](#running-the-application-locally-not-recommended)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Troubleshooting](#troubleshooting)

### Prerequisites
- [Docker](https://docs.docker.com/engine/install/)
- Preferred IDE (e.g., [IntelliJ](https://www.jetbrains.com/idea/download/?section=linux))
- To learn more about Docker, follow this [tutorial](https://www.youtube.com/watch?v=31ieHmcTUOk)

### Start the Application Using Docker:
1. Set the current application profile in `application.properties`. Set the attribute `spring.profiles.active` to one of the following: `dev | stage | prod`.
2. Create a new `.properties` file suffixed with the active Spring profile (e.g., `application-dev.properties`) and configure the required properties as defined in `application.properties`.
3. Build the Docker image and fire up the container:
    ```shell
    docker compose up --build
    ```

The application should be running on `0.0.0.0`(localhost) and port `8080`.

### Prequisites for setting up the Application locally (Not Recommended)
- [PostgreSQL@14](https://www.postgresql.org/download/)
- [Redis](https://redis.io/downloads/)
- [OpenJDK 17](https://openjdk.org/projects/jdk/17/)

### Start the Application Locally (Not Recommended)
1. **Install Dependencies:**
    - [PostgreSQL@14](https://www.postgresql.org/download/) or higher
    - [Redis](https://redis.io/downloads/)
    - [OpenJDK 17](https://openjdk.org/projects/jdk/17/)

2. **Set Up Local Environment:**
    - Ensure PostgresSQL and Redis are running.
    - Create a new `.properties` file suffixed with the active Spring profile (e.g., `application-dev.properties`) and set all the required properties as defined in `application.properties`.

3. **Run Application Using Spring Commands:**
    - Navigate to the project root and execute the following command to run the application:
      ```shell
      ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
      ```


### API Documentation
- Payment APIs for Clients - [Documentation on Confluence](https://toffee-bl.atlassian.net/wiki/spaces/TP/pages/264208443/Toffee+Client+APIs#Payment)
- Payment Aggregator Admin Service - [Documentation on Confluence](https://toffee-bl.atlassian.net/wiki/spaces/TP/pages/239009805/Payment+Aggregator+Admin+Service+Planning)

### Database Schema
- [Schema definition on dbdiagram.io](https://dbdiagram.io/d/payment_gatway_listing-662a36fa03593b6b61f2adc0)

### Troubleshooting
- **Docker Issues**: If you encounter issues while building or running the Docker container, ensure Docker is installed and running correctly. Refer to the [Docker documentation](https://docs.docker.com/get-docker/) for troubleshooting steps.
- **Database Connection**: Verify that the PostgreSQL and Redis instances are running and accessible. Check the connection properties in your Spring profiles.
- **Logs**: Check the application logs for any error messages. Logs can be found in the Docker container or in the `logs` directory.
