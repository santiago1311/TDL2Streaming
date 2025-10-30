package APP.Modelo;
public class ContenidoAudiovisual extends Contenido {
    private Metadatos metadat = new Metadatos();
    private Integer duracion;

    public Metadatos getMetadatos(){
        return this.metadat;
    }        
    public void setMetadatos(Metadatos metadat){
        this.metadat = metadat;
    } 
    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}