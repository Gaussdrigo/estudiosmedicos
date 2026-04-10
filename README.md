# 🏥 Sistema de Gestión de Estudios Médicos

Aplicación web moderna para la gestión integral de pacientes, consultas médicas y documentación clínica. Diseñada con arquitectura escalable y despliegue en la nube.

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)
- [Instalación Local](#instalación-local)
- [Despliegue en Render](#despliegue-en-render)
- [Configuración](#configuración)
- [Uso](#uso)

## 📖 Descripción

Sistema integral para la gestión de pacientes y estudios médicos. Permite:
- Registro y autenticación segura de usuarios
- Gestión completa de pacientes (creación, actualización, consultas)
- Carga y almacenamiento de imágenes médicas en la nube
- Generación automática de reportes PDF con códigos QR
- Visualización de consultas por paciente
- Descarga de documentación con trazabilidad

La aplicación utiliza **Spring Boot 3.5.6** con **Java 17**, **Thymeleaf** para plantillas y **PostgreSQL** para persistencia.

## ✨ Características

### Gestión de Pacientes
- **Crear pacientes**: Registro con información completa (nombre, email, teléfono, etc.)
- **Listado de pacientes**: Vista tabulada con búsqueda y filtros
- **Actualizar pacientes**: Edición de información personal
- **Imágenes médicas**: Carga múltiple de imagenes con almacenamiento en Cloudinary
- **Historial de consultas**: Registro detallado de cada consulta realizada

### Documentación
- **Generación de PDF**: Reportes automáticos con información de consultas
- **Códigos QR**: Inclusión de QR en documentos para trazabilidad
- **Descargas seguras**: Acceso controlado a documentos desde Cloudinary

### Autenticación y Seguridad
- **Login seguro**: Autenticación basada en Spring Security
- **Control de acceso**: Roles y permisos configurables
- **Admin automático**: Creación de usuario administrador inicial (opcional)
- **Sesiones**: Gestión segura de sesiones de usuario

## 🛠 Tecnologías

| Componente | Tecnología | Versión |
|------------|-----------|---------|
| **Framework** | Spring Boot | 3.5.6 |
| **Lenguaje** | Java | 17 |
| **Plantillas** | Thymeleaf | (Spring Boot default) |
| **Base de datos** | PostgreSQL | Latest |
| **ORM** | Spring Data JPA | (Spring Boot default) |
| **Seguridad** | Spring Security | (Spring Boot default) |
| **Almacenamiento** | Cloudinary | v1.39.0 |
| **Reportes** | OpenPDF | 1.3.30 |
| **QR** | ZXing | 3.5.1 |
| **Herramientas** | Lombok | (Spring Boot default) |
| **Contenedorización** | Docker | Latest |

## 📁 Estructura del Proyecto

```
estudiosmedicos/
├── src/
│   ├── main/
│   │   ├── java/com/aplicacionestudiosmedicos/
│   │   │   ├── EstudiosmedicosApplication.java        # Punto de entrada
│   │   │   ├── config/                                 # Configuraciones
│   │   │   │   ├── CloudinaryConfig.java              # Setup Cloudinary
│   │   │   │   ├── SecurityConfig.java                # Spring Security
│   │   │   │   └── WebConfig.java                     # Configuración web
│   │   │   ├── controller/                             # Controladores REST
│   │   │   │   ├── HomeController.java                # Página principal
│   │   │   │   ├── LoginController.java               # Autenticación
│   │   │   │   ├── PacienteController.java            # Gestión de pacientes
│   │   │   │   └── ConsultaController.java            # Gestión de consultas
│   │   │   ├── entities/                               # Modelos de datos
│   │   │   │   ├── Paciente.java                      # Entidad Paciente
│   │   │   │   ├── Usuario.java                       # Entidad Usuario
│   │   │   │   └── PacienteImagen.java                # Entidad Imágenes
│   │   │   ├── repositories/                           # Acceso a datos
│   │   │   │   ├── PacienteRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── PacienteImagenRepository.java
│   │   │   ├── security/                               # Autenticación
│   │   │   │   └── UsuarioDetailsService.java         # UserDetailsService
│   │   │   └── service/                                # Lógica de negocio
│   │   │       ├── PacienteService.java               # Servicios de pacientes
│   │   │       ├── UsuarioService.java                # Gestión de usuarios
│   │   │       ├── PdfService.java                    # Generación de PDF
│   │   │       ├── QrService.java                     # Generación de QR
│   │   │       └── storage/                           # Almacenamiento
│   │   │           ├── ArchivoStorageService.java     # Interfaz abstracta
│   │   │           ├── CloudinaryArchivoStorageService.java
│   │   │           ├── LocalArchivoStorageService.java
│   │   │           └── StoredFile.java
│   │   └── resources/
│   │       ├── application.properties                 # Config base
│   │       ├── application-dev.properties             # Config desarrollo
│   │       ├── application-prod.properties            # Config producción
│   │       ├── static/                                 # Archivos estáticos
│   │       │   └── img/
│   │       └── templates/                              # Plantillas HTML
│   │           ├── home.html
│   │           ├── login.html
│   │           ├── paciente_form.html
│   │           ├── pacientes.html
│   │           ├── paciente_imagenes.html
│   │           ├── consulta_paciente.html
│   │           └── ver_qr.html
│   └── test/
│       ├── java/com/aplicacionestudiosmedicos/
│       │   └── EstudiosmedicosApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── pom.xml                                             # Dependencias Maven
├── Dockerfile                                          # Para despliegue en Render
├── mvnw / mvnw.cmd                                     # Maven Wrapper
└── README.md

```

## 📋 Requisitos

## 📋 Requisitos

### Para Desarrollo Local
- **Java 17** o superior
- **Maven 3.6+** (incluido como wrapper)
- **PostgreSQL 12+** (local o remoto)
- **Git**

### Para Despliegue en Producción
- Cuenta en **Render** (hosting)
- Cuenta en **Neon** (PostgreSQL en la nube)
- Cuenta en **Cloudinary** (almacenamiento de imágenes)
- Repositorio en **GitHub**

## 🚀 Instalación Local

### 1. Verificación de Prerequisitos

```bash
# Verificar Java
java -version

# Verificar Maven
mvnw.cmd -v
```

### 2. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd estudiosmedicos
```

### 3. Configurar Base de Datos Local

```sql
-- Crear base de datos PostgreSQL
CREATE DATABASE estudiosmedicos;

-- Las tablas se crean automáticamente con Spring Data JPA
```

### 4. Configurar Ambiente de Desarrollo

Editar `src/main/resources/application-dev.properties`:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:postgresql://localhost:5432/estudiosmedicos
spring.datasource.username=postgres
spring.datasource.password=<tu-password>
spring.datasource.driverClassName=org.postgresql.Driver

# Almacenamiento local
app.storage.type=local

# (Opcional) Usuario admin inicial
app.admin.username=admin
app.admin.password=admin123
```

### 5. Ejecutar Tests

```bash
mvnw.cmd test
```

### 6. Compilar y Empaquetar

```bash
mvnw.cmd clean package -DskipTests
```

### 7. Ejecutar la Aplicación

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

La aplicación estará disponible en: `http://localhost:8080`

**Usuario por defecto**: admin / admin123

## 📦 Despliegue en Render

Este proyecto está optimizado para despliegue en **Render** usando contenedores Docker.

### 1. Prerequisitos en la Nube

#### 🗄️ Neon (PostgreSQL)
1. Crear cuenta en [neon.tech](https://neon.tech)
2. Crear un proyecto
3. Crear una base de datos (ej: `estudiosmedicos`)
4. Copiar la cadena de conexión (URL JDBC)

#### ☁️ Cloudinary
1. Crear cuenta en [cloudinary.com](https://cloudinary.com)
2. Ir al Dashboard
3. Copiar la URL completa de Cloudinary: `cloudinary://<api_key>:<api_secret>@<cloud_name>`

#### 🔌 Render
1. Crear cuenta en [render.com](https://render.com)
2. Conectar repositorio de GitHub
3. Autorizar acceso a Render

### 2. Preparar el Repositorio

Asegurar que el `Dockerfile` está presente en la raíz del proyecto (ya incluido).

```bash
git add -A
git commit -m "Preparado para despliegue en Render"
git push origin main
```

### 3. Crear Servicio en Render

1. En Render Dashboard: **New +** → **Web Service**
2. Seleccionar el repositorio de GitHub
3. Configurar el servicio:
   - **Name**: estudiosmedicos
   - **Environment**: Docker
   - **Dockerfile Path**: `./Dockerfile`
   - **Root Directory**: (dejar vacío)
   - **Redeploy on push**: Habilitado

### 4. Agregar Variables de Entorno

Definir estas variables en Render Dashboard (pestaña Environment):

#### Variables Requeridas

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Activar profile de producción |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<host-neon>/<db>?sslmode=require` | Cadena de conexión Neon |
| `SPRING_DATASOURCE_USERNAME` | `<usuario-neon>` | Usuario de Neon |
| `SPRING_DATASOURCE_PASSWORD` | `<password-neon>` | Contraseña de Neon |
| `CLOUDINARY_URL` | `cloudinary://<key>:<secret>@<cloud>` | URL completa de Cloudinary |

#### Variables Opcionales

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `APP_ADMIN_USERNAME` | `admin` | Usuario admin inicial (si no existe) |
| `APP_ADMIN_PASSWORD` | `securePassword123` | Contraseña admin inicial |

**Notas importantes:**
- Render inyecta automáticamente `PORT` (no lo configures manualmente)
- Usar valores con caracteres especiales: copiarlos exactamente
- Las variables se aplican inmediatamente después de guardar

### 5. Configurar Conexión a Neon

Para obtener la URL de conexión Neon:

```
Neon Dashboard → Proyectos → Seleccionar base de datos → Connection string
```

Formato JDBC correcto:
```
jdbc:postgresql://ec2-xx-xxx-xxx-xx.compute-1.amazonaws.com:5432/estudiosmedicos?sslmode=require&user=<usuario>&password=<password>
```

O usar la conexión estándar con variables separadas:
```
jdbc:postgresql://<host>:5432/<database>?sslmode=require
```

### 6. Configurar Cloudinary

Para obtener tu `CLOUDINARY_URL`:

1. Ir a [Cloudinary Dashboard](https://cloudinary.com/console)
2. Copiar el valor de "CLOUDINARY_URL" en la sección de API Credentials
3. Guardar en Render como variable de entorno

### 7. Desplegar

En Render:
1. Revisión de variables ✓
2. Click en **Deploy**
3. Revisar logs en **Logs** tab
4. Esperar a que diga "Your service is live!"

**URL de la app**: Se mostrará en Render como `https://estudiosmedicos-xxx.onrender.com`

## 🔧 Configuración

### Perfiles de Ambiente

| Perfil | Uso | Almacenamiento | BD |
|--------|-----|----------------|-----|
| `dev` (default) | Desarrollo local | Carpeta `uploads/` local | PostgreSQL local |
| `prod` | Producción (Render) | Cloudinary | Neon PostgreSQL |
| `test` | Tests unitarios | H2 en memoria | H2 en memoria |

### Activar Perfil en Desarrollo

```bash
# Opción 1: Variable de entorno
set SPRING_PROFILES_ACTIVE=dev
mvnw.cmd spring-boot:run

# Opción 2: Parámetro de línea de comandos
mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Propiedades Clave

```properties
# Puerto (dev usa 8080, prod usa PORT inyectado por Render)
server.port=8080

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Thymeleaf
spring.thymeleaf.cache=false  # En dev, útil para desarrollo

# Base de datos
spring.datasource.hikari.maximum-pool-size=10

# Cloudinary (auto-configurada desde CLOUDINARY_URL)
```

## 💻 Uso

### Flujo Principal de Usuario

1. **Login**: Acceder con credenciales (admin/admin123 o usuario creado)
2. **Dashboard**: Vista principal con opciones de navegación
3. **Gestión de Pacientes**:
   - Click en **Pacientes** → **Nuevo Paciente**
   - Completar información personal
   - Guardar
4. **Cargar Imágenes Médicas**:
   - Desde el listado de pacientes → **Ver Imágenes**
   - Seleccionar imágenes locales (jpg, png)
   - Cargar → Se guardan en Cloudinary
5. **Crear Consulta**:
   - Seleccionar paciente
   - Ingresar datos de consulta
   - Generar PDF (Se crea automáticamente con QR)
6. **Descargar Documentos**:
   - Acceder a consulta
   - Botón de descarga → PDF se descarga desde Cloudinary

### Pantallas Principales

- **Home** (`/`): Dashboard principal
- **Login** (`/login`): Autenticación
- **Pacientes** (`/pacientes`): Listado y búsqueda
- **Nuevo Paciente** (`/pacientes/nuevo`): Formulario de registro
- **Imágenes** (`/pacientes/{id}/imagenes`): Galería de imágenes
- **Consultas** (`/consultas`): Historial de consultas
- **QR** (`/pacientes/{id}/qr`): Generador de QR por fecha

## 📊 Verificación Post-Deploy (Smoke Test)

Una vez desplegado en Render, ejecutar estos pasos para validar:

```
✅ Paso 1: Abrir URL pública y confirmar que carga
https://estudiosmedicos-xxx.onrender.com

✅ Paso 2: Login con credenciales configuradas
   Usuario: admin
   Contraseña: (la configurada)

✅ Paso 3: Crear un paciente de prueba
   Dashboard → Pacientes → Nuevo Paciente → Completar formulario

✅ Paso 4: Cargar una imagen de prueba
   Pacientes → Seleccionar paciente → Ver Imágenes → Cargar imagen

✅ Paso 5: Verificar que imagen está en Cloudinary
   La URL debería mostrar: res.cloudinary.com

✅ Paso 6: Crear consulta y generar PDF
   Consulta → Nueva Consulta → Generar PDF

✅ Paso 7: Descargar PDF
   Confirmar que se descarga correctamente desde Cloudinary
```

Si algo falla, revisar logs:
```
Render Dashboard → Logs → Revisar errores recientes
```

## 🐳 Docker

El proyecto incluye un `Dockerfile` optimizado para:
- Multi-stage build (menor tamaño de imagen)
- Base image lightweight
- Compilación Java 17
- Compatible con Render

Para construir localmente:
```bash
docker build -t estudiosmedicos:latest .
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db \
  -e CLOUDINARY_URL=cloudinary://... \
  estudiosmedicos:latest
```

## 🔍 Troubleshooting

### Problema: "Connection refused" en base de datos

**Dev local:**
```bash
# Verificar que PostgreSQL está corriendo
psql -U postgres

# Si no está instalado, descargar desde https://www.postgresql.org/download/
```

**Prod (Render):**
- Verificar `SPRING_DATASOURCE_URL` está correcta
- Confirmar `SPRING_DATASOURCE_USERNAME` y `PASSWORD`
- Revisar que Neon tiene la base de datos creada

### Problema: Imágenes no se cargan (err_CLOUDINARY_INVALID_URL)

- Validar `CLOUDINARY_URL` format: `cloudinary://key:secret@cloud`
- Revisar que la cuenta Cloudinary está activa
- Confirmar permisos de API en Dashboard de Cloudinary

### Problema: "Port already in use"

```bash
# En Windows, encontrar y matar el proceso en puerto 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Luego reintentar
mvnw.cmd spring-boot:run
```

### Problema: Build falla con "Java version not found"

```bash
# Verificar que Java 17+ está en PATH
java -version

# Si no, agregar a PATH o usar -Dexec.javaHome=/path/to/java17
```

### Problema: Tests fallan localmente

- Asegurar que `application-test.properties` existe
- H2 se usa automáticamente para tests
- Ejecutar: `mvnw.cmd test -X` para output detallado

### Error: `release version 17 not supported`

Maven está usando otro JDK. Verifica:

```bash
mvnw.cmd -v
```

Debe mostrar Java 17.

## 📝 API Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/` | Home / Dashboard |
| **GET/POST** | `/login` | Autenticación |
| **GET/POST** | `/pacientes` | Listado y búsqueda |
| **POST** | `/pacientes/nuevo` | Crear paciente |
| **GET** | `/pacientes/{id}/imagenes` | Galería de imágenes |
| **POST** | `/pacientes/{id}/imagenes` | Cargar imagen |
| **GET** | `/pacientes/{id}/qr` | Generar QR |
| **GET/POST** | `/consultas` | Gestión de consultas |
| **POST** | `/consultas/generar-pdf` | Generar PDF |
| **GET** | `/descargar-pdf/{id}` | Descargar documento |

## 🔐 Seguridad

### Credenciales Iniciales
- El sistema crea un usuario admin automático si se configuran las variables:
  - `APP_ADMIN_USERNAME`
  - `APP_ADMIN_PASSWORD`

### Prácticas Implementadas
- ✅ Spring Security habilitado
- ✅ CSRF protection activa
- ✅ Contraseñas hasheadas (BCrypt)
- ✅ Control de acceso por rol
- ✅ HTTPS en Render (automático)
- ✅ Variables sensibles en environment (no en código)

### Recomendaciones de Seguridad
1. **Cambiar contraseña de admin** después del primer login
2. **Usar contraseñas fuertes**: mínimo 12 caracteres, mix de tipos
3. **Rotar `CLOUDINARY_URL`** periódicamente
4. **Revisar logs** en Render regularmente
5. **Usar HTTPS siempre** (Render lo aplica automáticamente)

## 📚 Recursos Útiles

- [Documentación Spring Boot 3.5](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/guides/topicals/spring-security-architecture/)
- [Thymeleaf Tutorial](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)
- [Cloudinary API](https://cloudinary.com/documentation/image_upload_api_reference)
- [OpenPDF](https://github.com/LibrePDF/OpenPDF)
- [ZXing QR Code](https://github.com/zxing/zxing)

## 🤝 Contribuir

1. Fork el repositorio
2. Crear rama de feature: `git checkout -b feature/mi-feature`
3. Commit cambios: `git commit -m "Agregar feature"`
4. Push: `git push origin feature/mi-feature`
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo licencia MIT. Ver archivo `LICENSE` para detalles.

## ✉️ Contacto y Soporte

Para preguntas o problemas:
1. Revisar esta documentación
2. Revisar logs de Render/Dev
3. Abrir un issue en GitHub
4. Revisar sección Troubleshooting

## ⚡ Comandos Útiles para Desarrollo

```bash
# Ejecutar local (dev)
mvnw.cmd spring-boot:run

# Ejecutar tests
mvnw.cmd test

# Generar artefacto para deploy
mvnw.cmd clean package -DskipTests

# Limpiar build anterior
mvnw.cmd clean

# Ver ayuda de Maven
mvnw.cmd help
```

---

**Última actualización**: Abril 2026
**Versión**: 0.0.1-SNAPSHOT
**Estado**: ✅ Producción
