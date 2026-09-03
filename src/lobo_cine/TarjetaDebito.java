/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
 * Metodo de pago mediante tarjeta de debito.
 * Implementa la interfaz MetodoPago con la misma validacion que la tarjeta de credito
 */
public class TarjetaDebito implements MetodoPago {

    /**
     * Solicita el numero de tarjeta de debito y valida su formato.
     * regresa true si el numero tiene exactamente 16 digitos; false en caso contrario
     */
    @Override
    public boolean pagar() {
        String numero = JOptionPane.showInputDialog(null, "Número de tarjeta:");
        if (numero == null) 
            return false;

        if (numero.trim().length() <= 1 || !numero.matches("\\d+")) { 
            JOptionPane.showMessageDialog(null, "Tarjeta inválida (debe tener más de un carácter y solo numeros)");
            return false;
        }

        String nip = JOptionPane.showInputDialog(null, "NIP:");

        if (nip.trim().length() <= 1 || !nip.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "NIP inválido");
            return false;
        }

        JOptionPane.showMessageDialog(null, "Pago con tarjeta de débito exitoso"); 
        return true; 
    }
}
