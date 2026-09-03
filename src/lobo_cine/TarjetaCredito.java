/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Metodo de pago mediante tarjeta de credito.
 * Implementa la interfaz MetodoPago y valida que el numero tenga exactamente 16 digitos.
 */
public class TarjetaCredito implements MetodoPago {

    /**
     * Solicita el numero de tarjeta y verifica que tenga el formato correcto.
     * regresa true si el numero tiene exactamente 16 digitos; false en caso contrario
     */
    @Override
    public boolean pagar() {
        String numero = JOptionPane.showInputDialog(null, "Número de tarjeta:");
        if (numero == null) 
            return false;

        if (numero.trim().length() <= 1 || !numero.matches("\\d+")) { 
            JOptionPane.showMessageDialog(null, "Tarjeta inválida (debe ser más de un dígito y solo numeros)");
            return false;
        }

        String nombre = JOptionPane.showInputDialog(null, "Nombre del titular:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre inválido");
            return false;
        }

        String cvv = JOptionPane.showInputDialog(null, "CVV:");
        if (cvv.trim().length() <= 1 || !cvv.matches("\\d+")){
            JOptionPane.showMessageDialog(null, "CVV inválido");
            return false;
        }

    JOptionPane.showMessageDialog(null, "Pago con tarjeta de crédito exitoso");
    return true;
}
}