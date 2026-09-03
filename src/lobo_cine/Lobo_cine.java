/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Clase principal del sistema de cine "Lobo Cine".
 * Contiene el metodo main que arranca la aplicacion.
 * NOTA: para entrar al Administrador el correo: admin@mail.com y contraseña: "123",
 */
public class Lobo_cine {

    public static void main(String[] args) {

        // Obtiene la unica instancia de la base de datos
        BaseDatos db = BaseDatos.getInstancia();

        // Crea todos los servicios inyectandoles la base de datos compartida,
        // luego lanza el menu principal que controla toda la aplicacion
        new MenuService(
            db,
            new AuthService(db),
            new AdminService(db),
            new PeliculaService(db),
            new FuncionService(db),
            new ReservaService(db)
        ).menuPrincipal();
    }
}
