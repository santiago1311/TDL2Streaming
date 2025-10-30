package APP.Modelo;

public class Resena {
    private int calificacion;
    private String opinion;
    private Pelicula pelicula;
    private Integer aprobado;
    private String fechaHora;
    private UsuarioCliente usuario;
    private Integer ID;

    
    public Resena(int calificacion, String opinion, Integer aprobado, String fechaHora, Integer iD, UsuarioCliente usuario, Pelicula pelicula) {
        this.calificacion = calificacion;
        this.opinion = opinion;
        this.aprobado = aprobado;
        this.fechaHora = fechaHora;
        this.ID = iD;
        this.usuario = usuario;
        this.pelicula = pelicula;
    }
    
    public Resena(){}

    public int getCalificacion() {
        return calificacion;
    }
    public void getCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    public String getOpinion() {
        return opinion;
    }
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    public Pelicula getPelicula() {
        return this.pelicula;
    }
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
    public Integer getAprobado() {
        return aprobado;
    }
    public void setAprobado(Integer aprobado) {
        this.aprobado = aprobado;
    }
    public String getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
    public UsuarioCliente getUsuario(){
        return this.usuario;
    }
    public Integer getId(){
        return this.ID;
    }
    
    public String toString() {
    return "ID= " + this.ID +
            "\n calificacion= " + this.calificacion +
            "\n opinion= " + this.opinion + 
            "\n aprobado= " + this.aprobado +
            "\n fechaHora= " + this.fechaHora +
            "\n Pelicula= " + this.pelicula.getTitulo() +
            "\n usuario= " + this.usuario.getNombreUsuario();
    }

}
