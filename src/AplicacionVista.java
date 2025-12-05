import APP.Modelo.DAO.Implementaciones.*;
import APP.Vista.*;
import java.sql.*;
import java.io.File;
import APP.*;


public class AplicacionVista{
    public static void main(String[] args) {
        BaseDeDatos.iniciarBase();
        try(Connection conn = MiConexion.getCon();){
            new VistaLog();
            File flag = new File("ya_importado.txt");
            if (!flag.exists()) {
                CargardorCSV.cargarPeliculas();
                flag.createNewFile();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}