package APP.Controladores;

import APP.Vista.*;
import APP.Modelo.DAO.Implementaciones.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

import APP.Excepciones.EncontrarPeliculaException;
import APP.Modelo.*;


public class ControladorListadoPeliculas implements ActionListener {
    private ListadoPeliculasVista vista;
    private PeliculaDAOjdbc dao;

    public ControladorListadoPeliculas(ListadoPeliculasVista vista ) {
        this.vista = vista;
        this.vista.getBotonBuscar().addActionListener(this);
        this.vista.getBotonCerrarSesion().addActionListener(this);
        this.vista.getTabla().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() != 1) return;
                if (vista.getTabla().isEditing()) {
                    vista.getTabla().getCellEditor().stopCellEditing();
                }

                int fila = vista.getTabla().rowAtPoint(e.getPoint());
                int columna = vista.getTabla().columnAtPoint(e.getPoint());
                if (columna == 4 && fila != -1) {
                    int filaModelo = vista.getTabla().convertRowIndexToModel(fila);
                    abrirCalificacion(filaModelo);
                }
            }
        });
    }

    public void listado(int auxiliar){
        dao = new PeliculaDAOjdbc();
        try {
            List<Pelicula> peliculas;
            UsuarioDAOjdbc usuarioDAO = new UsuarioDAOjdbc();
            UsuarioCliente usuarioActual = usuarioDAO.encontrar(vista.getNombreUsuarioActual());
            if (!usuarioDAO.inicioSesionOno(usuarioActual)) peliculas = dao.listarMejorRankeadas();
            else peliculas = dao.listarRandom(10);

            List<Resena> resenasUsuario = new ResenaDAOjdbc().obtenerResenasPorUsuario(usuarioActual);
            Boolean haResenado= false;
            for (Pelicula pelicula : peliculas) {
                haResenado = fueResenada(pelicula, resenasUsuario);
                if (auxiliar != 0){
                    while (haResenado || pelicula == null){
                        pelicula = dao.buscadorRandom();
                        haResenado = fueResenada(pelicula, resenasUsuario);
                    }
                }
                vista.agregarPelicula(pelicula.getPoster(),pelicula.getTitulo(),pelicula.getGenero().name(), pelicula.getSinopsis(),haResenado );
            }
        } catch (Exception e) {
            vista.setError("Error al cargar el listado de películas: " + e.getMessage());
        }
    }

    public Boolean fueResenada(Pelicula pelicula,List<Resena> resenasUsuario){
        Boolean haResenado = false;
        if (resenasUsuario != null && !resenasUsuario.isEmpty()) {
            for (Resena resena : resenasUsuario) {
                if (resena.getPelicula().getTitulo().equals(pelicula.getTitulo())) {
                    haResenado= true;
                    break;
                } else {
                    haResenado = false;
                }
            }
        } else {
            haResenado = false;
        }
        return haResenado;
    }

    public void abrirCalificacion(int filaModelo) {
        try {
            String titulo2 = vista.getTabla().getModel().getValueAt(filaModelo, 1).toString();
            Pelicula peliculaSeleccionada = new PeliculaDAOjdbc().encontrarPelicula(titulo2);
            CalificacionVista calificacion = new CalificacionVista(
                titulo2,
                vista.getNombreUsuarioActual(),
                vista
            );
            new ControladorCali(calificacion);
            vista.marcarComoCalificada(filaModelo);
            if (vista.getTabla().isEditing()) {
                vista.getTabla().getCellEditor().stopCellEditing();
            }
            vista.getTabla().repaint();

            vista.setVisible(false);
            calificacion.setVisible(true);
        } catch (Exception ex) {
            vista.setError("Error al abrir calificación: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == vista.getBotonBuscar()) {
            String titulo = vista.getCampoBuscar().getText().trim().toLowerCase();

            PeliculaDAOjdbc dao = new PeliculaDAOjdbc();
            if (titulo != null) {
                LoadingVista loading = new LoadingVista(vista);
                buscarPeliculaEnSegundoPlano(titulo, loading, vista);
                loading.setVisible(true);
                
            }else{
                vista.setError("No se ingreso ninguna pelicula para buscar");
            }
        }
        if (e.getSource() == vista.getBotonCerrarSesion()) {
            vista.dispose();
        }
    }

    private void buscarPeliculaEnSegundoPlano(String pelicula, LoadingVista loading, ListadoPeliculasVista vista) {
        new Thread(() -> {
            try {
                vista.setVisible(false);
               
                final BuscadorPelicula buscador = new BuscadorPelicula(pelicula, vista);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    loading.dispose();
                    buscador.setVisible(true);
                });
            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }


}
