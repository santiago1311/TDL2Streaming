package APP.Excepciones;

public class PeliculasNoCargadasException extends Exception{
    public PeliculasNoCargadasException(String mensaje) {
        super("Error a la hora de cargar el listado de peliculas: " + mensaje);
    }
    public PeliculasNoCargadasException(String mensaje, Throwable causa) {
        super("Error a la hora de cargar el listado de peliculas: "+ mensaje, causa);
    }

}
