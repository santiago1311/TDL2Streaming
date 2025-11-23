package APP.Controladores;
import APP.Vista.VistaLog;
import APP.Modelo.Validaciones.ValidacionUsuario;
import APP.Vista.Registrar;
import APP.Modelo.DAO.Implementaciones.*;
import APP.Modelo.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLog implements ActionListener {

    private VistaLog vistaLog;

    public ControladorLog(VistaLog vistaLog) {
        this.vistaLog = vistaLog;

       
        this.vistaLog.getBotonLog().addActionListener(this);
        this.vistaLog.getBotonRegistrarte().addActionListener(this);
        this.vistaLog.getBotonCerrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaLog.getBotonLog()) {

            String email = vistaLog.getCampoEmail().getText().trim();
            String contrasena = new String(vistaLog.getCampoContrasena().getPassword()).trim();


            if (email.isEmpty() || contrasena.isEmpty()) {
                vistaLog.mostrarMensaje("Complete todos los campos.");
                return;
            }

            if (!ValidacionUsuario.correo_valido(email)) {
                vistaLog.mostrarMensaje("Correo inválido.");
                return;
            }
            UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
            UsuarioCliente usuario = dao.encontrarEmail(email); // busca si existe el usuario en la base de datos
            if (usuario == null) {
                vistaLog.mostrarMensaje("Usuario no encontrado.");
                return;
            }
            if (!usuario.getContrasena().trim().equals(contrasena)) {
                vistaLog.mostrarMensaje("Contraseña incorrecta.");
                return;
            }
            vistaLog.mostrarMensaje("¡Inicio de sesión exitoso!");
            // aca se conecta con la ventana principal de la aplicacion
            //new LoadingVista().setVisible(true);
            //vistaLog.dispose(); // Cierra la ventana de login
            //return;
        }

        if (e.getSource() == vistaLog.getBotonRegistrarte()) {
            new Registrar().setVisible(true);
            vistaLog.dispose(); // Cierra la ventana de login
            return;
        }

        if (e.getSource() == vistaLog.getBotonCerrar()) {
            System.exit(0);
        }
    }
}
