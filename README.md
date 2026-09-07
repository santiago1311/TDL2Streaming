# TDL2 – Plataforma de Streaming

## Descripción
**TDL2 es una aplicación de Streaming**

Permite registrarse, ingresar datos personales, iniciar sesión, listar usuarios y películas, agregar reseñas y aprobarlas.

## Historial de Versiones
`1.0` 28/10/2025
`1.1` 5/12/2025

## Requisitos
- Java 17 o superior instalado.
- Archivo de base de datos `appstreaming.db` en la misma carpeta que los `.jar`.
- Driver JDBC de SQLite (`sqlite-jdbc-3.50.3.0.jar`) en la misma carpeta que el `.jar`.

## Archivos incluidos
- `TDL2.jar` – archivo ejecutable de la aplicación.
- `sqlite-jdbc-3.50.3.0.jar` – librería JDBC de SQLite.
- `appstreaming.db` – base de datos SQLite con la información de usuarios y películas.

## Notas técnicas y Decisiones de diseño

- **Manejo de los géneros**: para los géneros sería conveniente trabajarlos como una tabla aparte. Esto porque si se quiere agregar un nuevo género se tendría que dar de baja el sistema y después volver a levantarlo.
- **Conexión a la base**: la conexión con la base de datos no se realiza con un Singleton debido a que generaba errores a la hora de querer acceder a un metodo de un DAO desde otro DAO, por ejemplo cuando se quiere comprobar el ID de datos personales desde un DNI. Es por esto que se decidio que lo mejor para nuestra aplicación sería abrir una conexión cada vez que se requiera.
- **Scanners**: intentamos realizar la aplicación con Scanners independientes pero en algún punto de la ejecución se mostraba en pantalla un error y terminaba la ejecución argumentando que el mismo se había cerrado aunque solo se cierra cuando se termina de ejecutar el main. Es por este motivo que en `APP.Aplicacion.iniciarSesion()` se pregunta si el Scanner esta cerrado ya que siempre que se intentaba ingresar en ese metodo mostraba en pantalla este error antes mencionado.

## Ejecución
Para ejecutar la aplicación desde línea de comandos, estando en la carpeta donde están los archivos:

```bash
# Linux / macOS
java -cp "TDL2.jar:lib/sqlite-jdbc-3.50.3.0.jar:." APP.Plataforma

# Windows
java -cp "TDL2.jar;lib/sqlite-jdbc-3.50.3.0.jar;." APP.Plataforma

```
## Flujo de ejecución
- Se le mostrara en la línea de comandos para ingresar si desea iniciar sesión o registrarse.
- Si decide registrarse una vez que termine de hacerlo y cumpla con todas las validaciones se le preguntara si desea iniciar sesion.
- Al iniciar sesión con su nombre de usuario y contraseña, si el inicio de sesión es exitoso, se le mostrará en pantalla un panel que le da las siguientes opciones.
``` 
QUE DESEA REALIZAR?:
1-Ver listado de usuarios
2-Ver listado de peliculas
3-Realizar una resena 
4-Aprobar una resena 
5-Registrar una pelicula
6-Registrar Datos Personales
7-Cerrar sesion 
```

- En esta versión 1.0 del Programa se le permite acceder a cualquier usuario a estas funciones por lo que una vez que ingrese a su cuenta podrá realizar cualquiera de estas acciones.
- Las opciones `4-Aprobar una reseña` y `5-Registrar una película` en versiones posteriores serán utilizables únicamente por los administradores.
  
## Comentarios
- Para probar la aplicación se le brinda al tester un usuario `usuario1` cuya contraseña es `pass1`.
- Como aclaración el ingreso de datos en la aplicación es 
`Case-sensitive`, esto significa que si usted crea el usuario `R93` y desea iniciar sesión ingresando `r93` no se encontrará su usuario en la base.
- En esta versión aún no existen los usuarios administradores pero en un futuro se incorporarán a la app para controlar el aprobado de reseñas y el registro de nuevas películas.
- Dentro de la carpeta `Modelo` estan todas las clases de funcionamiento de la aplicación.
- Se utiliza el Driver `jdbc` para comunicarse con la base de datos, por lo que depende de la libreria externa `sqlite-jdbc` para comunicarse con la base de datos, si esta librería no es incluye correctamente en la aplicación, la misma fallará al intentar establecer la conexión.
- Todos los datos de la base se manejan mediante el patrón `DAO(Data Access Object)` donde cada fila de la base de datos se mapea y manipula como un objeto de java. En la carpeta `Modelo/DAO/Interfaces` se encuentran los contratos con los que se deben realizar los drivers `jdbc` anteriormente mencionados.

## Autores

Santiago Adriel Fernandez `Contacto`: adrielfernandez93@gmail.com

Francisco Biancuzzo `contacto`: franciscobiancuzzo03@gmail.com

