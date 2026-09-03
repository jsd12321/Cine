/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lobo_cine;

/*
 * Representa una pelicula disponible en el catalogo del cine.
 * Contiene toda la informacion descriptiva necesaria para mostrarla en cartelera.
 */
public class Pelicula {

    private String titulo;
    private String genero;          
    private String clasificacion;   
    private String idioma;          
    private int duracion;           

    //Crea una pelicula con todos sus atributos.
    public Pelicula(String t, String g, int d, String c, String i) {
        titulo = t;
        genero = g;
        duracion = d;
        clasificacion = c;
        idioma = i;
    }

    // --- Getters ---
    public String getTitulo()         { return titulo; }
    public String getGenero()         { return genero; }
    public String getClasificacion()  { return clasificacion; }
    public String getIdioma()         { return idioma; }
    public int    getDuracion()       { return duracion; }
}
