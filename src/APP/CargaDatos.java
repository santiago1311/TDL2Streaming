package APP;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import APP.Modelo.*;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.Validaciones.*;


public class CargaDatos {
    //Hecho con una combinacion de copilot y chat gpt para facilitarnos la carga manua;
    public static void insertarDatos(Connection connection) throws SQLException {
        Random rand = new Random();
        DatosPersonalesDAOjdbc daoDP = new DatosPersonalesDAOjdbc();
        // === DATOS PERSONALES ===
        for (int i = 1; i <= 5; i++) {
            String nombres = "Nombre" + i;
            String apellido = "Apellido" + i;
            int dni = 30000000 + rand.nextInt(10000000);

            // Validaciones
            if (!nombres.matches("[a-zA-Z]+") || !apellido.matches("[a-zA-Z]+")) {
                System.out.println("❌ Error: nombre o apellido inválido → " + nombres + " " + apellido);
                continue;
            }

            if (daoDP.encontrarDNIExistente(dni)) {
                System.out.println("❌ Error: DNI repetido (" + dni + "). Registro omitido.");
                continue;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO DATOS_PERSONALES (NOMBRES, APELLIDO, DNI) VALUES (?, ?, ?)");
            ps.setString(1, nombres);
            ps.setString(2, apellido);
            ps.setLong(3, dni);
            ps.executeUpdate();
            ps.close();
        }

        // === PELÍCULAS ===
        Genero[] generos = Genero.values();
        for (int i = 1; i <= 5; i++) {
            Genero genero = generos[rand.nextInt(generos.length)];
            String titulo = "Pelicula" + i;
            String resumen = "Resumen de la película " + i;
            String director = "Director" + i;
            int duracion = 90 + rand.nextInt(60);

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PELICULA (GENERO, TITULO, RESUMEN, DIRECTOR, DURACION) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, genero.name());
            ps.setString(2, titulo);
            ps.setString(3, resumen);
            ps.setString(4, director);
            ps.setInt(5, duracion);
            ps.executeUpdate();
            ps.close();
        }

        // === USUARIOS ===
        for (int i = 1; i <= 5; i++) {
            String nombreUsuario = "usuario" + i;
            String email = "usuario" + i + "@mail.com";
            String contrasena = "pass" + i;
            String idioma = "ES";
            int idDatos = i; // IDs 1..5

            if (!ValidacionUsuario.correo_valido(email)) {
                System.out.println("❌ Error: email inválido (" + email + "). Registro omitido.");
                continue;
            }

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO USUARIO (NOMBRE_USUARIO, EMAIL, IDIOMA, CONTRASENA, ID_DATOS_PERSONALES) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, nombreUsuario);
            ps.setString(2, email);
            ps.setString(3, idioma);
            ps.setString(4, contrasena);
            ps.setInt(5, idDatos);
            ps.executeUpdate();
            ps.close();
        }

        // === RESEÑAS ===
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i <= 10; i++) {
            int calificacion = 1 + rand.nextInt(5);
            String opinion = "Opinión de prueba " + i;
            int aprobado = rand.nextBoolean() ? 1 : 0;
            String fechaHora = LocalDateTime.now().minusDays(rand.nextInt(30)).format(formatter);
            int idUsuario = 1 + rand.nextInt(5);
            int idPelicula = 1 + rand.nextInt(5);

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO RESENA (CALIFICACION, OPINION, APROBADO, FECHA_HORA, ID_USUARIO, ID_PELICULA) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, calificacion);
            ps.setString(2, opinion);
            ps.setInt(3, aprobado);
            ps.setString(4, fechaHora);
            ps.setInt(5, idUsuario);
            ps.setInt(6, idPelicula);
            ps.executeUpdate();
            ps.close();
        }

        System.out.println("✅ Datos insertados correctamente (coherentes con las tablas).");
    }
}
