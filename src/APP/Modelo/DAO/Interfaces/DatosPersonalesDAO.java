package APP.Modelo.DAO.Interfaces;
import java.util.List;
import APP.Modelo.DatosPersonales;
public interface DatosPersonalesDAO {
    List<DatosPersonales> cargar();
    void cargarEnLaBase(DatosPersonales dp);
    void eliminar(DatosPersonales dp);
    boolean encontrarDNIExistente(Integer Dni);
    DatosPersonales encontrarId(Integer ID);    
}
