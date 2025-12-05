package APP.Controladores;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import APP.Modelo.Pelicula;

public class ConsultaPeliculasOMDb {
// Reemplazá con tu API Key obtenida en https://www.omdbapi.com/apikey.aspx
private static final String API_KEY = "737854d7";
    public static void main(String[] args) {
        String titulo = "Zootopia 2"; // Reemplazar por elitulo a buscar
        consultarPelicula(titulo);
    }
    public static Pelicula consultarPelicula(String titulo) {
        try {
            // Armar la URL de consulta (encodear espacios con '+')
            String url = "https://www.omdbapi.com/?t=" + titulo.replace(" ",
            "+") + "&apikey=" + API_KEY;
            // Crear cliente y solicitud
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
            HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            if (json.has("Response") && json.getString("Response").equals("True")) {
                Pelicula pelicula = new Pelicula (null, json.getString("Title"), json.getString("Year"),json.getString("Genre").split(",")[0].trim(),json.getString("Poster"),json.getString("Plot"),json.getFloat("imdbRating"));
                return pelicula;
            } else {
                System.out.println("Película no encontrada o error en la consulta");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
            return null;
        }
    }
}