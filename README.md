# Proyecto Restaurante - Microservicios Spring Boot

## Descripción general

Este proyecto corresponde a un sistema de gestión para un restaurante desarrollado con arquitectura de microservicios utilizando Java y Spring Boot.

El sistema permite administrar distintos procesos del restaurante, tales como comensales, reservas, mesas, garzones, menú, pedidos, cocina, inventario, notificaciones y autenticación. Cada funcionalidad principal fue separada en un microservicio independiente, permitiendo una mejor organización, separación de responsabilidades y escalabilidad del sistema.

Además, el proyecto incorpora API Gateway, documentación con Swagger, persistencia con MySQL, migraciones con Flyway, logging, pruebas unitarias y despliegue mediante Docker y Docker Compose.

---

## Arquitectura del sistema

El sistema está compuesto por los siguientes microservicios:

| Microservicio        | Puerto | Base de datos   | Descripción                         |
| -------------------- | -----: | --------------- | ----------------------------------- |
| api-gateway          |   8080 | No aplica       | Punto de entrada único al sistema   |
| auth-service         |   8084 | No aplica       | Autenticación y generación de token |
| diner-service        |   8081 | diner_db        | Gestión de comensales               |
| reservation-service  |   8082 | reservation_db  | Gestión de reservas                 |
| table-service        |   8083 | table_db        | Gestión de mesas                    |
| waiter-service       |   8085 | waiter_db       | Gestión de garzones                 |
| menu-service         |   8086 | menu_db         | Gestión de productos del menú       |
| order-service        |   8087 | order_db        | Gestión de pedidos                  |
| kitchen-service      |   8088 | kitchen_db      | Gestión de órdenes de cocina        |
| inventory-service    |   8089 | inventory_db    | Gestión de inventario               |
| notification-service |   8090 | notification_db | Gestión de notificaciones           |

---

## Tecnologías utilizadas

* Java
* Spring Boot
* Spring Web / Web MVC
* Spring Data JPA
* Spring Validation
* Spring Cloud Gateway
* Spring Security
* OpenFeign
* MySQL
* Flyway
* Lombok
* MapStruct
* Swagger / SpringDoc OpenAPI
* JUnit
* Mockito
* Docker
* Docker Compose
* Maven

---

## API Gateway

El API Gateway funciona como punto de entrada único al sistema. En lugar de que el cliente consuma directamente cada microservicio por su puerto individual, las solicitudes ingresan por el puerto `8080` y el Gateway las redirige al microservicio correspondiente.

Ejemplos de rutas mediante Gateway:

| Ruta Gateway                                 | Microservicio destino |
| -------------------------------------------- | --------------------- |
| `http://localhost:8080/api/v1/diners`        | diner-service         |
| `http://localhost:8080/api/v1/reservations`  | reservation-service   |
| `http://localhost:8080/api/v1/tables`        | table-service         |
| `http://localhost:8080/api/v1/auth`          | auth-service          |
| `http://localhost:8080/api/v1/waiters`       | waiter-service        |
| `http://localhost:8080/api/v1/menu-items`    | menu-service          |
| `http://localhost:8080/api/v1/orders`        | order-service         |
| `http://localhost:8080/api/v1/kitchen`       | kitchen-service       |
| `http://localhost:8080/api/v1/inventory`     | inventory-service     |
| `http://localhost:8080/api/v1/notifications` | notification-service  |

En Docker, el Gateway fue configurado para comunicarse con los microservicios usando los nombres de los contenedores, por ejemplo:

```yaml
uri: http://table-service:8083
```

Esto es necesario porque dentro de Docker `localhost` representa al propio contenedor, no a los demás servicios.

---

## Bases de datos

Cada microservicio con persistencia posee su propia base de datos MySQL, respetando la separación de responsabilidades de la arquitectura de microservicios.

Bases de datos utilizadas:

```txt
diner_db
reservation_db
table_db
waiter_db
menu_db
order_db
kitchen_db
inventory_db
notification_db
```

Cada base de datos se levanta en Docker mediante un contenedor MySQL independiente.

---

## Flyway

El proyecto utiliza Flyway para versionar y ejecutar automáticamente las migraciones de base de datos.

Las migraciones se encuentran en:

```txt
src/main/resources/db/migration
```

Al iniciar cada microservicio, Flyway revisa si existen migraciones pendientes y las ejecuta sobre la base de datos correspondiente. Esto permite crear las tablas automáticamente sin necesidad de ejecutar scripts manualmente en MySQL.

Flyway también crea una tabla llamada:

```txt
flyway_schema_history
```

Esta tabla registra qué migraciones ya fueron aplicadas.

---

## Docker y Docker Compose

El proyecto fue dockerizado utilizando un `Dockerfile` por microservicio y un archivo `docker-compose.yml` general para levantar todo el sistema.

Cada microservicio se ejecuta dentro de su propio contenedor, y cada base de datos MySQL también se ejecuta en un contenedor independiente.

### Contenedores principales

