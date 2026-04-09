# Estudios Medicos

Aplicacion web Spring Boot + Thymeleaf para gestion de pacientes, carga de imagenes y generacion de PDF con QR.

Este README esta enfocado en los pasos necesarios para desplegar en Render usando Neon PostgreSQL y Cloudinary.

## 1. Prerrequisitos

1. Cuenta en Render.
2. Cuenta en Neon.
3. Cuenta en Cloudinary.
4. Repositorio en GitHub con este proyecto.
5. Java 17 para build local.

Verificacion local recomendada:

```bash
java -version
mvnw.cmd -v
mvnw.cmd test
mvnw.cmd clean package -DskipTests
```

## 2. Variables De Entorno De Produccion

Definir estas variables en Render:

- `SPRING_PROFILES_ACTIVE=prod`
- `SPRING_DATASOURCE_URL=jdbc:postgresql://<host-neon>/<db>?sslmode=require`
- `SPRING_DATASOURCE_USERNAME=<usuario-neon>`
- `SPRING_DATASOURCE_PASSWORD=<password-neon>`
- `CLOUDINARY_URL=cloudinary://<api_key>:<api_secret>@<cloud_name>`

Opcionales:

- `APP_ADMIN_USERNAME=<usuario_admin_inicial>`
- `APP_ADMIN_PASSWORD=<password_admin_inicial>`

Notas:

- `PORT` no hace falta configurarlo manualmente en Render (Render lo inyecta).
- Si no defines `APP_ADMIN_USERNAME` y `APP_ADMIN_PASSWORD`, no se crea admin automatico.

## 3. Configurar Neon

1. Crear proyecto en Neon.
2. Crear base de datos (por ejemplo `estudiosmedicos`).
3. Crear o usar usuario existente.
4. Copiar datos de conexion y armar JDBC URL con SSL:

```text
jdbc:postgresql://<host-neon>/<db>?sslmode=require
```

5. Cargar `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD` en Render.

## 4. Configurar Cloudinary

1. Crear cuenta en Cloudinary.
2. Ir al Dashboard y copiar `CLOUDINARY_URL`.
3. Cargar `CLOUDINARY_URL` en Render.

Estrategia usada por la app: una sola variable (`CLOUDINARY_URL`).

## 5. Crear El Servicio En Render

1. En Render, crear `New +` -> `Web Service`.
2. Conectar el repositorio de GitHub.
3. Configurar:

- Runtime: `Java`
- Build Command:

```bash
./mvnw clean package -DskipTests
```

- Start Command:

```bash
java -jar target/estudiosmedicos-0.0.1-SNAPSHOT.jar
```

4. Agregar todas las variables del punto 2.
5. Deploy.

## 6. Verificacion Post Deploy (Smoke Test)

Una vez desplegado:

1. Abrir la URL publica de Render.
2. Iniciar sesion (si configuraste admin inicial).
3. Crear paciente.
4. Subir imagen.
5. Generar QR por fecha.
6. Abrir enlace de PDF y confirmar que apunta a dominio de Cloudinary.

## 7. Comportamiento Dev vs Prod

- `dev` (default local):
  - usa PostgreSQL local configurado en `application-dev.properties`
  - permite archivos locales (`uploads/` y `pdfs/`)
- `prod` (Render):
  - usa Neon por variables de entorno
  - sube imagenes y PDFs a Cloudinary
  - QR usa la URL publica final del PDF en Cloudinary
  - no depende de filesystem local para flujo principal

## 8. Troubleshooting Rapido

### Error: `release version 17 not supported`

Maven esta usando otro JDK. Verifica:

```bash
mvnw.cmd -v
```

Debe mostrar Java 17.

### Error de conexion a DB en tests

Los tests del proyecto usan perfil `test` con H2. Ejecuta:

```bash
mvnw.cmd test
```

### Render deploy falla por variables faltantes

Revisar especialmente:

- `SPRING_PROFILES_ACTIVE`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `CLOUDINARY_URL`

## 9. Comandos Utiles

```bash
# Ejecutar local (dev)
mvnw.cmd spring-boot:run

# Ejecutar tests
mvnw.cmd test

# Generar artefacto para deploy
mvnw.cmd clean package -DskipTests
```
