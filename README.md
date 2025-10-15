
# Backend - IntraHotel (Spring Boot)

Este es el backend del sistema de gestión hotelera "IntraHotel", desarrollado con Spring Boot. Expone una API REST que gestiona habitaciones, clientes, empleados, reservas, incidencias, servicios y facturación.

## 🔧 Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- MySQL / MariaDB
- Gradle
- Thymeleaf + Flying Saucer (PDF)

## 🧩 Funcionalidades principales

- Autenticación y autorización con roles.
- Gestión de habitaciones, reservas, clientes y servicios.
- Registro de incidencias con imágenes.
- Logs de limpieza diarios.
- Generación de facturas PDF.
- API REST para frontend Angular.

## ⚙️ Requisitos

- Java 17+
- MySQL 8+ / MariaDB
- IDE recomendado: IntelliJ IDEA Ultimate
-  Gradle

## 📦 Instalación

1. Clona el repositorio:

```bash
git clone http://castelargitlab.duckdns.org:8085/pedromarquez/back-fct.git
cd back-fct
```

2. Configura la base de datos:

**[Descarga de la BBDD](https://drive.google.com/file/d/1HbVrHktwFwEwC3UeSMTTtpeG-ezdrY1m/view?usp=drive_link)** 

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=none
```

3. Ejecuta la aplicación:


```bash
  ./gradlew bootRun
```

## 🔒 Seguridad

- Login: `POST /api/auth/login`
- JWT requerido en header `Authorization`

## 📂 Estructura destacada

- `web/` – Controladores REST.
- `security/` – Configuración JWT.
- `domain/models/dao/` – Repositorios JPA.
- `domain/models/entity/` – Entidades.
- `domain/dto/` – DTOs.
- `domain/converters/` – Convertidores de entidades a DTOs y viceversa.
- `domain/services/` – Servicios.

## 🧪 Datos iniciales

- Usuarios por rol.
- 100 habitaciones simuladas.
- Logs de limpieza cargados.

## 👨‍💻 Autor

Desarrollado por Pedro Javier Márquez Fabián como Proyecto Final de DAM.
