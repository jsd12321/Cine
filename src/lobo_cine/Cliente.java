/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.ArrayList;
import java.util.List;

/*
 * Representa a un cliente registrado en el cine.
 * Hereda de Persona y agrega un numero de cliente y un historial de reservas.
 */
public class Cliente extends Persona {

    // Numero unico asignado al cliente al registrarse. 
    private int numero;

    // Lista de todas las reservas que ha realizado el cliente (incluyendo canceladas). 
    private List<Reserva> historial = new ArrayList<>();

    /*
     * Crea un cliente con nombre y numero de cliente.
     * n   Nombre del cliente
     * num Numero unico de cliente (se asigna automaticamente al registrar)
     */
    public Cliente(String n, int num) {
        super(n);       // Llama al constructor de Persona para asignar el nombre
        numero = num;
    }

    /*
     * Agrega una reserva al historial del cliente.
     * Se llama automaticamente al completar una reserva.
     */
    public void agregarHistorial(Reserva r) {
        historial.add(r);
    }

    // Retorna el historial completo de reservas del cliente.
    public List<Reserva> getHistorial() {
        return historial;
    }

    // Retorna el numero unico del cliente. 
    public int getNumero() {
        return numero;
    }
}
