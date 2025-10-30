package APP.Modelo.Validaciones;
import java.util.Scanner;
import APP.Modelo.*;
import APP.Modelo.DAO.Implementaciones.DatosPersonalesDAOjdbc;

public class ValidacionDatosPersonales {
  
    public static DatosPersonales solicitarDatos(Scanner in) {
        
        DatosPersonales dp = new DatosPersonales();

        System.out.print("Nombre: ");
        String nombre = in.next();
        if (nombre.isEmpty() || !nombre.matches("[a-zA-Z]+")) {
            System.out.println("El nombre es obligatorio y solo puede contener letras.");
            return null;
        }
        dp.setNombre(nombre);

        System.out.print("Apellido: ");
        String apellido = in.next();
        if (apellido.isEmpty() || !apellido.matches("[a-zA-Z]+")) {
            System.out.println("El apellido es obligatorio y solo puede contener letras.");
            return null;
        }
        dp.setApellido(apellido);

        System.out.print("DNI: ");
        String dniTexto = in.next();
        int dni;
        try {
            dni = Integer.parseInt(dniTexto);
            if (dni <= 0) {
                System.out.println("El DNI debe ser un número positivo.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("El DNI debe ser un número válido.");
            return null;
        }
        dp.setDNI(dni);

        System.out.println("\n Datos ingresados correctamente:");
        System.out.println("Nombre: " + dp.getNombre());
        System.out.println("Apellido: " + dp.getApellido());
        System.out.println("DNI: " + dp.getDNI());

        System.out.print("\n¿Desea confirmar los datos? (1 = Sí / 0 = No): ");
        int confirmar = in.nextInt();
        if (confirmar != 1) {
            System.out.println("Ingreso cancelado por el usuario.");
            return null;
        }

        return dp;
    }

    

    public static void guardarDatosPersonales(DatosPersonales dp){
    	DatosPersonalesDAOjdbc dao = new DatosPersonalesDAOjdbc();
        if (dp != null){ 
    	    boolean encontrado=dao.encontrarDNIExistente(dp.getDNI());
            if (encontrado){
                System.out.println("El DNI ya esta registrado con otro usuario");
            }
            else {
               dao.cargarEnLaBase(dp);     
            }
        }       
    }


}
