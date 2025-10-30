package APP.Modelo;
public class Pelicula extends ContenidoAudiovisual{

    public Pelicula(Integer id, Genero generos, String titulo, String resumen, String director,Integer duracion) {
        super.setId(id);
        super.setGenero(generos);
        super.setTitulo (titulo);
        super.setSinopsis(resumen);
        super.setDuracion(duracion);
        super.getMetadatos().setDirector(director);
    }
    public Pelicula (){

    }

    public String toString() {
        return "Pelicula | " +
           "ID: " + getId() + " | " +
           "Título: " + getTitulo() + " | " +
           "Género: " + (getGenero().name()) + " | " +
           "Duración: " + getDuracion() + " min | " + 
           "Director: " + getMetadatos().getDirector() + " | " +
           "Sinopsis: " + getSinopsis();
        }

    
}
