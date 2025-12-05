package APP.Modelo;
public class Pelicula extends ContenidoAudiovisual{
    private String anio;
    private String poster;

    public Pelicula(Integer id, Genero generos, String titulo, String resumen, String director,Integer duracion) {
        super.setId(id);
        super.setGenero(generos);
        super.setTitulo (titulo);
        super.setSinopsis(resumen);
        super.setDuracion(duracion);
        super.getMetadatos().setDirector(director);
    }

    public Pelicula(Integer id, String titulo, String anio, String genero, String poster, String sinopsis, float rating_promedio) {
        setId(id);
        setTitulo(titulo);
        setSinopsis(sinopsis);
        this.anio = anio;
        setGenero(Genero.desdeTexto(genero));
        this.poster = poster;
        setRating_promedio(rating_promedio);
    }

    public Pelicula (){

    }
    

    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
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
