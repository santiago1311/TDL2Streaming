package APP.Modelo;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.Comparadores.ComparadorPorEmail;

public class UsuarioCliente extends Usuario {
    private String nombreUsuario;
    private String idioma;
    private String generos;
    private DatosPersonales datosPer;
    private Integer ID;

    public UsuarioCliente(String correo, String contrasena, DatosPersonales datosPer, Integer ID, String idioma, String nombreUsuario, int inicioSesionOno){
        super(correo, contrasena);
        this.datosPer= datosPer;
        this.idioma = idioma;
        this.nombreUsuario = nombreUsuario;
        this.ID = ID;
        setInicioDeSesionOno(inicioSesionOno);
    }

    public UsuarioCliente(String correo, String contrasena, DatosPersonales datosPer, Integer ID, String idioma, String nombreUsuario){
        super(correo, contrasena);
        this.datosPer= datosPer;
        this.idioma = idioma;
        this.nombreUsuario = nombreUsuario;
        this.ID = ID;
    }
    

    public UsuarioCliente(){}

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public String getGeneros() {
        return generos;
    }
    public void setGeneros(String generos) {
        this.generos = generos;
    }
    public DatosPersonales getDatosPersonales(){
        return this.datosPer;
    }
    public void setDatosPersonales(DatosPersonales dp){
        this.datosPer = dp;
    }
    public Integer getId(){
        return this.ID;
    }

}
