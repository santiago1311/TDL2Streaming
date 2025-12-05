package APP.Vista;
import javax.swing.*;
import java.awt.*;

public class LoadingVista extends JDialog {
    


    public LoadingVista(JFrame parent) {
        super(parent, "Cargando...", true);

        setLayout(new BorderLayout(10, 10));
        
        JLabel titulo = new JLabel("Bienvenido a la Plataforma de Streaming", SwingConstants.LEFT);
        
        
        add(titulo, BorderLayout.NORTH);
        java.net.URL urlImagen = ListadoPeliculasVista.class.getResource("../Imagenes/Loading_icon.gif");
        ImageIcon iconoLoading = new ImageIcon(urlImagen);
        JLabel imagen = new JLabel(iconoLoading, SwingConstants.CENTER);
        imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(imagen, BorderLayout.CENTER);

        JLabel mensaje = new JLabel("Un momento por favor......", SwingConstants.CENTER);
       
        add(mensaje, BorderLayout.SOUTH);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    public static LoadingVista mostrar(JFrame parent) {
        LoadingVista lv = new LoadingVista(parent);
        lv.setVisible(true);
        return lv;
    }
}
