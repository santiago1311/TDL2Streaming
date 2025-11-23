package APP.Modelo.DAO.Implementaciones;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import APP.Modelo.DAO.Interfaces.PeliculaDAO;
import APP.Modelo.*;

public class PeliculaDAOjdbc implements PeliculaDAO{

    @Override
    public List<Pelicula> mostrarPeliculas() {
        
        List<Pelicula> peliculas= new ArrayList<>();
        String sql= "SELECT \n"+
                    "   p.ID, \n " + 
                    "   p.GENERO, \n" + 
                    "   p.TITULO, \n" +
                    "   P.RESUMEN, \n" + 
                    "   p.DIRECTOR, \n" +
                    "   p.DURACION \n "+
                    "FROM PELICULA p";
        try(Connection con = MiConexion.getCon();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()){
                Genero genero = Genero.valueOf(rs.getString("GENERO").toUpperCase());
                Pelicula r = new Pelicula(rs.getInt("ID"),genero,rs.getString("TITULO"),rs.getString("RESUMEN"),rs.getString("DIRECTOR"),rs.getInt("DURACION"));
                peliculas.add(r);
            }
            rs.close();
            st.close();
        } catch (SQLException e){
            System.out.println("Error al cargar las peliculas: "+e.getMessage());
        }
        return peliculas;      
    }        
    

    @Override
    public void agregarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO PELICULA (GENERO,TITULO,RESUMEN,DIRECTOR,DURACION) VALUES (?,?,?,?,?)";    
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql);
        ){
            p_sent.setString(1,pelicula.getGenero().name());
            p_sent.setString(2,pelicula.getTitulo());
            p_sent.setString(3,pelicula.getSinopsis());
            p_sent.setString(4, pelicula.getMetadatos().getDirector());
            p_sent.setInt(5,pelicula.getDuracion());
            
            p_sent.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al cargar los datos: "+ e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql= " DELETE FROM PELICULA WHERE ID= ?";
        try (Connection con = MiConexion.getCon();
            PreparedStatement p_sent = con.prepareStatement(sql)){
            p_sent.setInt(1, id); 
            int filasAfectadas = p_sent.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("pelicula eliminada correctamente.");
            } else {
                System.out.println("No se encontró el registro con ID: " +id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el pelicula: " + e.getMessage());
        }
        
    }

    @Override
    public Pelicula encontrarPelicula(Integer id) {
        Pelicula pelicula= null;
        String sql = "SELECT * FROM PELICULA WHERE ID = ?";
        try (Connection con = MiConexion.getCon(); 
            PreparedStatement ps = con.prepareStatement(sql);
            ){
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){    
                        Genero genero = Genero.desdeTexto(rs.getString("GENERO"));
                        pelicula = new Pelicula (rs.getInt("ID"), genero, rs.getString("TITULO"),rs.getString("RESUMEN"),rs.getString("DIRECTOR"),rs.getInt("DURACION"));
                    }
                }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }              
        return pelicula;

    }

    @Override
    public Pelicula encontrarPelicula(String titulo) {
        Pelicula pelicula= null;
        String sql = "SELECT * FROM PELICULA WHERE LOWER(TITULO)= LOWER(?) ";
        try {
            Connection con = MiConexion.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, titulo);
            ResultSet rs= st.executeQuery();
            if (rs.next()==true) {
                String genPrincipal= rs.getString("GENERO").split(",")[0].trim();//toma el primer genero como genero principal
                Genero genero = Genero.desdeTexto(genPrincipal);
                pelicula = new Pelicula(rs.getInt("ID"),rs.getString("TITULO"),rs.getString("FECHA_ESTRENO"),genPrincipal,rs.getString("DIRECTOR"),rs.getString("RESUMEN"));
            }
            rs.close();
            st.close();
        } catch (java.sql.SQLException e) {
            System.out.println("Error de SQL: "+e.getMessage());
        }
        return pelicula;
    }

}