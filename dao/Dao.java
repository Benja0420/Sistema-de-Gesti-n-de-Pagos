package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
    /* private static final String URL = "jdbc:mysql://localhost/pagosbd";
    private static final String USUARIO = "root";
    private static final String CLAVE = ""; */

     private static final String URL = "jdbc:mysql://10.51.0.55:3306/pagosbd";
     private static final String USUARIO = "Alumnos";
     private static final String CLAVE = "alumnos2025";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("✓ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver de MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }


    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("✗ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
