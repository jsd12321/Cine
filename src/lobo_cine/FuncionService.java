/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

/*
 * Servicio que gestiona las operaciones relacionadas con funciones de cine:
 * agregar, eliminar, ver ocupacion y mostrar la cartelera.
 */
public class FuncionService {

    private BaseDatos db;

    /*
     * Constructor que recibe la base de datos compartida.
     * Instancia unica de BaseDatos
     */
    public FuncionService(BaseDatos db) {
        this.db = db;
    }

    /*
     * Permite al administrador programar una nueva funcion.
     * Realiza las siguientes validaciones antes de guardarla:
     * - Que existan peliculas registradas
     * - Que el horario tenga formato "HH:MM"
     * - Que la sala no este ocupada o en limpieza en ese horario 
     * - Que el precio sea mayor a 0
     */
    public void agregarFuncion(Scanner leer) {

        if (db.getPeliculas().isEmpty()) {
            System.out.println("No hay peliculas");
            return;
        }

        // Muestra la lista de peliculas disponibles para seleccionar
        System.out.println("\n--- SELECCIONAR PELICULA ---");
        for (int i = 0; i < db.getPeliculas().size(); i++) {
            System.out.println((i + 1) + ". " + db.getPeliculas().get(i).getTitulo());
        }

        int op;
        do {
            System.out.print("Seleccione pelicula: ");
            op = Validador.leerEntero(leer);
            if (op < 1 || op > db.getPeliculas().size()) System.out.println("Opcion invalida");
        } while (op < 1 || op > db.getPeliculas().size());

        Pelicula p = db.getPeliculas().get(op - 1);

        // Seleccion de sala
        System.out.println("\nSeleccione sala:");
        for (Sala s : db.getSalas()) {
            System.out.println(s.getNumero() + ". Sala " + s.getNumero());
        }

        Sala sala = null;
        do {
            System.out.print("Sala: ");
            int numSala = Validador.leerEntero(leer);
            for (Sala s : db.getSalas()) {
                if (s.getNumero() == numSala) { sala = s; break; }
            }
            if (sala == null) System.out.println("Sala invalida");
        } while (sala == null);

        // Solicita el horario con validacion de formato "HH:MM"
        String horario = "";
        do {
            horario = Validador.leerTextoNoVacio(leer, "Horario inicio (HH:MM): ");
            if (!horario.matches("\\d{2}:\\d{2}")) {
                System.out.println("Formato invalido (Ej: 18:30)");
                horario = "";
            }
        } while (horario.equals(""));

        // --- Validacion de conflicto de horarios en la sala ---
        // Convierte el horario de la nueva funcion a minutos para comparar rangos
        int inicioNuevo = convertirAHoraMin(horario);
        int finNuevo    = inicioNuevo + p.getDuracion();

        for (Funcion f : db.getFunciones()) {
            if (f.getSala() == sala) {
                int inicioExistente = f.getHoraInicioMin();
                int finExistente    = f.getHoraFinConLimpieza(); // Incluye 15 min de limpieza

                // Detecta traslape: la nueva funcion empieza antes de que termine la existente
                // o la existente empieza antes de que termine la nueva
                if (inicioNuevo < finExistente && finNuevo > inicioExistente) {
                    System.out.println("Error: sala ocupada o en limpieza");
                    return;
                }
            }
        }

        // Solicita el precio con validacion de numero positivo
        double precio = 0;
        do {
            System.out.print("Precio: ");
            try {
                precio = Double.parseDouble(leer.nextLine());
                if (precio <= 0) System.out.println("El precio debe ser mayor a 0");
            } catch (Exception e) {
                precio = -1;
                System.out.println("Precio invalido");
            }
        } while (precio <= 0);

        // Guarda la nueva funcion en la base de datos
        db.agregarFuncion(new Funcion(p, sala, horario, precio));
        System.out.println("Funcion agregada correctamente");
    }

    /*
     * Permite al administrador eliminar una funcion programada.
     * Muestra la lista de funciones y elimina la seleccionada.
     */
    public void eliminarFuncion(Scanner leer) {

        if (db.getFunciones().isEmpty()) {
            System.out.println("No hay funciones");
            return;
        }

        // Lista todas las funciones con su pelicula y horario
        for (int i = 0; i < db.getFunciones().size(); i++) {
            Funcion f = db.getFunciones().get(i);
            System.out.println((i + 1) + ". " + f.getPelicula().getTitulo() + " " + f.getHorario());
        }

        System.out.println("Seleccione funcion:");
        int op = Validador.leerEntero(leer) - 1;  

        if (op < 0 || op >= db.getFunciones().size()) {
            System.out.println("Opcion invalida");
            return;
        }

        db.eliminarFuncion(db.getFunciones().get(op));
        System.out.println("Funcion eliminada");
    }

    /*
     * Muestra el reporte de ocupacion de todas las funciones activas.
     * Para cada funcion indica cuantos asientos estan ocupados vs el total.
     */
    public void verOcupacion() {

        System.out.println("\n--- OCUPACION ---");

        for (Funcion f : db.getFunciones()) {
            int ocupados = 0;

            // Cuenta cuantos asientos tienen el estado "O" (Ocupado)
            for (String a : f.getAsientos()) {
                if (a.equals("O")) ocupados++;
            }

            System.out.println(
                f.getPelicula().getTitulo() +
                " | " + f.getHorario() +
                " | Ocupados: " + ocupados + "/" + f.getAsientos().length
            );
        }
    }

    /*
     * Muestra la cartelera completa con todas las funciones disponibles.
     * Incluye: titulo, genero, clasificacion, sala, idioma, horario, duracion y precio.
     */
    public void mostrarCartelera() {

        if (db.getFunciones().isEmpty()) {
            System.out.println("No hay funciones disponibles");
            return;
        }

        System.out.println("\n===== CARTELERA =====");

        for (Funcion f : db.getFunciones()) {
            System.out.println(
                f.getPelicula().getTitulo()               + " | " +
                f.getPelicula().getGenero()                + " | " +
                "Clas: " + f.getPelicula().getClasificacion() + " | " +
                "Sala " + f.getSala().getNumero()          + " | " +
                f.getPelicula().getIdioma()                + " | " +
                f.getHorario() + "-" + f.getHoraFinTexto() + " | " +
                f.getPelicula().getDuracion() + " min | $" +
                f.getPrecio()
            );
        }
    }

    //Convierte un horario en formato "HH:MM" a minutos totales.
    private int convertirAHoraMin(String h) {
        String[] partes = h.split(":");
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }
}
