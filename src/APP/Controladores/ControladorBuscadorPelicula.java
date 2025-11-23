package APP.Controladores;

import javax.swing.Action;
import APP.Modelo.DatosPersonales;
import APP.Modelo.Pelicula;
import APP.Modelo.DAO.Implementaciones.*;
public class ControladorBuscadorPelicula {

    
    public Pelicula buscarPeliculaPorTitulo(String tituloBuscado) {
        tituloBuscado = tituloBuscado.trim().toLowerCase();
        PeliculaDAOjdbc dao = new PeliculaDAOjdbc();
        Pelicula pelicula = null;
        pelicula = dao.encontrarPelicula(tituloBuscado);
        return pelicula;
    }
}


