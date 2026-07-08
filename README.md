# EventHub — Backend API

Backend de **EventHub**, la plataforma de agenda cultural de la municipalidad: categorías culturales, búsqueda de eventos con filtros y agenda personal. Construido con Spring Boot 3.4 + JWT + H2 en memoria.

> **Ojo:** en este backend las rutas **no llevan el prefijo `/api`** (estilo del laboratorio Fly Away): `/auth/login`, `/users/register`, `/events/search`, etc.

---

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

> No requiere variables de entorno ni base de datos externa. La base de datos H2 se crea en memoria al arrancar y se destruye al apagar.

El servidor corre en `http://localhost:8080`.

### Documentación de la API (Swagger)

📖 **Documentación en línea: [https://0wex.github.io/sim-eventhub-backend/](https://0wex.github.io/sim-eventhub-backend/)**

Ahí puedes consultar todos los endpoints y sus DTOs sin levantar nada. Con el backend corriendo también la tienes en `http://localhost:8080/swagger-ui.html` (JSON OpenAPI en `/v3/api-docs`). Usa el botón **Authorize** con el token de register/login para probar los endpoints protegidos.

### Consola H2 (opcional)

Disponible en `http://localhost:8080/h2-console`

| Campo    | Valor                    |
|----------|--------------------------|
| JDBC URL | `jdbc:h2:mem:eventhubdb` |
| Usuario  | `sa`                     |
| Password | *(vacío)*                |

---

## Datos precargados

Al arrancar, el backend carga automáticamente **8 categorías culturales** con **5 eventos cada una** (40 eventos, con fechas entre `2026-08-08` y `2026-09-12`):

| ID | Categoría   | Eventos (primeros 2)                                                            |
|----|-------------|---------------------------------------------------------------------------------|
| 1  | Música      | Concierto de la Orquesta Sinfónica Municipal, Festival de Rock Emergente        |
| 2  | Teatro      | La Casa de Bernarda Alba, Función de Títeres: El Zorro y el Cuy                 |
| 3  | Danza       | Gala de Ballet Municipal, Concurso de Marinera                                  |
| 4  | Cine        | Cine bajo las Estrellas: Clásicos Peruanos, Ciclo de Cine Documental            |
| 5  | Literatura  | Feria del Libro Municipal, Recital de Poesía Joven                              |
| 6  | Arte        | Exposición: Miradas de mi Distrito, Muestra de Fotografía Urbana                |
| 7  | Gastronomía | Feria Gastronómica: Sabores de mi Tierra, Festival del Pan Artesanal            |
| 8  | Talleres    | Taller de Guitarra para Principiantes, Taller de Pintura al Aire Libre          |

---

## Autenticación

El backend usa **JWT (Bearer Token)**. Los endpoints protegidos requieren el header:

```
Authorization: Bearer <token>
```

El token se obtiene al registrarse o iniciar sesión.

---

## Resumen de endpoints

| Método | Ruta              | Acceso      | Descripción                              |
|--------|-------------------|-------------|-------------------------------------------|
| POST   | `/users/register` | Público     | Registrar nuevo usuario                   |
| POST   | `/auth/login`     | Público     | Iniciar sesión y obtener token            |
| GET    | `/categories`     | Público     | Listar las 8 categorías culturales        |
| GET    | `/events/search`  | Público     | Buscar eventos (filtros opcionales)       |
| GET    | `/events/{id}`    | Público     | Detalle de un evento                      |
| POST   | `/users/agenda`   | Autenticado | Agregar evento a Mi Agenda                |
| DELETE | `/users/agenda`   | Autenticado | Remover evento de Mi Agenda               |

---

## Errores comunes

| Código | Causa                                              |
|--------|----------------------------------------------------|
| `400`  | Regla de negocio: email en uso, credenciales inválidas, evento no encontrado |
| `401`  | Usuario no encontrado                              |
| `403`  | Endpoint protegido sin token o token inválido      |
| `500`  | Error interno del servidor                         |
