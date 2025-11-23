package APP.Vista;

import javax.swing.*;
import java.awt.*;

import APP.Controladores.ControladorBuscadorPelicula;
import APP.Modelo.Genero;
import APP.Modelo.Pelicula;

public class BuscadorPelicula extends JFrame {

    JLabel titulo, genero, anio, director, sinopsis;
    JButton botonContinuar;

    public BuscadorPelicula(Pelicula pelicula) {
    super("TDL2 - Información de la Película");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    add(panel, BorderLayout.CENTER);
    JLabel titulo = new JLabel(pelicula.getTitulo());
    titulo.setFont(new Font("Arial", Font.BOLD, 22));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createVerticalStrut(15));
    JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
    infoPanel.add(new JLabel("Año:"));
    infoPanel.add(new JLabel(pelicula.getAnio()));
    infoPanel.add(new JLabel("Género:"));
    infoPanel.add(new JLabel(pelicula.getGenero().toString()));
    infoPanel.add(new JLabel("Puntuacion Promedio:"));
    infoPanel.add(new JLabel(pelicula.getMetadatos().getDirector()));
    panel.add(infoPanel);
    panel.add(Box.createVerticalStrut(15));
    panel.add(new JLabel("Resumen:"));
    JTextArea sinopsis = new JTextArea(pelicula.getSinopsis());
    sinopsis.setFont(new Font("Arial", Font.PLAIN, 14));
    sinopsis.setLineWrap(true);
    sinopsis.setWrapStyleWord(true);
    sinopsis.setEditable(false);
    JScrollPane scroll = new JScrollPane(sinopsis);
    scroll.setPreferredSize(new Dimension(550, 150));
    panel.add(scroll);
    panel.add(Box.createVerticalStrut(15));
    JButton botonContinuar = new JButton("Continuar");
    botonContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(botonContinuar);

    setVisible(true);
}

    public JLabel getTitulo() {
        return titulo;
    }

    public static void main(String[] args) {
        ControladorBuscadorPelicula con = new ControladorBuscadorPelicula();
        Pelicula pelicula = con.buscarPeliculaPorTitulo("Spider-MaN");
        new BuscadorPelicula(pelicula);
    }
}
