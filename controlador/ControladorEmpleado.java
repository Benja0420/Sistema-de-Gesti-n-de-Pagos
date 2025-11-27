package controlador;

import dao.Dao;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorEmpleado {
    private DefaultTableModel modeloTabla;
    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public ControladorEmpleado(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }

    // Método para agregar un empleado
    public void agregarEmpleado(int idEmpleado, String nombreEmpleado, String fechaInicio, 
                                String fechaTermino, String tipoContrato, boolean planSalud, boolean afp) {
        // Validación de campos vacíos
        if (nombreEmpleado.isEmpty() || fechaInicio.isEmpty() || fechaTermino.isEmpty() || tipoContrato.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos de texto son obligatorios.", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conexion = Dao.obtenerConexion();
        String sql = "INSERT INTO Empleado (IdEmpleado, nombreEmpleado, fechaInicio, fechaTermino, tipoContrato, planSalud, afp) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idEmpleado);
            statement.setString(2, nombreEmpleado);
            statement.setString(3, fechaInicio);
            statement.setString(4, fechaTermino);
            statement.setString(5, tipoContrato);
            statement.setBoolean(6, planSalud);
            statement.setBoolean(7, afp);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listarEmpleados();
            }

            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar empleado: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Dao.cerrarConexion(conexion);
        }
    }

    // Método para consultar un empleado por ID
    public Empleado consultarEmpleado(int idEmpleado) {
        Connection conexion = Dao.obtenerConexion();
        String sql = "SELECT * FROM Empleado WHERE IdEmpleado = ?";
        Empleado empleado = null;

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idEmpleado);
            ResultSet resultado = statement.executeQuery();

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
            } else {
                JOptionPane.showMessageDialog(null, "Empleado no encontrado", 
                        "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }

            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar empleado: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Dao.cerrarConexion(conexion);
        }

        return empleado;
    }

    // Método para modificar un empleado
    public void modificarEmpleado(int idEmpleado, String nombreEmpleado, String fechaInicio, 
                                  String fechaTermino, String tipoContrato, boolean planSalud, boolean afp) {
        // Validación de campos vacíos
        if (nombreEmpleado.isEmpty() || fechaInicio.isEmpty() || fechaTermino.isEmpty() || tipoContrato.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos de texto son obligatorios.", 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conexion = Dao.obtenerConexion();
        String sql = "UPDATE Empleado SET nombreEmpleado = ?, fechaInicio = ?, fechaTermino = ?, tipoContrato = ?, planSalud = ?, afp = ? WHERE IdEmpleado = ?";

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, nombreEmpleado);
            statement.setString(2, fechaInicio);
            statement.setString(3, fechaTermino);
            statement.setString(4, tipoContrato);
            statement.setBoolean(5, planSalud);
            statement.setBoolean(6, afp);
            statement.setInt(7, idEmpleado);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Empleado modificado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listarEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el empleado para modificar", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar empleado: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Dao.cerrarConexion(conexion);
        }
    }

    // Método para eliminar un empleado
    public void eliminarEmpleado(int idEmpleado) {
        Connection conexion = Dao.obtenerConexion();
        String sql = "DELETE FROM Empleado WHERE IdEmpleado = ?";

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idEmpleado);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listarEmpleados();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el empleado para eliminar", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar empleado: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Dao.cerrarConexion(conexion);
        }
    }

    // Método para listar todos los empleados
    public void listarEmpleados() {
        Connection conexion = Dao.obtenerConexion();
        String sql = "SELECT * FROM Empleado";

        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();

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

            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar empleados: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Dao.cerrarConexion(conexion);
        }
    }
}
