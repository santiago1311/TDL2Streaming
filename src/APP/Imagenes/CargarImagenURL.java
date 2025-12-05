package APP.Imagenes;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class CargarImagenURL {

    public static ImageIcon cargarImagenDesdeURL(String ruta, int ancho, int alto) {
        try {
            URL imagenURL;

            if (ruta.startsWith("http")) {
                // URL externa
                imagenURL = new URL(ruta);

            } else {
                // Cargar como recurso interno del jar
                imagenURL = CargarImagenURL.class.getResource(ruta.startsWith("/") ? ruta : "/" + ruta);

                if (imagenURL == null) {
                    throw new Exception("Recurso interno no encontrado: " + ruta);
                }
            }

            ImageIcon original = new ImageIcon(imagenURL);
            Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);

        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen: " + e.getMessage());

            URL urlDefault = CargarImagenURL.class.getResource("/APP/Imagenes/noImage.jpg");
            ImageIcon defaultImg = new ImageIcon(urlDefault);

            Image scaled = defaultImg.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
    }
}
