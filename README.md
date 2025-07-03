# Workshop Registration System

Sistema backend para la gestión de inscripciones a talleres, desarrollado con Java y Spring Boot. Implementa autenticación segura mediante JWT, persistencia con MySQL, validaciones y envío de correos electrónicos.

## 🚀 Tecnologías utilizadas

| Tecnología                                                                                  | Descripción                                                                                             | Documentación Oficial                                                                                  |
| ------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| [Java 21](https://openjdk.org/projects/jdk/21/)                                             | Lenguaje de programación principal utilizado para desarrollar la aplicación.                            | [Documentación](https://docs.oracle.com/en/java/javase/21/)                                            |
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Framework para crear aplicaciones Java de manera rápida y sencilla.                                     | [Documentación](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)                 |
| [Spring Security](https://spring.io/projects/spring-security)                               | Módulo de Spring para manejar autenticación y autorización.                                             | [Documentación](https://docs.spring.io/spring-security/reference/)                                     |
| [JWT (JSON Web Tokens)](https://jwt.io/)                                                    | Estándar para autenticación y transmisión segura de información entre partes como un objeto JSON.       | [Documentación](https://jwt.io/introduction)                                                           |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa)                               | Abstracción sobre JPA para el acceso a datos con bases relacionales como MySQL.                         | [Documentación](https://docs.spring.io/spring-data/jpa/reference/jpa.html)                             |
| [Spring Validation](https://docs.spring.io/spring-framework/reference/core/validation.html) | Módulo de validación de datos de entrada, especialmente útil en DTOs.                                   | [Documentación](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html) |
| [Java Mail Sender](https://spring.io/projects/spring-framework)                             | Herramienta para enviar correos electrónicos desde la aplicación.                                       | [Documentación](https://docs.spring.io/spring-framework/reference/integration/email.html)              |
| [Lombok](https://projectlombok.org/)                                                        | Librería para reducir el código repetitivo (getters, setters, constructores, etc.).                     | [Documentación](https://projectlombok.org/features/all)                                                |
| [MySQL](https://www.mysql.com/)                                                             | Base de datos relacional utilizada para almacenar la información de usuarios, talleres, registros, etc. | [Documentación](https://dev.mysql.com/doc/)                                                            |
| [Docker](https://www.docker.com/)                                                           | Contenerización de la aplicación para facilitar su despliegue.                                          | [Documentación](https://docs.docker.com/)                                                              |
| [Docker Compose](https://docs.docker.com/compose/)                                          | Orquestación de múltiples contenedores (aplicación y base de datos) en desarrollo local.                | [Documentación](https://docs.docker.com/compose/)                                                      |
| [Amazon EC2](https://aws.amazon.com/ec2/)                                                   | Servicio de AWS utilizado para desplegar y ejecutar la aplicación en la nube.                           | [Documentación](https://docs.aws.amazon.com/ec2/)                                                      |
| [Amazon RDS](https://aws.amazon.com/rds/)                                                   | Servicio de base de datos gestionado en AWS utilizado para la persistencia de datos en producción.       | [Documentación](https://docs.aws.amazon.com/rds/)                                                      |


## 📦 Requisitos Previos

Antes de comenzar asegúrate de tener instalado:

- [Docker](https://www.docker.com/products/docker-desktop) >= 20.x.x
- Docker Compose >= 1.29.x
- Java 21+ (solo si deseas ejecutar sin Docker)
- Maven 3.8+

## 🔧 Instalación local con Docker Compose

Clona este repositorio:

```bash
git clone https://github.com/brittanypallasco2003/workshop_registration_system.git
```

Ve al directorio del proyecto

```bash
cd workshop_registration_system
```

3. Copia el archivo .env.example a .env:

```bash
cp .env.example .env
```

### Variables de Entorno

Es obligatorio editarlo antes de ejecutar el proyecto, y reemplazar los valores por los tuyos reales:

| Variable                | Descripción                                                                                                      |
| ----------------------- | ---------------------------------------------------------------------------------------------------------------- |
| `MYSQL_ROOT_PASSWORD`   | Contraseña del usuario root de MySQL.                                                                            |
| `MYSQL_DATABASE`        | Nombre de la base de datos que usará la aplicación.                                                              |
| `MYSQL_USER`            | Usuario que la aplicación utilizará para acceder a la base de datos.                                             |
| `MYSQL_PASSWORD`        | Contraseña del usuario especificado en `MYSQL_USER`.                                                             |
| `SPRING_DATASOURCE_URL` | URL de conexión a la base de datos (generalmente `jdbc:mysql://db:3306/nombre_bd` cuando se usa Docker Compose). |
| `JWT_SECRET`            | Clave secreta utilizada para firmar y validar los tokens JWT.                                                    |
| `USER_GENERATOR`        | Entidad firmante del token                                                                                       |
| `MAIL_HOST`             | Host del servidor SMTP para el envío de correos.                                                                 |
| `MAIL_PORT`             | Puerto del servidor SMTP.                                                                                        |
| `MAIL_USERNAME`         | Usuario para autenticarse en el servidor de correo.                                                              |
| `MAIL_PASSWORD`         | Contraseña para autenticarse en el servidor de correo.                                                           |

4. Levanta los contenedores

```bash
docker-compose up --build
```

5. Las tablas se crearán automáticamente gracias a JPA/Hibernate (no hay datos iniciales insertados).
> [!IMPORTANT]  
> Para probar la aplicación, es necesario crear manualmente los roles y un usuario administrador.


## Documentación de la API
https://workshop-backend.ddns.net/swagger-ui/index.html
