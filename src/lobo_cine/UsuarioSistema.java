/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/**
 * Asocia las credenciales de acceso (correo y contraseña) con una Persona del sistema.
 * Permite que tanto Clientes como Admins inicien sesion con el mismo mecanismo.
 */
public class UsuarioSistema {

    private String correo;
    private String password;

    //La persona real asociada: puede ser Cliente o Admin.
    private Persona persona;

    //Crea un usuario del sistema con sus credenciales y la persona vinculada.

    public UsuarioSistema(String c, String p, Persona per) {
        correo   = c;
        password = p;
        persona  = per;
    }

    // --- Getters ---
    public String getCorreo()   { return correo; }
    public String getPassword() { return password; }
    public Persona getPersona() { return persona; }
}
