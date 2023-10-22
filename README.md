# Accenture Tech-Hub: Sistema de Administración para Hospitales

## Descripción
En este proyecto se ha desarrollado un sistema de citas médicas que permite a los pacientes agendar citas con los médicos de su preferencia.

Para llevar a cabo esta aplicación, se nos ha encargado la tarea de crear el sistema de gestión de citas, además de crear pruebas unitarias JUnit para cada una de las clases que se han creado previamente.

## Informe detallado

En primer lugar, se comenzó con el desarrollo del apartado asignado, que fue el sistema de creación de citas médicas. Este se encuentra dentro de la clase **AppointmentController**, donde se ha trabajado en el método **createAppointment**, encargado de crear una cita médica y agregarla a la base de datos de las citas médicas del hospital.

El segundo punto a cubrir era la creación de pruebas unitarias JUnit para todas las entidades de nuestro proyecto, así como para todos los métodos utilizados por estas entidades dentro de sus respectivos controladores:

- **Doctor** - **DoctorControllerUnitTest**, **EntityUnitTest**
- **Patient** - **PatientControllerUnitTest**, **EntityUnitTest**
- **Room** - **RoomControllerUnitTest**, **EntityUnitTest**

Por último, además de realizar todo este proceso de programación con Spring Boot, se ha llevado a cabo el despliegue de la aplicación utilizando Docker. Para ello, se han creado dos archivos Dockerfile. El primero de ellos se encargó del proceso de creación de la imagen de la aplicación y su ejecución, comprobando el correcto funcionamiento al asegurarse de que todas las pruebas unitarias se ejecutaran correctamente. Además, se ha creado un segundo Dockerfile que despliega un contenedor con el servicio SQL ya montado y funcionando correctamente.

## Tecnologías utilizadas

Para la realización de este proyecto, se han utilizado las siguientes tecnologías:

- **Java** - Lenguaje de programación
- **Spring Boot** - Framework de desarrollo
- **Maven** - Gestor de dependencias
- **JUnit** - Framework de pruebas unitarias
- **Docker** - Contenedor de aplicaciones
- **MySQL** - Base de datos
- **Git** - Control de versiones
- **GitHub** - Repositorio de código

## Instalación

En principio, este es un proyecto que se ejecutará en un servicio web, por lo que no es necesario realizar ninguna instalación. Simplemente debe clonar el repositorio y desplegarlo en un servidor web. Sin embargo, si desea realizar la instalación y la creación del archivo .war, debe ejecutar el siguiente comando:

mvn clean package -DskipTests


Este comando creará el archivo .war en la carpeta "target", que le permitirá ejecutar el servicio.
