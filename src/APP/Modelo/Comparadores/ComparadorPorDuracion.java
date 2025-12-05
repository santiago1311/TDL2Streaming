package APP.Modelo.Comparadores;
import java.util.Comparator;
import APP.Modelo.ContenidoAudiovisual;

public class ComparadorPorDuracion implements Comparator<ContenidoAudiovisual>{
    public int compare(ContenidoAudiovisual c1, ContenidoAudiovisual c2){
        return Long.compare(c1.getDuracion(),c2.getDuracion()); 

    }
}
