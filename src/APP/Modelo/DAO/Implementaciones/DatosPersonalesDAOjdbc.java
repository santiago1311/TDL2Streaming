package APP.Modelo.DAO.Implementaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import APP.Modelo.*;
import APP.Modelo.DAO.Interfaces.DatosPersonalesDAO;

public class DatosPersonalesDAOjdbc implements DatosPersonalesDAO {

    public void cargarEnLaBase(DatosPersonales dp){
        String sql = "INSERT INTO DATOS_PERSONALES (NOMBRES,APELLIDO,DNI) VALUES (?,?,?)";
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql);
        ){
            p_sent.setString(1,dp.getNombre());
            p_sent.setString(2,dp.getApellido());
            p_sent.setInt(3,dp.getDNI());
            p_sent.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al cargar los datos: "+ e.getMessage());
        }
    }

    public boolean encontrarDNIExistente(Integer dNI){
        String sql = "SELECT * FROM DATOS_PERSONALES WHERE DNI = ?";
        try (Connection con = MiConexion.getCon();
             PreparedStatement p_sent = con.prepareStatement(sql)) {
            
            p_sent.setInt(1, dNI);
            try (ResultSet rs = p_sent.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar dni: "+ e.getMessage());
            return false;
        }
    }    
    
    public DatosPersonales encontrarId(Integer n){
        DatosPersonales dp = null;
        String sql = "SELECT * FROM DATOS_PERSONALES WHERE ID = ?";
        
        try (Connection con = MiConexion.getCon();
             PreparedStatement p_sent = con.prepareStatement(sql)) {
            
            p_sent.setInt(1,n);
            
            try (ResultSet rs = p_sent.executeQuery()) {
                if (rs.next() == true){
                    dp =new DatosPersonales(rs.getString("NOMBRES"), rs.getString("APELLIDO"), rs.getInt("DNI"), n);
                }
                else{
                    System.out.println("ID NO ENCONTRADO");
                }
            }         
        } catch (SQLException e){
            System.out.println("Error al verificar ID: "+ e.getMessage());
        }
        return dp;
    }

    // MÉTODO CORREGIDO
    public DatosPersonales encontrarPorDni(Integer DNI){
        DatosPersonales dp = null;
        String sql = "SELECT * FROM DATOS_PERSONALES WHERE DNI = ?";
        
        try (Connection con = MiConexion.getCon();
             PreparedStatement p_sent = con.prepareStatement(sql)) {
            
            p_sent.setInt(1,DNI);
            try (ResultSet rs = p_sent.executeQuery()) {
                if (rs.next() == true){
                    dp = new DatosPersonales(rs.getString(2), rs.getString(3), DNI, rs.getInt(1));
                } else {
                    System.out.println("DNI NO ENCONTRADO");
                }
            }
        } catch (SQLException e){
            System.out.println("Error al verificar DNI: "+ e.getMessage());
        }
        return dp;
    }
 

    public void eliminar (DatosPersonales dp){
        String sql = "DELETE FROM DATOS_PERSONALES WHERE ID = ?";
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql)){
            p_sent.setInt(1, dp.getId()); 
            int filasAfectadas = p_sent.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró el registro con ID: " + dp.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el registro: " + e.getMessage());
        }
    }

    // ESTE MÉTODO YA ESTABA CORRECTO
    public List<DatosPersonales> cargar(){
        List<DatosPersonales> listDatos = new ArrayList<>();
    
        String sql = "SELECT \n" +
                        "    d.ID, \n"+ 
                        "    d.NOMBRES, \n" + 
                        "    d.APELLIDO, \n" + 
                        "    d.DNI \n" + 
                        "FROM DATOS_PERSONALES d\n" + 
                        "";
        
        // Fíjate como aquí el ResultSet SÍ estaba en el try-with-resources
        try(Connection con = MiConexion.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()){
                DatosPersonales dp = new DatosPersonales(
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDO"),
                    rs.getInt("DNI"),
                    rs.getInt("ID")
                );
                listDatos.add(dp);}
            System.out.println("Datos personales cargados:");
            } catch (SQLException e){
                System.out.println("Error al cargar los usuarios: "+e.getMessage());
            }

            
        return listDatos;
        
    }
}