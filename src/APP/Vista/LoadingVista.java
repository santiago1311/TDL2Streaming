package APP.Vista;
import javax.swing.*;
import java.awt.*;

public class LoadingVista extends JDialog {
    


    public LoadingVista(JFrame parent) {
        super(parent, "Cargando...", true);

        setLayout(new BorderLayout(10, 10));
        
        JLabel titulo = new JLabel("Bienvenido a la Plataforma de Streaming", SwingConstants.LEFT);
        
        add(titulo, BorderLayout.NORTH);

        JLabel imagen = new JLabel("hay que mandar el pad de la imagen ", SwingConstants.CENTER);
        imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(imagen, BorderLayout.CENTER);

        JLabel mensaje = new JLabel("Un momento por favor......", SwingConstants.CENTER);
       
        add(mensaje, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    public static void mostrar(JFrame parent) {
        new LoadingVista(parent).setVisible(true);
    }
}
