package APP.Modelo.DAO.Interfaces;
import java.util.List;
import APP.Modelo.Resena;
public interface ResenaDAO{
    void agregarResena(Resena resena);
    Resena obtenerResenaId(int id);
    Resena obtenerResena(String criterio);
    List<Resena> obtenerTodasResenas();
    void actualizarResena(int id);
    void eliminarResena(int id);
}