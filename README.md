# Proyecto FullStack con JWT, SQL Server y Angular

Este proyecto incluye un backend con autenticación JWT y migraciones de base de datos mediante Liquibase, junto con un frontend Angular básico (no integrado aún con el backend).

## 📋 Requisitos previos

- **Java 21+** (Backend)
- **Node.js v16+ y Angular CLI** (Frontend)
- **SQL Server** (Base de datos)
- **Maven** (Gestión de dependencias Java)

## 🚀 Instalación

### 1. Clonar repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd nombre-repositorio
```

### 2. Configurar Backend
1. **Configurar base de datos**:
   - Crear una base de datos en SQL Server
   - Actualizar credenciales en `backend-app/src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=tu_db
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

2. **Ejecutar migraciones (Liquibase)**:
   ```bash
   cd backend-app
   mvn liquibase:update
   ```

3. **Iniciar backend**:
   ```bash
   mvn spring-boot:run
   ```
   dentro de las carpetas ya existe una JAR compilado por lo que tambien puedes ejecutar


   ```bash   
   java -jar target/backend-app-0.0.1-SNAPSHOT.jar
   ```
4. **Puerto por defecto 8082**
- si quieres cambiar el puerto ejecuta el comando:
  ```bash
   java -jar -Dserver.port="tu puerto" target/backend-app-0.0.1-SNAPSHOT.jar
  ```

5. **Todos los endpoints estan en:**
   ```bash
   http://localhost:8082 #dependiendo del puerto que se haya configurado
   ```

### 3. Configurar Frontend
```bash
cd frontend-app
npm install
ng serve
```
Acceder a: http://localhost:4200

## 📂 Estructura del proyecto
```
.
├── backend-app
│   ├── src/main/java
│   │   └── com/ejemplo/auth (Config JWT, controladores, servicios)
│   ├── src/main/resources
│   │   ├── db/changelog (Archivos de migración Liquibase)
│   │   └── application.properties
│   └── pom.xml
└── frontend-app
    ├── src/app (Componentes Angular)
    ├── angular.json
    └── package.json
```

## 🔐 Endpoints clave (Backend)
- `POST /auth/login` - Autenticación JWT
- `POST /auth/singup` - Registro de usuario
- `GET /usuarios/1` - Ejemplo de ruta protegida
- `GET /post/allpublic` - Ejemplo de ruta publica

## 🛠 Migraciones de base de datos
Liquibase ejecutará automáticamente las migraciones al iniciar la aplicación. Para crear nuevas migraciones:
```bash
mvn liquibase:diff
```

## ⚠️ Notas importantes
1. Asegurar que SQL Server permita conexiones remotas
2. El frontend actualmente muestra una plantilla básica sin conexión al backend
3. Configurar secretos JWT en `application.properties`:
   ```properties
   jwt.secret=mi_secreto_super_seguro
   jwt.expiration=86400000  # 24h en ms
   ```

## 📬 Contacto
Para soporte técnico o reporte de errores: 
[abrir issue](<URL_REPO/issues>) | anthony.naranjo96@yahoo.com
```
