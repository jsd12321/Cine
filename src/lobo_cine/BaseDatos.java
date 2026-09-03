/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.*;

/*
 * Clase que simula la base de datos del sistema (almacenamiento en memoria).
 * Implementa el patron Singleton para garantizar que exista una unica instancia
 * compartida por todos los servicios durante la ejecucion.
 *
 * Almacena y provee acceso a: usuarios, peliculas, funciones, reservas y salas.
 */
public class BaseDatos {

    // Unica instancia de BaseDatos (patron Singleton).
    private static BaseDatos instancia;

    // --- Lista de datos ---
    private List<UsuarioSistema> usuarios  = new ArrayList<>();
    private List<Funcion>        funciones = new ArrayList<>();
    private List<Reserva>        reservas  = new ArrayList<>();
    private List<Pelicula>       peliculas = new ArrayList<>();
    private List<Sala>           salas     = new ArrayList<>();

    /*
     * Constructor privado carga los datos iniciales del sistema.
     * Solo se ejecuta una vez cuando se solicita la primera instancia.
     */
    private BaseDatos() {

        // --- Datos de prueba: usuario administrador por defecto ---
        Admin a = new Admin("admin", "A1");
        usuarios.add(new UsuarioSistema("admin@mail.com", "123", a));

        // --- Salas del cine (4 salas con 25 asientos cada una) ---
        salas.add(new Sala(1, 25));
        salas.add(new Sala(2, 25));
        salas.add(new Sala(3, 25));
        salas.add(new Sala(4, 25));

        // --- Peliculas de ejemplo precargadas ---
        Pelicula p1 = new Pelicula("Batman",    "accion",  120, "B", "doblada");
        Pelicula p2 = new Pelicula("Titanic",   "romance", 180, "B", "subtitulada");

        peliculas.add(p1);
        peliculas.add(p2);

        // --- Funciones de ejemplo con sus peliculas, salas, horarios y precios ---
        funciones.add(new Funcion(p1, salas.get(0), "18:00", 70));
        funciones.add(new Funcion(p2, salas.get(1), "19:00", 80));
    }

    /*
     * Retorna la unica instancia de BaseDatos (la crea si aun no existe).
     */
    public static BaseDatos getInstancia() {
        if (instancia == null) instancia = new BaseDatos();
        return instancia;
    }

    // --- Metodos de acceso y modificacion de Usuarios ---
    public List<UsuarioSistema> getUsuarios()        { return usuarios; }
    public void agregarUsuario(UsuarioSistema u)     { usuarios.add(u); }

    // --- Metodos de acceso y modificacion de Peliculas ---
    public List<Pelicula> getPeliculas()             { return peliculas; }
    public void agregarPelicula(Pelicula p)          { peliculas.add(p); }
    public void eliminarPelicula(Pelicula p)         { peliculas.remove(p); }

    // --- Metodos de acceso y modificacion de Funciones ---
    public List<Funcion> getFunciones()              { return funciones; }
    public void agregarFuncion(Funcion f)            { funciones.add(f); }
    public void eliminarFuncion(Funcion f)           { funciones.remove(f); }

    /*
     * Elimina todas las funciones asociadas a una pelicula especifica.
     * Se usa antes de eliminar una pelicula para mantener consistencia de datos.
     */
    public void eliminarFuncionesDePelicula(Pelicula p) {
        funciones.removeIf(f -> f.getPelicula() == p);
    }

    // --- Metodos de acceso y modificacion de de Reservas ---
    public List<Reserva> getReservas()               { return reservas; }
    public void agregarReserva(Reserva r)            { reservas.add(r); }

    // --- Metodos de acceso a Salas (solo lectura, las salas son fijas) ---
    public List<Sala> getSalas()                     { return salas; }
}
