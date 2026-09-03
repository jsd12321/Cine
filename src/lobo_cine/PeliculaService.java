/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

/*
 * Servicio que gestiona las operaciones relacionadas con peliculas:
 * agregar, eliminar y buscar en el catalogo.
 */
public class PeliculaService {

    private BaseDatos db;

    //Constructor que recibe la base de datos compartida.
    public PeliculaService(BaseDatos db) {
        this.db = db;
    }

    /*
     * Solicita al administrador los datos de una nueva pelicula y la agrega al catalogo.
     * El administrador elige genero, clasificacion e idioma de menus predefinidos.
     */
    public void agregarPelicula(Scanner leer) {

        System.out.println("\n--- AGREGAR PELICULA ---");

        String titulo = Validador.leerTextoNoVacio(leer, "Titulo: ");

        // Seleccion de genero mediante menu numerico
        String genero = "";
        do {
            System.out.println("Seleccione genero:");
            System.out.println("1. Accion\n2. Drama\n3. Romance\n4. Animada\n5. Terror");

            int g = Validador.leerEntero(leer);
            switch (g) {
                case 1: genero = "accion";  break;
                case 2: genero = "drama";   break;
                case 3: genero = "romance"; break;
                case 4: genero = "animada"; break;
                case 5: genero = "terror";  break;
                default: System.out.println("Opcion invalida");
            }
        } while (genero.equals(""));    // Repite hasta que se elija un genero valido

        System.out.print("Duracion (minutos): ");
        int duracion = Validador.leerEntero(leer);

        // Seleccion de clasificacion
        String clasificacion = "";
        do {
            System.out.println("Seleccione clasificacion:");
            System.out.println("1. A\n2. B\n3. C");

            int c = Validador.leerEntero(leer);
            switch (c) {
                case 1: clasificacion = "A"; break;
                case 2: clasificacion = "B"; break;
                case 3: clasificacion = "C"; break;
                default: System.out.println("Opcion invalida");
            }
        } while (clasificacion.equals(""));

        // Seleccion del idioma de la pelicula
        String idioma = "";
        do {
            System.out.println("Seleccione idioma:");
            System.out.println("1. Doblada\n2. Subtitulada");

            int i = Validador.leerEntero(leer);
            switch (i) {
                case 1: idioma = "doblada";     break;
                case 2: idioma = "subtitulada"; break;
                default: System.out.println("Opcion invalida");
            }
        } while (idioma.equals(""));

        // Crea y guarda la nueva pelicula en la base de datos
        db.agregarPelicula(new Pelicula(titulo, genero, duracion, clasificacion, idioma));
        System.out.println("Pelicula agregada correctamente");
    }

    /*
     * Permite al administrador eliminar una pelicula del catalogo.
     * Tambien elimina automaticamente todas las funciones asociadas a esa pelicula.
     */
    public void eliminarPelicula(Scanner leer) {

        if (db.getPeliculas().isEmpty()) {
            System.out.println("No hay peliculas");
            return;
        }

        // Muestra la lista numerada de peliculas disponibles
        for (int i = 0; i < db.getPeliculas().size(); i++) {
            System.out.println((i + 1) + ". " + db.getPeliculas().get(i).getTitulo());
        }

        System.out.println("Seleccione pelicula a eliminar:");
        int op = Validador.leerEntero(leer) - 1;    // Resta 1 para convertir a indice base-0

        if (op < 0 || op >= db.getPeliculas().size()) {
            System.out.println("Opcion invalida");
            return;
        }

        Pelicula p = db.getPeliculas().get(op);

        // Primero elimina las funciones de esa pelicula para no dejar referencias huerfanas
        db.eliminarFuncionesDePelicula(p);
        db.eliminarPelicula(p);
        System.out.println("Pelicula eliminada");
    }

    //Busca y muestra las funciones activas cuya pelicula sea del genero indicado.
    public void buscarPorGenero(String genero) {
        boolean encontrado = false;
        System.out.println("Resultados:");
        for (Funcion f : db.getFunciones()) {
            if (f.getPelicula().getGenero().equalsIgnoreCase(genero)) {
                mostrarFuncion(f);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay peliculas disponibles en ese genero");
    }

    //Busca y muestra las funciones activas cuya pelicula tenga la clasificacion indicada.
    public void buscarPorClasificacion(String clas) {
        boolean encontrado = false;
        for (Funcion f : db.getFunciones()) {
            if (f.getPelicula().getClasificacion().equalsIgnoreCase(clas)) {
                mostrarFuncion(f);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay peliculas disponibles en esa clasificacion");
    }

    /*
     * Busca y muestra las funciones activas cuyo titulo contenga el texto ingresado.
     * La busqueda no distingue entre mayusculas y minusculas.
     */
    public void buscarPorTitulo(String titulo) {
        boolean encontrado = false;
        for (Funcion f : db.getFunciones()) {
            if (f.getPelicula().getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                mostrarFuncion(f);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay peliculas con ese nombre");
    }

    /*
     * Muestra la informacion resumida de una funcion (titulo, genero y horario).
     * Metodo auxiliar reutilizado por los tres metodos de busqueda.
     */
    private void mostrarFuncion(Funcion f) {
        System.out.println(
            f.getPelicula().getTitulo() +
            " | " + f.getPelicula().getGenero() +
            " | " + f.getHorario()
        );
    }
}
