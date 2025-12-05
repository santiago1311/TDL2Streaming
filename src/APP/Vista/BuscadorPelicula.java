package APP.Vista;

import javax.swing.*;
import java.awt.*;
import APP.Controladores.ControladorBuscadorPelicula;
import APP.Modelo.Pelicula;
import APP.Vista.ListadoPeliculasVista;
import APP.Imagenes.CargarImagenURL;
import APP.Vista.*;

public class BuscadorPelicula extends JFrame {

    JLabel titulo, imagenLabel;
    JButton botonContinuar;

    public BuscadorPelicula(String pelicula, ListadoPeliculasVista vistaListado) {
        super("TDL2 - Información de la Película");
        setSize(800, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.botonContinuar = new JButton("Continuar");
        this.botonContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        ControladorBuscadorPelicula con = new ControladorBuscadorPelicula(this, vistaListado, pelicula);
    }

    public void cargarPelicula(Pelicula pelicula){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.CENTER);
        imagenLabel = new JLabel();
        imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon poster = CargarImagenURL.cargarImagenDesdeURL(
                pelicula.getPoster(),
                100,
                150                  
        );
        imagenLabel.setIcon(poster);
        panel.add(imagenLabel);
        panel.add(Box.createVerticalStrut(15));

        titulo = new JLabel(pelicula.getTitulo());
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(15));
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.add(new JLabel("Año:"));
        infoPanel.add(new JLabel(String.valueOf(pelicula.getAnio())));
        infoPanel.add(new JLabel("Género:"));
        infoPanel.add(new JLabel(pelicula.getGenero().toString()));
        infoPanel.add(new JLabel("Puntuación Promedio:"));
        infoPanel.add(new JLabel(String.valueOf(pelicula.getRating_promedio())));
        panel.add(infoPanel);

        panel.add(Box.createVerticalStrut(15));
        panel.add(new JLabel("Resumen:"));

        JTextArea sinopsis = new JTextArea(pelicula.getSinopsis());
        sinopsis.setFont(new Font("Arial", Font.PLAIN, 14));
        sinopsis.setLineWrap(true);
        sinopsis.setWrapStyleWord(true);
        sinopsis.setEditable(false);
        JScrollPane scroll = new JScrollPane(sinopsis);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setMinimumSize(new Dimension(550, 150));
        scroll.setMaximumSize(new Dimension(550, 300));
        scroll.setPreferredSize(new Dimension(550, 200));
        panel.add(scroll);


        panel.add(Box.createVerticalStrut(15));
        panel.add(this.botonContinuar);
    }

    public JLabel getTitulo() {
        return titulo;
    }

    public JButton getBotonContinuar() {
        return this.botonContinuar;
    }

    public JLabel getImagenLabel() {
        return imagenLabel;
    }

}
