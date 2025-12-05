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
        if (valor == null) return null;
        String t = valor.trim().toUpperCase().replace(" ", "_");

        switch (t) {
            case "SCIENCE_FICTION":
            case "SCI-FI":
            case "SCIENCEFICTION":
                return SCIENCE_FICTION;
            case "TV_MOVIE":
            case "TV":
            case "TELEVISION_MOVIE":
                return TV_MOVIE;
            default:
                try {
                    return Genero.valueOf(t);
                } catch (IllegalArgumentException e) {
                    return null; 
                }
        }
    }

    
}

