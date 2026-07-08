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

## Endpoints

### Autenticación y Registro

#### `POST /users/register` — Registrar usuario

**Acceso:** público
**Content-Type:** `application/json`

**Body:**
```json
{
  "email": "vecino@municipalidad.gob.pe",
  "password": "miPassword123",
  "name": "María García"
}
```

| Campo      | Tipo   | Requerido | Descripción                  |
|------------|--------|-----------|------------------------------|
| `email`    | String | Sí        | Correo electrónico válido    |
| `password` | String | Sí        | Contraseña del usuario       |
| `name`     | String | Sí        | Nombre completo del usuario  |

**Respuesta exitosa (`200 OK`):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Error (`400`):** correo ya registrado → `{"error": "Email already in use"}`

---

#### `POST /auth/login` — Iniciar sesión

**Acceso:** público
**Content-Type:** `application/json`

**Body:**
```json
{
  "email": "vecino@municipalidad.gob.pe",
  "password": "miPassword123"
}
```

**Respuesta exitosa (`200 OK`):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Error (`400`):** credenciales inválidas → `{"error": "Invalid credentials"}`

---

### Categorías y Eventos

#### `GET /categories` — Listar todas las categorías

**Acceso:** público (no requiere token)

**Respuesta exitosa (`200 OK`):**
```json
[
  {
    "id": 1,
    "name": "Música",
    "description": "Conciertos y recitales en espacios públicos",
    "imageUrl": "https://via.placeholder.com/300"
  },
  ...
]
```

---

#### `GET /events/search` — Buscar eventos con filtros

**Acceso:** público (no requiere token)
**Query params (todos opcionales y combinables):**

| Param        | Tipo   | Descripción                                              |
|--------------|--------|-----------------------------------------------------------|
| `title`      | String | Busca por título (contiene, sin distinguir mayúsculas)    |
| `categoryId` | Long   | Filtra por categoría                                      |
| `from`       | String | Fecha mínima de realización, formato `YYYY-MM-DD` (inclusive) |
| `to`         | String | Fecha máxima de realización, formato `YYYY-MM-DD` (inclusive) |

Sin parámetros devuelve los 40 eventos.

**Ejemplos:**
- `GET /events/search?categoryId=1`
- `GET /events/search?from=2026-08-10&to=2026-08-20`
- `GET /events/search?title=taller&from=2026-09-01`

**Respuesta exitosa (`200 OK`):**
```json
[
  {
    "id": 1,
    "title": "Concierto de la Orquesta Sinfónica Municipal",
    "date": "2026-08-08",
    "location": "Teatro Municipal",
    "categoryId": 1
  },
  ...
]
```

---

#### `GET /events/{id}` — Detalle de un evento

**Acceso:** público (no requiere token)
**Path param:** `id` — ID del evento (Long)

**Ejemplo:** `GET /events/3`

**Respuesta exitosa (`200 OK`):**
```json
{
  "id": 3,
  "title": "Noche de Jazz y Bolero",
  "date": "2026-08-22",
  "location": "Plaza de Armas",
  "description": "Velada al aire libre con ensambles de jazz y trío de boleros.",
  "categoryId": 1,
  "categoryName": "Música"
}
```

**Error (`400`):** si el evento no existe → `{"error": "Event not found"}`

---

### Mi Agenda

> Los endpoints de agenda requieren el header `Authorization: Bearer <token>`.

#### `POST /users/agenda` — Agregar evento a Mi Agenda

**Acceso:** autenticado
**Content-Type:** `application/json`

**Body:**
```json
{
  "eventId": 1
}
```

**Respuesta exitosa (`200 OK`):** sin cuerpo

---

#### `DELETE /users/agenda` — Remover evento de Mi Agenda

**Acceso:** autenticado
**Content-Type:** `application/json`

**Body:**
```json
{
  "eventId": 1
}
```

**Respuesta exitosa (`200 OK`):** sin cuerpo

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