* api-gateway
* auth-service
* diner-service
* reservation-service
* table-service
* waiter-service
* menu-service
* order-service
* kitchen-service
* inventory-service
* notification-service

### Contenedores MySQL

* mysql-diner-db
* mysql-reservation-db
* mysql-table-db
* mysql-waiter-db
* mysql-menu-db
* mysql-order-db
* mysql-kitchen-db
* mysql-inventory-db
* mysql-notification-db

---

## Ejecución del proyecto con Docker

### Requisitos previos

Antes de ejecutar el proyecto, se debe tener instalado:

* Docker Desktop
* Git
* Java
* Maven o Maven Wrapper incluido en cada microservicio

---

### Paso 1: construir las imágenes

Cada microservicio debe tener generada su imagen Docker. Desde la carpeta de cada microservicio se ejecuta:

```powershell
.\mvnw.cmd clean package -DskipTests
docker build -t nombre-del-servicio .
```

Ejemplo para `table-service`:

```powershell
.\mvnw.cmd clean package -DskipTests
docker build -t table-service .
```

En caso de que el archivo `.jar` generado sea muy pequeño y no sea ejecutable, se puede ejecutar:

```powershell
.\mvnw.cmd clean package spring-boot:repackage -DskipTests
```

Luego se vuelve a construir la imagen:

```powershell
docker build -t nombre-del-servicio .
```

---

### Paso 2: levantar el sistema completo

Desde la carpeta principal del proyecto, donde se encuentra el archivo `docker-compose.yml` general, ejecutar:

```powershell
docker compose up
```

Esto levantará todos los microservicios, sus bases de datos MySQL y el API Gateway.

---

### Paso 3: verificar contenedores

Para verificar que los contenedores estén corriendo:

```powershell
docker ps
```

---

### Paso 4: detener el sistema

Para detener la ejecución:

```powershell
Ctrl + C
```

Luego:

```powershell
docker compose down
```

---

## Documentación Swagger

Cada microservicio posee su propia documentación Swagger.

| Microservicio        | Swagger                                 |
| -------------------- | --------------------------------------- |
| diner-service        | `http://localhost:8081/swagger-ui.html` |
| reservation-service  | `http://localhost:8082/swagger-ui.html` |
| table-service        | `http://localhost:8083/swagger-ui.html` |
| auth-service         | `http://localhost:8084/swagger-ui.html` |
| waiter-service       | `http://localhost:8085/swagger-ui.html` |
| menu-service         | `http://localhost:8086/swagger-ui.html` |
| order-service        | `http://localhost:8087/swagger-ui.html` |
| kitchen-service      | `http://localhost:8088/swagger-ui.html` |
| inventory-service    | `http://localhost:8089/swagger-ui.html` |
| notification-service | `http://localhost:8090/swagger-ui.html` |

El API Gateway se utiliza para centralizar el acceso funcional a los endpoints, mientras que Swagger se mantiene disponible individualmente en cada microservicio.

---

## Endpoints principales

### Diner Service

```txt
GET    /api/v1/diners
GET    /api/v1/diners/{id}
POST   /api/v1/diners
PUT    /api/v1/diners/{id}
DELETE /api/v1/diners/{id}
```

### Table Service

```txt
GET    /api/v1/tables
GET    /api/v1/tables/{id}
POST   /api/v1/tables
PUT    /api/v1/tables/{id}
DELETE /api/v1/tables/{id}
```

### Waiter Service

```txt
GET    /api/v1/waiters
GET    /api/v1/waiters/{id}
POST   /api/v1/waiters
PUT    /api/v1/waiters/{id}
DELETE /api/v1/waiters/{id}
```

### Menu Service

```txt
GET    /api/v1/menu-items
GET    /api/v1/menu-items/{id}
POST   /api/v1/menu-items
PUT    /api/v1/menu-items/{id}
DELETE /api/v1/menu-items/{id}
```

### Reservation Service

```txt
GET    /api/v1/reservations
GET    /api/v1/reservations/{id}
POST   /api/v1/reservations
PUT    /api/v1/reservations/{id}
DELETE /api/v1/reservations/{id}
```

### Order Service

```txt
GET    /api/v1/orders
GET    /api/v1/orders/{id}
POST   /api/v1/orders
PUT    /api/v1/orders/{id}/status
DELETE /api/v1/orders/{id}
```

### Kitchen Service

```txt
GET    /api/v1/kitchen
GET    /api/v1/kitchen/{id}
POST   /api/v1/kitchen
PUT    /api/v1/kitchen/{id}
DELETE /api/v1/kitchen/{id}
```

### Inventory Service

```txt
GET    /api/v1/inventory
GET    /api/v1/inventory/{id}
POST   /api/v1/inventory
PUT    /api/v1/inventory/{id}
DELETE /api/v1/inventory/{id}
```

### Notification Service

```txt
GET    /api/v1/notifications
GET    /api/v1/notifications/{id}
POST   /api/v1/notifications
PUT    /api/v1/notifications/{id}
DELETE /api/v1/notifications/{id}
```

### Auth Service

```txt
POST /api/v1/auth/login
```

