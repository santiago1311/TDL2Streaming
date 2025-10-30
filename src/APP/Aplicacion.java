package APP;

import java.util.List;
import java.util.Scanner;
import APP.Modelo.*;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.Validaciones.*;


public class Aplicacion {

    public static void aplicacion(UsuarioCliente usuario, int selector){
        Scanner in = new Scanner(System.in);
        while (usuario != null){
            System.out.println("QUE DESEA REALIZAR? :\n"+
            "1-Ver listado de usuarios\n"+
            "2-Ver listado de peliculas\n"+
            "3-Realizar una resena \n"+
            "4-Aprobar una resena \n"+
            "5-Registrar una pelicula\n"+
            "6-Registrar Datos Personales\n"+
            "7-Cerrar sesion");
            selector = in.nextInt();
            switch (selector){
                case 1:
                    List<UsuarioCliente> lista = listarClientes(usuario, in);
                    for (UsuarioCliente iterador:lista){
                        System.out.println(iterador.toString());
                    }
                    break;
                case 2:
                    List<Pelicula> lista1 = getPeliculas(usuario, in);
                    for(Pelicula iterador1 :lista1){
                        System.out.println(iterador1.toString());
                    }
                    System.out.println("Desea seleccionar una pelicula del listado?\n"+
                    "1- Si\n Cualquier otro valor terminara la ejecución");
                    selector = in.nextInt();
                    if (selector == 1){
                        seleccionarPeli(in);
                    }
                    break;
                case 3:
                    System.out.println("AGREGANDO NUEVA RESENA");
                    usuario.agregarResena(in);
                    break;
                case 4:
                    System.out.println("RESENAS SIN APROBAR: \n");
                    usuario.aprobarResena(in);
                    break;
                case 5:
                    System.out.println("REGISTRO DE NUEVA PELICULA: \n");
                    usuario.agregarPelicula();
                    break;
                case 6:
                    System.out.println("REGISTRO DE DATOS PERSONALES");
                    if (cargarDatosPersonales(in) != null){
                        System.out.println("Carga Exitosa");
                    } else {
                        System.out.println("Error al cargar datos personales");
                    }
                    break;
                case 7:
                    System.out.println("Cerrando sesion...");
                    usuario = null;
                    break;
                default:
                    System.out.println("Opción inválida. FIN EJECUCION");
            }
        }
    }

    public static UsuarioCliente crearUsuario(Scanner in){
        UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
        DatosPersonales dp = cargarDatosPersonales(in);
        UsuarioCliente usuario = ValidacionUsuario.validacionUsuario(dp, in);
        dao.registrar(usuario);
        return usuario;
    }

    public static UsuarioCliente iniciarSesion(Scanner in){
        if (in == null){
            in = new Scanner(System.in);
        }
        System.out.println("Ingrese nombre de usuario");
        String nombreUs = in.next();
        System.out.println("Ingrese contrasena");
        String contrasena = in.next();
        UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
        UsuarioCliente usuario = dao.encontrar(nombreUs);
        if (usuario == null) {
            System.out.println("Usuario no encontrado en la base de datos");
            return null;
        }        
        if (usuario.getContrasena().equals(contrasena)){
            System.out.println("Sesion iniciada correctamente");
            return usuario;
        }
        System.out.println("Error en nombre de usuario o contrasena");
        return null;
    }

    public static Pelicula seleccionarPeli(Scanner in){
        System.out.println("Ingrese el id la pelicula a seleccionar");
        int idPeli= in.nextInt();
        PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
        Pelicula peliSeleccionada =peliculaDAO.encontrarPelicula(idPeli);
        if (peliSeleccionada != null){
            System.out.println("Se ha seleccionado para reproducir la pelicula " + peliSeleccionada.getTitulo());
            return peliSeleccionada;
        }
        else {
            System.out.println("el id ingresado no es correcto");
        }
        return null;
    }
 
    public static List<UsuarioCliente> listarClientes(UsuarioCliente usu, Scanner in){
        System.out.println("DESEA ORDENAR LOS USUARIOS POR \n"+
        "1-NOMBRE DE USUARIO \n"+
        "2-EMAIL\n"+
        "CUALQUIER OTRO VALOR SE ASUMIRA QUE NO SE DESEA ORDENAR POR CRITERIO\n");
        int aux = in.nextInt();
        switch (aux){
            case 1:
                return usu.listarUsuarios("NOMBREUSUARIO");
            case 2:
                return usu.listarUsuarios("EMAIL");
            default:
                System.out.println("NO SE DIO NINGUN CRITERIO VALIDO DE ORDENACION");
                return usu.listarUsuarios("");
        }
    }

    public static List<Pelicula> getPeliculas(UsuarioCliente usu, Scanner in){
        System.out.println("DESEA ORDENAR LAS PELICULAS POR \n"+
        "1-TITULO \n"+
        "2-GENERO\n"+
        "3-DURACION\n"+
        "CUALQUIER OTRO VALOR SE ASUMIRA QUE NO SE DESEA ORDENAR POR CRITERIO\n");
        int aux = in.nextInt();
        switch(aux){
            case 1:
                return usu.mostrarPor("TITULO");
            case 2:
                return usu.mostrarPor("GENERO");
            case 3:
                return usu.mostrarPor("DURACION");
            default:
                return usu.mostrarPor("");
        }
    }
    public static DatosPersonales cargarDatosPersonales(Scanner in){
        DatosPersonalesDAOjdbc dao = new DatosPersonalesDAOjdbc();
        System.out.println("DESEA CARGAR SUS DATOS PERSONALES O SELECCIONAR UNO EXISTENTE?\n"+
        "1-CARGAR NUEVOS DATOS\n"+
        "2-SELECCIONAR UNO EXISTENTE\n");
        DatosPersonales dp = null;
        int aux = in.nextInt();
        if (aux == 1) {
            dp = ValidacionDatosPersonales.solicitarDatos(in);
            ValidacionDatosPersonales.guardarDatosPersonales(dp);
        } else if (aux ==2){
            List<DatosPersonales> lista = dao.cargar();
            for (DatosPersonales iterador: lista){
                System.out.println(iterador.toString());
            }
            System.out.println("INGRESE EL ID QUE DESEA CARGAR COMO SUS DATOS");
            Integer id = in.nextInt();
            dp = dao.encontrarId(id);
        }else {System.out.println("valor ingresado invalido");}
        if (dp != null){
            System.out.println("Los datos personales cargados son: "+dp.toString());
        }
        return dp;
    }
}


