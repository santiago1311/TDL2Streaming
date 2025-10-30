package APP.Modelo.Validaciones;
import APP.Modelo.*;
import java.util.Scanner;


public class ValidacionUsuario {

    public static UsuarioCliente validacionUsuario(DatosPersonales dp, Scanner in){
        String nombre_usuario= null;
        String contrasena_usuario= null;
        String email_usuario= null;
        int datos_correctos=0;

        while (nombre_usuario == null || contrasena_usuario  == null || email_usuario == null ){
            System.out.println("Ingrese su email");
            email_usuario= in.next();
            if (correo_valido(email_usuario)){
                System.out.println("Ingrese nombre de usuario");
                nombre_usuario= in.next();
                System.out.println("Ingrese su contraseña");
                contrasena_usuario= in.next();
                if (nombre_usuario == null || contrasena_usuario == null){
                    System.out.println("Datos ingresados incorrectamente, vuelva a intentarlo");
                }
                else{
                    String cadena = email_usuario + ", " + nombre_usuario + "," + contrasena_usuario;
                    System.out.println("los datos ingresados: email , nombre_usuario , contraseña respectivamente, son correctos?"+ cadena + " ingrese 1 para si y 0 para no");
                    datos_correctos=in.nextInt();
                    if (datos_correctos == 1 ){
                    	return new UsuarioCliente(email_usuario, contrasena_usuario,dp,null , null, nombre_usuario);
                    }
                }
            }
            else{
                System.out.println("El correo ingresado es invalido");
            }
        }
        return null;
    }
        
    public static boolean correo_valido( String correo ){
        char arroba = '@';
        return (correo.matches(".+" + arroba + ".+"));
    }
}