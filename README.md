# Job Application Project
The project involves four microservices:
1. [Job App](https://github.com/Ansh7899/job): Can be used to add jobs associated with companies.
2. [Company App](https://github.com/Ansh7899/company): Can be used to add companies.
3. [Review App](https://github.com/Ansh7899/review): Can be used to add reviews for the companies.
4. [ServiceRegistry App](https://github.com/Ansh7899/serviceregistry): Uses Eureka for service-registry for the above three microservices.

### Requirements:
- Java 17
- Docker Desktop (to setup the PostgresDB)


### Project Specs:
- The project uses Spring boot JPA for persisting data into DB.
- PostgreSQL is used for storing all the entities.
- The project uses OpenFeign for communication between microservices.


### How to set up the project on local:
1. Clone all the four repositories (links provided above).
2. Configure the Postgres properties in application.properties file for all repos.
3. Run the docker-compose.yaml file to start containers for PostgreSQL and PgAdmin.
4. Create the databases using the PgAdmin console.
5. Start the ServiceRegistry app followed by the other three apps.
6. You are all set to go and play with your mini version of LinkedIn.


