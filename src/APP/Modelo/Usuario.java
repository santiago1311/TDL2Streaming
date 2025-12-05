package APP.Modelo;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.Validaciones.ValidacionPelicula;
import APP.Modelo.Comparadores.*;

public abstract class Usuario{
    private String correo;
    private String contrasena;
    private int inicioSesionOno;
    
    protected Usuario(String correo, String contrasena){
        this.correo=correo;
        this.contrasena = contrasena;
    }
    protected Usuario(){
        
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void changeContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getContrasena(){
        return this.contrasena;
    }
    public void setInicioDeSesionOno(int inicio){
        this.inicioSesionOno=inicio;
    }
    public int getInicioDeSesionOno(){
        return this.inicioSesionOno;
    }


}
