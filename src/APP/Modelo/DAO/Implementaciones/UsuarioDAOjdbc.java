package APP.Modelo.DAO.Implementaciones;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import APP.Modelo.DAO.Interfaces.UsuarioDAO;
import APP.Modelo.*;

public class UsuarioDAOjdbc implements UsuarioDAO {
    public List<UsuarioCliente> getUsuarios(){
        List<UsuarioCliente> usuarios = new ArrayList<>();
        String sql = "SELECT \n"
                + "u.ID AS ID_USUARIO, \n"
                + "u.NOMBRE_USUARIO, \n"
                + "u.EMAIL, \n"
                + "u.CONTRASENA,\n"
                + "u.IDIOMA,\n"
                + "d.ID AS ID_DATOS,\n"
                + "d.NOMBRES, \n"
                + "d.APELLIDO, \n"
                + "d.DNI\n"
                + "FROM USUARIO u\n"
                + "JOIN DATOS_PERSONALES d ON u.ID_DATOS_PERSONALES = d.ID";
        
        try(Connection con = MiConexion.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()){
                DatosPersonales dp = new DatosPersonales(
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDO"),
                    rs.getInt("DNI"),
                    rs.getInt("ID_DATOS")
                );

                UsuarioCliente u = new UsuarioCliente(
                    rs.getString("EMAIL"),
                    rs.getString("CONTRASENA"),
                    dp,
                    rs.getInt("ID_USUARIO"),
                    rs.getString("IDIOMA"),
                    rs.getString("NOMBRE_USUARIO")
                );

                usuarios.add(u);}
            } catch (SQLException e){
                System.out.println("Error al cargar los usuarios: "+e.getMessage());
            }
        return usuarios;
        
    }
   
    public void registrar (UsuarioCliente usuario){
        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, EMAIL, IDIOMA, CONTRASENA, ID_DATOS_PERSONALES) VALUES (?,?,?,?,?)";
        DatosPersonalesDAOjdbc dao = new DatosPersonalesDAOjdbc();
        DatosPersonales dp = dao.encontrarPorDni(usuario.getDatosPersonales().getDNI());
        
        try (
            PreparedStatement ps = MiConexion.getCon().prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getIdioma());
            ps.setString(4, usuario.getContrasena());
            
            ps.setInt(5, dp.getId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }

    }

    public void eliminar (UsuarioCliente usuario){
        String sql = "DELETE FROM USUARIO WHERE ID = ?";
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql)){
            p_sent.setInt(1, usuario.getId()); 
            int filasAfectadas = p_sent.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró el registro con ID: " + usuario.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el registro: " + e.getMessage());
        }
    }

    public UsuarioCliente encontrarID ( Integer iD ){
        UsuarioCliente usuario = null;
        String sql = "SELECT * FROM USUARIO WHERE ID = ?";
        DatosPersonalesDAOjdbc daoDP = new DatosPersonalesDAOjdbc();
        try (
            PreparedStatement p_sent = MiConexion.getCon().prepareStatement(sql)    
        ) {
            p_sent.setInt(1,iD);
            
            try(ResultSet rs = p_sent.executeQuery()){
                if (rs.next() == true){
                    DatosPersonales dp = daoDP.encontrarId(rs.getInt("ID_DATOS_PERSONALES"));
                    usuario = new UsuarioCliente( rs.getString("EMAIL"),
                    rs.getString("CONTRASENA"),
                    dp,
                    rs.getInt("ID"),
                    rs.getString("IDIOMA"),
                    rs.getString("NOMBRE_USUARIO"));
                }else {
                    System.out.println("ID NO ENCONTRADO");
                }
            }
        } catch (SQLException e){
            System.out.println("Error al verificar ID del Usuario: "+ e.getMessage());
        }
        return usuario;
 
    }
    public UsuarioCliente encontrar(String identificacion) {
        UsuarioCliente usuario = null;
        DatosPersonalesDAOjdbc daoDP = new DatosPersonalesDAOjdbc();
        String sql = "SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ?";
        try 
        (
        PreparedStatement ps = MiConexion.getCon().prepareStatement(sql); 
        ){
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()==true) {
                DatosPersonales dp = daoDP.encontrarId(rs.getInt("ID_DATOS_PERSONALES"));        
                usuario = new UsuarioCliente( rs.getString("EMAIL"),
                rs.getString("CONTRASENA"),
                dp,
                rs.getInt("ID"),
                rs.getString("IDIOMA"),
                rs.getString("NOMBRE_USUARIO"));
            }
            rs.close();

        } catch (java.sql.SQLException e) {
            System.out.println("Error de SQL: "+e.getMessage());
        }
        return usuario;
    }

    public UsuarioCliente encontrarEmail(String identificacion) {
        UsuarioCliente usuario = null;
        DatosPersonalesDAOjdbc daoDP = new DatosPersonalesDAOjdbc();
        String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
        try 
        (
        PreparedStatement ps = MiConexion.getCon().prepareStatement(sql); 
        ){
            ps.setString(1, identificacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()==true) {
                DatosPersonales dp = daoDP.encontrarId(rs.getInt("ID_DATOS_PERSONALES"));        
                usuario = new UsuarioCliente( rs.getString("EMAIL"),
                rs.getString("CONTRASENA"),
                dp,
                rs.getInt("ID"),
                rs.getString("IDIOMA"),
                rs.getString("NOMBRE_USUARIO"));
            }
            rs.close();

        } catch (java.sql.SQLException e) {
            System.out.println("Error de SQL: "+e.getMessage());
        }
        return usuario;
    }
}

