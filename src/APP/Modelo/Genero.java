package APP.Modelo;
public enum Genero {
    ACCION,
    DRAMA,
    COMEDIA,
    TERROR,
    CIENCIA_FICCION,
    ROMANCE;

    public static boolean esGeneroValido(String valor) {
        for (Genero g : Genero.values()) {
            if (g.name().equalsIgnoreCase(valor)) {
                return true;
            }
        }
        return false;
    }
    public static Genero desdeTexto(String valor) {
        return Genero.valueOf(valor.toUpperCase());
    }
    
}

