/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class ConexionDB {
    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3307/ecuamayferrr";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "admin";
    static final String QUERY = "SELECT * FROM videojuegos";

    // Método para obtener una conexión a la base de datos
    public Connection obtenerConexion() {
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
    public void lanzaConsulta(Connection conn) {
        
            try {
                
                Statement stmnt = conn.createStatement();
                
                ResultSet rs = stmnt.executeQuery("Select * from videojuegos");
                while(rs.next()){
                    System.out.println("ID: "+rs.getString("id")+ " Nombre: "+rs.getString("nombre"));
                }
            } catch (SQLException ex) {
                System.out.println("ERROR : "+ex.getMessage());
            }
        
    }
    /**
     * 
     * @param conexion
     * @param nombre
     * @param genero
     * @param anioLanzamiento
     * @param compania
     * @param precio
     * @return Devolvemos si es true
     */
    //Private porque por el momento solo quiero acceder para introducir nuevos juegos desde Scanner
    private boolean nuevoRegistro(Connection conexion, String nombre, String genero, String anioLanzamiento,String compania, double precio ) {
        Videojuego nuevoJuego = new Videojuego(nombre, genero, anioLanzamiento,compania,precio);
        videojuegos.add(nuevoJuego);
        try {
            // Realizar operaciones con la conexión , IMPORTANTE; SI NO PONEMOS (USERNAME, PASSWORD) Y PONEMOS DIRECTAMENTE
            //VALUES TENEMOS QUE ESPECIFICAR EN EL CAMPO DE ID(NULL,?,?,?...) POR EJEMPLO. (nombre, genero,fechalanzamiento,compania,precio) 
            String insercion = "INSERT INTO videojuegos VALUES (NULL,?,?,?,?,?)";
            PreparedStatement sentencia = conexion.prepareStatement(insercion);
            sentencia.setString(1, nuevoJuego.getNombre());
            sentencia.setString(2, nuevoJuego.getGenero());
            sentencia.setString(3, nuevoJuego.getAnioLanzamiento());
            sentencia.setString(4, nuevoJuego.getCompania());
            sentencia.setDouble(5, nuevoJuego.getPrecio());

            // Ejecutar la inserción
            int filasAfectadas = sentencia.executeUpdate();
            
            System.out.println("Se ha añadido el videojuego correctamente desde java");
            // Verificar si la inserción fue exitosa (al menos una fila afectada)
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            // Manejar la excepción en caso de error de la consulta
            System.out.println("Error al insertar usuario: " + ex.getMessage());
            ex.printStackTrace(); // Esto imprime la traza de la excepción para obtener más detalles
            return false; // Indicar que la operación falló
        }
    }
    // Método que crea un nuevo videojuego en la tabla con los datos pedidos al usuario por teclado
    public void nuevoRegistroDesdeTeclado() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del videojuego:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el género del videojuego:");
        String genero = scanner.nextLine();

        System.out.println("Ingrese el año de lanzamiento del videojuego:");
        String anioLanzamiento = scanner.nextLine();
        
        System.out.println("Ingrese la compañia del videojuego:");
        String compania = scanner.nextLine();
        
        System.out.println("Ingrese el precio del videojuego:");
        double precio = scanner.nextDouble();
        
        Connection conn = obtenerConexion();

        nuevoRegistro(conn,nombre, genero, anioLanzamiento,compania,precio);
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
