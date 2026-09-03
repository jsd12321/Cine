/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

/*
 * Servicio encargado de toda la navegacion de menus del sistema.
 * Actua como controlador principal: recibe la entrada del usuario y delega
 * las operaciones a los servicios correspondientes (auth, peliculas, funciones, reservas).
 *
 * Menus que maneja:
 * - Menu principal (acceso publico: login, registro, busqueda)
 * - Menu de busqueda (por genero, clasificacion o titulo)
 * - Menu de cliente (reservar, cancelar, ver reservas, historial)
 * - Menu de administrador (CRUD de peliculas/funciones, ocupacion, crear admins)
 */
public class MenuService {

    private Scanner         leer;
    private BaseDatos       db;
    private AuthService     authService;
    private AdminService    adminService;
    private PeliculaService pelService;
    private FuncionService  funService;
    private ReservaService  resService;

    //Constructor que inyecta todos los servicios necesarios y crea el Scanner de entrada.
    public MenuService(BaseDatos db, AuthService authService, AdminService adminService,
                       PeliculaService pelService, FuncionService funService,
                       ReservaService resService) {
        this.db = db;
        this.leer = new Scanner(System.in);
        this.authService = authService;
        this.adminService = adminService;
        this.pelService = pelService;
        this.funService = funService;
        this.resService = resService;
    }

    // ===================== MENU PRINCIPAL =====================
    public void menuPrincipal() {

        int op;
        do {
            funService.mostrarCartelera();  
            System.out.println("\n===== LOBO CINE =====");
            System.out.println("1. Login");
            System.out.println("2. Nuevo Usuario");
            System.out.println("3. Busqueda");
            System.out.println("0. Salir");

            op = Validador.leerEntero(leer);

            switch (op) {
                case 1:
                    Persona usuario = authService.login(leer);
                    if (usuario != null) menuUsuario(usuario);
                    break;
                case 2:
                    authService.registrar(leer);
                    break;
                case 3:
                    menuBusqueda();
                    break;
                case 0:
                    System.out.println("Saliendo......");
                    break;
                default:
                    System.out.println("Ingrese una opcion correcta");
            }

        } while (op != 0);
    }

    // ===================== MENU BUSQUEDA =====================
    private void menuBusqueda() {

        int op;
        do {
            System.out.println("\n1.Genero\n2.Clasificacion\n3.Nombre\n0.Volver");

            do {
                op = Validador.leerEntero(leer);
                if (op < 0 || op > 3) System.out.println("Opcion invalida. Ingrese de nuevo:");
            } while (op < 0 || op > 3);

            switch (op) {
                case 1:
                    System.out.println("Seleccione genero:");
                    System.out.println("1. Accion\n2. Drama\n3. Romance\n4. Animada\n5. Terror");
                    int g;
                    do {
                        g = Validador.leerEntero(leer);
                        if (g < 1 || g > 5) System.out.println("Opcion invalida. Ingrese de nuevo:");
                    } while (g < 1 || g > 5);

                    String genero = "";
                    switch (g) {
                        case 1: genero = "accion";  break;
                        case 2: genero = "drama";   break;
                        case 3: genero = "romance"; break;
                        case 4: genero = "animada"; break;
                        case 5: genero = "terror";  break;
                    }
                    pelService.buscarPorGenero(genero);
                    break;

                case 2:
                    System.out.println("Seleccione clasificacion:");
                    System.out.println("1. A\n2. B\n3. C");
                    int c;
                    do {
                        c = Validador.leerEntero(leer);
                        if (c < 1 || c > 3) System.out.println("Opcion invalida. Ingrese de nuevo:");
                    } while (c < 1 || c > 3);

                    String clas = "";
                    switch (c) {
                        case 1: clas = "A"; break;
                        case 2: clas = "B"; break;
                        case 3: clas = "C"; break;
                    }
                    pelService.buscarPorClasificacion(clas);
                    break;

                case 3:
                    System.out.print("Nombre: ");
                    pelService.buscarPorTitulo(leer.nextLine());
                    break;
            }

        } while (op != 0);
    }

    // ===================== MENU USUARIO =====================
    private void menuUsuario(Persona usuario) {
        if (usuario instanceof Cliente)
            menuCliente((Cliente) usuario);
        else
            menuAdmin((Admin) usuario);
    }

    // ===================== MENU CLIENTE =====================
    private void menuCliente(Cliente c) {

        // Busca el correo del cliente en la base de datos para mostrarlo
        String correo = "";
        for (UsuarioSistema u : db.getUsuarios()) {
            if (u.getPersona() == c) { correo = u.getCorreo(); break; }
        }

        System.out.println("\n===== CLIENTE =====");
        System.out.println("Nombre: "           + c.getNombre());
        System.out.println("Correo: "            + correo);
        System.out.println("Numero de cliente: " + c.getNumero());

        int op;
        do {
            System.out.println("\n1.Reservar\n2.Cancelar\n3.Reservas\n4.Historial\n0.Salir");
            do {
                op = Validador.leerEntero(leer);
                if (op < 0 || op > 4) System.out.println("Opcion invalida. Ingrese de nuevo:");
            } while (op < 0 || op > 4);

            switch (op) {
                case 1: resService.reservar(c, leer); break;   // Nueva reserva
                case 2: resService.cancelar(c, leer); break;   // Cancelar reserva activa
                case 3: resService.verReservas(c);    break;   // Ver reservas activas
                case 4: resService.historial(c);       break;   // Ver historial completo
            }

        } while (op != 0);
    }

    // ===================== MENU ADMIN =====================
    private void menuAdmin(Admin a) {

        // Busca el correo del admin en la base de datos para mostrarlo
        String correo = "";
        for (UsuarioSistema u : db.getUsuarios()) {
            if (u.getPersona() == a) { correo = u.getCorreo(); break; }
        }

        System.out.println("\n===== ADMIN =====");
        System.out.println("Nombre: "    + a.getNombre());
        System.out.println("Correo: "    + correo);
        System.out.println("Matricula: " + a.getMatricula());

        int op;
        do {
            System.out.println("\n1.Agregar pelicula\n2.Agregar funcion\n3.Eliminar pelicula\n4.Eliminar funcion\n5.Ocupacion\n6.Crear admin\n0.Salir");
            do {
                op = Validador.leerEntero(leer);
                if (op < 0 || op > 6) System.out.println("Opcion invalida. Ingrese de nuevo:");
            } while (op < 0 || op > 6);

            switch (op) {
                case 1: pelService.agregarPelicula(leer);  break;   // Agrega pelicula al catalogo
                case 2: funService.agregarFuncion(leer);   break;   // Programa nueva funcion
                case 3: pelService.eliminarPelicula(leer); break;   // Elimina pelicula y sus funciones
                case 4: funService.eliminarFuncion(leer);  break;   // Elimina una funcion
                case 5: funService.verOcupacion();          break;   // Reporte de asientos ocupados
                case 6: adminService.crearAdmin(leer);     break;   // Crea nuevo administrador
            }

        } while (op != 0);
    }
}
