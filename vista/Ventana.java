package vista;

import controlador.ControladorEmpleado;
import modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Ventana extends JFrame {

    // Componentes Swing
    private JLabel lblTitulo;
    private JLabel lblNombre, lblFechaInicio, lblFechaTermino, lblTipoContrato;
    private JLabel lblBuscarId;
    
    private JTextField txtNombre, txtFechaInicio, txtFechaTermino;
    private JTextField txtBuscarId;
    
    private JComboBox<String> cmbTipoContrato;
    private JCheckBox chkPlanSalud, chkAFP;
    
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar;
    
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    
    private ControladorEmpleado controlador;

    public Ventana() {
        setTitle("Sistema de Gestión de Pagos - TECNOQUIM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 620);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Fecha Inicio", "Fecha Término", "Tipo Contrato", "Plan Salud", "AFP"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        
        // Crear controlador
        controlador = new ControladorEmpleado(modeloTabla);
        
        // Configurar panel principal
        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(new Color(250, 128, 114));
        setContentPane(panelPrincipal);
        
        configurarComponentes();
        configurarEventos();
        cargarEmpleados();
    }

    private void configurarComponentes() {
        JPanel panel = (JPanel) getContentPane();
        
        // Título
        lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE PAGOS - TECNOQUIM");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 20, 1100, 30);
        panel.add(lblTitulo);
        
        // Sección: Búsqueda por ID
        lblBuscarId = new JLabel("Buscar por ID:");
        lblBuscarId.setBounds(50, 70, 120, 20);
        panel.add(lblBuscarId);
        
        txtBuscarId = new JTextField();
        txtBuscarId.setBounds(50, 95, 200, 25);
        txtBuscarId.setBackground(Color.WHITE);
        panel.add(txtBuscarId);
        
        // Sección: Formulario de registro
        lblNombre = new JLabel("Nombre Empleado:");
        lblNombre.setBounds(50, 135, 150, 20);
        panel.add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(50, 160, 200, 25);
        txtNombre.setBackground(Color.WHITE);
        panel.add(txtNombre);
        
        // Fecha Inicio
        lblFechaInicio = new JLabel("Fecha Inicio (yyyy-MM-dd):");
        lblFechaInicio.setBounds(50, 195, 150, 20);
        panel.add(lblFechaInicio);
        
        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(50, 220, 200, 25);
        txtFechaInicio.setBackground(Color.WHITE);
        panel.add(txtFechaInicio);
        
        // Fecha Término
        lblFechaTermino = new JLabel("Fecha Término (yyyy-MM-dd):");
        lblFechaTermino.setBounds(50, 255, 150, 20);
        panel.add(lblFechaTermino);
        
        txtFechaTermino = new JTextField();
        txtFechaTermino.setBounds(50, 280, 200, 25);
        txtFechaTermino.setBackground(Color.WHITE);
        panel.add(txtFechaTermino);
        
        // Tipo Contrato
        lblTipoContrato = new JLabel("Tipo Contrato:");
        lblTipoContrato.setBounds(50, 315, 120, 20);
        panel.add(lblTipoContrato);
        
        cmbTipoContrato = new JComboBox<>(new String[]{"Indefinido", "Plazo Fijo", "Honorarios"});
        cmbTipoContrato.setBounds(50, 340, 200, 25);
        panel.add(cmbTipoContrato);
        
        // Checkboxes
        chkPlanSalud = new JCheckBox("Plan de Salud");
        chkPlanSalud.setBounds(50, 375, 150, 20);
        chkPlanSalud.setBackground(new Color(250, 128, 114));
        panel.add(chkPlanSalud);
        
        chkAFP = new JCheckBox("AFP");
        chkAFP.setBounds(50, 400, 100, 20);
        chkAFP.setBackground(new Color(250, 128, 114));
        panel.add(chkAFP);
        
        // Tabla de empleados
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listener para seleccionar fila y rellenar formulario
        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEmpleados.getSelectedRow() != -1) {
                int row = tablaEmpleados.getSelectedRow();
                int idSeleccionado = (Integer) modeloTabla.getValueAt(row, 0);
                txtBuscarId.setText(String.valueOf(idSeleccionado));
                
                txtNombre.setText(modeloTabla.getValueAt(row, 1).toString());
                txtFechaInicio.setText(modeloTabla.getValueAt(row, 2).toString());
                txtFechaTermino.setText(modeloTabla.getValueAt(row, 3).toString());
                cmbTipoContrato.setSelectedItem(modeloTabla.getValueAt(row, 4).toString());
                chkPlanSalud.setSelected("Sí".equals(modeloTabla.getValueAt(row, 5).toString()));
                chkAFP.setSelected("Sí".equals(modeloTabla.getValueAt(row, 6).toString()));
            }
        });
        
        scrollPane = new JScrollPane(tablaEmpleados);
        scrollPane.setBounds(290, 70, 880, 390);
        panel.add(scrollPane);
        
        // Botones
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(50, 450, 100, 35);
        panel.add(btnAgregar);
        
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(160, 450, 100, 35);
        panel.add(btnConsultar);
        
        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(50, 495, 100, 35);
        panel.add(btnModificar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(160, 495, 100, 35);
        panel.add(btnEliminar);
    }

    private void configurarEventos() {
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });
        
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarEmpleado();
            }
        });
        
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEmpleado();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
            }
        });
    }

    private void agregarEmpleado() {
        try {
            String nombre = txtNombre.getText().trim();
            String fechaInicio = txtFechaInicio.getText().trim();
            String fechaTermino = txtFechaTermino.getText().trim();
            String tipoContrato = (String) cmbTipoContrato.getSelectedItem();
            boolean planSalud = chkPlanSalud.isSelected();
            boolean afp = chkAFP.isSelected();
            
            if (controlador.agregarEmpleado(nombre, fechaInicio, fechaTermino, 
                                           tipoContrato, planSalud, afp)) {
                JOptionPane.showMessageDialog(this, "Empleado agregado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar el empleado", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarEmpleado() {
        try {
            String idStr = txtBuscarId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID para buscar", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = Integer.parseInt(idStr);
            Empleado empleado = controlador.consultarEmpleado(id);
            
            if (empleado != null) {
                txtNombre.setText(empleado.getNombreEmpleado());
                txtFechaInicio.setText(empleado.getFechaInicio().toString());
                txtFechaTermino.setText(empleado.getFechaTermino().toString());
                cmbTipoContrato.setSelectedItem(empleado.getTipoContrato());
                chkPlanSalud.setSelected(empleado.isPlanSalud());
                chkAFP.setSelected(empleado.isAfp());
                
                JOptionPane.showMessageDialog(this, "Empleado encontrado", 
                        "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Empleado no encontrado", 
                        "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEmpleado() {
        try {
            String idStr = txtBuscarId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado o ingrese su ID", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = Integer.parseInt(idStr);
            String nombre = txtNombre.getText().trim();
            String fechaInicio = txtFechaInicio.getText().trim();
            String fechaTermino = txtFechaTermino.getText().trim();
            String tipoContrato = (String) cmbTipoContrato.getSelectedItem();
            boolean planSalud = chkPlanSalud.isSelected();
            boolean afp = chkAFP.isSelected();
            
            if (controlador.modificarEmpleado(id, nombre, fechaInicio, fechaTermino, 
                                             tipoContrato, planSalud, afp)) {
                JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el empleado para modificar", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Error de Validación", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado() {
        try {
            String idStr = txtBuscarId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado o ingrese su ID", 
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = Integer.parseInt(idStr);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de eliminar el empleado con ID " + id + "?", 
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controlador.eliminarEmpleado(id)) {
                    JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el empleado para eliminar", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEmpleados() {
        try {
            controlador.listarEmpleados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtBuscarId.setText("");
        txtNombre.setText("");
        txtFechaInicio.setText("");
        txtFechaTermino.setText("");
        cmbTipoContrato.setSelectedIndex(0);
        chkPlanSalud.setSelected(false);
        chkAFP.setSelected(false);
        tablaEmpleados.clearSelection();
        cargarEmpleados();
    }
}


