package APP.Imagenes;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;

import APP.Vista.ListadoPeliculasVista; 
public class CargarImagenURL {

    public static ImageIcon cargarImagenDesdeURL(String url, int ancho, int alto) {
        try {
            URL imagenURL = new URL(url);
            ImageIcon imagenOriginal = new ImageIcon(imagenURL);
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (Exception e){
            System.out.println("No se pudo cargar imagen: " + e.getMessage());
            java.net.URL urlImagen = ListadoPeliculasVista.class.getResource("../Imagenes/noImage.jpg");
            ImageIcon imagenOriginal = new ImageIcon(urlImagen);
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        }
    }
}
