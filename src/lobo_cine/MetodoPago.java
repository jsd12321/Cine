/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Interfaz que define el contrato para los metodos de pago disponibles.
 * Cada metodo de pago (TarjetaCredito, TarjetaDebito, PayPal) debe implementar
 * este metodo, permitiendo intercambiarlos facilmente (polimorfismo).
 */
public interface MetodoPago {

    /*
     * Solicita los datos necesarios al usuario y procesa el pago.
     * regresa true si el pago fue exitoso; false si fallo o los datos son invalidos
     */
    boolean pagar();
}
