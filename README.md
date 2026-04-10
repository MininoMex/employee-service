# employee-service

Este proyecto es una API REST para la gestión de empleados, desarrollada con **Spring Boot**, **Java 17**, **JPA** y **H2 en memoria**.

## Tecnologías

* Java 17
* Spring Boot 2.7.18
* Spring Web
* Spring Data JPA
* H2 Database
* Swagger / OpenAPI
* JUnit 5 y Mockito
* Maven

## Requisitos

Para ejecutar el proyecto se necesita tener instalado:

* Java 17
* Maven
* Git

## Instalación

Clonar el repositorio:

```bash
git clone https://github.com/MininoMex/employee-service.git
cd employee-service
```

Compilar el proyecto:

```bash
mvn clean install
```

## Ejecución

Ejecutar la aplicación con Maven:

```bash
mvn spring-boot:run
```

O generar el JAR y ejecutarlo:

```bash
mvn clean package -DskipTests
java -jar target/employee-service-0.0.1-SNAPSHOT.jar
```

La aplicación levanta por defecto en:

```text
http://localhost:8080
```

## Documentación y utilidades

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

H2 Console:

```text
http://localhost:8080/h2-console
```

Actuator:

```text
http://localhost:8080/actuator/health
```

## Pruebas

Para ejecutar las pruebas:

```bash
mvn clean test
```

Actualmente el proyecto cuenta con una prueba base de contexto para validar que la aplicación arranque correctamente.

## Notas

* La base de datos es **H2 en memoria**, por lo que la información se pierde al reiniciar la aplicación.
* El formato de fecha manejado por la API es `dd-MM-yyyy`.
* El endpoint principal del proyecto es `/employees`.
