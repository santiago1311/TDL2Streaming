package APP.Modelo.DAO.Interfaces;
import java.util.List;
import APP.Modelo.Pelicula;
public interface PeliculaDAO {
    List<Pelicula> mostrarPeliculas();
    void agregarPelicula(Pelicula pelicula);
    void eliminar(Integer ID);
    Pelicula encontrarPelicula(Integer ID);
    Pelicula encontrarPelicula(String titulo);
}
