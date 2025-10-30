package APP.Modelo.Comparadores;
import java.util.Comparator;
import APP.Modelo.UsuarioCliente;

public class ComparadorPorEmail implements Comparator<UsuarioCliente>
{
    public int compare(UsuarioCliente u1, UsuarioCliente u2){
        return u1.getCorreo().compareToIgnoreCase(u2.getCorreo()); 
    } 
}
