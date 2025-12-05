package APP.Controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import APP.Modelo.Validaciones.*;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Vista.Registrar;
import APP.Vista.VistaLog;
import APP.Modelo.DatosPersonales;
import APP.Modelo.UsuarioCliente;


public class ControladorReg implements ActionListener{
    
    private ValidacionEmail valEmail;
    private ValidacionDNI valDNI;
    private Registrar registrar;
    private JButton botonRegistrarte;

    public ControladorReg(Registrar registrar) {
        this.registrar = registrar;
        this.registrar.getBotonRegistrar().addActionListener(this);
        valEmail = new ValidacionEmail();
        valDNI = new ValidacionDNI();
    }

    public boolean validarRegistro(String email, String dni, JLabel errorEmail, JLabel errorDni, boolean valido, String contrasena, String confirmar) {
        valido = valEmail.validarEmail(email, errorEmail, valido);
        valido = valDNI.validarDni(dni, errorDni, valido);
        if (!contrasena.equals(confirmar)) {
            registrar.getErrorContrasena().setText("Las contraseñas no coinciden.");
            registrar.getErrorContrasena().setVisible(true);
            valido = false;
        }
        return valido;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = registrar.getCampoEmail().getText().trim();
        String dni = registrar.getCampoDni().getText().trim();
        String contrasena = new String(((JPasswordField)registrar.getCampoContrasena()).getPassword());
        String confirmar = new String(((JPasswordField)registrar.getCampoConfirmarContrasena()).getPassword());
        String nombre = registrar.getCampoNombre().getText().trim();
        String apellido = registrar.getCampoApellido().getText().trim();
        String nombreUsuario = registrar.getCampoNombreUsuario().getText().trim();
        String idioma = registrar.getCampoIdioma().getText().trim();
        boolean valido = true;
        valido = validarRegistro(email, dni, registrar.getErrorEmail(), registrar.getErrorDni(), valido,contrasena, confirmar);
        if (valido) {
            UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
            DatosPersonales dp = new DatosPersonales(nombre, apellido, Integer.valueOf(dni),null);
            DatosPersonalesDAOjdbc daoDP = new DatosPersonalesDAOjdbc();
            daoDP.cargarEnLaBase(dp);
            UsuarioCliente nuevoUsuario = new UsuarioCliente(email, contrasena, dp, null, idioma, nombreUsuario);
            dao.registrar(nuevoUsuario);
            registrar.getEtiquetaError().setForeground(java.awt.Color.GREEN);
            registrar.mostrarMensaje("Registro exitoso.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            registrar.dispose();
            new VistaLog().setVisible(true);
            return;
        } else {
            registrar.mostrarMensaje("Error en el registro.");
            if(registrar.getErrorEmail().isVisible()) registrar.getCampoEmail().requestFocus();
            else if(registrar.getErrorContrasena().isVisible()) registrar.getCampoContrasena().requestFocus();
            else if(registrar.getErrorDni().isVisible()) registrar.getCampoDni().requestFocus();
            return;
        }

    }

}
