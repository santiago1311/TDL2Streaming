package APP.Vista;
import javax.swing.*;
import java.awt.*;

public class ConfirmacionVista extends JDialog {

    public ConfirmacionVista(JFrame parent) {
        super(parent, "Información", true); 
        
        
        setLayout(new BorderLayout(10, 10));

    
        JLabel texto = new JLabel(
            "<html><div style='text-align: center;'>"  // esto nos dice que todo lo que esta en el div va centrado 
            + "Se registró correctamente su Calificación.<br>" // el br es salto de línea en HTML
            + "Muchas gracias." 
            + "</div></html>", 
            SwingConstants.CENTER
        );
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        add(texto, BorderLayout.CENTER);

        // boton continuar
        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonContinuar);
        add(panelBoton, BorderLayout.SOUTH);

        // Tamaño y posición
        setSize(400, 200);
        setLocationRelativeTo(parent); // centrar respecto a la ventana principal
    }

    
    public static void mostrar(JFrame parent) {
        new ConfirmacionVista(parent).setVisible(true);
    }
}
