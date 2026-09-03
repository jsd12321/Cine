/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Clase base (superclase) que representa a cualquier persona del sistema.
 * Tanto Cliente como Admin heredan de esta clase mediante herencia.
 */
public class Persona {
    protected String nombre;

    //Constructor que inicializa el nombre de la persona.
    public Persona(String n) {
        nombre = n;
    }

    // Retorna el nombre de la persona.
    public String getNombre() {
        return nombre;
    }
}
