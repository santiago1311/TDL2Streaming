package APP.Modelo.DAO.Interfaces;
import java.util.List;
import APP.Modelo.UsuarioCliente;

public interface UsuarioDAO {
    List<UsuarioCliente> getUsuarios();
    void registrar(UsuarioCliente u);
}
