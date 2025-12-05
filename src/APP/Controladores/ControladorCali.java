package APP.Controladores;
import APP.Modelo.Validaciones.ValidacionUsuario;
import APP.Vista.CalificacionVista;
import APP.Modelo.DAO.Implementaciones.PeliculaDAOjdbc;
import APP.Modelo.DAO.Implementaciones.ResenaDAOjdbc;
import APP.Modelo.DAO.Implementaciones.UsuarioDAOjdbc;
import APP.Modelo.Resena;
import APP.Modelo.*;
import APP.Modelo.DAO.Interfaces.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import APP.Vista.ConfirmacionVista;


public class ControladorCali implements ActionListener{
    private CalificacionVista calificacion; 
    

    
    public ControladorCali(CalificacionVista calificacion){
        this.calificacion= calificacion;
        this.calificacion.getBotonGuardar().addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String tituloPelicula = calificacion.getTituloDePelicula();
        calificacion.mostrarMensaje("Calificando película: " + tituloPelicula);
        
        if (e.getSource() == calificacion.getBotonGuardar()){
            
            int cali;
            String comentario= new String(calificacion.getComentario().getText());
            try {
                cali = calificacion.getCalificacion();
            } catch (NumberFormatException ex) {
                calificacion.mostrarMensaje("La calificación debe ser un número entero.");
                return;  
            }

            if (comentario.isEmpty() || cali == 0) {
                calificacion.mostrarMensaje("Por favor complete todos los campos.");
                return ;
            }
            if (cali < 1 || cali > 5){
                calificacion.mostrarMensaje("La calificación debe estar entre 1 y 5.");
                return ;
            } 
             
            PeliculaDAOjdbc peliculaDAO = new PeliculaDAOjdbc();
            Pelicula pelicula;
            try{
                pelicula = peliculaDAO.encontrarPelicula(tituloPelicula);
            }catch (Exception ex){
                calificacion.mostrarMensaje("Error al encontrar la película: " + ex.getMessage());
                return;
            }
            UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc();
            try{
            Resena nuevaCalificacion = new Resena(cali*2, comentario,0,"25/09/2025",null,usuarioDAO.encontrar(calificacion.getNombreUsuario()),pelicula);
            ResenaDAOjdbc ResenaDAOjdbc = new ResenaDAOjdbc();
            ResenaDAOjdbc.agregarResena(nuevaCalificacion);


      
             
            new ConfirmacionVista(null).setVisible(true);

            
            calificacion.notificarCalificacionGuardada();// agregado nuevo 

            calificacion.dispose();
            } catch (Exception ex){
                calificacion.mostrarMensaje("Error al guardar la calificación: " + ex.getMessage());
            }
            
            
        }
    }

}
