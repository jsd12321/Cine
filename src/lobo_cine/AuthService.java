/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

// Servicio de autenticacion: maneja el inicio de sesion y el registro de nuevos clientes
public class AuthService {

    private BaseDatos db;

    //Constructor que recibe la base de datos compartida.
    public AuthService(BaseDatos db) {
        this.db = db;
    }

    /*
     * Solicita correo y contraseña al usuario e intenta autenticarlo.
     * Busca entre todos los usuarios registrados uno que coincida con las credenciales.
     */
    public Persona login(Scanner leer) {

        System.out.print("Correo: ");
        String c = leer.nextLine();

        System.out.print("Password: ");
        String p = leer.nextLine();

        // Recorre todos los usuarios buscando uno que coincida con correo y contraseña
        for (UsuarioSistema u : db.getUsuarios()) {
            if (u.getCorreo().equals(c) && u.getPassword().equals(p))
                return u.getPersona();  // Retorna el objeto Persona asociado (Admin o Cliente)
        }

        System.out.println("Error login");
        return null;    // Credenciales incorrectas
    }

    /*
     * Registra un nuevo cliente en el sistema.
     * Valida que el correo no este duplicado antes de crear la cuenta.
     */
    public void registrar(Scanner leer) {

        String nombre = Validador.leerTextoNoVacio(leer, "Nombre: ");

        // Pide el correo y verifica que no este ya registrado
        String correo;
        while (true) {
            correo = Validador.leerTextoNoVacio(leer, "Correo: ");
            boolean existe = false;
            for (UsuarioSistema u : db.getUsuarios()) {
                if (u.getCorreo().equalsIgnoreCase(correo)) { existe = true; break; }
            }
            if (existe) System.out.println("Correo ya registrado, intenta otro.");
            else        break;  // Correo disponible, se puede continuar
        }

        String password = Validador.leerTextoNoVacio(leer, "Password: ");

        // El numero de cliente se asigna automaticamente segun cuantos usuarios hay
        Cliente cli = new Cliente(nombre, db.getUsuarios().size());
        db.agregarUsuario(new UsuarioSistema(correo, password, cli));
        System.out.println("Registrado correctamente");
    }
}
