# Proyecto: Franquicias 

Este proyecto es una aplicación basada en Spring Boot con R2DBC para manejar una lista de franquicias que tienen asociadas a ellas sucursales y a las**** surcursales están asociados ciertos productos.

## Requisitos para Ejecutar Localmente

Para correr la aplicación sin Docker, necesitas instalar los siguientes componentes:

- Java 17 o superior ([descargar](https://adoptium.net/))
- Gradle ([descargar](https://gradle.org/install/))
- MySQL 8 ([descargar](https://dev.mysql.com/downloads/))

### Configuración de la Base de Datos

1. Asegúrate de que MySQL esté corriendo y crea la base de datos:
   ```sql
   CREATE DATABASE franchises;
   ```

2. Modifica el archivo `application.properties` o `application.yml` poniendo las credenciales de tu maquina local y descomentando la línea de ejecución de scripts:
   ```properties
   spring.sql.init.mode=always
   spring.sql.init.data-locations=classpath:/database/data.sql
   spring.r2dbc.username=${DATABASE_USER_NAME:root}
   spring.r2dbc.password=${DATABASE_PASSWORD:l1109660212j}
   spring.r2dbc.url=${DATABASE_URL:r2dbc:mysql://localhost:3306/franchises}
   
   logging.level.org.springframework.r2dbc=DEBUG
   ```

### Pasos para Ejecutar la Aplicación Localmente

1. Clona el repositorio y accede a la carpeta del proyecto:
   ```sh
   git https://github.com/Lau36/franchises_challenge.git
   cd franquicias_prueba
   ```

2. Compila el proyecto con Gradle:
   ```sh
   ./gradlew build
   ```

3. Ejecuta la aplicación:
   ```sh
   ./gradlew bootRun
   ```

4. La aplicación estará haciendo uso de una base de datos en la nube y disponible en:
   ```
   http://localhost:9090
   ```

5. La documentación de la API se encuentra en:
   ```
   http://localhost:9090/swagger-ui/index.html
   ```

---

## Ejecutar con Docker

### Pasos para Ejecutar con Docker

1. Clona el repositorio y accede a la carpeta del proyecto:
   ```sh
   git clone https://github.com/Lau36/franchises_challenge.git
   cd franquicias_prueba
   ```

2. Construye la aplicación:
   ```sh
   ./gradlew build
   ```

3. Levanta los contenedores con Docker:
   ```sh
   docker-compose up --build
   ```

4. La aplicación estará disponible en:
   ```
   http://localhost:9090
   ```

5. La documentación de la API se encuentra en:
   ```
   http://localhost:9090/swagger-ui/index.html
   ```




