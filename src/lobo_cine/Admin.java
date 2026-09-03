/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Representa a un administrador del sistema.
 * Hereda de Persona y agrega una matricula unica que lo identifica.
 */
public class Admin extends Persona {
    private String matricula;

    /*
     Crea un administrador con nombre y matricula.
      n Nombre del administrador
      m Matricula unica
     */
    public Admin(String n, String m) {
        super(n);       // Llama al constructor de Persona para asignar el nombre
        matricula = m;
    }

    //Retorna la matricula del administrador. 
    public String getMatricula() {
        return matricula;
    }
}
