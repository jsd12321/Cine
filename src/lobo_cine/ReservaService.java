/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.*;

/*
 * Servicio que gestiona todo el ciclo de vida de las reservas de un cliente:
 * crear reservas, cancelarlas y consultar el historial.
 */
public class ReservaService {

    private BaseDatos db;

    //Constructor que recibe la base de datos compartida.
    public ReservaService(BaseDatos db) {
        this.db = db;
    }

    /*
     * Permite a un cliente reservar uno o varios asientos para una funcion.
     * Flujo:
     * 1. Muestra funciones disponibles y el cliente elige una
     * 2. Muestra el mapa de asientos (L=Libre, O=Ocupado)
     * 3. El cliente indica cuantos asientos quiere y los selecciona uno a uno
     * 4. El cliente elige el metodo de pago y se procesa el cobro
     * 5. Si el pago es exitoso, se ocupan los asientos y se genera un "QR"
     */
    public void reservar(Cliente c, Scanner leer) {

        if (db.getFunciones().isEmpty()) {
            System.out.println("No hay funciones disponibles");
            return;
        }

        // Muestra las funciones disponibles con sala, horario y precio
        System.out.println("\n===== FUNCIONES DISPONIBLES =====");
        for (int i = 0; i < db.getFunciones().size(); i++) {
            Funcion f = db.getFunciones().get(i);
            System.out.println((i + 1) + ". " +
                f.getPelicula().getTitulo() + " | " +
                "Sala " + f.getSala().getNumero() + " | " +
                f.getHorario() + "-" + f.getHoraFinTexto() + " | $" +
                f.getPrecio()
            );
        }

        // Solicita la seleccion de funcion con validacion de rango
        int op;
        do {
            System.out.print("Seleccione funcion: ");
            op = Validador.leerEntero(leer);
        } while (op < 1 || op > db.getFunciones().size());

        Funcion f = db.getFunciones().get(op - 1);

        // Muestra el mapa visual de asientos de la funcion seleccionada
        System.out.println("\nAsientos (L=Libre, O=Ocupado)");
        String[] asientos = f.getAsientos();

        // Imprime los asientos en filas de 5 para facilitar la visualizacion
        for (int i = 0; i < asientos.length; i++) {
            System.out.print(i + "[" + asientos[i] + "] ");
            if ((i + 1) % 5 == 0) System.out.println();
        }

        // El cliente indica cuantos asientos desea reservar
        int cantidad;
        do {
            System.out.print("\nCuantos asientos deseas???: ");
            cantidad = Validador.leerEntero(leer);
        } while (cantidad <= 0 || cantidad > asientos.length);

        // El cliente selecciona cada asiento individualmente
        List<Integer> seleccionados = new ArrayList<>();
        for (int j = 0; j < cantidad; j++) {
            int asiento;
            do {
                System.out.print("Asiento #" + (j + 1) + ": ");
                asiento = Validador.leerEntero(leer);

                // Valida: indice valido, no ocupado y no ya seleccionado en esta sesion
                if (asiento < 0 || asiento >= asientos.length ||
                    asientos[asiento].equals("O") ||
                    seleccionados.contains(asiento)) {
                    System.out.println("Asiento invalido, ocupado o repetido");
                    asiento = -1;   // Fuerza repetir el bucle
                }
            } while (asiento == -1);

            seleccionados.add(asiento);
        }

        // --- Seleccion del metodo de pago (polimorfismo con MetodoPago) ---
        MetodoPago mp = null;
        do {
            System.out.println("\nMetodo de pago:");
            System.out.println("1. Tarjeta Credito\n2. Tarjeta Debito\n3. PayPal");

            int m = Validador.leerEntero(leer);
            switch (m) {
                case 1: mp = new TarjetaCredito(); break;
                case 2: mp = new TarjetaDebito();  break;
                case 3: mp = new PayPal();          break;
                default: System.out.println("Opcion invalida");
            }
        } while (mp == null);

        // Intenta procesar el pago; si falla, cancela toda la operacion
        if (!mp.pagar()) {
            System.out.println("Pago fallido");
            return;
        }

        // Pago exitoso: ocupa los asientos y registra una Reserva por cada uno
        for (int a : seleccionados) {
            f.ocupar(a);                        // Marca el asiento como "O" en la funcion
            Reserva r = new Reserva(c, f, a);
            db.agregarReserva(r);               // Guarda en la base de datos global
            c.agregarHistorial(r);              // Agrega al historial personal del cliente
        }

        // Muestra el resumen de la reserva (simula un QR)
        System.out.println("\n===== QR RESERVA =====");
        System.out.println("Cliente: "  + c.getNombre());
        System.out.println("Pelicula: " + f.getPelicula().getTitulo());
        System.out.println("Sala: "     + f.getSala().getNumero());
        System.out.println("Asientos: " + seleccionados);
        System.out.println("Hora: "     + f.getHorario());
        System.out.println("########################");
        System.out.println("Reserva realizada con exito");
    }

    /*
     * Permite a un cliente cancelar una de sus reservas activas.
     * Al cancelar, el asiento se libera en la funcion para que otros puedan reservarlo.
     */
    public void cancelar(Cliente c, Scanner leer) {

        // Filtra solo las reservas activas (no canceladas) del cliente
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : db.getReservas()) {
            if (r.getCliente() == c && !r.isCancelada()) {
                activas.add(r);
            }
        }

        if (activas.isEmpty()) {
            System.out.println("No tienes reservas activas");
            return;
        }

        // Muestra las reservas activas del cliente numeradas
        System.out.println("\n===== RESERVAS ACTIVAS =====");
        for (int i = 0; i < activas.size(); i++) {
            Reserva r = activas.get(i);
            System.out.println((i + 1) + ". " +
                r.getFuncion().getPelicula().getTitulo() +
                " | Sala " + r.getFuncion().getSala().getNumero() +
                " | Asiento " + r.getAsiento()
            );
        }

        int op;
        do {
            System.out.print("Seleccione a cancelar: ");
            op = Validador.leerEntero(leer);
        } while (op < 1 || op > activas.size());

        Reserva r = activas.get(op - 1);

        // Libera el asiento en la funcion y marca la reserva como cancelada
        r.getFuncion().liberar(r.getAsiento());
        r.cancelar();

        System.out.println("Reserva cancelada");
    }

    // Muestra las reservas activas (no canceladas) del cliente.

    public void verReservas(Cliente c) {

        boolean hay = false;

        for (Reserva r : db.getReservas()) {
            if (r.getCliente() == c && !r.isCancelada()) {
                System.out.println(
                    r.getFuncion().getPelicula().getTitulo() +
                    " | Sala " + r.getFuncion().getSala().getNumero() +
                    " | Asiento " + r.getAsiento()
                );
                hay = true;
            }
        }

        if (!hay) System.out.println("No tienes reservas activas");
    }

    /*
     * Muestra el historial completo de reservas del cliente (activas y canceladas).
     * El historial se guarda directamente en el objeto Cliente al momento de reservar.
     */
    public void historial(Cliente c) {

        if (c.getHistorial().isEmpty()) {
            System.out.println("Sin historial");
            return;
        }

        System.out.println("\n===== HISTORIAL =====");

        for (Reserva r : c.getHistorial()) {
            // Muestra el estado actual de cada reserva del historial
            String estado = r.isCancelada() ? "CANCELADA" : "ACTIVA";
            System.out.println(
                r.getFuncion().getPelicula().getTitulo() +
                " | Sala " + r.getFuncion().getSala().getNumero() +
                " | Asiento " + r.getAsiento() +
                " | " + estado
            );
        }
    }
}

