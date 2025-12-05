package APP.Excepciones;

public class EncontrarPeliculaException extends Exception{
    public EncontrarPeliculaException(String mensaje) {
        super("Error al encontrar la pelicula: " + mensaje);
    }
    public EncontrarPeliculaException(String mensaje, Throwable causa) {
        super("Error al encontrar la pelicula: "+ mensaje, causa);
    }
    
}
