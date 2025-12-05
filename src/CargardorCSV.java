import java.sql.*;
import java.io.*;
import java.util.*;

import APP.Modelo.DAO.Implementaciones.MiConexion;

public class CargardorCSV {

    private static String[] parseCSVLine(String line) {
        ArrayList<String> cols = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                cols.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        cols.add(current.toString());

        return cols.toArray(new String[0]);
    }

    public static void cargarPeliculas() {
        String csvFile = "movies_database.csv";

        try (Connection conn = MiConexion.getCon()) {

            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String line;

            // Leer encabezado
            reader.readLine();

            // Verificar columnas existentes en la tabla PELICULA
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("PRAGMA table_info(PELICULA)");

            Set<String> existentes = new HashSet<>();
            while (rs.next()) {
                existentes.add(rs.getString("name").toUpperCase());
            }

            // Crear columnas si faltan
            String[][] columnas = {
                    {"FECHA_ESTRENO", "TEXT"},
                    {"TITULO", "TEXT"},
                    {"RESUMEN", "TEXT"},
                    {"POPULARIDAD", "REAL"},
                    {"VOTOS", "INTEGER"},
                    {"PUNTAJE", "REAL"},
                    {"IDIOMA_ORIGINAL", "TEXT"},
                    {"GENERO", "TEXT"},
                    {"POSTER", "TEXT"}
            };

            for (String[] col : columnas) {
                if (!existentes.contains(col[0])) {
                    st.execute("ALTER TABLE PELICULA ADD COLUMN " + col[0] + " " + col[1]);
                }
            }

            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PELICULA (" +
                            "TITULO, RESUMEN, GENERO, FECHA_ESTRENO, POPULARIDAD, VOTOS, PUNTAJE, IDIOMA_ORIGINAL, POSTER" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            int filasSaltadas=0;
            while ((line = reader.readLine()) != null) {
                String[] row = parseCSVLine(line);
                if (row.length < 9) {
                    filasSaltadas++;
                    continue;
                }
                String puntajeStr = row[5].trim();
                if (puntajeStr.isEmpty()) {
                    filasSaltadas++;
                    continue;
                }
                double puntaje;
                try {
                    puntaje = Double.parseDouble(puntajeStr);
                    if (puntaje > 10){
                        filasSaltadas++;
                        continue;
                    }
                } catch (NumberFormatException e) {
                    filasSaltadas++;
                    continue;
                }

                // Si llegó acá, el puntaje es válido → insertar
                ps.setString(1, row[1]);  // TITULO
                ps.setString(2, row[2]);  // RESUMEN
                ps.setString(3, row[7]);  // GENERO
                ps.setString(4, row[0]);  // FECHA_ESTRENO
                ps.setString(5, row[3]);  // POPULARIDAD
                ps.setString(6, row[4]);  // VOTOS
                ps.setDouble(7, puntaje); // PUNTAJE (numérico)
                ps.setString(8, row[6]);  // IDIOMA_ORIGINAL
                ps.setString(9, row[8]);  // POSTER

                ps.executeUpdate();
            }

            conn.commit();
            System.out.println("\nIMPORTACIÓN COMPLETADA — TABLA PELICULA ACTUALIZADA CORRECTAMENTE");
            System.out.println("Filas saltadas por algun error en formato: "+ filasSaltadas);
            reader.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
