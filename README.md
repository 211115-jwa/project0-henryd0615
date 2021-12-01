# Project 0: Bike Shop
The goal of this project is to show that you can plan, code, prioritize, and meet deadlines. Failure to present a satisfactory project will result in a release from training.

Note: testing is a primary focus of this project. A fully-functional application with little-to-no testing is *not* a satisfactory project, but a well-tested application that is missing some functionality may still be satisfactory.

The application that you will be building will be an API for a bicycle shop. There will not be a front end for this project, so presentations will be done using Postman.

## Due Dates
The project (all pushed code on your project0-yourname repository) will be due on Monday, December 20th by the end of the day.
Presentations to me and QC will be on Wednesday, December 22nd (time TBD).

## Technical Requirements
1. Data must be stored and retrieved from a PostgreSQL database (local or AWS).
2. Data access in Java will be performed using JDBC DAOs.
3. HTTP handling in Java will be done using Javalin.
4. Service layers must be fully unit tested using JUnit and Mockito.
5. DAOs are fully unit tested.
6. Postman test suites are created to test all endpoints.

## Functional Requirements
1. As a user, I can view all bicycles.
    - GET /bicycles
2. As a user, I can add a new bicycle.
    - POST /bicycles
3. As a user, I can update a bicycle.
    - PUT /bicycles/{id}
4. As a user, I can view bicycles by ID.
    - GET /bicycles/{id}
5. As a user, I can search bicycles by brand/model.
    - GET /bicycles?brand=
    - GET /bicycles?model=

## Presentation Requirements
Presentations should be no more than 5 minutes long. Your primary goals in your presentation should be:

- introduce yourself
- show off the functionality of your project
- show off some of your tests

*If* you have additional time, you can show parts of your code that you're especially proud of.