# sprintbootnn
 
Mi Proyecto Spring Boot con Gradle
Este es un proyecto Spring Boot con Gradle que implementa una aplicación web para gestionar usuarios.

Requisitos
Docker 20.10 o superior
Gradle 7.0 o superior
 
Configuración
Clonar el repositorio:
  
Abrir una terminal, introducir los comandos.
git clone https://github.com/nescobar2102/pruebannsprintboot.git
 cd pruebannsprintboot/


Crear una imagen Docker de la aplicación con el siguiente comando :
 docker build -t pruebann:1.0 .
  
Ejecutar el contenedor Docker de la aplicación:
 docker run -p 8080:8080 pruebann:1.0

Acceder a la aplicación en el navegador web:
http://localhost:8080/
 

Para acceder a la documentaciòn 
http://localhost:8080/swagger-ui/#/

Pruebas
Se pueden ejecutar las pruebas unitarias y de integración incluidas en el proyecto usando el siguiente comando:

gradle test
