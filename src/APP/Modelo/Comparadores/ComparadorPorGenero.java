package APP.Modelo.Comparadores;
import java.util.Comparator;
import APP.Modelo.ContenidoAudiovisual;

public class ComparadorPorGenero implements Comparator <ContenidoAudiovisual>{
    public int compare(ContenidoAudiovisual c1, ContenidoAudiovisual c2){
        return c1.getGenero().name().compareTo(c2.getGenero().name());
    }
}
