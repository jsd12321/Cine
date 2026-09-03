
package lobo_cine;
import javax.swing.*;
import java.awt.*;

public class LoginUI extends javax.swing.JFrame {
    private BaseDatos db;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginUI.class.getName());

    public LoginUI() {
        db = BaseDatos.getInstancia();

        // ===== CONFIGURACIÓN GENERAL =====
        setTitle("Lobo Cine");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // pantalla completa
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== COLORES =====
        Color fondo = new Color(30, 30, 30);
        Color blanco = Color.WHITE;

        // ===== PANEL PRINCIPAL =====
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(fondo);

        // ===== TÍTULO =====
        JLabel titulo = new JLabel("LOBO CINE", SwingConstants.CENTER);
        titulo.setForeground(blanco);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));

        main.add(titulo, BorderLayout.NORTH);

        // ===== PANEL FORMULARIO =====
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(fondo);

        JPanel form = new JPanel(new GridLayout(2,2,15,15));
        form.setBackground(fondo);

        JTextField txtCorreo = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        JLabel lblCorreo = new JLabel("Correo:");
        JLabel lblPass = new JLabel("Password:");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 20));
        lblPass.setFont(new Font("Arial", Font.BOLD, 20));
        txtCorreo.setPreferredSize(new Dimension(250, 40));
        txtPassword.setPreferredSize(new Dimension(250, 40));

        lblCorreo.setForeground(blanco);
        lblPass.setForeground(blanco);

        form.add(lblCorreo);
        form.add(txtCorreo);
        form.add(lblPass);
        form.add(txtPassword);

        center.add(form);

        main.add(center, BorderLayout.CENTER);

        // ===== BOTONES =====
        JPanel bottom = new JPanel();
        bottom.setBackground(fondo);

        JButton btnLogin = new JButton("Login");
        JButton btnRegistro = new JButton("Registrarse");
        JButton btnCartelera = new JButton("Ver Cartelera");
        JButton btnBusqueda = new JButton("Búsqueda");
        JButton btnSalir = new JButton("Salir");

        // estilo botones
        btnLogin.setBackground(Color.WHITE);
        btnRegistro.setBackground(Color.WHITE);
        btnCartelera.setBackground(Color.WHITE);
        btnBusqueda.setBackground(Color.WHITE);
        btnSalir.setBackground(Color.WHITE);

        btnSalir.setFocusPainted(false);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 20));

        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 20));
        
        btnRegistro.setFocusPainted(false);
        btnRegistro.setFont(new Font("Arial", Font.BOLD, 20));
        
        btnCartelera.setFocusPainted(false);
        btnCartelera.setFont(new Font("Arial", Font.BOLD, 20));
        
        btnBusqueda.setFocusPainted(false);
        btnBusqueda.setFont(new Font("Arial", Font.BOLD, 20));

        bottom.add(btnLogin);
        bottom.add(btnRegistro);
        bottom.add(btnCartelera);
        bottom.add(btnBusqueda);
        bottom.add(btnSalir);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        // ===== EVENTOS =====

        btnLogin.addActionListener(e -> {

            String correo = txtCorreo.getText();
            String pass = new String(txtPassword.getPassword());

            for (UsuarioSistema u : db.getUsuarios()) {

                if (u.getCorreo().equals(correo) &&
                    u.getPassword().equals(pass)) {

                    if (u.getPersona() instanceof Cliente) {
                        new ClienteUI((Cliente) u.getPersona()).setVisible(true);
                    } else {
                        new AdminUI((Admin) u.getPersona()).setVisible(true);
                    }

                    dispose();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Datos incorrectos");
        });

        btnRegistro.addActionListener(e ->
            new RegistroUI().setVisible(true)
        );
        
        btnSalir.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que deseas salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0);
    }
});
        btnCartelera.addActionListener(e -> new CarteleraUI());
        
        btnBusqueda.addActionListener(e -> new BusquedaUI().setVisible(true));
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
        java.awt.EventQueue.invokeLater(() -> new LoginUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
