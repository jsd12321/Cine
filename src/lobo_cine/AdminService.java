/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

/*
 * Servicio exclusivo para operaciones de administracion.
 * Actualmente permite crear nuevos administradores en el sistema.
 */
public class AdminService {

    private BaseDatos db;

    // Constructor que recibe la base de datos compartida.
    public AdminService(BaseDatos db) {
        this.db = db;
    }

    /*
     * Solicita los datos necesarios y crea un nuevo administrador.
     * Valida que el correo y la matricula no esten duplicados antes de guardar.
     */
    public void crearAdmin(Scanner leer) {

        String nombre = Validador.leerTextoNoVacio(leer, "Nombre: ");

        // Pide el correo y verifica que no este ya registrado (en todos los usuarios)
        String correo;
        while (true) {
            correo = Validador.leerTextoNoVacio(leer, "Correo: ");
            boolean existe = false;
            for (UsuarioSistema u : db.getUsuarios()) {
                if (u.getCorreo().equalsIgnoreCase(correo)) { existe = true; break; }
            }
            if (existe) System.out.println("Correo ya registrado, intenta otro.");
            else        break;
        }

        String password = Validador.leerTextoNoVacio(leer, "Password: ");

        // Pide la matricula y verifica que no este ya usada por otro administrador
        String matricula;
        while (true) {
            matricula = Validador.leerTextoNoVacio(leer, "Matricula: ");
            boolean existe = false;
            for (UsuarioSistema u : db.getUsuarios()) {
                // Solo compara con usuarios que sean Admin
                if (u.getPersona() instanceof Admin) {
                    Admin admin = (Admin) u.getPersona();
                    if (admin.getMatricula().equalsIgnoreCase(matricula)) { existe = true; break; }
                }
            }
            if (existe) System.out.println("Matricula ya existente, intenta otra.");
            else        break;
        }

        // Crea el objeto Admin y lo guarda como UsuarioSistema en la base de datos
        db.agregarUsuario(new UsuarioSistema(correo, password, new Admin(nombre, matricula)));
        System.out.println("Admin creado correctamente");
    }
}
