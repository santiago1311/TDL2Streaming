package APP.Modelo;
public enum Genero {
    ACTION,
    ADVENTURE,
    ANIMATION,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    HISTORY,
    HORROR,
    MUSIC,
    MYSTERY,
    ROMANCE,
    SCIENCE_FICTION,
    TV_MOVIE,
    THRILLER,
    WAR,
    WESTERN;

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

