# Spring Boot, PostgreSQL, Spring Security, JWT, JPA, Rest API

Build Restful CRUD API for a task manager using Spring Boot, PostgreSQL, JPA and Hibernate.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/kaymi42/TaskManager.git
```

**2. Create PostgreSQL database**
```bash
create database task_manager_db
```

**3. Change postgreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your postgreSQL installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

**5. OpenAPI documentation**

+ open `src/main/resources/static/openapi.json` with SwaggerUI