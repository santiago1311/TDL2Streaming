package APP.Modelo.DAO.Implementaciones;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import APP.Modelo.*;
import APP.Modelo.DAO.Interfaces.ResenaDAO;

public class ResenaDAOjdbc implements ResenaDAO{

    public void actualizarResena (int id){
        String sql = "UPDATE RESENA SET APROBADO = 1 WHERE ID = ?";
        
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql)    
        ) {
            p_sent.setInt(1,id);
            int filasMod = p_sent.executeUpdate();
            if (filasMod <= 0) {
                System.out.println("ID NO ENCONTRADO");
            }
            else System.out.println("Update exitoso");
        } catch (SQLException e){
            System.out.println("Error al verificar ID de Resena que se desea actualizar: "+ e.getMessage());
        }
    }

    @Override
    public void agregarResena(Resena resena) {
        String sql = "  INSERT INTO RESENA (CALIFICACION,OPINION,APROBADO,FECHA_HORA,ID_USUARIO,ID_PELICULA) VALUES(?,?,?,?,?,?) ";
        UsuarioDAOjdbc usuariodao = new UsuarioDAOjdbc();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql);
        ){
            p_sent.setInt(1,resena.getCalificacion());
            p_sent.setString(2, resena.getOpinion());
            p_sent.setInt(3, resena.getAprobado());
            p_sent.setString(4, resena.getFechaHora());
            UsuarioCliente us = resena.getUsuario();
            p_sent.setInt(5, usuariodao.encontrar(us.getNombreUsuario()).getId());
            Pelicula pelicula = resena.getPelicula();
            p_sent.setInt(6, peliculaDAO.encontrarPelicula(pelicula.getTitulo()).getId());
            
            p_sent.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al cargar los datos: "+ e.getMessage());
        }
    }

    @Override
    public void eliminarResena(int id) {
        String sql= "DELETE FROM RESENA WHERE ID=?" ;
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql)){
            p_sent.setInt(1, id); 
            int filasAfectadas = p_sent.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Resena eliminada correctamente.");
            } else {
                System.out.println("No se encontró el registro con ID: " +id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el resena: " + e.getMessage());
        }
    }
    @Override
    public Resena obtenerResena(String criterio) {
        return null;
    }

    @Override
    public Resena obtenerResenaId(int id) {
        UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        Resena resenaID = new Resena();
        String sql = "SELECT * FROM RESENA WHERE ID =?";
        try (PreparedStatement st = MiConexion.getCon().prepareStatement(sql);
            ){
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()){
                if (rs.next()){
                    UsuarioCliente usuario =usuarioDAO.encontrarID(rs.getInt("ID_USUARIO"));
                    Pelicula pelicula = peliculaDAO.encontrarPelicula(rs.getInt("ID_PELICULA"));
                    resenaID = new Resena (rs.getInt("CALIFICACION"),rs.getString("OPINION"), rs.getInt("APROBADO"), rs.getString("FECHA_HORA"), rs.getInt("ID"), usuario, pelicula);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resenaID;
    
    }

    @Override
    public List<Resena> obtenerTodasResenas() {
        List<Resena> resenas = new ArrayList<>();
        UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        String sql = "SELECT \n" + 
                        "    r.ID, \n" + 
                        "    r.CALIFICACION,\n"+
                        "    r.OPINION, \n" +
                        "    r.APROBADO, \n "+ 
                        "    r.FECHA_HORA,\n" + 
                        "    r.ID_USUARIO,\n" +
                        "    r.ID_PELICULA\n" +
                        "FROM RESENA r\n";
        try(Connection con = MiConexion.getCon();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()){
                Resena r =new Resena(rs.getInt(2),rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(1), usuarioDAO.encontrarID(rs.getInt(6)), peliculaDAO.encontrarPelicula(rs.getInt(7)));
                resenas.add(r);
            }
        } catch (SQLException e){
            System.out.println("Error al cargar los usuarios: "+e.getMessage());
        }
        return resenas;      
    }
    
    public List<Resena> obtenerNoAprobadas(){
        List<Resena> noAprobadas = new ArrayList<>();

        UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        String sql = "SELECT * FROM RESENA WHERE APROBADO = 0";

        try (Connection con = MiConexion.getCon(); 
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while (rs.next()){
                UsuarioCliente usuario = usuarioDAO.encontrarID(rs.getInt("ID_USUARIO"));
                Pelicula pelicula =peliculaDAO.encontrarPelicula(rs.getInt("ID_PELICULA"));
                Resena r = new Resena (rs.getInt("CALIFICACION"),rs.getString("OPINION"), rs.getInt("APROBADO"), rs.getString("FECHA_HORA"), rs.getInt("ID"), usuario, pelicula);
                noAprobadas.add(r);
            }   
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return noAprobadas;
    }
}
