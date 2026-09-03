/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lobo_cine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BusquedaUI extends javax.swing.JFrame {
    private BaseDatos db;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BusquedaUI.class.getName());

    public BusquedaUI() {
        this.db = BaseDatos.getInstancia();

        // ===== CONFIGURACIÓN DE LA VENTANA =====
        setTitle("Búsqueda Avanzada - Lobo Cine");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(30, 30, 30);
        getContentPane().setBackground(fondo);

        // ===== PANEL SUPERIOR: TÍTULO =====
        JLabel titulo = new JLabel("BÚSQUEDA DE PELÍCULAS", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // ===== PANEL DE FILTROS (Mantenemos solo Título, Género y Clasificación) =====
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelFiltros.setBackground(new Color(45, 45, 45));

        JTextField txtTitulo = new JTextField(15);
        String[] generos = {"Todos", "Acción", "Drama", "Romance", "Animada", "Terror"};
        JComboBox<String> cbGeneros = new JComboBox<>(generos);
        String[] clasificaciones = {"Todas", "A", "B", "C"};
        JComboBox<String> cbClas = new JComboBox<>(clasificaciones);

        JButton btnBuscar = new JButton("Aplicar Filtros");
        JButton btnLimpiar = new JButton("Limpiar");

        JLabel lblT = new JLabel("Título:"); lblT.setForeground(Color.WHITE);
        JLabel lblG = new JLabel("Género:"); lblG.setForeground(Color.WHITE);
        JLabel lblC = new JLabel("Clasificación:"); lblC.setForeground(Color.WHITE);

        panelFiltros.add(lblT); panelFiltros.add(txtTitulo);
        panelFiltros.add(lblG); panelFiltros.add(cbGeneros);
        panelFiltros.add(lblC); panelFiltros.add(cbClas);
        panelFiltros.add(btnBuscar);
        panelFiltros.add(btnLimpiar);

        add(panelFiltros, BorderLayout.NORTH);

        // ===== ÁREA DE RESULTADOS =====
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> listaResultados = new JList<>(modelo);
        listaResultados.setFont(new Font("Monospaced", Font.PLAIN, 16)); 
        JScrollPane scroll = new JScrollPane(listaResultados);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        add(scroll, BorderLayout.CENTER);

        // ===== LÓGICA DE BÚSQUEDA =====
        btnBuscar.addActionListener(e -> {
            modelo.clear();
            String filtroTitulo = txtTitulo.getText().toLowerCase().trim();
            String filtroGeneroRaw = cbGeneros.getSelectedItem().toString();
            String filtroClas = cbClas.getSelectedItem().toString();

            String filtroGenero = normalizarTexto(filtroGeneroRaw);
            boolean encontroAlgo = false;

            for (Funcion f : db.getFunciones()) { 
                Pelicula p = f.getPelicula(); 
                String generoBD = normalizarTexto(p.getGenero()); 

                boolean coincideTitulo = filtroTitulo.isEmpty() || p.getTitulo().toLowerCase().contains(filtroTitulo); 
                boolean coincideGenero = filtroGenero.equals("todos") || generoBD.equals(filtroGenero);
                boolean coincideClas = filtroClas.equals("Todas") || p.getClasificacion().equalsIgnoreCase(filtroClas); 

                if (coincideTitulo && coincideGenero && coincideClas) {
                    // AQUÍ AÑADIMOS LA SALA, IDIOMA Y PRECIO A LA INFORMACIÓN MOSTRADA 
                    String info = String.format("%-20s | %-10s | Clas: %-2s | Sala %-2d | %-12s | %s | $%s", 
                        p.getTitulo(), 
                        p.getGenero(), 
                        p.getClasificacion(), 
                        f.getSala().getNumero(), 
                        p.getIdioma(),           
                        f.getHorario(),          
                        f.getPrecio());          
                    
                    modelo.addElement(info);
                    encontroAlgo = true;
                }
            }

            if (!encontroAlgo) {
                modelo.addElement("No se encontraron películas con los filtros seleccionados.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtTitulo.setText("");
            cbGeneros.setSelectedIndex(0);
            cbClas.setSelectedIndex(0);
            modelo.clear();
        });

        // Botón Regresar
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> dispose());
        JPanel south = new JPanel();
        south.setBackground(fondo);
        south.add(btnRegresar);
        add(south, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        return texto.toLowerCase()
                    .replace("á", "a").replace("é", "e").replace("í", "i")
                    .replace("ó", "o").replace("ú", "u")
                    .trim();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new BusquedaUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
