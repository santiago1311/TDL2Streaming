package APP.Modelo;
public class Contenido implements Comparable<Contenido>{
    private String titulo;
    private int promedioPuntuacion;
    private int visto;
    private String sinopsis;
    private Genero genero;
    private Integer iD;
    private float rating_promedio;
    
    public float getRating_promedio() {
        return rating_promedio;
    }
    public void setRating_promedio(float rating_promedio) {
        this.rating_promedio = rating_promedio;
    }
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero generos) {
        this.genero = generos;
    }
    public Integer getId(){
        return this.iD;
    }
    public void setId(Integer id){
        this.iD =id;
    }

    public int compareTo (Contenido otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    } 

}
