package APP.Modelo;
public class Metadatos {
    private String director;
    
    public Metadatos (String director){
        this.director = director;
    }
    
    public Metadatos() {
        this.director = "";
    }
    public void setDirector(String d){
        this.director = d;
    }
    
    public String getDirector(){
        return this.director;
    }
}

