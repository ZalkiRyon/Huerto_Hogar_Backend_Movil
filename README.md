# 🌱 Huerto Hogar - Backend API

API REST desarrollada con **Spring Boot** para la plataforma de e-commerce móvil **Huerto Hogar**. Proporciona servicios completos de gestión de productos orgánicos, usuarios, categorías, órdenes, favoritos y autenticación para una aplicación Android nativa.

## 📋 Tabla de Contenidos

- [Descripción General](#-descripción-general)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Endpoints API](#-endpoints-api)
- [Base de Datos](#-base-de-datos)
- [Cloudinary](#-cloudinary---gestión-de-imágenes)
- [Seguridad](#-seguridad-y-autenticación)
- [Testing](#-testing)
- [Documentación](#-documentación-api)

---

## 🎯 Descripción General

Backend robusto para marketplace móvil de productos orgánicos y frescos, con arquitectura RESTful completa que soporta:

- 🛍️ **CRUD completo** de productos, usuarios y órdenes
- ❤️ **Sistema de favoritos** persistente
- 📦 **Gestión de órdenes** con estados y snapshots históricos
- 📸 **Integración con Cloudinary** para imágenes
- 🔒 **Soft delete** para productos y usuarios
- 🔄 **Validación en tiempo real** de productos activos

### Roles de Usuario

- **👨‍💼 Admin**: Control total del sistema
- **👤 Cliente**: Compras y gestión de favoritos

---

## ✨ Características

### Funcionalidades Principales

- ✅ **Autenticación simple** 
- ✅ **CRUD de Productos** con categorías
- ✅ **Gestión de Usuarios** con roles
- ✅ **Sistema de Favoritos** con validación de productos activos
- ✅ **Órdenes con Snapshots** (precios/nombres históricos)
- ✅ **Costo de Envío Fijo** ($3,000)
- ✅ **Soft Delete** (borrado lógico)
- ✅ **Imágenes en Cloudinary** (CDN externo)
- ✅ **Validación de Datos** (emails @duocuc.cl, RUN chileno)
- ✅ **CORS Configurado** para cliente Android

---

## 🛠️ Tecnologías

### Core

- **Spring Boot** 3.3.5 - Framework principal
- **Java** 21 - Lenguaje de programación
- **Spring Data JPA** - ORM y persistencia
- **Hibernate** - Implementación JPA
- **MySQL** 8.0+ - Base de datos relacional

### Dependencias Clave

- **Spring Web** - RESTful API
- **Spring Validation** - Validación de DTOs
- **Lombok** - Reducción de boilerplate
- **Springdoc OpenAPI** 2.6.0 - Documentación Swagger
- **Cloudinary** 1.33.0 - Gestión de imágenes CDN
- **Maven** - Gestión de dependencias

### Testing

- **JUnit 5** - Framework de testing
- **Spring Boot Test** - Testing integrado
- **H2 Database** - Base de datos en memoria para tests

---

## 🏗️ Arquitectura

### Patrón MVC con Capas

```
┌─────────────────────────────────────────────────────┐
│              Controller Layer                       │
│  ┌─────────────────────────────────────────────┐    │
│  │  REST Controllers (@RestController)         │    │
│  │  - ProductoController                       │    │
│  │  - UsuarioController                        │    │
│  │  - OrdenController                          │    │
│  │  - FavoritoController, etc.                 │    │
│  └────────────────┬────────────────────────────┘    │
└───────────────────┼─────────────────────────────────┘
                    │ calls Service
                    ▼
┌─────────────────────────────────────────────────────┐
│              Service Layer                          │
│  ┌─────────────────────────────────────────────┐    │
│  │  Business Logic (@Service)                  │    │
│  │  - ProductoService                          │    │
│  │  - UsuarioService                           │    │
│  │  - OrdenService                             │    │
│  │  - CloudinaryService (image upload)         │    │
│  └────────────────┬────────────────────────────┘    │
└───────────────────┼─────────────────────────────────┘
                    │ uses Repository
                    ▼
┌─────────────────────────────────────────────────────┐
│           Repository Layer (JPA)                    │
│  ┌─────────────────────────────────────────────┐    │
│  │  Data Access (@Repository)                  │    │
│  │  - ProductoRepository                       │    │
│  │  - UsuarioRepository                        │    │
│  │  - OrdenRepository                          │    │
│  │  - FavoritoRepository                       │    │
│  └────────────────┬────────────────────────────┘    │
└───────────────────┼─────────────────────────────────┘
                    │ JDBC/Hibernate
                    ▼
            ┌───────────────┐
            │  MySQL 8.0+   │
            │   hh_db       │
            └───────────────┘
```

### Flujo de Petición

1. **Cliente Android** envía HTTP Request
2. **Controller** recibe y valida DTO
3. **Service** ejecuta lógica de negocio
4. **Repository** accede a base de datos (JPA)
5. **Response** fluye de vuelta como JSON

---

## 📋 Requisitos

### Software Necesario

- ☕ **Java JDK** 21 o superior
- 📦 **Maven** 3.8+
- 🐬 **MySQL** 8.0+
- 🌐 Puerto **8080** disponible (backend server)
- 🔌 Puerto **3306** disponible (MySQL)
- ☁️ **Cuenta Cloudinary** capa gratuita 

### Cliente

- 📱 **Android App** ([Frontend Repository](https://github.com/ZalkiRyon/Huerto_Hogar_Frontend_Movil))

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/ZalkiRyon/Huerto_Hogar_Backend_Movil.git
cd Huerto_Hogar_Backend_Movil
```

### 2. Configurar Base de Datos

#### Opción A: Importar Script SQL

```bash
mysql -u root -p < bbdd.sql
```

#### Opción B: Manual

```sql
-- Crear base de datos
CREATE DATABASE hh_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Importar datos
USE hh_db;
SOURCE bbdd.sql;
```

El script `bbdd.sql` crea:

| Tabla | Descripción | Características |
|-------|-------------|-----------------|
| `roles` | Roles de sistema | admin, cliente |
| `categorias` | Categorías de productos | Con prefijos (FR, VR, PO, PL) |
| `usuarios` | Usuarios del sistema | Soft delete con `activo` |
| `productos` | Catálogo de productos | Soft delete, `imagen_url` Cloudinary |
| `favoritos` | Relación usuario-producto | UNIQUE constraint, CASCADE |
| `ordenes` | Pedidos con snapshots | Datos históricos del cliente |
| `detalles_orden` | Items de orden | Snapshots de producto y precio |
| `estados` | Estados de órdenes | Enviado, Pendiente, Cancelado, Procesando |
| `blogs` | Artículos informativos | Con imágenes y autores |

### 2. Configurar Cloudinary (Obligatorio)

El proyecto usa **Cloudinary** como CDN para almacenar imágenes de productos. Sigue estos pasos:

1. **Crear cuenta gratuita en Cloudinary**:
   - Visita https://cloudinary.com
   - Crea una cuenta gratuita (25GB storage/bandwidth)
   
2. **Obtener credenciales**:
   - En el Dashboard, copia: **Cloud Name**, **API Key**, **API Secret**

3. **Configurar credenciales locales**:
   ```bash
   # Copia el archivo template
   cp src/main/resources/application-local.properties.template src/main/resources/application-local.properties
   
   # Edita application-local.properties con tus credenciales reales
   ```

4. **Editar `application-local.properties`** (este archivo está en `.gitignore`, NUNCA se sube a GitHub):
   ```properties
   cloudinary.cloud-name=tu_cloud_name
   cloudinary.api-key=tu_api_key
   cloudinary.api-secret=tu_api_secret
   ```

5. **Verificar profile activo**: El archivo `application.properties` ya tiene configurado:
   ```properties
   spring.profiles.active=local
   ```
   Esto carga automáticamente `application-local.properties` con tus credenciales.

⚠️ **IMPORTANTE**: 
- `application-local.properties` contiene credenciales REALES y está gitignoreado.
- `application-local.properties.template` es solo una plantilla segura para commits.
- NUNCA commitees `application-local.properties` ni expongas tus credenciales.
- El profile `local` debe estar activo para cargar las credenciales.

### 3. Configurar Conexión MySQL

Edita `src/main/resources/application.properties`:

```properties
# Conexión MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/hh_db?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

### 4. Instalar Dependencias

```bash
# Limpiar y compilar
./mvnw clean install

# O sin ejecutar tests
./mvnw clean install -DskipTests
```

### 5. Ejecutar Servidor

```bash
./mvnw spring-boot:run
```

✅ Servidor corriendo en: `http://localhost:8080`

---

## ⚙️ Configuración

### Variables de Entorno

#### `application.properties` (principal)

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/hh_db?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Profile activo (carga application-local.properties)
spring.profiles.active=local

# Multipart (tamaño máximo de archivos)
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

#### `application-local.properties` (credenciales Cloudinary)

```properties
# Cloudinary Configuration (GITIGNORED)
cloudinary.cloud-name=tu_cloud_name
cloudinary.api-key=tu_api_key
cloudinary.api-secret=tu_api_secret
```

---

## 🗂️ Estructura del Proyecto

```
src/main/java/com/backend/huertohogar/
│
├── config/
│   ├── CorsConfig.java              # Configuración CORS
│   ├── CloudinaryConfig.java        # Bean de Cloudinary
│   └── OpenApiConfig.java           # Swagger UI config
│
├── controller/                      # REST Endpoints
│   ├── AuthController.java          # Login
│   ├── ProductoController.java      # CRUD Productos
│   ├── UsuarioController.java       # CRUD Usuarios
│   ├── OrdenController.java         # CRUD Órdenes
│   ├── FavoritoController.java      # Sistema de Favoritos
│   ├── CategoriaController.java     # Categorías
│   └── BlogController.java          # Blogs
│
├── dto/                             # Data Transfer Objects
│   ├── request/
│   │   ├── LoginRequestDTO.java
│   │   ├── ProductoRequestDTO.java
│   │   ├── UsuarioRequestDTO.java
│   │   └── OrdenRequestDTO.java
│   │
│   └── response/
│       ├── ProductoResponseDTO.java
│       ├── UsuarioResponseDTO.java
│       ├── FavoritoResponseDTO.java
│       └── OrdenResponseDTO.java
│
├── model/                           # Entidades JPA
│   ├── Producto.java                # @Entity productos
│   ├── Usuario.java                 # @Entity usuarios
│   ├── Orden.java                   # @Entity ordenes
│   ├── DetalleOrden.java            # @Entity detalles_orden
│   ├── Favorito.java                # @Entity favoritos
│   ├── Categoria.java               # @Entity categorias
│   ├── Estado.java                  # @Entity estados
│   ├── Role.java                    # @Entity roles
│   └── Blog.java                    # @Entity blogs
│
├── repository/                      # JPA Repositories
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   ├── OrdenRepository.java
│   ├── FavoritoRepository.java
│   ├── CategoriaRepository.java
│   └── ... (otros)
│
├── service/                         # Lógica de Negocio
│   ├── impl/
│   │   ├── ProductoServiceImpl.java
│   │   ├── UsuarioServiceImpl.java
│   │   ├── OrdenServiceImpl.java
│   │   ├── FavoritoServiceImpl.java
│   │   └── CloudinaryService.java   # Upload a Cloudinary
│   │
│   └── (interfaces)
│
├── exception/                       # Manejo de Excepciones
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
│
└── HuertoHogarApplication.java      # Main class

src/main/resources/
├── application.properties           # Config principal
├── application-local.properties.template  # Template Cloudinary
└── bbdd.sql                         # Script de base de datos

src/test/
└── java/com/backend/huertohogar/
    └── (tests unitarios)
```

---

## 🌐 Endpoints API

### Base URL

```
http://localhost:8080/api/
```

### **1. Autenticación** (`AuthController`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| POST | `/auth/login` | Login de usuario | `{ email, password }` |
| POST | `/auth/register` | Registro público | `{ email, password, nombre, ... }` |

### **2. Productos** (`ProductoController`)

| Método | Endpoint | Descripción | Permisos |
|--------|----------|-------------|----------|
| GET | `/productos` | Todos los productos | Público |
| GET | `/productos/activos` | Solo productos activos | Público |
| GET | `/productos/inactivos` | Solo productos inactivos | Admin |
| GET | `/productos/categoria/{id}` | Productos por categoría | Público |
| GET | `/productos/{id}` | Detalle de producto | Público |
| POST | `/productos` | Crear producto | Admin |
| PUT | `/productos/{id}` | Actualizar producto | Admin |
| PUT | `/productos/{id}/activar` | Reactivar producto | Admin |
| PUT | `/productos/{id}/desactivar` | Desactivar producto (soft delete) | Admin |
| POST | `/productos/{id}/imagen` | Subir imagen (Cloudinary) | Admin |

**Ejemplo Request - Crear Producto:**
```json
POST /api/productos
{
  "nombre": "FR001 - Manzanas Fuji",
  "categoriaId": 1,
  "precio": 1200,
  "stock": 150,
  "descripcion": "Manzanas frescas del valle",
  "activo": true
}
```

**Ejemplo Response:**
```json
{
  "id": 1,
  "nombre": "FR001 - Manzanas Fuji",
  "categoria": "Frutas frescas",
  "precio": 1200,
  "stock": 150,
  "descripcion": "Manzanas frescas del valle",
  "imagenUrl": "https://res.cloudinary.com/.../manzana.jpg",
  "activo": true
}
```

### **3. Usuarios** (`UsuarioController`)

| Método | Endpoint | Descripción | Permisos |
|--------|----------|-------------|----------|
| GET | `/usuarios` | Todos los usuarios | Admin |
| GET | `/usuarios/activos` | Solo usuarios activos | Admin |
| GET | `/usuarios/inactivos` | Solo usuarios inactivos | Admin |
| GET | `/usuarios/{id}` | Detalle de usuario | Usuario propio / Admin |
| POST | `/usuarios` | Crear usuario | Admin |
| PUT | `/usuarios/{id}` | Actualizar usuario | Usuario propio / Admin |
| PUT | `/usuarios/{id}/activar` | Reactivar usuario | Admin |
| PUT | `/usuarios/{id}/desactivar` | Desactivar usuario | Admin |
| POST | `/usuarios/{id}/imagen` | Subir foto de perfil | Usuario propio |

### **4. Favoritos** (`FavoritoController`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| GET | `/favoritos/usuario/{usuarioId}` | Favoritos del usuario | - |
| POST | `/favoritos` | Agregar a favoritos | `{ usuarioId, productoId }` |
| DELETE | `/favoritos` | Remover de favoritos | `{ usuarioId, productoId }` |

**Características Especiales:**
- ✅ Retorna datos actualizados del producto (precio, nombre, stock)
- ✅ Filtra automáticamente productos inactivos
- ✅ Validación de productos activos en backend

### **5. Órdenes** (`OrdenController`)

| Método | Endpoint | Descripción | Permisos |
|--------|----------|-------------|----------|
| GET | `/ordenes` | Todas las órdenes | Admin |
| GET | `/ordenes/usuario/{id}` | Órdenes de un usuario | Usuario propio / Admin |
| GET | `/ordenes/{id}` | Detalle de orden | Usuario propio / Admin |
| POST | `/ordenes` | Crear orden | Cliente autenticado |
| PUT | `/ordenes/{id}/estado` | Actualizar estado | Admin |
| DELETE | `/ordenes/{id}` | Eliminar orden | Admin |

**Request - Crear Orden:**
```json
POST /api/ordenes
{
  "clienteId": 4,
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 2,
      "precioUnitario": 1200
    }
  ],
  "direccionEnvio": "Av. Providencia 123",
  "regionEnvio": "region-metropolitana",
  "comunaEnvio": "providencia",
  "telefonoContacto": "912345678",
  "costoEnvio": 3000,
  "estadoId": 2,
  "comentario": ""
}
```

**Características Especiales:**
- 📸 **Snapshots históricos**: Guarda nombre y precio del producto al momento de compra
- 🚚 **Costo de envío fijo**: $3,000 por orden
- 📊 **Estados**: Pendiente (2), Procesando (4), Enviado (1), Cancelado (3)

### **6. Categorías** (`CategoriaController`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/categorias` | Todas las categorías |

**Categorías Disponibles:**
- **Frutas frescas** (FR)
- **Verduras orgánicas** (VR)
- **Productos orgánicos** (PO)
- **Productos lácteos** (PL)

### **7. Blogs** (`BlogController`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/blogs` | Todos los blogs |
| GET | `/blogs/{id}` | Detalle de blog |

---

## 📚 Documentación API

### Swagger UI

Una vez iniciado el servidor:

```
http://localhost:8080/swagger-ui/index.html
```

**Características:**
- 📖 Documentación interactiva completa
- 🧪 Prueba de endpoints en vivo
- 📝 Schemas de DTOs
- 🔍 Ejemplos de requests/responses

---

## 🗄️ Base de Datos

### Diseño de Tablas

#### **1. Productos - Soft Delete**
```sql
CREATE TABLE productos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL UNIQUE,
  categoria_id INT NOT NULL,
  precio INT NOT NULL DEFAULT 0,
  stock INT NOT NULL DEFAULT 0,
  descripcion TEXT,
  imagen_url VARCHAR(500),  -- URL de Cloudinary
  activo BOOLEAN NOT NULL DEFAULT TRUE,  -- Soft delete
  FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);
```

**Soft Delete:**
- `activo = true` → Producto visible en catálogo
- `activo = false` → Producto "eliminado" (no se muestra pero se mantiene en BD)

#### **2. Favoritos - Relación Pura**
```sql
CREATE TABLE favoritos (
  id INT PRIMARY KEY AUTO_INCREMENT,
  usuario_id INT NOT NULL,
  producto_id INT NOT NULL,
  UNIQUE KEY uk_usuario_producto (usuario_id, producto_id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT,
  FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE RESTRICT
);
```

**Características:**
- ✅ Almacena solo la relación (usuario-producto)
- ✅ NO guarda snapshots de datos
- ✅ Los datos del producto se obtienen mediante JOIN
- ✅ Siempre muestra información actualizada
- ✅ Filtra automáticamente productos inactivos

**Query de Favoritos:**
```java
@Query("SELECT f FROM Favorito f JOIN f.usuario u JOIN f.producto p " +
       "WHERE u.id = :usuarioId AND u.activo = true AND p.activo = true")
List<Favorito> findActiveByUsuarioId(@Param("usuarioId") Integer usuarioId);
```

#### **3. Órdenes - Snapshots Históricos**
```sql
CREATE TABLE ordenes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  numero_orden VARCHAR(50) NOT NULL UNIQUE,
  cliente_id INT NULL,  -- Puede ser NULL si usuario se elimina
  
  -- SNAPSHOTS (datos históricos)
  nombre_cliente_snapshot VARCHAR(200) NOT NULL,
  email_cliente_snapshot VARCHAR(100) NOT NULL,
  direccion_envio VARCHAR(255) NOT NULL,
  region_envio VARCHAR(100) NOT NULL,
  comuna_envio VARCHAR(100) NOT NULL,
  
  fecha DATE,
  estado_id INT NOT NULL,
  monto_total INT NOT NULL DEFAULT 0,
  costo_envio INT NOT NULL DEFAULT 0,
  comentario TEXT,
  
  FOREIGN KEY (cliente_id) REFERENCES usuarios(id) ON DELETE SET NULL,
  FOREIGN KEY (estado_id) REFERENCES estados(id)
);

CREATE TABLE detalles_orden (
  id INT PRIMARY KEY AUTO_INCREMENT,
  orden_id INT NOT NULL,
  producto_id INT NULL,  -- Referencia opcional
  
  -- SNAPSHOTS (lo que realmente se vendió)
  nombre_producto_snapshot VARCHAR(150) NOT NULL,
  precio_unitario_snapshot INT NOT NULL,
  
  cantidad INT NOT NULL DEFAULT 1,
  subtotal INT NOT NULL,
  
  FOREIGN KEY (orden_id) REFERENCES ordenes(id) ON DELETE CASCADE,
  FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE SET NULL
);
```

**¿Por qué Snapshots?**
- 📸 Mantienen historial exacto de lo comprado
- 💰 Si el precio cambia, las órdenes antiguas mantienen el precio original
- 📝 Si el nombre cambia, las órdenes reflejan el nombre al momento de compra
- 🗑️ Si el producto se elimina, la orden mantiene el registro

### Relaciones

```
usuarios (1) ──────────< (N) ordenes
usuarios (1) ──────────< (N) favoritos >─────────── (1) productos
categorias (1) ────────< (N) productos
ordenes (1) ───────────< (N) detalles_orden >────── (1) productos
estados (1) ───────────< (N) ordenes
```

---

## 🔒 Seguridad y Autenticación

### Sistema de Autenticación

**Implementación Simplificada (Sin JWT):**
- ✅ Login básico con validación de credenciales
- ✅ Control de acceso por roles
- ✅ CORS configurado para cliente Android

### Validaciones Implementadas

| Campo | Validación | Ejemplo |
|-------|------------|---------|
| **Email** | `@duocuc.cl` o `@profesor.duoc.cl` | `alumno@duocuc.cl` |
| **RUN** | Formato chileno `##.###.###-K` | `12.345.678-9` |
| **Contraseña** | Mínimo 4 caracteres | `cliente123` |
| **Teléfono** | Opcional, mínimo 9 dígitos | `912345678` |
| **Dirección** | Opcional, mínimo 5 caracteres | `Av. Providencia 123` |

### Roles y Permisos

| Rol | Permisos |
|-----|----------|
| **👨‍💼 Admin** | ✅ Gestión completa de productos, usuarios y órdenes<br>✅ Activar/desactivar productos y usuarios<br>✅ Ver estadísticas y reportes |
| **👤 Cliente** | ✅ Ver catálogo de productos<br>✅ Agregar a favoritos<br>✅ Crear órdenes<br>✅ Ver historial de compras<br>✅ Actualizar perfil |

### CORS Configuration

**Orígenes Permitidos:**
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.addAllowedOrigin("*");  // Permite desde cualquier origen
        config.addAllowedMethod("*");  // GET, POST, PUT, DELETE, OPTIONS
        config.addAllowedHeader("*");
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
```

**Cliente Android:**
- Base URL: `http://10.0.2.2:8080/api/` (emulador)
- Base URL: `http://192.168.1.X:8080/api/` (dispositivo físico)

---

## ☁️ Cloudinary - Gestión de Imágenes

### ¿Por qué Cloudinary?

El backend usa **Cloudinary** como CDN externo para almacenar todas las imágenes:

| Ventaja | Descripción |
|---------|-------------|
| 🌐 **CDN Global** | Entrega rápida desde servidores distribuidos |
| 💾 **Sin carga en servidor** | Imágenes no ocupan espacio en backend |
| 🔄 **Optimización automática** | Compresión y redimensionamiento |
| 💰 **Plan gratuito** | 25GB storage + 25GB bandwidth/mes |
| 📱 **URLs públicas** | Accesibles desde Android, Web, iOS |
| 🔒 **Seguro** | Credenciales en backend, no expuestas |

### Flujo de Carga

```
1. 📱 Android App selecciona imagen (galería/cámara)
   ↓
2. 📤 Envía MultipartFile al backend
   POST /api/productos/{id}/imagen
   ↓
3. 🖥️ Backend recibe archivo
   CloudinaryService.uploadProductImage(file)
   ↓
4. ☁️ Backend sube a Cloudinary API
   cloudinary.uploader().upload(file.bytes, options)
   ↓
5. 🔗 Cloudinary retorna URL pública
   https://res.cloudinary.com/.../manzana.jpg
   ↓
6. 💾 Backend guarda URL en columna imagen_url
   producto.setImagenUrl(cloudinaryUrl)
   ↓
7. ✅ Backend retorna URL al frontend
   { "imageUrl": "https://..." }
   ↓
8. 🖼️ Android carga imagen con Coil
   AsyncImage(model = imageUrl)
```

### Implementación Backend

**CloudinaryService.java:**
```java
@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    
    public String uploadProductImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", "productos",
                "resource_type", "image"
            )
        );
        return uploadResult.get("secure_url").toString();
    }
    
    public String uploadProfilePicture(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", "usuarios",
                "resource_type", "image"
            )
        );
        return uploadResult.get("secure_url").toString();
    }
}
```

**ProductoController.java:**
```java
@PostMapping("/{id}/imagen")
public ResponseEntity<String> uploadProductImage(
    @PathVariable Long id,
    @RequestPart("file") MultipartFile file
) {
    String imageUrl = cloudinaryService.uploadProductImage(file);
    productoService.updateProductImage(id, imageUrl);
    return ResponseEntity.ok().body("\"" + imageUrl + "\"");
}
```

### Configuración

**application-local.properties:**
```properties
cloudinary.cloud-name=tu_cloud_name
cloudinary.api-key=tu_api_key
cloudinary.api-secret=tu_api_secret
```

**CloudinaryConfig.java:**
```java
@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    
    @Value("${cloudinary.api-key}")
    private String apiKey;
    
    @Value("${cloudinary.api-secret}")
    private String apiSecret;
    
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }
}
```

### Estructura de URLs

**Productos:**
```
https://res.cloudinary.com/{cloud_name}/image/upload/v{timestamp}/productos/{filename}.jpg
```

**Usuarios:**
```
https://res.cloudinary.com/{cloud_name}/image/upload/v{timestamp}/usuarios/{filename}.jpg
```

### Seguridad

- ✅ Credenciales en `application-local.properties` (gitignored)
- ✅ Frontend nunca conoce las credenciales
- ✅ Todas las subidas pasan por backend
- ⚠️ NUNCA commitear `application-local.properties`

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Con cobertura
./mvnw clean test jacoco:report

# Ver reporte
open target/site/jacoco/index.html
```

### Configuración de Tests

**application.properties (test):**
```properties
# H2 In-Memory Database para tests
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop

# Cloudinary Mock (sin credenciales reales)
cloudinary.cloud-name=test-cloud
cloudinary.api-key=test-key
cloudinary.api-secret=test-secret
```

### Tests Implementados

- ✅ Tests unitarios de servicios
- ✅ Tests de repositorios con H2
- ✅ Tests de controladores con MockMvc
- ✅ Validación de DTOs

---

## 📊 Datos de Prueba

El script `bbdd.sql` incluye datos iniciales para testing:

### Usuarios

| Email | Password | Rol | Datos |
|-------|----------|-----|-------|
| `admin@duocuc.cl` | `admin123` | Admin | Super Administrador - ID: 1 |
| `maria.gonzalez@duocuc.cl` | `admin456` | Admin | María José González - ID: 2 |
| `carlos.torres@profesor.duoc.cl` | `admin789` | Admin | Carlos Eduardo Torres - ID: 3 |
| `ana.martinez@duocuc.cl` | `cliente123` | Cliente | Ana María Martínez - ID: 4 ✨ |
| `pedro.ramirez@duocuc.cl` | `cliente456` | Cliente | Pedro Antonio Ramírez - ID: 5 |
| `lucia.fernandez@duocuc.cl` | `cliente789` | Cliente | Lucía Elena Fernández - ID: 6 |
| *(y 13 usuarios más)* | - | Cliente | IDs: 7-19 |

**✨ Usuario recomendado para testing:** Ana María (ID: 4)
- ✅ Cliente VIP con foto de perfil
- ✅ Tiene favoritos pre-cargados
- ✅ Historial de órdenes

### Productos

**10 productos con imágenes reales en Cloudinary:**

| ID | Nombre | Categoría | Precio | Stock | Imagen |
|----|--------|-----------|--------|-------|--------|
| 1 | FR001 - Manzanas Fuji | Frutas | $1,200 | 150 | ✅ Cloudinary |
| 2 | FR002 - Naranjas Valencia | Frutas | $1,000 | 200 | ✅ Cloudinary |
| 3 | FR003 - Plátanos Cavendish | Frutas | $800 | 250 | ✅ Cloudinary |
| 4 | VR001 - Zanahorias Orgánicas | Verduras | $900 | 100 | ✅ Cloudinary |
| 5 | VR002 - Espinacas Frescas | Verduras | $700 | 80 | ✅ Cloudinary |
| 6 | VR003 - Pimentones Tricolores | Verduras | $1,500 | 120 | ✅ Cloudinary |
| 7 | PO001 - Miel Orgánica | Orgánicos | $5,000 | 50 | ✅ Cloudinary |
| 8 | PO002 - Quinua Orgánica | Orgánicos | $3,000 | 70 | ✅ Cloudinary |
| 9 | PL001 - Leche Entera | Lácteos | $1,200 | 100 | ✅ Cloudinary |
| 10 | PL002 - Queso de Cabra | Lácteos | $5,000 | 100 | ✅ Cloudinary |

**Todas las imágenes apuntan a:** `https://res.cloudinary.com/dg7dcbcjn/image/upload/...`

### Categorías

1. **Frutas frescas** (FR)
2. **Verduras orgánicas** (VR)
3. **Productos orgánicos** (PO)
4. **Productos lácteos** (PL)

### Favoritos Pre-cargados

- **Ana María** (ID 4): Manzanas, Miel, Queso, Pimentones
- **Pedro** (ID 5): Naranjas, Zanahorias, Leche, Quinua
- **Lucía** (ID 6): Plátanos, Espinacas, Manzanas, Leche
- **Juan Carlos** (ID 10): Miel, Quinua, Espinacas, Naranjas
- **Carla** (ID 11): Manzanas, Naranjas, Plátanos, Queso

### Órdenes de Ejemplo

- 20 órdenes históricas con snapshots
- Estados variados (Enviado, Pendiente, Cancelado, Procesando)
- Montos entre $5,400 y $38,000
- Incluyen costo de envío variable (histórico)

---

## 🔧 Scripts Maven

```bash
# Limpiar proyecto
./mvnw clean

# Compilar sin tests
./mvnw clean install -DskipTests

# Compilar con tests
./mvnw clean install

# Solo tests
./mvnw test

# Ejecutar aplicación
./mvnw spring-boot:run

# Empaquetar JAR
./mvnw package

# Ver dependencias
./mvnw dependency:tree
```

---

## 🐛 Troubleshooting

### Error: Cannot connect to MySQL

```bash
# Verificar que MySQL esté corriendo
# Windows
net start MySQL80

# Linux/Mac
sudo systemctl start mysql
```

### Error: Cloudinary credentials invalid

```properties
# Verificar application-local.properties existe
# Copiar desde template
cp src/main/resources/application-local.properties.template \
   src/main/resources/application-local.properties

# Editar con credenciales reales de Cloudinary
```


### Error: Base de datos no existe

```sql
-- Crear manualmente
CREATE DATABASE hh_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Importar script
mysql -u root -p hh_db < bbdd.sql
```


## 👥 Autores

Desarrollado por:
- **Sebastián Valdivia** - [GitHub](https://github.com/ZalkiRyon)
- **Paula Frías** - [GitHub](https://github.com/paufriasest)

**Institución:** DUOC UC  
**Año:** 2025  
**Curso:** Desarrollo de Aplicaciones Móviles

---

## 🔗 Enlaces

- **Backend Repository:** [Huerto_Hogar_Backend_Movil](https://github.com/ZalkiRyon/Huerto_Hogar_Backend_Movil)
- **Frontend Repository:** [Huerto_Hogar_Frontend_Movil](https://github.com/ZalkiRyon/Huerto_Hogar_Frontend_Movil)

---

**Hecho usando Spring Boot y Java**

```
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠛⠛⠉⠙⠛⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠟⠉⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠘⠿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠛⢿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⣿⣟⣛⣻⡿⣿⣿⣿⣫⡿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠘⢿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣫⠴⠿⠛⡛⠣⠤⣤⡤⣠⣾⣿⣷⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢠⣀⠄⠄⠄⠘⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠈⣃⢌⣭⣬⣭⠻⠁⣴⠆⣿⣿⣿⣿⣷⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠹⣿⡖⣀⣀⣾⣯⡻⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⠃⠄⠄⢫⣬⣭⠛⢱⣾⣶⢤⣾⠙⣿⣿⣿⣿⣿⣦⣄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⣽⢸⣿⣿⣿⣿⣿⡝⣿
⣿⣿⣿⣿⣿⣿⣿⣿⡄⠄⠄⠈⠉⢉⠄⡟⣿⡏⣸⣿⣷⡜⢿⣿⣿⣿⣿⣿⣷⣶⣤⣀⠄⠄⠄⠄⠄⠄⢀⡐⣿⣿⡘⣿⣿⣿⣿⣿⣰⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣇⠄⠐⠄⠄⢸⡄⣷⡘⢰⣿⣿⣿⣿⣷⡹⢿⣿⣿⣿⣿⣿⣿⣿⣿⣟⣿⣿⣿⣾⣿⣧⢸⣿⣿⣮⣙⣛⣫⣴⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢰⠄⢀⠐⢷⣸⣿⣾⣿⣿⣿⣿⡏⣿⣷⣍⡛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡀⠄⣀⠚⢧⡁⣿⣿⣿⣿⣿⡇⡫⢅⢰⡶⣀⢰⡦⡈⢉⡛⡛⡛⣛⠛⡛⡉⠄⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⡿⠟⠛⠋⠉⠁⠄⠄⢀⠄⡁⣿⣿⣿⣿⣿⠃⠐⠛⠈⠐⠻⢬⣴⣷⣬⣴⠃⠐⠉⢀⡀⣡⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⡀⠄⠄⠄⠄⠄⠄⠄⡀⠄⠄⣿⣿⣿⣿⣿⢰⠄⠄⠄⠄⢲⣾⣿⣿⣿⣿⠄⠄⠄⠄⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣧⡀⠄⠄⠄⠄⠄⠄⠯⠈⠄⢿⣿⣿⡿⡿⢸⠄⠄⠄⠄⣸⣿⣿⣿⣿⣿⡀⠄⠄⠄⣧⡙⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣄⠄⠄⠄⠄⠄⠑⠠⠈⢘⢻⣿⡇⠁⢸⣧⡀⠄⣠⣿⣿⣿⣿⣿⣿⣷⣄⣀⣴⣿⡇⡟⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣤⣠⣤⣶⠄⠄⠈⠄⠙⢿⡇⠄⣸⣿⣿⣿⣿⠏⣼⣿⣿⣿⣿⣼⣿⣿⠟⠋⡀⠄⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠄⠄⠄⠄⠄⠄⡁⠈⠉⠉⠉⠛⠛⠃⠹⠻⠛⠋⠁⠉⠁⠄⡆⢀⠨⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠄⠄⢀⣀⡀⢀⣄⣀⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠐⡶⢾⣿⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣿⣿⣿⣗⠻⣿⣿⣿⣿⣷⣶⠄⠄⠄⡀⠄⢀⣸⣿⣮⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⠿⣟⣫⣥⣶⣶⣶⣦⣤⣭⣛⡿⣿⣿⣿⡿⠗⠄⠄⠉⠉⠛⠃⠄⢒⣛⣛⡂⠛⠉⢡⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⢛⣥⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣬⠛⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠘⣿⣿⣷⡄⠄⠄⠉⠛⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡿⢃⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣿⡄⢀⣤⣴⣶⣶⣶⣦⣀⠄⠄⢻⣿⣿⣿⡀⠄⠄⠄⠄⠄⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣝⢣⠘⠿⠟⠛⠛⠛⢛⠛⠄⠄⢸⣿⣿⣿⣧⠄⠄⠄⠄⣀⣀⣬⣭⢻⣿⣿⣿⣿⣿⣿⣿
⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠄⣶⢀⡀⠲⣶⣯⣹⣶⣰⣾⣿⣿⣿⣿⣶⣶⣿⣿⣿⣿⣿⣿⣷⡽⣿⣿⣿⣿⣿⣿
⣧⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⡅⣠⣤⡅⣤⣴⣬⡝⢫⣬⣥⣿⣿⣿⣿⣿⡿⠿⠿⠟⢛⣛⣻⣯⣥⣴⣿⣿⣿⣿⣿⣿
⣿⣷⡙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣴⣽⣷⣧⣿⣟⣿⡗⣰⣶⣶⣶⣶⣦⣀⠄⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣧⣤⣛⣛⣛⣛⣛⣛⣛⣛⣛⣋⣉⣉⣉⣉⣉⣉⣉⣉⣉⣥⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
```
