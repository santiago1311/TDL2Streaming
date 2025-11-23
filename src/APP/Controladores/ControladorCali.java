package APP.Controladores;
import APP.Modelo.Validaciones.ValidacionUsuario;
import APP.Vista.CalificacionVista;
import APP.Modelo.DAO.Implementaciones.ResenaDAOjdbc;
import APP.Modelo.Resena;
import APP.Modelo.*;
import APP.Modelo.DAO.Interfaces.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCali implements ActionListener{
    private CalificacionVista calificacion; 

    
    public ControladorCali(CalificacionVista calificacion){
        this.calificacion= calificacion;
        this.calificacion.getBotonGuardar().addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == calificacion.getBotonGuardar()){
            String comentario= new String(calificacion.getComentario().getText());
            String cali = new String (calificacion.getCalificacion().getText());
            int numCalificacion;
            numCalificacion=Integer.parseInt(cali);

            if (comentario.isEmpty() || cali.isEmpty()){
                calificacion.mostrarMensaje("Por favor complete todos los campos.");
            } else if (numCalificacion < 1 || numCalificacion > 5){
                calificacion.mostrarMensaje("La calificación debe estar entre 1 y 5.");
            } else {
                Resena nuevaCalificacion = new Resena(numCalificacion, comentario,null,null,null,null,null);
                ResenaDAOjdbc ResenaDAOjdbc = new ResenaDAOjdbc();
                ResenaDAOjdbc.agregarResena(nuevaCalificacion);
                calificacion.mostrarMensaje("Calificación guardada exitosamente.");
                calificacion.getComentario().setText("");
                calificacion.getCalificacion().setText("");
            }

            calificacion.mostrarMensaje("La calificación no se pudo guardar .");
            calificacion.getComentario().setText("");
            calificacion.getCalificacion().setText("");
        }






    }
}