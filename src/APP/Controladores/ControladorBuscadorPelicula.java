package APP.Controladores;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import APP.Modelo.DatosPersonales;
import APP.Modelo.Pelicula;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Vista.*;


public class ControladorBuscadorPelicula implements ActionListener{
    private BuscadorPelicula vistaBuscador;
    private ListadoPeliculasVista vistaListado;
    
    public ControladorBuscadorPelicula(BuscadorPelicula vistaBuscador, ListadoPeliculasVista vistaListado, String titulo) {
        this.vistaBuscador = vistaBuscador;
        this.vistaBuscador.getBotonContinuar().addActionListener(this);
        this.vistaListado = vistaListado;
        PeliculaDAOjdbc dao = new PeliculaDAOjdbc();
        Pelicula peli=null;
        try{
            peli = dao.encontrarPelicula(titulo);
            if (peli == null){
                peli = ConsultaPeliculasOMDb.consultarPelicula(titulo);
            }
        }catch(Exception e){
            System.out.println(e);
            //si entro aca algo fallo al encontrar la pelicula en la base de datos
        }    
        if (peli != null){
            vistaBuscador.cargarPelicula(peli);

        }else{
            vistaListado.setError("No se encontro la pelicula: "+titulo);
        }
    }

    

    public void actionPerformed(ActionEvent e) {
        vistaBuscador.dispose();
        vistaListado.setVisible(true);
    }
}


