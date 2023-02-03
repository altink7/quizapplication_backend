# Backend Web Engineering Template Project
This is the template project for the BWENG course.

## Requirements
* Docker
    * [Get Docker](https://docs.docker.com/get-docker/)

## Container
* Spring Boot basic setup container
  * Port 8080
* MariaDB container
  * Port 3306
* MinIO container
  * Port 9000
  * Port 9001 (Dashboard)

## Already installed dependecies
* springdoc-openapi
  * /api (API json)
  * /swagger.html (Swagger API UI)

## Setup
Build Docker container
```shell
docker-compose build
```
Start Docker container (with build)
```shell
docker-compose up
docker-compose up --build
```
Stop Docker container
```shell
docker-compose stop
```
Remove Docker container
```shell
docker-compose down
```
