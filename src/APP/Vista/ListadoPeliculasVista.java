package APP.Vista;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.swing.table.TableCellEditor;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import APP.Controladores.ControladorListadoPeliculas;
import APP.Modelo.Pelicula;
import java.util.List;
import static APP.Imagenes.CargarImagenURL.cargarImagenDesdeURL;

public class ListadoPeliculasVista extends JFrame {
    ControladorListadoPeliculas controlador;
    private JButton botonBuscar;
    private JButton btnCerrarSesion;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JPanel panelPrinc,panelBusqueda,panelSup;
    private JTextField campoBusqueda;
    private JLabel titulo;
    private JLabel etiquetaError = new JLabel("");
    String nombreUsuarioActual;

    
    //RENDERER PARA MOSTRAR BOTÓN EN LA TABLA
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
                
            setText(value != null ? value.toString() : "Calificar");

            setEnabled(!"Calificada".equals(value));

            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor() {
            super(new JTextField());
            button = new JButton("Calificar");
            button.setOpaque(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
                
            String estado = value != null ? value.toString() : "Calificar";
            button.setText(estado);
                
            if ("Calificada".equals(estado)) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        
            return button;
        }


        public JButton getButton() {
            return button;
        }
    }
   
  
    public ListadoPeliculasVista(String usuario) { 
        JPanel panelPrinc = new JPanel(new BorderLayout());
        add(panelPrinc);
        nombreUsuarioActual = usuario;
        java.net.URL urlImagen = ListadoPeliculasVista.class.getResource("APP/Imagenes/lupa.png");
        
        ImageIcon iconoLupa = new ImageIcon(urlImagen);
        Image iconoLupaesc = iconoLupa.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconoLupa = new ImageIcon(iconoLupaesc);
        botonBuscar = new JButton();
        botonBuscar.setIcon(iconoLupa);
        botonBuscar.setToolTipText("Buscar Película");
        botonBuscar.setPreferredSize(new Dimension(40, 40));
        botonBuscar.setFocusPainted(false);
        botonBuscar.setBorder(BorderFactory.createEmptyBorder());
        panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        campoBusqueda = new JTextField(20);
        campoBusqueda.setPreferredSize(new Dimension(200,30));
        panelSup = new JPanel (new BorderLayout());
        titulo = new JLabel("Listado de Películas");

        etiquetaError.setForeground(Color.RED);
        etiquetaError.setHorizontalAlignment(SwingConstants.CENTER);
        panelSup.add(etiquetaError, BorderLayout.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD,25));
        titulo.setBorder(BorderFactory.createEmptyBorder(10,15,10,10));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelSup.add(panelBusqueda, BorderLayout.EAST);
        panelSup.add(titulo, BorderLayout.WEST); 
        panelPrinc.add(panelSup, BorderLayout.NORTH);

        setTitle("Listado de peliculas de TDL2");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblUsuario = new JLabel("Usuario: "+nombreUsuarioActual); 
        btnCerrarSesion = new JButton("Cerrar sesión");
        panelUsuario.add(lblUsuario);
        panelUsuario.add(btnCerrarSesion);
        panelSup.add(panelUsuario, BorderLayout.SOUTH);
        modeloTabla = new DefaultTableModel(
            new Object[]{"Poster", "Título ▲▼", "Género ▲▼", "Resumen", "Calificar"},
            0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4) {
                    Object value = getValueAt(row, column);
                    return !"Calificada".equals(value);
                }
                return false;
            }
        };


        tabla = new JTable(modeloTabla);
        tabla.setAutoCreateRowSorter(true);
        tabla.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
            return (Component) value;
            }
        });



        tabla.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

        tabla.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());

        ((ButtonEditor) tabla.getColumnModel().getColumn(4).getCellEditor())
            .getButton()
            .addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    int filaModelo = tabla.convertRowIndexToModel(fila);
                    controlador.abrirCalificacion(filaModelo);
                }
            });
        JScrollPane scroll = new JScrollPane(tabla);
        panelPrinc.add(scroll, BorderLayout.CENTER);

        // Tamaños para las columnas
        tabla.setRowHeight(120);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(90);  // poster
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150); // titulo
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100); // genero
        tabla.getColumnModel().getColumn(3).setPreferredWidth(400); // resumen
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // resumen
    }
    public void agregarPelicula(String poster, String titulo, String genero, String resumen, boolean yaCalificada) {
        try {
            JLabel lblPoster = new JLabel(cargarImagenDesdeURL(poster, 80, 120));
            lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
            lblPoster.setVerticalAlignment(SwingConstants.CENTER);

            
            Object estadoCalificacion = yaCalificada ? "Calificada" : "Calificar";

            modeloTabla.addRow(new Object[]{
                    lblPoster,
                    titulo,
                    genero,
                    resumen,
                    estadoCalificacion   
            });

        } catch (Exception e) {
            modeloTabla.addRow(new Object[]{
                    "Sin imagen",
                    titulo,
                    genero,
                    resumen,
                    yaCalificada ? "Calificada" : "Calificar"
            });
        }
    }

    public String getNombreUsuarioActual() {
        return nombreUsuarioActual;
    }
    public JTable getTabla() { return tabla; }

    public JButton getBotonCerrarSesion() {
        return btnCerrarSesion;
    }

    public JTextField getCampoBuscar() {
        return campoBusqueda;
    }

    public void setError(String mensaje) {
        etiquetaError.setText(mensaje);
    }

    
    public JButton getBotonBuscar() {
        return botonBuscar;
    }
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return ImageIcon.class; 
        return String.class;
    }
    public void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
    }   
    public void marcarComoCalificada(int fila) {
        if (fila >= 0 && fila < modeloTabla.getRowCount()) {
            modeloTabla.setValueAt("Calificada", fila, 4);
        } else {
            setError("Fila inválida: " + fila);
        }
    }



}