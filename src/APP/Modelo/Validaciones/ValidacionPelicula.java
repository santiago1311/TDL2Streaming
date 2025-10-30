package APP.Modelo.Validaciones;
import java.util.Scanner;
import APP.Modelo.*;

public class ValidacionPelicula {

    public static Pelicula solicitarDatos() {
        Scanner in1 = new Scanner(System.in);
        System.out.print("Título: ");
        String titulo = in1.nextLine();

        if (titulo.isEmpty()) {
            System.out.println("El título es obligatorio");
            return null;
        }
        System.out.print("Director: ");
        String director = in1.nextLine();
        
        if (director.isEmpty()) {
            System.out.println("El director es obligatorio");
            return null;
        }

        Metadatos metadat = new Metadatos(director);
        System.out.print("Género (Accion, Comedia, Drama, Terror, Romance, Documental): ");
        String generoTexto = in1.nextLine();
        
        if (!Genero.esGeneroValido(generoTexto)) {
            System.out.println("Género inválido.");
            return null;
        }
        
        System.out.println("Ingrese duracion");
        int dura= in1.nextInt();
        in1.nextLine();
        
        System.out.print("Resumen (opcional): ");
        String resumen = in1.nextLine();
        
        return new Pelicula(null, Genero.desdeTexto(generoTexto), titulo, resumen, metadat.getDirector(),dura);
    }

    public static boolean confirmarDatos(Pelicula p) {
        Scanner in1 = new Scanner(System.in);
        System.out.println("Datos ingresados:");
        System.out.println("Título: " + p.getTitulo());
        System.out.println("Director: " + p.getMetadatos().getDirector());
        System.out.println("Género: " + p.getGenero());
        System.out.println("Duracion: " + p.getDuracion());
        System.out.println("Resumen: " + (p.getSinopsis() == null ? "(sin resumen)" : p.getSinopsis()));

        System.out.print("\n Los datos son correctos? (s/n): ");
        String confirmacion = in1.next();
        return confirmacion.equalsIgnoreCase("s");
    }
}
