# Rent Car Simulator

Yousset Rent Car Simulator

## Install

This project uses Java 11 to run. 

Based in Spring Boot and features, working with graphQL.

### Installing Java 11 on Ubuntu

1. Install OpenJdk 11, and update JAVA_HOME  
    ```bash
    sudo apt install openjdk-11-jdk
    update-alternatives --config java
    # choose java 11 and copy the path to java 11 bin
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
    ```
    
2. Build the project

    ```bash
    mvn clean install
    ```
    
## Running

To compile and run the app, type this in a terminal. 

    mvn spring-boot:run 

You can ping the `health-check` endpoint to verify that the app is up and running.

    curl http://localhost:9290/health-check
    
To run in Docker. 

    docker-compose up -d
    
You can configure the project to validate JWT, with the SECURITY_HEADER_ENABLE and SECURITY_JWT_ENABLE env vars


## Swagger

API documentation is accessible from [localhost:9290/swagger-ui.html](http://localhost:9290/swagger-ui.html)

Endpoints can be documented via annotations. Follow instructions detailed in this blog post: [springfox](https://springfox.github.io/springfox/docs/current/)

## Services

###GraphQL
- [/graphiq](http://localhost:9290/graphiq) GraphQl Implementation

###GET
- [/api/cars/available](http://localhost:9290/api/cars/available) List the available cars
```
curl --location --request GET 'http://localhost:9290/api/cars/available' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset'
```
- [/api/reserve/user/{idUser}](http://localhost:9290/reserve/user/{idUser}) List all reserves for user
```
curl --location --request GET 'http://localhost:9290/api/reserve/user/2' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXZpZGJlbGFuZHJpYTEyNkBnbWFpbC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MTU5NTUyNDYsImV4cCI6MTYxNjA0MTY0Nn0.Jmr5ImuTtcdzH1EuBQb06ndt9MrTutTxPEHzKCRmEvFZ6JFxEQOazDJnDCSCEqIR_pTNJSumYGuQEBv6AqydjQ'
```
- [/api/orders/user/{idUser}](http://localhost:9290/orders/user/{idUser}) List all Order for user
```
curl --location --request GET 'http://localhost:9290/api/orders/user/2' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXZpZGJlbGFuZHJpYTEyNkBnbWFpbC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MTU5NTUyNDYsImV4cCI6MTYxNjA0MTY0Nn0.Jmr5ImuTtcdzH1EuBQb06ndt9MrTutTxPEHzKCRmEvFZ6JFxEQOazDJnDCSCEqIR_pTNJSumYGuQEBv6AqydjQ'
```

- [/api/orders](http://localhost:9290/orders) List all Order (Working Only with ADMIN role)
```
curl --location --request GET 'http://localhost:9290/api/orders' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkByZW50Y2FyLmNvbSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IkFETUlOIn1dLCJpYXQiOjE2MTU5NTUzMTYsImV4cCI6MTYxNjA0MTcxNn0.IPvfCQ5P9zdbfjEywEKBrCBDYFxp6d-627ky5Kq15MWquZ1zjarbvuSrDymUKSzyqWH8N0yFHHDo5v6jJWvnrA'
```

###POST
- [/register](http://localhost:9290/register) Register New User
```
curl --location --request POST 'http://localhost:9290/register' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--data-raw '{
    "name": "Yousset",
    "lastName": "Chacon",
    "email": "davidbelandria126@gmail.com",
    "password": "1234."
}'
```

- [/reserve/user/{idUser}](http://localhost:9290/reserve/user/{idUser}) Create a new reserve:
```
curl --location --request POST 'http://localhost:9290/api/reserve/user/2' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXZpZGJlbGFuZHJpYTEyNkBnbWFpbC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MTU5NTUyNDYsImV4cCI6MTYxNjA0MTY0Nn0.Jmr5ImuTtcdzH1EuBQb06ndt9MrTutTxPEHzKCRmEvFZ6JFxEQOazDJnDCSCEqIR_pTNJSumYGuQEBv6AqydjQ' \
--data-raw '{
    "dtFrom": "2021-05-01T12:00:00",
    "dtTo": "2021-05-15T12:00:00",
    "licensePlate": "AAABBB"
}'
```

- [/orders/user/{idUser}](http://localhost:9290//orders/user/{idUser}) Create a new order (Working Only with ADMIN role):
```
curl --location --request POST 'http://localhost:9290/api/orders/user/2' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkByZW50Y2FyLmNvbSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IkFETUlOIn1dLCJpYXQiOjE2MTU5NTUzMTYsImV4cCI6MTYxNjA0MTcxNn0.IPvfCQ5P9zdbfjEywEKBrCBDYFxp6d-627ky5Kq15MWquZ1zjarbvuSrDymUKSzyqWH8N0yFHHDo5v6jJWvnrA' \
--data-raw '{
    "dtPickUp": "2021-05-01T12:00:00",
    "placePickUp": "Aeropuerto de Tocument",
    "reserve": 2
}'
```

###PUT
- [/orders/{id}](http://localhost:9290/orders/{id}) Close order (Working Only with ADMIN role)
```
curl --location --request PUT 'http://localhost:9290/api/orders/1' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkByZW50Y2FyLmNvbSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IkFETUlOIn1dLCJpYXQiOjE2MTU5NTUzMTYsImV4cCI6MTYxNjA0MTcxNn0.IPvfCQ5P9zdbfjEywEKBrCBDYFxp6d-627ky5Kq15MWquZ1zjarbvuSrDymUKSzyqWH8N0yFHHDo5v6jJWvnrA' \
--data-raw '{
    "recharge": 1.0,
    "placeGiveUp": "Aeropuerto de Tocument"
}'
```

###DELETE
- [/reserve/{id}](http://localhost:9290/reserve/{id}) Cancel Reserve 
```
curl --location --request DELETE 'http://localhost:9290/api/reserve/1' \
--header 'Content-Type: application/json' \
--header 'rentcar: yousset' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXZpZGJlbGFuZHJpYTEyNkBnbWFpbC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE2MTU5NTUyNDYsImV4cCI6MTYxNjA0MTY0Nn0.Jmr5ImuTtcdzH1EuBQb06ndt9MrTutTxPEHzKCRmEvFZ6JFxEQOazDJnDCSCEqIR_pTNJSumYGuQEBv6AqydjQ' \
--data-raw '{
    "dtFrom": "2021-05-01T12:00:00",
    "dtTo": "2021-05-15T12:00:00",
    "licensePlate": "AAABBB"
}'
```