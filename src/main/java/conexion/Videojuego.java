/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author luis
 */
public class Videojuego {
     private String nombre;
    private String genero;
    private String anioLanzamiento;
    private String compania;
    private double precio;
    
    // Constructor
    public Videojuego(String nombre, String genero, String anioLanzamiento, String compania, double precio) {
        this.nombre = nombre;
        this.genero = genero;
        this.anioLanzamiento = anioLanzamiento;
        this.compania = compania;
        this.precio = precio;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(String anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }
    
    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método toString para representar la información del videojuego como una cadena
    @Override
    public String toString() {
        return "Videojuego{" +
                "nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", anioLanzamiento=" + anioLanzamiento +
                '}';
    }
}
