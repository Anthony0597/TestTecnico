# Usa una imagen base con OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/backend-app-0.0.1-SNAPSHOT.jar /app/mi-aplicacion.jar

# Expone el puerto en el que Spring Boot corre
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "mi-aplicacion.jar"]