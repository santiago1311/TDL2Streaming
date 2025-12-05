package APP.Modelo.DAO.Implementaciones;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import APP.Modelo.DAO.Interfaces.PeliculaDAO;
import APP.Excepciones.*;
import APP.Modelo.*;

public class PeliculaDAOjdbc implements PeliculaDAO{

    @Override
    public List<Pelicula> mostrarPeliculas() throws PeliculasNoCargadasException {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT p.ID, p.GENERO, p.TITULO, p.RESUMEN, p.DIRECTOR, p.DURACION FROM PELICULA p";

        try (Connection con = MiConexion.getCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Genero genero;
                try {
                    genero = Genero.valueOf(rs.getString("GENERO").toUpperCase());
                } catch (IllegalArgumentException ex) {
                    throw new PeliculasNoCargadasException("El género '" + rs.getString("GENERO") + "' no es válido", ex);
                }

                Pelicula pelicula = new Pelicula(
                    rs.getInt("ID"),
                    genero,
                    rs.getString("TITULO"),
                    rs.getString("RESUMEN"),
                    rs.getString("DIRECTOR"),
                    rs.getInt("DURACION")
                );
                peliculas.add(pelicula);
            }

        } catch (SQLException e) {
            throw new PeliculasNoCargadasException("Error al cargar las películas desde la base de datos", e);
        }

        return peliculas;
    }

    public List<Pelicula> listarMejorRankeadas() throws PeliculasNoCargadasException{
        List<Pelicula> peliculas= new ArrayList<>();
        String sql= "SELECT \n"+
                    "   p.ID, \n " + 
                    "   p.GENERO, \n" + 
                    "   p.TITULO, \n" +
                    "   P.RESUMEN, \n" + 
                    "   p.PUNTAJE, \n "+
                    "   p.POSTER, \n "+                    
                    "   p.FECHA_ESTRENO, \n "+
                    "   p.VOTOS \n"+
                    "FROM PELICULA p "+
                    "WHERE p.PUNTAJE IS NOT NULL "+
                    "ORDER BY p.PUNTAJE DESC "+
                    "LIMIT 10";
        try(Connection con = MiConexion.getCon();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()){
                String genPrincipal = rs.getString("GENERO").split(",")[0].trim().toUpperCase();
                Pelicula r = new Pelicula(rs.getInt("ID"),rs.getString("TITULO"),rs.getString("FECHA_ESTRENO"), genPrincipal,rs.getString("POSTER"), rs.getString("RESUMEN"), rs.getFloat("PUNTAJE"));
                peliculas.add(r);
            }
            rs.close();
            st.close();
        } catch (SQLException e){
            throw new PeliculasNoCargadasException("Error al cargar las peliculas desde la base de datos ",e);
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
    public Pelicula encontrarPelicula(Integer id) throws EncontrarPeliculaException {
        Pelicula pelicula= null;
        String sql = "SELECT * FROM PELICULA WHERE ID = ?";
        try (Connection con = MiConexion.getCon(); 
            PreparedStatement ps = con.prepareStatement(sql);
            ){
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){    
                        String genPrincipal= rs.getString("GENERO").split(",")[0].trim();//toma el primer genero como genero principal
                        Genero genero = Genero.desdeTexto(genPrincipal);
                        pelicula = new Pelicula(rs.getInt("ID"),rs.getString("TITULO"),rs.getString("FECHA_ESTRENO"),genPrincipal,rs.getString("POSTER"),rs.getString("RESUMEN"),rs.getFloat("PUNTAJE"));
                    }
                }
        }
        catch (Exception e) {
            throw new EncontrarPeliculaException("Error al encontrar la pelicula con id: " + id, e);
        }              
        return pelicula;

    }

    @Override
    public Pelicula encontrarPelicula(String titulo) throws EncontrarPeliculaException {
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
                pelicula = new Pelicula(rs.getInt("ID"),rs.getString("TITULO"),rs.getString("FECHA_ESTRENO"),genPrincipal,rs.getString("POSTER"),rs.getString("RESUMEN"),rs.getFloat("PUNTAJE"));
            }
            rs.close();
            st.close();
        } catch (java.sql.SQLException e) {
            throw new EncontrarPeliculaException("Error al encontrar la pelicula con titulo: " + titulo + " en la Base de datos", e);
        }
        return pelicula;
    }

    public Pelicula buscadorRandom() {
        String sqlMax = "SELECT MAX(ID) FROM PELICULA";
        int maxId = 0;
        Pelicula peli;
        try (Connection con = MiConexion.getCon();
             PreparedStatement ps =con.prepareStatement(sqlMax);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        if (maxId == 0) {
            System.out.println("La tabla está vacía.");
            return null;
        }
        int randomId = 1 + (int)(Math.random() * maxId);
        try {
            peli = encontrarPelicula(randomId);
            return peli;
        }catch (EncontrarPeliculaException e){
            System.out.println(e);
            return null;
        }
    }

    public List<Pelicula> listarRandom(int cantidad){
        List<Pelicula> peliculas= new ArrayList<>();
        for (int i=0; i < cantidad; i++){
            Pelicula peli = buscadorRandom();
            peliculas.add(peli);
        }
        return peliculas;
    }


}