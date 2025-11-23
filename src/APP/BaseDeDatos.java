package APP;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class BaseDeDatos {

    public static void creacionDeTablasEnBD(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS DATOS_PERSONALES (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "NOMBRES TEXT NOT NULL," +
                "APELLIDO TEXT NOT NULL," +
                "DNI INTEGER NOT NULL" +
                ");";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS PELICULA (" +
	        "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
	        "GENERO	TEXT,"+
	        "TITULO TEXT,"+
	        "RESUMEN TEXT,"+
	        "DIRECTOR TEXT,"+
	        "DURACION INTEGER,"+
	        "FECHA_ESTRENO TEXT,"+
	        "POPULARIDAD REAL,"+
	        "VOTOS INTEGER,"+
	        "PUNTAJE REAL,"+
	        "IDIOMA_ORIGINAL TEXT,"+
	        "POSTER TEXT"+
                ");";

        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS USUARIO (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE_USUARIO TEXT NOT NULL," +
                "EMAIL TEXT NOT NULL," +
                "IDIOMA TEXT,"+
                "CONTRASENA TEXT NOT NULL," +
                "ID_DATOS_PERSONALES INTEGER NOT NULL," +
                "CONSTRAINT USUARIO_DATOS_PERSONALES_FK FOREIGN KEY (ID_DATOS_PERSONALES) REFERENCES DATOS_PERSONALES(ID)"+
                ");";
        stmt.executeUpdate(sql);
        
        sql=" CREATE TABLE IF NOT EXISTS RESENA ("+
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "CALIFICACION INTEGER NOT NULL,"+
                "OPINION TEXT,"+
                "APROBADO INTEGER DEFAULT 0 NOT NULL,"+
                "FECHA_HORA DATETIME NOT NULL,"+
                "ID_USUARIO INTEGER NOT NULL,"+
                "ID_PELICULA INTEGER NOT NULL,"+
                "CONSTRAINT RESENA_USUARIO_FK FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),"+
                "CONSTRAINT RESENA_PELICULA_FK FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA(ID)"+
                ");";
        stmt.executeUpdate(sql);
        
        stmt.close();
    }

        public static void main (String args[]){
                System.out.println("Iniciando base de datos");
                Connection c = null;
                try {
                        c = DriverManager.getConnection("jdbc:sqlite:appstreaming.db");
                        creacionDeTablasEnBD(c);
                } catch (SQLException e){
                        System.out.println("Que base de datos?" + e.getMessage());
                }
        }
}
