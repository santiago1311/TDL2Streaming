package APP.Vista;

import javax.swing.*;
import java.awt.*;
import APP.Controladores.ControladorLog;

public class VistaLog extends JFrame {
    ControladorLog controlador;
    private JTextField campoEmail;
    private JPasswordField campoContrasena;
    private JButton botonLog, botonRegistrarte, botonCerrar;
    private ImageIcon imagenOriginal;
    private JLabel etiquetaImagen;
    private JLabel etiquetaError = new JLabel("");


    public VistaLog() {
        campoEmail = new JTextField(15);
        campoContrasena = new JPasswordField(15);
        botonLog = new JButton("Ingresar");
        botonRegistrarte = new JButton("Registrarse");
        botonCerrar = new JButton("X");

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.EAST; 

        //  Email
        addRow(panelFormulario, gbc, 0, "Email:", campoEmail);

        // Contraseña
        addRow(panelFormulario, gbc, 1, "Contraseña:", campoContrasena);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(botonLog, gbc);
        gbc.gridy = 4;
        gbc.gridx = 1;
        etiquetaError.setForeground(Color.RED);
        panelFormulario.add(etiquetaError, gbc);
        gbc.gridy = 3;
        panelFormulario.add(botonRegistrarte, gbc);
        JPanel panelImagen = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("TDL2 Streaming", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        java.net.URL urlImagen = ClassLoader.getSystemClassLoader().getResource("APP/Imagenes/Logo.jpg");
        imagenOriginal = new ImageIcon(urlImagen);
        etiquetaImagen = new JLabel(imagenOriginal);
        etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
        panelImagen.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int ancho = panelImagen.getWidth();
                int alto = panelImagen.getHeight();
                if (ancho > 0 && alto > 0) {
                    Image img = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    etiquetaImagen.setIcon(new ImageIcon(img));
                }
            }
        });

        panelImagen.add(titulo, BorderLayout.NORTH);
        panelImagen.add(etiquetaImagen, BorderLayout.CENTER);

        
        JPanel panelCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonCerrar.setPreferredSize(new Dimension(45, 25));
        panelCerrar.add(botonCerrar);

        botonCerrar.addActionListener(e -> System.exit(0));

     
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelImagen, BorderLayout.CENTER);
        panelPrincipal.add(panelCerrar, BorderLayout.NORTH);

        
        setTitle("Ingreso de Usuario");
        setContentPane(panelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        controlador = new ControladorLog(this);
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

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JPasswordField getCampoContrasena() {
        return campoContrasena;
    }

    public JButton getBotonLog() {
        return botonLog;
    }

    public JButton getBotonRegistrarte() {
        return botonRegistrarte;
    }

    public JButton getBotonCerrar() {
        return botonCerrar;
    }

    public void setError(String mensaje) {
        etiquetaError.setText(mensaje);
    }

    public void setCampoEmail(JTextField campoEmail) {
        this.campoEmail = campoEmail;
    }

    public void setCampoContrasena( JPasswordField campoContrasena) {
        this.campoContrasena = campoContrasena;
    }

    public void mostrarMensaje(String mensaje) {
        etiquetaError.setText(mensaje);
    }

}
