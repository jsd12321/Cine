/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/**
 * Representa una reserva de asiento hecha por un cliente para una funcion.
 * Puede estar activa o cancelada.
 */
public class Reserva {

    private Cliente cliente;    // Cliente que realizo la reserva
    private Funcion funcion;    // Funcion reservada
    private int     asiento;   // Numero de asiento reservado

    // Indica si la reserva fue cancelada.
    private boolean cancelada = false;

    /*
     * Crea una reserva con el cliente, la funcion y el asiento elegido.
     * c Cliente que reserva
     * f Funcion para la que se reserva
     * a Numero del asiento
     */
    public Reserva(Cliente c, Funcion f, int a) {
        cliente = c;
        funcion = f;
        asiento = a;
    }

    // --- Getters ---
    public Cliente getCliente()  { return cliente; }
    public Funcion getFuncion()  { return funcion; }
    public int     getAsiento()  { return asiento; }

    //Marca la reserva como cancelada.
    public void cancelar() {
        cancelada = true;
    }

    //Retorna true si la reserva esta cancelada, false si esta activa.
    public boolean isCancelada() {
        return cancelada;
    }
}
