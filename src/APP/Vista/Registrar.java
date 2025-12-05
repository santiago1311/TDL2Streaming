package APP.Vista;

import javax.swing.*;

import APP.Controladores.ControladorReg;

import java.awt.*;
import java.awt.event.*;

public class Registrar extends JFrame {
    private JTextField campoNombre, campoApellido, campoEmail, campoNombreUsuario, campoContrasena, campoConfirmarContrasena, campoDni, campoIdioma;
    private JButton botonRegistrar;
    private ImageIcon imagenOriginal;
    private JLabel etiquetaImagen;
    private JLabel errorEmail, errorContrasena, errorDni, etiquetaError;
    private JButton cerrar;
    
    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }

    public Registrar() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        campoApellido = new JTextField("Perez", 25);
        campoNombre = new JTextField("juan", 25);
        campoNombreUsuario = new JTextField("juan123", 25);
        campoEmail = new JTextField("juan@example", 25);
        campoContrasena = new JPasswordField("********", 25);
        campoConfirmarContrasena = new JPasswordField("********", 25);
        campoDni = new JTextField("12345678", 25);
        campoIdioma = new JTextField("Español", 25);
        botonRegistrar = new JButton("Registrar");
        errorEmail = createErrorLabel("");
        errorContrasena = createErrorLabel("");
        errorDni = createErrorLabel("");
        int row = 0;
        row = addRow(panelFormulario, gbc, row, "Nombre:", campoNombre, null);
        row = addRow(panelFormulario, gbc, row, "Apellido:", campoApellido, null);
        row = addRow(panelFormulario, gbc, row, "Email:", campoEmail, errorEmail);
        row = addRow(panelFormulario, gbc, row, "Nombre de Usuario:", campoNombreUsuario, null);
        row = addRow(panelFormulario, gbc, row, "Contraseña:", campoContrasena, errorContrasena);
        row = addRow(panelFormulario, gbc, row, "Confirmar Contraseña:", campoConfirmarContrasena, null);
        row = addRow(panelFormulario, gbc, row, "DNI:", campoDni, errorDni);
        row = addRow(panelFormulario, gbc, row, "Idioma:", campoIdioma, null);
        gbc.gridx = 1; gbc.gridy = row; gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(botonRegistrar, gbc);
        etiquetaError= createErrorLabel("");
        gbc.gridy = row + 1; gbc.gridx = 1;
        panelFormulario.add(etiquetaError, gbc);
        etiquetaError.setVisible(true);
        JPanel panelImagen = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("TDL2 Streaming", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 37));

        java.net.URL urlImagen = Registrar.class.getResource("../Imagenes/Logo.jpg");

        if (urlImagen != null) {
            imagenOriginal = new ImageIcon(urlImagen);
        } else {
            System.err.println("Error: No se encontró el recurso de imagen en ../Imagenes/Logo.jpg");
        }
        etiquetaImagen = new JLabel();
        etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
        panelImagen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int ancho = panelImagen.getWidth();
                int alto = panelImagen.getHeight();
                if (ancho > 0 && alto > 0 && imagenOriginal != null) {
                    Image imagenEscalada = imagenOriginal.getImage()
                            .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    etiquetaImagen.setIcon(new ImageIcon(imagenEscalada));
                }
            }
        });

        panelImagen.add(titulo, BorderLayout.NORTH);
        panelImagen.add(etiquetaImagen, BorderLayout.CENTER);
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelImagen, BorderLayout.EAST);

        setTitle("Registro de Usuario");
        setContentPane(panelPrincipal);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ControladorReg controlador = new ControladorReg(this);
    }

    private JLabel createErrorLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.RED);
        label.setVisible(false);//Ocultar por defecto el mensaje de error
        return label;
    }

    private int addRow(JPanel panel, GridBagConstraints gbc, int y, String labelText, JTextField field, JLabel errorLabel){
        if (errorLabel != null) {
            gbc.gridx = 1; gbc.gridy = y;
            gbc.gridwidth = 1; 
            gbc.anchor = GridBagConstraints.WEST; 
            errorLabel.setText(" ");
            panel.add(errorLabel, gbc);
            y++; 
        }
        
        gbc.gridx = 0; gbc.gridy = y;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(field, gbc);
        return y + 1;//row+1
    }

    public void mostrarMensaje(String mensaje) {
        etiquetaError.setText(mensaje);
    }
    

    public JTextField getCampoNombre() {
        return campoNombre;
    }

    public JTextField getCampoApellido() {
        return campoApellido;
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JTextField getCampoNombreUsuario() {
        return campoNombreUsuario;
    }

    public JTextField getCampoContrasena() {
        return campoContrasena;
    }

    public JTextField getCampoConfirmarContrasena() {
        return campoConfirmarContrasena;
    }

    public JTextField getCampoDni() {
        return campoDni;
    }

    public JTextField getCampoIdioma() {
        return campoIdioma;
    }

    public JLabel getErrorEmail() {
        return errorEmail;
    }

    public JLabel getErrorContrasena() {
        return errorContrasena;
    }

    public JLabel getErrorDni() {
        return errorDni;
    }
    public JLabel getEtiquetaError() {
        return etiquetaError;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Registrar());
    }
}