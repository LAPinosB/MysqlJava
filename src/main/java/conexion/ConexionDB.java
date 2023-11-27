/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author luis
 */
public class ConexionDB {
    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3307/ecuamayferrr";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "admin";

    // Método para obtener una conexión a la base de datos
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("¡Conexión exitosa!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error al conectar a la base de datos.");
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al cerrar la conexión.");
            }
        }
    }
    
    ArrayList<Videojuego> videojuegos = new ArrayList<>();
    public boolean buscaNombre(String nombre){
        for (Videojuego juego : videojuegos) {
            if (juego.getNombre().equals(nombre)) {
                return true; // El nombre existe en la tabla
            }
        }
        return false; // El nombre no existe en la tabla
    }
    
    // Método que muestra por pantalla los resultados de una consulta
    public void lanzaConsulta(String consulta) {
        for (Videojuego juego : videojuegos) {
            // Supongamos que cada videojuego tiene un método toString() que devuelve su información
            System.out.println(juego.toString());
        }
    }
    
    // Método que crea un nuevo videojuego en la tabla con los datos pasados como parámetro
    public void nuevoRegistro(String nombre, String genero, int anioLanzamiento) {
        Videojuego nuevoJuego = new Videojuego(nombre, genero, anioLanzamiento);
        videojuegos.add(nuevoJuego);
    }
    
    // Método que crea un nuevo videojuego en la tabla con los datos pedidos al usuario por teclado
    public void nuevoRegistroDesdeTeclado() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del videojuego:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el género del videojuego:");
        String genero = scanner.nextLine();

        System.out.println("Ingrese el año de lanzamiento del videojuego:");
        int anioLanzamiento = scanner.nextInt();

        nuevoRegistro(nombre, genero, anioLanzamiento);
    }
    
    // Método que elimina un videojuego con el nombre pasado como parámetro
    // Devuelve true si la eliminación fue exitosa
    public boolean eliminarRegistro(String nombre) {
        for (Videojuego juego : videojuegos) {
            if (juego.getNombre().equals(nombre)) {
                videojuegos.remove(juego);
                return true; // Eliminación exitosa
            }
        }
        return false; // No se encontró el videojuego con el nombre especificado
    }
}
