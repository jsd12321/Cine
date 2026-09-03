/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lobo_cine;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author jessi
 */
public class ReservarUI extends javax.swing.JFrame {
    private Cliente cliente;
    private BaseDatos db;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservarUI.class.getName());

    /**
     * Creates new form ReservarUI
     */
    public ReservarUI(Cliente c) {
         this.cliente = c;
        this.db = BaseDatos.getInstancia();

        setTitle("Reservar - Lobo Cine");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fondo = new Color(40, 44, 52);
        getContentPane().setBackground(fondo);

        // ===== TOP (TÍTULO + REGRESAR) =====
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(fondo);

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setFocusPainted(false);

        btnRegresar.addActionListener(e -> {
            dispose();
            new ClienteUI(cliente).setVisible(true);
        });

        JLabel titulo = new JLabel("Selecciona una función", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        top.add(btnRegresar, BorderLayout.WEST);
        top.add(titulo, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);

        // ===== LISTA FUNCIONES =====
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);

        for (Funcion f : db.getFunciones()) {
            modelo.addElement(
                    f.getPelicula().getTitulo() +
                    " | Sala " + f.getSala().getNumero() +
                    " | " + f.getHorario()
            );
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setPreferredSize(new Dimension(300, 0));

        // ===== PANEL ASIENTOS =====
        JPanel panelAsientos = new JPanel();
        panelAsientos.setBackground(fondo);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(fondo);

        centro.add(scroll, BorderLayout.WEST);
        centro.add(panelAsientos, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // ===== BOTÓN VER ASIENTOS =====
        JButton btnCargar = new JButton("Ver Asientos");
        add(btnCargar, BorderLayout.SOUTH);

        btnCargar.addActionListener(e -> {

            int index = lista.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una función");
                return;
            }

            Funcion f = db.getFunciones().get(index);

            panelAsientos.removeAll();

            String[] asientos = f.getAsientos();
            int total = asientos.length;

            int columnas = 8;
            int filas = (int) Math.ceil(total / (double) columnas);

            panelAsientos.setLayout(new BorderLayout());

            JPanel grid = new JPanel(new GridLayout(filas, columnas, 10, 10));
            grid.setBackground(fondo);

            List<Integer> seleccionados = new ArrayList<>();

            // ===== ASIENTOS =====
            for (int i = 0; i < total; i++) {

                JButton asiento = new JButton(String.valueOf(i));

                if (asientos[i].equals("O")) {
                    asiento.setBackground(Color.RED);
                    asiento.setEnabled(false);
                } else {
                    asiento.setBackground(Color.GREEN);
                }

                int idx = i;

                asiento.addActionListener(ev -> {
                    if (seleccionados.contains(idx)) {
                        seleccionados.remove(Integer.valueOf(idx));
                        asiento.setBackground(Color.GREEN);
                    } else {
                        seleccionados.add(idx);
                        asiento.setBackground(Color.YELLOW);
                    }
                });

                grid.add(asiento);
            }

            // ===== BOTÓN CONFIRMAR =====
            JButton confirmar = new JButton("Confirmar Reserva");

            confirmar.addActionListener(ev -> {

                if (seleccionados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Selecciona al menos un asiento");
                    return;
                }

                double totalPagar = seleccionados.size() * f.getPrecio();

                JOptionPane.showMessageDialog(this, "Total a pagar: $" + totalPagar);

                String[] opciones = {"Crédito", "Débito", "PayPal"};

                int op = JOptionPane.showOptionDialog(
                        this,
                        "Selecciona método de pago",
                        "Pago",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                if (op == -1) return;

                MetodoPago metodo = null;

                switch (op) {
                    case 0 -> metodo = new TarjetaCredito();
                    case 1 -> metodo = new TarjetaDebito();
                    case 2 -> metodo = new PayPal();
                }

                boolean pagoExitoso = metodo.pagar();

                if (!pagoExitoso) {
                    JOptionPane.showMessageDialog(this, "Pago fallido");
                    return;
                }

                // VALIDAR DISPONIBILIDAD
                for (int a : seleccionados) {
                    if (!f.ocupar(a)) {
                        JOptionPane.showMessageDialog(this, "Un asiento ya fue ocupado");
                        return;
                    }
                }

                // GUARDAR RESERVA
                for (int a : seleccionados) {
                    Reserva r = new Reserva(cliente, f, a);
                    db.agregarReserva(r);
                    cliente.agregarHistorial(r);
                }

                JOptionPane.showMessageDialog(this,
                        "===== RESERVA EXITOSA =====\n" +
                        "Cliente: " + cliente.getNombre() +
                        "\nPelícula: " + f.getPelicula().getTitulo() +
                        "\nSala: " + f.getSala().getNumero() +
                        "\nAsientos: " + seleccionados +
                        "\nHora: " + f.getHorario()
                );

                dispose();
            });

            JPanel panelInferior = new JPanel();
            panelInferior.setBackground(fondo);
            panelInferior.add(confirmar);

            panelAsientos.add(grid, BorderLayout.CENTER);
            panelAsientos.add(panelInferior, BorderLayout.SOUTH);

            panelAsientos.revalidate();
            panelAsientos.repaint();
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
