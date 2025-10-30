package APP.Modelo;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.Comparadores.ComparadorPorEmail;

public class UsuarioCliente extends Usuario implements Comparable<UsuarioCliente> {
    private String nombreUsuario;
    private String idioma;
    private String generos;
    private DatosPersonales datosPer;
    private Integer ID;

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
    //este sera por nombre
    public int compareTo(UsuarioCliente otro) {
        return this.nombreUsuario.compareToIgnoreCase(otro.nombreUsuario);
    }
    
    public List<UsuarioCliente> listarUsuarios(String criterio) {
        UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
        List<UsuarioCliente> usuarios = dao.getUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("No hay usuarios para mostrar.");
            return usuarios;
        }

        if (criterio.equalsIgnoreCase("NOMBREUSUARIO")) {
            Collections.sort(usuarios);
        } else if (criterio.equalsIgnoreCase("EMAIL")) {
            Collections.sort(usuarios, new ComparadorPorEmail());
        } else {
            System.out.println("Criterio inválido. Use 'NOMBREUSUARIO' o 'EMAIL'.");
        }

        return usuarios;
    }

    public String toString() {
        return "UsuarioCliente \n" +
                " ID=" + ID +
                " NombreUsuario='" + this.nombreUsuario + '\'' +
                " Idioma='" + this.idioma + '\'' +
                " Generos='" + this.generos + '\'' +
                " Correo='" + super.getCorreo() + '\'' +
                " DatosPersonales=" + (datosPer.toString());
    }

    public void agregarResena(Scanner in){
        ResenaDAOjdbc resenadao = new ResenaDAOjdbc();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        
        //Scanner in = new Scanner(System.in);
     
            System.out.println("Ingrese el id de la pelicula que desea hacerle una reseña");
            int iDPeli = in.nextInt();
            Pelicula peli = peliculaDAO.encontrarPelicula(iDPeli);
            if (peli != null){

                System.out.println("Pelicula encontrada "+peli.getTitulo() + " compelete los siguientes campos");
                
                System.out.print("Ingrese calificación (número entero): ");
                int calificacion = in.nextInt();
                in.nextLine();
                
                System.out.print("Ingrese opinión: ");
                String opinion = in.nextLine();

                System.out.print("Ingrese fecha y hora (formato: yyyy-MM-dd HH:mm): ");
                String fechaTexto = in.nextLine();
                resenadao.agregarResena(new Resena(calificacion,opinion,0,fechaTexto,null,this, peli)); 
            }   
            else {
                System.out.println("acceso denegado, la pelicula con ID "+ iDPeli+" NO EXISTE");
            }
    }
    
}
