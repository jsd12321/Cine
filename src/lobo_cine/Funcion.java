/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Arrays;

/*
 * Representa una funcion de cine: la proyeccion de una pelicula en una sala
 * especifica, a un horario y precio determinados.
 * Gestiona tambien el estado de cada asiento (Libre/Ocupado).
 */
public class Funcion {

    private Pelicula pelicula;
    private Sala     sala;
    private String   horario;   // Hora de inicio en formato.
    private double   precio;

    /*
     * Arreglo que representa el estado de cada asiento de la sala.
     * "L" = Libre, "O" = Ocupado.
     * El tamaño se determina por la capacidad de la sala.
     */
    private String[] asientos;

    /*
     * Crea una funcion e inicializa todos los asientos como libres ("L").
     *  p      Pelicula a proyectar
     *  s      Sala donde se proyecta
     *  h      Horario de inicio
     *  precio Precio por boleto
     */
    public Funcion(Pelicula p, Sala s, String h, double precio) {
        this.pelicula = p;
        this.sala = s;
        this.horario = h;
        this.precio = precio;

        // Crea el arreglo de asientos con la capacidad de la sala y marca todos como libres
        asientos = new String[s.getCapacidad()];
        Arrays.fill(asientos, "L");
    }

    /*
     * Intenta ocupar el asiento en el indice dado.
     * regresa true si el asiento estaba libre y se ocupo; false si ya estaba ocupado
     */
    public boolean ocupar(int i) {
        if (asientos[i].equals("L")) {
            asientos[i] = "O";
            return true;
        }
        return false;
    }

    //Libera un asiento previamente ocupado (al cancelar una reserva)./
    public void liberar(int i) {
        asientos[i] = "L";
    }

    // --- Getters basicos ---
    public String[] getAsientos() { return asientos; }
    public Pelicula getPelicula() { return pelicula; }
    public String   getHorario()  { return horario; }
    public Sala     getSala()     { return sala; }
    public double   getPrecio()   { return precio; }

    /*
     * Convierte el horario de inicio a minutos totales desde medianoche.
     * Util para comparar horarios y detectar solapamientos entre funciones.
     */
    public int getHoraInicioMin() {
        String[] partes = horario.split(":");
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }

    //Retorna el minuto en que termina la pelicula
    public int getHoraFinMin() {
        return getHoraInicioMin() + pelicula.getDuracion();
    }

    /*
     * Retorna el minuto en que termina la funcion incluyendo 15 minutos de limpieza.
     * Se usa para verificar que no se programen funciones que se traslapen.
     */
    public int getHoraFinConLimpieza() {
        return getHoraFinMin() + 15;
    }

    /*
     * Retorna la hora de fin de la pelicula como texto en formato.
     * Se usa para mostrar en cartelera y en la pantalla de reservas.
     */
    public String getHoraFinTexto() {
        int total = getHoraFinMin();
        int h     = total / 60;
        int m     = total % 60;
        return String.format("%02d:%02d", h, m);
    }
}
