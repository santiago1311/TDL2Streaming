package APP.Excepciones;

public class EncontrarUsuarioException extends Exception{
    public EncontrarUsuarioException(String mensaje) {
        super("Error en el inicio de sesion: " + mensaje);
    }
    public EncontrarUsuarioException(String mensaje, Throwable causa) {
        super("Error en el inicio de sesion: "+ mensaje, causa);
    }
}
