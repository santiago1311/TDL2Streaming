package APP.Modelo;
public class Pelicula extends ContenidoAudiovisual{
    private String anio;

    public Pelicula(Integer id, Genero generos, String titulo, String resumen, String director,Integer duracion) {
        super.setId(id);
        super.setGenero(generos);
        super.setTitulo (titulo);
        super.setSinopsis(resumen);
        super.setDuracion(duracion);
        super.getMetadatos().setDirector(director);
    }

    public Pelicula(Integer id, String titulo, String anio, String genero, String director, String sinopsis) {
        super.setTitulo(titulo);
        super.setSinopsis(sinopsis);
        this.anio = anio;
        super.setGenero(Genero.desdeTexto(genero));
        super.getMetadatos().setDirector(director);
    }


    public Pelicula (){

    }

    public String getAnio() {
        return anio;
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
