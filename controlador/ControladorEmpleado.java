package controlador;

import dao.Dao;
import modelo.Empleado;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorEmpleado {
    private DefaultTableModel modeloTabla;
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ControladorEmpleado(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }

    public boolean agregarEmpleado(String nombreEmpleado, String fechaInicio, 
                                   String fechaTermino, String tipoContrato, 
                                   boolean planSalud, boolean afp) throws SQLException, IllegalArgumentException {
        
        // Validación de campos vacíos
        if (nombreEmpleado.isEmpty() || fechaInicio.isEmpty() || fechaTermino.isEmpty() || tipoContrato.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos de texto son obligatorios.");
        }

        String sql = "INSERT INTO empleado (nombreEmpleado, fechaInicio, fechaTermino, tipoContrato, planSalud, afp) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = Dao.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            
            statement.setString(1, nombreEmpleado);
            statement.setString(2, fechaInicio);
            statement.setString(3, fechaTermino);
            statement.setString(4, tipoContrato);
            statement.setBoolean(5, planSalud);
            statement.setBoolean(6, afp);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                listarEmpleados();
                return true;
            }
            return false;
        }
    }

    public Empleado consultarEmpleado(int idEmpleado) throws SQLException {
        String sql = "SELECT * FROM empleado WHERE IdEmpleado = ?";
        Empleado empleado = null;

        try (Connection conexion = Dao.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            
            statement.setInt(1, idEmpleado);
            
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    empleado = new Empleado(
                            resultado.getInt("IdEmpleado"),
                            resultado.getString("nombreEmpleado"),
                            LocalDate.parse(resultado.getString("fechaInicio"), formato),
                            LocalDate.parse(resultado.getString("fechaTermino"), formato),
                            resultado.getString("tipoContrato"),
                            resultado.getBoolean("planSalud"),
                            resultado.getBoolean("afp")
                    );
                }
            }
        }
        return empleado;
    }

    public boolean modificarEmpleado(int idEmpleado, String nombreEmpleado, String fechaInicio, 
                                     String fechaTermino, String tipoContrato, 
                                     boolean planSalud, boolean afp) throws SQLException, IllegalArgumentException {
        
        // Validación de campos vacíos
        if (nombreEmpleado.isEmpty() || fechaInicio.isEmpty() || fechaTermino.isEmpty() || tipoContrato.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos de texto son obligatorios.");
        }

        String sql = "UPDATE empleado SET nombreEmpleado = ?, fechaInicio = ?, fechaTermino = ?, tipoContrato = ?, planSalud = ?, afp = ? WHERE IdEmpleado = ?";

        try (Connection conexion = Dao.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            
            statement.setString(1, nombreEmpleado);
            statement.setString(2, fechaInicio);
            statement.setString(3, fechaTermino);
            statement.setString(4, tipoContrato);
            statement.setBoolean(5, planSalud);
            statement.setBoolean(6, afp);
            statement.setInt(7, idEmpleado);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                listarEmpleados();
                return true;
            }
            return false;
        }
    }

    public boolean eliminarEmpleado(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM empleado WHERE IdEmpleado = ?";

        try (Connection conexion = Dao.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            
            statement.setInt(1, idEmpleado);
            
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                listarEmpleados();
                return true;
            }
            return false;
        }
    }

    public void listarEmpleados() throws SQLException {
        String sql = "SELECT * FROM empleado";
        modeloTabla.setRowCount(0);

        try (Connection conexion = Dao.obtenerConexion();
             Statement statement = conexion.createStatement();
             ResultSet resultado = statement.executeQuery(sql)) {
            
            while (resultado.next()) {
                Object[] fila = {
                        resultado.getInt("IdEmpleado"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("fechaInicio"),
                        resultado.getString("fechaTermino"),
                        resultado.getString("tipoContrato"),
                        resultado.getBoolean("planSalud") ? "Sí" : "No",
                        resultado.getBoolean("afp") ? "Sí" : "No"
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}
