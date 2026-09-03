/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Representa una sala fisica del cine.
 * Cada sala tiene un numero identificador y una capacidad de asientos.
 */
public class Sala {

    private int numero;     // Numero que identifica la sala
    private int capacidad;  // Total de asientos disponibles en la sala

    public Sala(int n, int c) {
        numero    = n;
        capacidad = c;
    }

    // Retorna el numero identificador de la sala.
    public int getNumero()    { return numero; }

    // Retorna la capacidad total de asientos de la sala.
    public int getCapacidad() { return capacidad; }
}
