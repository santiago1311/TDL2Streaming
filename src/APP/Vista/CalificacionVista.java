package APP.Vista;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import APP.Controladores.ControladorCali;


public class CalificacionVista extends JFrame {
    private int idPelicula;
    private String tituloDePelucula;
    private int calificacionSeleccionada = 0; 
    private ControladorCali controlador;
    
   
    private JPanel ratingPanel; 
    
    private JTextField comentario;
    private JLabel etiquetaError = new JLabel("");
    private JButton botonGuardar;
    private String nombreUsuario;
    
    // Array de JLabels para las estrellas
    private JLabel[] estrellas = new JLabel[5]; 
    
    // Iconos 
    private ImageIcon iconoEstrellaVacia;
    private ImageIcon iconoEstrellaLlena;

    private JFrame padre;


    public CalificacionVista(String tituloDePelucula, String nombreUsuario,JFrame padre) {
        this.padre=padre;
       
        
        try {
            
            URL urlLlena = CalificacionVista.class.getResource("../Imagenes/estrellaLlena.png");
            URL urlVacia = CalificacionVista.class.getResource("../Imagenes/estrellaVacia.png");
            
            if (urlLlena != null && urlVacia != null) {
                iconoEstrellaLlena = new ImageIcon(urlLlena);
                iconoEstrellaVacia = new ImageIcon(urlVacia);
                
               
                int tam = 30;
                iconoEstrellaLlena = scaleIcon(iconoEstrellaLlena, tam, tam);
                iconoEstrellaVacia = scaleIcon(iconoEstrellaVacia, tam, tam);
            } else {
                 // Fallback si las imágenes no se encuentran
                System.err.println("Advertencia: No se encontraron iconos de estrellas. Usando texto.");
            }
        } catch (Exception e) {
             System.err.println("Error al cargar iconos de estrellas: " + e.getMessage());
        }
       

        this.nombreUsuario = nombreUsuario;
        this.tituloDePelucula = tituloDePelucula;
        comentario = new JTextField(20);
        botonGuardar = new JButton("GUARDAR");
        ratingPanel = crearPanelEstrellas();
        JPanel panelPrincipal= new JPanel(new BorderLayout());
        JPanel panelformulario= new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel titulo = new JLabel("Calificar Película", SwingConstants.LEFT);
        
        JLabel lblTituloPeli = new JLabel("Película: " + tituloDePelucula);
        lblTituloPeli.setFont(new Font("Arial", Font.BOLD, 18));
        lblTituloPeli.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloPeli.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        gbc.insets= new Insets(8,8,8,8);
        gbc.anchor= GridBagConstraints.EAST;

        // para la calificacion (usando el nuevo ratingPanel)
        addRow(panelformulario, gbc, 0, "Calificación:", ratingPanel);

        // para el comentario
        addRow(panelformulario, gbc, 1, "Comentario:", comentario);

        // boton
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.CENTER;
        panelformulario.add(botonGuardar,gbc);

        // Mensaje de error 
        gbc.gridx = 1;
        gbc.gridy = 3; 
        gbc.anchor = GridBagConstraints.CENTER;
        etiquetaError.setForeground(Color.RED);
        panelformulario.add(etiquetaError, gbc);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.add(titulo);
        panelSuperior.add(lblTituloPeli);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelformulario,BorderLayout.CENTER);
    
        setTitle("Calificacion");
        setContentPane(panelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Mejor usar DISPOSE_ON_CLOSE para ventanas secundarias
        //setVisible(true);
        //controlador = new ControladorCali(this);

    }
    
    private JPanel crearPanelEstrellas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        for (int i = 0; i < 5; i++) {
            final int calificacionEstrella = i + 1;
            JLabel estrella = new JLabel();
            estrellas[i] = estrella; 
            setStarIcon(estrella, false);
            estrella.setCursor(new Cursor(Cursor.HAND_CURSOR));
            estrella.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    calificacionSeleccionada = calificacionEstrella; 
                    actualizarEstrellas(calificacionSeleccionada);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    actualizarEstrellas(calificacionEstrella);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    actualizarEstrellas(calificacionSeleccionada);
                }
            });
            
            panel.add(estrella);
        }
        return panel;
    }
    
    private void actualizarEstrellas(int calificacion) {
        for (int i = 0; i < 5; i++) {
            boolean estaLlena = (i < calificacion);
            setStarIcon(estrellas[i], estaLlena);
        }
    }
    private void setStarIcon(JLabel label, boolean llena) {
        if (llena) {
            if (iconoEstrellaLlena != null) {
                label.setIcon(iconoEstrellaLlena);
                label.setText(null);
            } else {
                label.setText("★");
                label.setFont(new Font("Arial", Font.BOLD, 30));
                label.setForeground(Color.ORANGE);
            }
        } else {
            if (iconoEstrellaVacia != null) {
                label.setIcon(iconoEstrellaVacia);
                label.setText(null);
            } else {
                label.setText("☆");
                label.setFont(new Font("Arial", Font.BOLD, 30));
                label.setForeground(Color.GRAY);
            }
        }
    }
    
    private ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String texto, JComponent componente) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(texto), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(componente, gbc);
    }
    
    public int getCalificacion() {
        return calificacionSeleccionada;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdPelicula() {
        return idPelicula;
    }
    public String getTituloDePelicula() {
        return tituloDePelucula;
    }
    public JTextField getComentario() {
        return comentario;
    }
    
    public JButton getBotonGuardar() {
        return botonGuardar;
    }
    public JPanel getRatingPanel() {
        return ratingPanel;
    }

    public void setComentario(JTextField comentario) {
        this.comentario = comentario;
    }

    public void setError(String mensaje) {
        etiquetaError.setText(mensaje);
        etiquetaError.setForeground(Color.RED);
    }
    public void mostrarMensaje(String mensaje) {
        etiquetaError.setText(mensaje);
        etiquetaError.setForeground(Color.BLUE); 
    }
    
    
  
    private Runnable onCalificacionGuardada;

    public void setOnCalificacionGuardada(Runnable r) {
        this.onCalificacionGuardada = r;
    }

    public void notificarCalificacionGuardada() {
        if (onCalificacionGuardada != null) 
            onCalificacionGuardada.run();
        if (padre!=null){
            padre.setVisible(true);
        }
         
    }

    
    public static void main(String[] args) {
        // Ejemplo de uso:
        SwingUtilities.invokeLater(() -> {
            new CalificacionVista("Interstellar", "r91",null);
        });
    }

}