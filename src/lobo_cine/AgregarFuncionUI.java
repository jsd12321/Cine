/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lobo_cine;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author jessi
 */
public class AgregarFuncionUI extends javax.swing.JFrame {
    private BaseDatos db;
    private Admin admin;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AgregarFuncionUI.class.getName());
 
    /**
     * Creates new form AgregarFuncionUI
     */
    public AgregarFuncionUI(Admin admin) {
         this.admin = admin;
        db = BaseDatos.getInstancia();
 
        // ===== CONFIGURACIÓN =====
        setTitle("Agregar Función");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
 
        Color fondo = new Color(120, 120, 0);
        Color blanco = Color.WHITE;
 
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(fondo);
 
        // ===== TÍTULO =====
        JLabel titulo = new JLabel("AGREGAR FUNCIÓN", SwingConstants.CENTER);
        titulo.setForeground(blanco);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        main.add(titulo, BorderLayout.NORTH);
 
        // ===== CENTRO =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(fondo);
 
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(fondo);
        form.setPreferredSize(new Dimension(400, 250));
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
 
        JComboBox<String> comboPeliculas = new JComboBox<>();
        JComboBox<String> comboSalas = new JComboBox<>();
        JTextField txtHorario = new JTextField();
        JTextField txtPrecio = new JTextField();
 
        txtHorario.setPreferredSize(new Dimension(200, 35));
        txtPrecio.setPreferredSize(new Dimension(200, 35));
 
        JLabel lblPelicula = new JLabel("Película:");
        JLabel lblSala = new JLabel("Sala:");
        JLabel lblHorario = new JLabel("Horario:");
        JLabel lblPrecio = new JLabel("Precio:");
 
        JLabel[] labels = {lblPelicula, lblSala, lblHorario, lblPrecio};
 
        for (JLabel l : labels) {
            l.setForeground(blanco);
            l.setFont(new Font("Arial", Font.BOLD, 16));
        }
 
        // ===== CARGA =====
        for (Pelicula p : db.getPeliculas()) {
            comboPeliculas.addItem(p.getTitulo());
        }
 
        for (Sala s : db.getSalas()) {
            comboSalas.addItem("Sala " + s.getNumero());
        }
 
        // ===== FORM =====
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(lblPelicula, gbc);
        gbc.gridx = 1;
        form.add(comboPeliculas, gbc);
 
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(lblSala, gbc);
        gbc.gridx = 1;
        form.add(comboSalas, gbc);
 
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(lblHorario, gbc);
        gbc.gridx = 1;
        form.add(txtHorario, gbc);
 
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(lblPrecio, gbc);
        gbc.gridx = 1;
        form.add(txtPrecio, gbc);
 
        center.add(form);
        main.add(center, BorderLayout.CENTER);
 
        // ===== BOTONES (AQUÍ EL CAMBIO) =====
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setBackground(fondo);
 
        JButton btnGuardar = new JButton("Guardar");
        JButton btnRegresar = new JButton("Regresar");
 
        JButton[] botones = {btnGuardar, btnRegresar};
 
        for (JButton b : botones) {
            b.setBackground(Color.WHITE);
            b.setForeground(Color.BLACK);
            b.setFocusPainted(false);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setPreferredSize(new Dimension(120, 35));
            bottom.add(b);
        }
 
        main.add(bottom, BorderLayout.SOUTH);
        add(main);
 
        // ===== EVENTOS =====
        btnGuardar.addActionListener(e -> {
            try {
                int indexP = comboPeliculas.getSelectedIndex();
                int indexS = comboSalas.getSelectedIndex();
 
                if (indexP == -1 || indexS == -1) {
                    JOptionPane.showMessageDialog(this, "Selecciona datos válidos");
                    return;
                }
 
                Pelicula p = db.getPeliculas().get(indexP);
                Sala s = db.getSalas().get(indexS);
 
                String horario = txtHorario.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
 
                Funcion f = new Funcion(p, s, horario, precio);
                db.agregarFuncion(f);
 
                JOptionPane.showMessageDialog(this, "Función agregada correctamente");
                dispose();
                new AdminUI(admin).setVisible(true);
 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos");
            }
        });
 
        btnRegresar.addActionListener(e -> {
        dispose();
        new AdminUI(admin).setVisible(true);
        });
        setVisible(true);
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
     
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
