package APP.Modelo.DAO.Implementaciones;

import java.sql.*;

public class MiConexion {    
    private static final String URL = "jdbc:sqlite:appstreaming.db";
    private MiConexion() {}
    public static Connection getCon() throws SQLException {
        Connection con = DriverManager.getConnection(URL);
        try (Statement st = con.createStatement()) {
         st.execute("PRAGMA journal_mode=WAL;");
            
        st.execute("PRAGMA busy_timeout = 5000;");
        }
        return con;
    }
    
}
