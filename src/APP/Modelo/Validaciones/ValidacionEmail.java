package APP.Modelo.Validaciones;

import javax.swing.JLabel;

import APP.Modelo.DAO.Implementaciones.UsuarioDAOjdbc;

public class ValidacionEmail {
    public boolean validarEmail(String email, JLabel errorEmail, boolean valido) {
        UsuarioDAOjdbc dao = new UsuarioDAOjdbc();
        if (!email.contains("@") || !email.contains(".")) {
            errorEmail.setText("El email debe contener '@' y dominio.");
            errorEmail.setVisible(true);
            valido = false;
        } else if (dao.encontrarEmail(email)!= null) {
            errorEmail.setText("El email ya está registrado.");
            errorEmail.setVisible(true);
            valido = false;
        }
        return valido;
    }
}
