package APP.Modelo.DAO.Interfaces;
import java.util.List;

import APP.Excepciones.EncontrarPeliculaException;
import APP.Excepciones.PeliculasNoCargadasException;
import APP.Modelo.Pelicula;
public interface PeliculaDAO {
    List<Pelicula> mostrarPeliculas() throws PeliculasNoCargadasException;
    void agregarPelicula(Pelicula pelicula);
    void eliminar(Integer ID);
    Pelicula encontrarPelicula(Integer ID) throws EncontrarPeliculaException;
    Pelicula encontrarPelicula(String titulo) throws EncontrarPeliculaException;
}
