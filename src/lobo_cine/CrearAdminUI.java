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
public class CrearAdminUI extends javax.swing.JFrame {
    private BaseDatos db;
    private Admin admin;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CrearAdminUI.class.getName());

    /**
     * Creates new form CrearAdminUI
     */
    public CrearAdminUI(Admin admin) {
        this.admin = admin;
        db = BaseDatos.getInstancia();

        // ===== CONFIG =====
        setTitle("Crear Administrador");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(120, 0, 120);
        Color blanco = Color.WHITE;

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(fondo);

        // ===== TITULO =====
        JLabel titulo = new JLabel("CREAR ADMINISTRADOR", SwingConstants.CENTER);
        titulo.setForeground(blanco);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        main.add(titulo, BorderLayout.NORTH);

        // ===== CENTRO =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(fondo);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(fondo);
        form.setPreferredSize(new Dimension(450, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtPassword = new JTextField();
        JTextField txtMatricula = new JTextField();

        txtNombre.setPreferredSize(new Dimension(250, 35));
        txtCorreo.setPreferredSize(new Dimension(250, 35));
        txtPassword.setPreferredSize(new Dimension(250, 35));
        txtMatricula.setPreferredSize(new Dimension(250, 35));

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblCorreo = new JLabel("Correo:");
        JLabel lblPass = new JLabel("Password:");
        JLabel lblMatricula = new JLabel("Matrícula:");

        JLabel[] labels = {lblNombre, lblCorreo, lblPass, lblMatricula};

        for (JLabel l : labels) {
            l.setForeground(blanco);
            l.setFont(new Font("Arial", Font.BOLD, 16));
        }

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(lblNombre, gbc);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(lblCorreo, gbc);
        gbc.gridx = 1;
        form.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(lblPass, gbc);
        gbc.gridx = 1;
        form.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(lblMatricula, gbc);
        gbc.gridx = 1;
        form.add(txtMatricula, gbc);

        center.add(form);
        main.add(center, BorderLayout.CENTER);

        // ===== BOTONES (AQUÍ EL CAMBIO) =====
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setBackground(fondo);

        JButton btnCrear = new JButton("Crear");
        JButton btnRegresar = new JButton("Regresar");

        JButton[] botones = {btnCrear, btnRegresar};

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

        // ===== EVENTO CREAR =====
        btnCrear.addActionListener(e -> {

            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String password = txtPassword.getText();
            String matricula = txtMatricula.getText();

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || matricula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos");
                return;
            }

            for (UsuarioSistema u : db.getUsuarios()) {
                if (u.getCorreo().equalsIgnoreCase(correo)) {
                    JOptionPane.showMessageDialog(this, "Correo ya registrado");
                    return;
                }
            }

            for (UsuarioSistema u : db.getUsuarios()) {
                if (u.getPersona() instanceof Admin) {
                    Admin a = (Admin) u.getPersona();
                    if (a.getMatricula().equalsIgnoreCase(matricula)) {
                        JOptionPane.showMessageDialog(this, "Matrícula ya existe");
                        return;
                    }
                }
            }

            Admin nuevo = new Admin(nombre, matricula);
            db.agregarUsuario(new UsuarioSistema(correo, password, nuevo));

            JOptionPane.showMessageDialog(this, "Administrador creado correctamente");
            dispose();
        });

        // ===== EVENTO REGRESAR =====
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
