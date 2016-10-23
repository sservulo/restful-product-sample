## Introduction

This project is sample project for building a RESTful service using JAX-RS that depends on [Spring Boot](http://projects.spring.io/spring-boot/), [Jersey](https://jersey.java.net/) and [H2](http://www.h2database.com/html/main.html) projects.

Scenario:

We have a Product Entity with One to Many relationship with Image entity.

Product also has a Many to One relationship with itself (Many Products to one Parent Product).


## Requirements

1. Build a Restful service using JAX-RS to perform CRUD operations on a Product resource using Image as a sub resource of Product.
2. Build API classes to perform these operations:
  1. Get all products excluding relationships (child products, images).
  2. Get all products including specified relationships (child product and/or images).
  3. Same as i using specific product identity.
  4. Same as ii using specific product identity.
  5. Get set of child products for specific product.
  6. Get set of images for specific product.
3. Build  JPA/Hibernate classes using annotations to persist these objects in the database.


### Other requirements

1. Maven must be used to build, run tests and start the application.
2. The tests must be started with the mvn test command.
3. The application must start with a Maven command: mvn exec:java, mvn jetty:run, mvn spring-boot:run, etc.
4. The application must have a stateless API and use a database to store data.
5. An embedded in-memory database should be used: either H2, HSQL, SQLite or Derby.
6. The database and tables creation should be done by Maven or by the application.


## Running

Assuming Java and mvn are installed, to run the application, on the root folder, simply run:

```
../root$ mvn spring-boot:run
```

The application should start at port 8080 (note that only one instance of the server can run at time).


## Tests

To run all the tests, on the root folder, run:

```
../root$ mvn test
```

For the sake of simplicity, tests for simple getters and setters are not included.


## Input

Input should be formatted as described in the problem description and provided as a text (.txt) file to be upload to the application.


## License

Apache 2.0, you know the drill ;)
