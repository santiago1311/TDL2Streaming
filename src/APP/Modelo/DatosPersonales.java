package APP.Modelo;
public class DatosPersonales {
    private String nombre;
    private String apellido;
    private Integer dNI;
    private Integer ID;
    public DatosPersonales(String nombre, String apellido, Integer dNI, Integer iD) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dNI = dNI;
        this.ID = iD;
    }
    public DatosPersonales(){}

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Integer getDNI() {
        return dNI;
    }
    public void setDNI(Integer dNI) {
        this.dNI = dNI;
    }
    public Integer getId(){
        return this.ID;
    }
    public String toString() {
        return "Datos Personales" +
               " ID=" + ID +
               " Nombre='" + nombre + '\'' +
               " Apellido='" + apellido + '\'' +
               " DNI=" + dNI;
    }

}   
