# ConnectionPooling

This project demonstrates how to implement and compare various connection pooling techniques in Java using different libraries.

## Overview

Connection pooling helps improve performance by reusing existing database connections instead of creating a new one for every request. This project includes custom and library-based connection pools:

- **CustomDataSource** – A simple, manually managed connection pool.
- **BasicDataSourceCP** – Apache Commons DBCP2 implementation.
- **C3p0CP** – C3P0 library implementation.
- **HikariCP** – HikariCP implementation, known for its speed and reliability.

## How To Run

### Run command 
```
git clone https://github.com/SpellZZZ/ConnectionPoolingBenchmark.git
```
### Create local PostgeSQL database
```
public static final String URL = "jdbc:postgresql://localhost:5432/pg_custom_db";
public static final String USER = "root";
public static final String PASSWORD = "123456";
```


