package APP.Vista;
import javax.swing.*;
import java.awt.*;
import APP.Controladores.ControladorCali;



public class CalificacionVista extends JFrame {
    private ControladorCali controlador;
    private JTextField calificacion;
    private JTextField comentario;
    private JLabel etiquetaError = new JLabel("");
    private JButton botonGuardar;

    public CalificacionVista(){
        calificacion = new JTextField(3);
        comentario = new JTextField(20);
        botonGuardar = new JButton("GUARDAR");

        JPanel panelPrincipal= new JPanel(new BorderLayout());
        JPanel panelformulario= new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel titulo = new JLabel("Calificar Película", SwingConstants.LEFT);
        gbc.insets= new Insets(8,8,8,8);
        gbc.anchor= GridBagConstraints.EAST;

        // para la calificacion 
        addRow(panelformulario,gbc,0,"Calificacion:",calificacion);

        // para el comentario
        addRow(panelformulario,gbc,1,"Comentario:",comentario);

        // boton
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.CENTER;
        panelformulario.add(botonGuardar,gbc);

        // Mensaje de error 
        gbc.gridx = 1;
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.CENTER;
        panelformulario.add(etiquetaError, gbc);

        panelPrincipal.add(titulo, BorderLayout.NORTH); 
        panelPrincipal.add(panelformulario,BorderLayout.CENTER);
    
        setTitle("Calificacion");
        setContentPane(panelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        controlador = new ControladorCali(this);

    }
    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String texto, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(texto), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campo, gbc);
    }

    public JTextField getCalificacion() {
        return calificacion;
    }
    public JTextField getComentario() {
        return comentario;
    }
    public JButton getBotonGuardar() {
        return botonGuardar;
    }

    public void setCalificacion(JTextField calificacion) {
        this.calificacion = calificacion;
    }
    public void setComentario(JTextField comentario) {
        this.comentario = comentario;
    }

    public void setError(String mensaje) {
        etiquetaError.setText(mensaje);
    }
    public void mostrarMensaje(String mensaje) {
        etiquetaError.setText(mensaje);
    }
    public static void main(String[] args) {
        
        new CalificacionVista();
    }

}