---

## Autenticación

El microservicio `auth-service` permite autenticar usuarios definidos en memoria.

Usuarios disponibles:

| Usuario | Contraseña | Rol    |
| ------- | ---------- | ------ |
| admin   | admin      | ADMIN  |
| waiter  | 1234       | WAITER |
| diner   | 1234       | DINER  |

Ejemplo de login:

```json
{
  "username": "admin",
  "password": "admin"
}
```

El servicio responde con un token que puede ser utilizado para autorizar solicitudes protegidas.

---

## Logging

El proyecto incorpora logging en los microservicios para registrar eventos importantes durante la ejecución.

Los logs permiten observar:

* Inicio de operaciones
* Creación de registros
* Actualizaciones
* Eliminaciones
* Validaciones
* Errores
* Comunicación entre microservicios

Esto facilita el monitoreo, la depuración y la defensa técnica del sistema.

---

## Pruebas unitarias

El proyecto incorpora pruebas unitarias con JUnit y Mockito.

JUnit se utiliza para ejecutar los casos de prueba, mientras que Mockito permite simular dependencias como repositorios o clientes Feign.

Las pruebas permiten validar la lógica de negocio sin depender directamente de la base de datos ni de otros microservicios levantados.

Ejecutar pruebas:

```powershell
.\mvnw.cmd test
```

Ejecutar una clase de prueba específica:

```powershell
.\mvnw.cmd -Dtest=NombreTest test
```

Ejemplo:

```powershell
.\mvnw.cmd -Dtest=OrderServiceImplTest test
```

---

## Comunicación entre microservicios

Algunos microservicios necesitan consultar información desde otros servicios.

Ejemplos:

* `reservation-service` consulta `diner-service` y `table-service`.
* `order-service` consulta `diner-service`, `table-service`, `waiter-service` y `menu-service`.
* `kitchen-service` se relaciona con `order-service`.

Esta comunicación se realiza mediante clientes HTTP, permitiendo que cada microservicio mantenga su propia responsabilidad pero pueda colaborar con los demás cuando sea necesario.

En Docker, esta comunicación se realiza usando nombres de contenedores dentro de la red de Docker Compose.

Ejemplo:

```txt
http://diner-service:8081
http://table-service:8083
http://menu-service:8086
```

---

## Estructura general del proyecto

```txt
nota3
├── docker-compose.yml
├── restaurante
│   └── restaurante
│       └── api-gateway
├── tbrm.restaurante.auth
├── tbrm.restaurante.diner
├── tbrm.restaurante.reservation
├── tbrm.restaurante.table
├── tbrm.restaurante.waiter
├── tbrm.restaurante.menu
├── tbrm.restaurante.order
├── tbrm.restaurante.kitchen
├── tbrm.restaurante.inventory
└── tbrm.restaurante.notification
```

---

## Orden recomendado para pruebas manuales

Una vez levantado el sistema con Docker Compose, se recomienda probar:

1. `GET http://localhost:8080/api/v1/tables`
2. `GET http://localhost:8080/api/v1/diners`
3. `GET http://localhost:8080/api/v1/menu-items`
4. Crear registros base:

   * Mesa
   * Comensal
   * Garzón
   * Producto de menú
5. Crear una reserva
6. Crear un pedido
7. Enviar el pedido a cocina
8. Revisar notificaciones o inventario según corresponda

---

## Problemas comunes

### Error: `no main manifest attribute`

Este error ocurre cuando el `.jar` generado no es ejecutable como aplicación Spring Boot.

Solución:

```powershell
.\mvnw.cmd clean package spring-boot:repackage -DskipTests
docker build -t nombre-del-servicio .
```

---

### Error: `Communications link failure`

Este error ocurre cuando un microservicio intenta conectarse a MySQL antes de que la base de datos esté lista.

Solución aplicada:

* Uso de `healthcheck` en los contenedores MySQL.
* Uso de `depends_on` con `condition: service_healthy`.

---

### Error por usar `localhost` dentro de Docker

Dentro de Docker, `localhost` hace referencia al propio contenedor, no a los otros servicios.

Por eso, en el Gateway y en la comunicación entre microservicios se deben usar nombres de contenedores:

```txt
http://table-service:8083
```

en lugar de:

```txt
http://localhost:8083
```

---

### Error 403 en Swagger de auth-service

El microservicio de autenticación posee configuración de seguridad. Para permitir Swagger, se agregaron como rutas públicas:

```txt
/swagger-ui.html
/swagger-ui/**
/v3/api-docs/**
/v3/api-docs.yaml
```

---

## Estado final del proyecto

El sistema cuenta con:

* Arquitectura basada en microservicios
* API Gateway funcional
* Bases de datos separadas por microservicio
* Migraciones automáticas con Flyway
* Documentación Swagger
* Logging
* Pruebas unitarias
* Dockerfile por microservicio
* Docker Compose general
* Comunicación entre servicios mediante nombres de contenedores
* Sistema ejecutándose de forma completa en Docker

---

## Integrantes

* Tomás Rojas
