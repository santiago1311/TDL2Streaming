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



    public  void agregarPelicula(){
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        Pelicula pelicula = ValidacionPelicula.solicitarDatos();
        if (pelicula == null) {
            System.out.println("El registro de la película fue cancelado o los datos eran inválidos.");
            return;
        }
        if (ValidacionPelicula.confirmarDatos(pelicula)){
            peliculaDAO.agregarPelicula(pelicula);    
            System.out.println("Pelicula registrada correctamente.");
        }else{System.out.println("Pelicula no registrada.");}
    }

    public void aprobarResena(Scanner in) {
        ResenaDAOjdbc dao = new ResenaDAOjdbc();
        for (Resena r: dao.obtenerNoAprobadas()){
            System.out.println("ID: " + r.getId() + " Opinion:" + r.getOpinion());
        }
        System.out.println("Ingrese el ID de la resena a aprobar");
        //Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        Resena resena = dao.obtenerResenaId(id);
        if (resena != null){
            System.out.println("Resena: " + resena.toString());
            System.out.println("Es la resena que desea aprobar? Ingrese 1 para confirmar");
            int aux = in.nextInt();
              
            if (aux == 1){
                dao.actualizarResena(id);
            } else {
                System.out.println("No es la resena que deseaba actualizar. FIN EJECUCION");
            }
        } 
    }
    public List<Pelicula> mostrarPor (String criterio){
        PeliculaDAOjdbc dao = new PeliculaDAOjdbc();
        List<Pelicula> peliculas = dao.mostrarPeliculas();
        if (peliculas == null || peliculas.isEmpty()){
            System.out.println("No hay peliculas para mostrar");
            return peliculas;
        }
        if (criterio.equalsIgnoreCase("TITULO")){
            Collections.sort(peliculas);
        } else if (criterio.equalsIgnoreCase("DURACION")){
            Collections.sort(peliculas, new ComparadorPorDuracion());
        } else if (criterio.equalsIgnoreCase("GENERO")){
            Collections.sort(peliculas, new ComparadorPorGenero());
        } else {
            System.out.println("Criterio invalido");
        }
        return peliculas;

    }
}
