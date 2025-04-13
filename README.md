# Base API

Proyecto Base para creación de api.


### Comenzando 🚀

Las tecnologías incluidas en este proyecto base son las siguientes:

	- Spring Boot
	- Spring Data JPA
	- Spring Data Rest
	- Spring Data Rest Hal
	- Jwt
	- Profiles
	- Jndi
	- SSL
	- jib
    - NewRelic


Para generar una nueva a partir de este proyecto hay que seguir los siguientes pasos:

	1.- Clonar proyecto en el directorio deseado.
	2.- Eliminar directorio .git del proyecto clonado.
	3.- Subir proyecto al nuevo repositorio creado para el nuevo proyecto.

Para personalizar el código para la nueva API hay que:

	1.- Cambiar el contexto de la aplicación en el fichero yml. 
		La propiedad que indica el contexto es: 
			server.servlet.context-path
	
	2.- Cambiar el nombre del paquete principal del proyecto. El actual se corresponde con "com.preving.restapi.base"
	
	3.- Cambiar el nombre de la clase principal del proyecto: "RestApiBaseApplication"
	
	4.- Cambiar el nombre de generación del war en el fichero build.gradle.
	
	5.- Editar las conexiones necesarias para la nueva API. 
		Bastará con modificar los datasource para el profile dev , demo y prod dentro del fichero application.yml
		
	6.-	En el fichero /conf/DataBaseConf tenemos las diferentes configuraciones según los diferentes perfiles.
        También tenemos que editar las propiedades que referencia al package base de la aplicación
    
    7.- Modificar el nombre de la aplicación en NewRelic en el archivo jib/newrelic/newrelic.yml (app_name)

    8.- Cambiar mainClass y nombre de imagen en las tareas jar y jib de build.gradle.
    
    9.- Modificar nombre aplicación, servicio y deployment en los manifiestos de k8s (carpeta k8s).

    10.- Modificar nombre aplicación en el archivo .gitlab-ci.yml.

    11.- Cambiar el nombre del proyecto Sonarqube en el build.gradle.

    12.- Modificar Readme con los cambios oportunos (Funcionaldiad de la aplicación, despliegue,..).



## API Documentation 📄
Generada mediante OpenAPI Docs
A tool that simplifies the generation and maintenance of API docs based on the OpenAPI 3 specification for Spring Boot 1.x and 2.x applications.

Disponible en las siguientes URLs:

- /{context-path}/api-docs
- /{context-path}/swagger-ui.html


## Construido con 🛠️

Herramientas utilizadas para crear el proyecto

* [Gradle](https://gradle.org/) - Manejador de dependencias
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework Java
* [NewRelic](https://newrelic.com/) - Monitorizar aplicación


## Configuration 🔧
Parámetros a tener en cuenta para el uso y despliegue de la api:

#### OpenAPI
- **OPENAPI_DOCS_ENABLED:** Enable or disable OpenAPI Docs (true/false: true by default)

## Pre-requisitos 📋

- Java 17
- IDE (Intellij 2021.3)

## Ejecución ⌨️

Seleccionar el perfil deseado en application.yml (Recuerda dejar el perfil activo adecuado para cada entorno antes de deslpegar!):
```
profiles:
    active: dev #arranques en local contra desarrollo 
```

## Wiki 📖

Capturas informativas del proyecto [Wiki]:



## Build 🛠️
```
Gradle.build
```

## Despliegue 📦

Mediante GitLab CI (https://docs.google.com/document/d/1J4dGbX7bXuav1YZuXsbLVeJiTE2Fd86B7n9lvSH13sY)





## Autores ✒️

Contribuyentes principales en el proyecto

* **[Roge Gragera](https://gitlab.preving.com/rogelio.gragera)** - *Elaboración del proyecto*
* **[Felips González](https://gitlab.preving.com/felipe.gonzalez)** - *Lombok, Slf4j*
* **[Jesús Riolobos](https://gitlab.preving.com/jesus.riolobos)**: 

  - Updated Oracle jdbc Driver
  - Updated JVM to 17-18 compatibility
  - Updated Gradle version 7.2 => 7.4.2 to work with JVM_18
  - Upgraded all project framework/libraries:
    - SpringBoot 2.5.6 => 2.7.1
    - OpenApi 1.5.2 => 1.6.9
    - JsonWebToken 0.7.0 => 0.9.1
    - Bouncycastle jdk16: 1.45 => jdk18: 1.71 (fixed security vulnerabilities: CVE-2020-26939, CVE-2020-15522)
    - Commons-codec 1.10 => 1.15
    - Apache Httpclient 4.5.2 => 4.5.13
    - implementation 'org.json:json:20190722' => 'org.json:json:20220320'
    - Lombock 1.18.22 => 1.18.24
    - preving-security 1.0 => 1.1
  - Added Logback/logstash logging capabilities
  - Removed several boot WARNINGS:
    - Deprecated spring.profiles
    - Improved performance: spring.jpa.open-in-view
    - oracle.jdbc.driver.OracleDriver was not found
    - Swagger endpoint security not recommended configuration
  - Added new spring profile 'local', for testing purposes, with H2 in-memory database that can simulate any comercial database:
    - IBM DB2
    - Apache Derby
    - HSQLDB
    - MS SQL Server
    - MySQL
    - Oracle
    - PostgreSQL


## Licencia 📄

Este proyecto está bajo la Licencia de Grupo Preving


## Agradecimientos 🎁

* [Plantilla](https://gist.github.com/Villanuevand/6386899f70346d4580c723232524d35a) utilizada para generar este README.md



---
⌨️ con ❤️ por [Roge](http://gitlab.preving.com/rogelio.gragera) 😊
