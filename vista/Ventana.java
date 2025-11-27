package vista;

import controlador.ControladorEmpleado;
import modelo.Empleado;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class Ventana extends Frame {

    // Componentes de la interfaz gráfica
    private Label lblTitulo;
    private Label lblIdEmpleado, lblNombreEmpleado, lblFechaInicio, lblFechaTermino, lblTipoContrato;
    private JTextField txtIdEmpleado, txtNombreEmpleado, txtFechaInicio, txtFechaTermino;
    private Choice choiceTipoContrato;
    private Checkbox chkPlanSalud, chkAFP;
    private Button btnAgregar, btnConsultar, btnModificar, btnEliminar;
    private JTable tablaRegistrosEmpleado;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private ControladorEmpleado controlador;

    // Constructor de la ventana
    public Ventana() {
        setLayout(null);
        setSize(1200, 600);
        setTitle("Sistema de Gestión de Pagos - TECNOQUIM");
        setBackground(new Color(250, 128, 114)); // Color salmón
        setLocationRelativeTo(null);
        
        // Crear el modelo de tabla
        String[] columnas = {"ID", "Nombre", "Fecha Inicio", "Fecha Término", "Tipo Contrato", "Plan Salud", "AFP"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        
        // Crear el controlador
        controlador = new ControladorEmpleado(modeloTabla);
        
        configurarCamposDeEntradas();
        configurarBotonesDeEventos();
        configurarTablaDeDatos();
        
        // Cargar los registros al iniciar
        controlador.listarEmpleados();
        
        // Evento para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Método de configuración para los campos de entrada
    private void configurarCamposDeEntradas() {
        lblTitulo = new Label("SISTEMA DE GESTIÓN DE PAGOS - TECNOQUIM");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignment(Label.CENTER);
        lblTitulo.setBounds(50, 70, 1100, 30);
        add(lblTitulo);

        // ID Empleado
        lblIdEmpleado = new Label("ID Empleado:");
        lblIdEmpleado.setBounds(50, 120, 120, 20);
        add(lblIdEmpleado);
        txtIdEmpleado = new JTextField();
        txtIdEmpleado.setBounds(50, 145, 200, 25);
        txtIdEmpleado.setBackground(Color.WHITE);
        add(txtIdEmpleado);

        // Nombre Empleado
        lblNombreEmpleado = new Label("Nombre Empleado:");
        lblNombreEmpleado.setBounds(50, 180, 150, 20);
        add(lblNombreEmpleado);
        txtNombreEmpleado = new JTextField();
        txtNombreEmpleado.setBounds(50, 205, 200, 25);
        txtNombreEmpleado.setBackground(Color.WHITE);
        add(txtNombreEmpleado);

        // Fecha Inicio
        lblFechaInicio = new Label("Fecha Inicio (yyyy-MM-dd):");
        lblFechaInicio.setBounds(50, 240, 150, 20);
        add(lblFechaInicio);
        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(50, 265, 200, 25);
        txtFechaInicio.setBackground(Color.WHITE);
        txtFechaInicio.setToolTipText("yyyy-MM-dd");
        add(txtFechaInicio);

        // Fecha Término
        lblFechaTermino = new Label("Fecha Término (yyyy-MM-dd):");
        lblFechaTermino.setBounds(50, 300, 150, 20);
        add(lblFechaTermino);
        txtFechaTermino = new JTextField();
        txtFechaTermino.setBounds(50, 325, 200, 25);
        txtFechaTermino.setBackground(Color.WHITE);
        txtFechaTermino.setToolTipText("yyyy-MM-dd");
        add(txtFechaTermino);

        // Tipo Contrato
        lblTipoContrato = new Label("Tipo Contrato:");
        lblTipoContrato.setBounds(50, 360, 120, 20);
        add(lblTipoContrato);
        choiceTipoContrato = new Choice();
        choiceTipoContrato.add("Indefinido");
        choiceTipoContrato.add("Plazo Fijo");
        choiceTipoContrato.add("Honorarios");
        choiceTipoContrato.setBounds(50, 385, 200, 25);
        add(choiceTipoContrato);

        // Checkboxes
        chkPlanSalud = new Checkbox("Plan de Salud");
        chkPlanSalud.setBounds(50, 420, 150, 20);
        add(chkPlanSalud);
        chkAFP = new Checkbox("AFP");
        chkAFP.setBounds(50, 445, 100, 20);
        add(chkAFP);
    }

    // Método para configurar la tabla de datos
    private void configurarTablaDeDatos() {
        tablaRegistrosEmpleado = new JTable(modeloTabla);
        tablaRegistrosEmpleado.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane = new JScrollPane(tablaRegistrosEmpleado);
        scrollPane.setBounds(290, 120, 880, 340);
        add(scrollPane);

        // Selección de fila para rellenar inputs
        tablaRegistrosEmpleado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRegistrosEmpleado.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaRegistrosEmpleado.getSelectedRow() != -1) {
                int row = tablaRegistrosEmpleado.getSelectedRow();
                txtIdEmpleado.setText(modeloTabla.getValueAt(row, 0).toString());
                txtNombreEmpleado.setText(modeloTabla.getValueAt(row, 1).toString());
                txtFechaInicio.setText(modeloTabla.getValueAt(row, 2).toString());
                txtFechaTermino.setText(modeloTabla.getValueAt(row, 3).toString());
                choiceTipoContrato.select(modeloTabla.getValueAt(row, 4).toString());
                chkPlanSalud.setState("Sí".equals(modeloTabla.getValueAt(row, 5).toString()));
                chkAFP.setState("Sí".equals(modeloTabla.getValueAt(row, 6).toString()));
            }
        });
    }

    // Método para configurar los botones y sus eventos
    private void configurarBotonesDeEventos() {
        btnAgregar = new Button("Agregar");
        btnAgregar.setBounds(50, 490, 100, 35);
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
                limpiarCampos();
            }
        });
        add(btnAgregar);

        btnConsultar = new Button("Consultar");
        btnConsultar.setBounds(160, 490, 100, 35);
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarEmpleado();
                limpiarCampos();
            }
        });
        add(btnConsultar);

        btnModificar = new Button("Modificar");
        btnModificar.setBounds(50, 530, 100, 35);
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEmpleado();
                limpiarCampos();
            }
        });
        add(btnModificar);

        btnEliminar = new Button("Eliminar");
        btnEliminar.setBounds(160, 530, 100, 35);
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
                limpiarCampos();
            }
        });
        add(btnEliminar);
    }

    // Método para agregar un empleado
    private void agregarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            String nombreEmpleado = txtNombreEmpleado.getText();
            String fechaInicio = txtFechaInicio.getText();
            String fechaTermino = txtFechaTermino.getText();
            String tipoContrato = choiceTipoContrato.getSelectedItem();
            boolean planSalud = chkPlanSalud.getState();
            boolean afp = chkAFP.getState();

            controlador.agregarEmpleado(idEmpleado, nombreEmpleado, fechaInicio, fechaTermino, 
                                       tipoContrato, planSalud, afp);
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para consultar un empleado
    private void consultarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            Empleado empleado = controlador.consultarEmpleado(idEmpleado);
            
            if (empleado != null) {
                txtNombreEmpleado.setText(empleado.getNombreEmpleado());
                txtFechaInicio.setText(empleado.getFechaInicio().toString());
                txtFechaTermino.setText(empleado.getFechaTermino().toString());
                choiceTipoContrato.select(empleado.getTipoContrato());
                chkPlanSalud.setState(empleado.isPlanSalud());
                chkAFP.setState(empleado.isAfp());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para modificar un empleado
    private void modificarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            String nombreEmpleado = txtNombreEmpleado.getText();
            String fechaInicio = txtFechaInicio.getText();
            String fechaTermino = txtFechaTermino.getText();
            String tipoContrato = choiceTipoContrato.getSelectedItem();
            boolean planSalud = chkPlanSalud.getState();
            boolean afp = chkAFP.getState();

            controlador.modificarEmpleado(idEmpleado, nombreEmpleado, fechaInicio, fechaTermino, 
                                         tipoContrato, planSalud, afp);
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un empleado
    private void eliminarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            controlador.eliminarEmpleado(idEmpleado);
            limpiarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar los campos de entrada
    private void limpiarCampos() {
        txtIdEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtFechaInicio.setText("");
        txtFechaTermino.setText("");
        choiceTipoContrato.select(0);
        chkPlanSalud.setState(false);
        chkAFP.setState(false);
    }
}


