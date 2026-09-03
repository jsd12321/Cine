/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;
import java.util.Scanner;

/*
 * Clase utilitaria con metodos estaticos para leer y validar entradas del usuario.
 * Centraliza la logica de validacion para evitar duplicarla en cada servicio.
 */
public class Validador {

    /*
     * Lee un numero entero desde la entrada del usuario.
     * Si el usuario escribe algo que no es un numero, muestra un mensaje de error
     * y vuelve a pedir la entrada (bucle infinito hasta que sea valido).
     */
    public static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Numero invalido");
            }
        }
    }

    /*
     * Solicita al usuario una cadena de texto que no este vacia.
     * Muestra el mensaje dado como prompt y repite la solicitud si el usuario
     * presiona Enter sin escribir nada (o solo espacios).
     */
    public static String leerTextoNoVacio(Scanner sc, String mensaje) {
        String texto;

        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();    // trim() elimina espacios al inicio y al final

            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacio");
            }

        } while (texto.isEmpty());

        return texto;
    }
}
