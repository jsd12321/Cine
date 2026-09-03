/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 * Metodo de pago mediante PayPal.
 * Implementa la interfaz MetodoPago y valida que se ingrese un correo valido
 */
public class PayPal implements MetodoPago {

    //Solicita el correo de PayPal y verifica que tenga formato de email.
    @Override
    public boolean pagar() {

        String correo = JOptionPane.showInputDialog(null, "Correo PayPal:");

        if (correo == null) return false;

        if (!correo.contains("@")) {
            JOptionPane.showMessageDialog(null, "Correo inválido");
            return false;
        }

        String password = JOptionPane.showInputDialog(null, "Contraseña:");

        if (password == null || password.trim().isEmpty()) { 
            JOptionPane.showMessageDialog(null, "Contraseña inválida");
            return false;
        }

        JOptionPane.showMessageDialog(null, "Pago con PayPal exitoso");
        return true;
    }
}
