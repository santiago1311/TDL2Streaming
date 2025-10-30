package APP;
import java.util.Scanner;
import APP.Modelo.*;

public class Plataforma {
    public static void main(String args[]){
        int selector;
        Scanner in = new Scanner(System.in);
        System.out.println("BIENVENIDO A TDL2 PARA ACCEDER A NUESTRAS FUNCIONES\n"+
        "      DEBE INICIAR SESION O REGISTRARSE: \n"+
        "INGRESE:\n "+
        "1-INICIAR SESION\n"+
        "2-REGISTRARSE");
        selector = in.nextInt();
        if(selector == 2) {
            Aplicacion.crearUsuario(in);
            System.out.println("Desea iniciar sesion?\n 1- Si\n Cualquier otro valor terminara la ejecución");
            selector = in.nextInt();
        }if (selector == 1){
            UsuarioCliente usuario = Aplicacion.iniciarSesion(in);
            Aplicacion.aplicacion(usuario, selector);
        }
        in.close(); 
    }
}