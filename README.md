# To-Do List Backend (to--do-list)

Mid-term project for INSY 8322 Web Technology and Internet, Adventist University of Central Africa (AUCA).

- Name: Ngoga Nixon
- Student ID: 27980
- Project name: to--do-list

## About the project
A small REST API for a team task board. It manages projects, members, desks, skills, tasks and work logs.
It is built with Spring Boot, Spring Data JPA and PostgreSQL.

## What you need
- Java 17
- PostgreSQL
- Postman (to test the endpoints)

## Steps to run the API
1. Clone the repository:
   git clone https://github.com/NgogaNixon/to--do-list-backend.git
2. Create the database in PostgreSQL:
   CREATE DATABASE todo_db;
3. Open src/main/resources/application.properties and check the username and password
   (the project uses username postgres and password 123456).
4. Start the application from the project folder:
   .\mvnw spring-boot:run
5. The API runs on http://localhost:8080
6. Import to--do-list-midterm.postman_collection.json in Postman to test the endpoints.

The tables are created automatically when the application starts.

## Main endpoints
- /api/skills, /api/desks, /api/members, /api/projects, /api/tasks (save, all, {id}, update, delete)
- /api/work-logs (save, project/{projectCode}, delete)
- /api/tasks/stats/by-status and /api/tasks/cancel-day

## Parts of the project
- Part A: entities, CRUD and seed data
- Part B: derived queries
- Part C: JPQL queries
- Part D: business rules
- Part E: work logs
- Part F: reports and bulk update