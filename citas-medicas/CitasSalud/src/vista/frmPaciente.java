package vista;

import controlador.ControladorPaciente;
import modelo.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;

public class frmPaciente extends JDialog {

    // Campos del formulario
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtDni;
    private JTextField txtEdad;
    private JTextField txtCelular;
    private JTextField txtEmail;
    private JComboBox<String> cboEstado;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmPaciente(Frame owner) {
        super(owner, "Mantenimiento de Paciente", true);
        setSize(780, 560);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ETIQUETAS Y CAMPOS

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 170, 25);
        getContentPane().add(txtNombre);

        // Apellidos
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(20, 55, 80, 25);
        getContentPane().add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(100, 55, 170, 25);
        getContentPane().add(txtApellidos);

        // DNI
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 90, 80, 25);
        getContentPane().add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(100, 90, 170, 25);
        getContentPane().add(txtDni);

        // Edad
        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(20, 125, 80, 25);
        getContentPane().add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(100, 125, 170, 25);
        getContentPane().add(txtEdad);

        // Celular
        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(20, 160, 80, 25);
        getContentPane().add(lblCelular);

        txtCelular = new JTextField();
        txtCelular.setBounds(100, 160, 170, 25);
        getContentPane().add(txtCelular);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 195, 80, 25);
        getContentPane().add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 195, 170, 25);
        getContentPane().add(txtEmail);

        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 230, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cboEstado.setBounds(100, 230, 120, 25);
        getContentPane().add(cboEstado);

        // BOTONES


        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(420, 20, 120, 30);
        getContentPane().add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(550, 20, 120, 30);
        getContentPane().add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(420, 60, 120, 30);
        getContentPane().add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(550, 60, 120, 30);
        getContentPane().add(btnLimpiar);


        // TABLA 

        modelo = new DefaultTableModel(
                new String[]{"Código", "Nombre", "Apellidos", "DNI",
                             "Edad", "Celular", "Email", "Estado"}, 0) {
								private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // solo lectura
            }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // Código
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100); // Apellidos
        tabla.getColumnModel().getColumn(3).setPreferredWidth(70);  // DNI
        tabla.getColumnModel().getColumn(4).setPreferredWidth(40);  // Edad
        tabla.getColumnModel().getColumn(5).setPreferredWidth(80);  // Celular
        tabla.getColumnModel().getColumn(6).setPreferredWidth(130); // Email
        tabla.getColumnModel().getColumn(7).setPreferredWidth(60);  // Estado

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 275, 730, 235);
        getContentPane().add(scroll);

        // EVENTOS

        // AGREGAR
        btnAgregar.addActionListener(e -> {

            String nombre    = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String dni       = txtDni.getText().trim();
            String edadTxt   = txtEdad.getText().trim();
            String celularTxt= txtCelular.getText().trim();
            String email     = txtEmail.getText().trim();

            // Nombre, apellidos y DNI son obligatorios
            if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre, Apellidos y DNI son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar que edad y celular sean números
            int edad, celular;
            try {
                edad    = edadTxt.isEmpty() ? 0 : Integer.parseInt(edadTxt);
                celular = celularTxt.isEmpty() ? 0 : Integer.parseInt(celularTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Edad y Celular deben ser números enteros.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (ControladorPaciente.agregar(nombre, apellidos, dni, edad, celular, email)) {
                JOptionPane.showMessageDialog(this, "Paciente agregado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El DNI ya existe o la edad es inválida.",
                        "Error al agregar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente de la tabla.");
                return;
            }

            String nombre    = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String dni       = txtDni.getText().trim();
            String edadTxt   = txtEdad.getText().trim();
            String celularTxt= txtCelular.getText().trim();
            String email     = txtEmail.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre, Apellidos y DNI son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad, celular;
            try {
                edad    = edadTxt.isEmpty() ? 0 : Integer.parseInt(edadTxt);
                celular = celularTxt.isEmpty() ? 0 : Integer.parseInt(celularTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Edad y Celular deben ser números enteros.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo      = (int) modelo.getValueAt(fila, 0);
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorPaciente.modificar(codigo, nombre, apellidos, dni,
                                              edad, celular, email, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Paciente modificado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El DNI ya está en uso por otro paciente.",
                        "Error al modificar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este paciente?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int codigo = (int) modelo.getValueAt(fila, 0);

                if (ControladorPaciente.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente.");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo eliminar el paciente.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CLICK EN TABLA — carga los datos del paciente seleccionado en los campos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtNombre.setText((String) modelo.getValueAt(fila, 1));
                txtApellidos.setText((String) modelo.getValueAt(fila, 2));
                txtDni.setText((String) modelo.getValueAt(fila, 3));
                txtEdad.setText(String.valueOf(modelo.getValueAt(fila, 4)));
                txtCelular.setText(String.valueOf(modelo.getValueAt(fila, 5)));
                txtEmail.setText((String) modelo.getValueAt(fila, 6));
                String estadoStr = (String) modelo.getValueAt(fila, 7);
                cboEstado.setSelectedIndex(estadoStr.equals("Activo") ? 0 : 1);
            }
        });

        // LIMPIAR
        btnLimpiar.addActionListener(e -> limpiar());

        // Cargar datos al abrir
        cargarTabla();
    }

    // CARGAR TABLA — muestra los 8 campos de cada paciente

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Paciente p : ControladorPaciente.listar()) {
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getApellidos(),
                    p.getDni(),
                    p.getEdad(),
                    p.getCelular(),
                    p.getEmail(),
                    p.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    // LIMPIAR — resetea todos los campos del formulario

    private void limpiar() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtEdad.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        cboEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
