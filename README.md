### (RU)
# Simple task management system

## Описание проекта:
Простая система управления задачами с использованием Spring Boot и Spring Data JPA.
Данная система позволяет создавать, просматривать, обновлять и удалять задачи.

## REST API
URL: http://localhost:8080

- POST /tasks - создать новую задачу.
- GET /tasks/{id} - получить информацию о задаче по ее id.
- GET /tasks - получить список всех задач.
- PUT /tasks/{id} - обновить информацию о задаче.
- DELETE /tasks/{id} - удалить задачу.

Подробнее: http://localhost:8080/swagger-ui/index.html

## Запуск приложения:
- клонировать проект в среду разработки;
- настроить подключение к базе данных в файле application.properties;
- запустить метод main в файле SimpleTaskManagementSystemApplication.java

После этого будет доступен OpenAPI http://localhost:8080/swagger-ui/index.html.

## Технологии, используемые в проекте:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.

### (EN)
# Simple task management system

## Project description:
A simple task management system using Spring Boot and Spring Data JPA.
This system allows you to create, view, update and delete tasks.

## REST API
URL: http://localhost:8080

- POST /tasks - create a new task.
- GET /tasks/{id} - get information about a task by its id.
- GET /tasks - get all task list.
- PUT /tasks/{id} - update information about a task.
- DELETE /tasks/{id} - delete a task.

More detailed: http://localhost:8080/swagger-ui/index.html

## How to run an application:
- clone the project into the development environment;
- configure the connection to the database in the application.properties file;
- run the main method in the file SimpleTaskManagementSystemApplication.java.

After doing that OpenAPI by this URL http://localhost:8080/swagger-ui/index.html will be available.

## Technologies:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.