package APP.Modelo.Validaciones;

import javax.swing.JLabel;

import APP.Modelo.DAO.Implementaciones.DatosPersonalesDAOjdbc;

public class ValidacionDNI {
    public boolean validarDni(String dni, JLabel errorDni, boolean valido) {
        DatosPersonalesDAOjdbc dao = new DatosPersonalesDAOjdbc();
        if (!dni.matches("\\d+")) {
            errorDni.setText("El DNI sólo debe contener números.");
            errorDni.setVisible(true);
            valido = false;
        } else if (dao.encontrarPorDni(Integer.valueOf(dni))!= null) {
            errorDni.setText("El DNI ya está registrado.");
            errorDni.setVisible(true);
            valido = false;
        }
        return valido;
    }

}
