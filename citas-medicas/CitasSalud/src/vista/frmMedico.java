package vista;

import controlador.ControladorMedico;
import modelo.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;

public class frmMedico extends JDialog {

    // ── Campos del formulario ────────────────────────────────────────────
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtEspecialidad;
    private JTextField txtCmp;
    private JComboBox<String> cboEstado;

    // ── Tabla ────────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmMedico(Frame owner) {
        super(owner, "Mantenimiento de Médico", true);
        setSize(680, 530);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ================================================================
        // ETIQUETAS Y CAMPOS
        // ================================================================

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(125, 20, 180, 25);
        getContentPane().add(txtNombre);

        // Apellidos
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(20, 55, 80, 25);
        getContentPane().add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(125, 55, 180, 25);
        getContentPane().add(txtApellidos);

        // Especialidad
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(20, 90, 90, 25);
        getContentPane().add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(125, 90, 180, 25);
        getContentPane().add(txtEspecialidad);

        // CMP
        JLabel lblCmp = new JLabel("CMP:");
        lblCmp.setBounds(20, 125, 80, 25);
        getContentPane().add(lblCmp);

        txtCmp = new JTextField();
        txtCmp.setBounds(125, 125, 180, 25);
        getContentPane().add(txtCmp);

        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 160, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cboEstado.setBounds(125, 160, 120, 25);
        getContentPane().add(cboEstado);

        // ================================================================
        // BOTONES
        // ================================================================

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(370, 20, 120, 30);
        getContentPane().add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(500, 20, 120, 30);
        getContentPane().add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(370, 60, 120, 30);
        getContentPane().add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(500, 60, 120, 30);
        getContentPane().add(btnLimpiar);

        // ================================================================
        // TABLA — muestra los 6 campos del médico
        // ================================================================
        modelo = new DefaultTableModel(
                new String[]{"Código", "Nombre", "Apellidos", "Especialidad", "CMP", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // solo lectura
            }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(55);  // Código
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(110); // Apellidos
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120); // Especialidad
        tabla.getColumnModel().getColumn(4).setPreferredWidth(70);  // CMP
        tabla.getColumnModel().getColumn(5).setPreferredWidth(65);  // Estado

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 210, 630, 270);
        getContentPane().add(scroll);

        // ================================================================
        // EVENTOS
        // ================================================================

        // AGREGAR
        btnAgregar.addActionListener(e -> {

            String nombre       = txtNombre.getText().trim();
            String apellidos    = txtApellidos.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String cmp          = txtCmp.getText().trim();

            // Nombre, apellidos y especialidad son obligatorios; CMP es opcional
            if (nombre.isEmpty() || apellidos.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre, Apellidos y Especialidad son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (ControladorMedico.agregar(nombre, apellidos, especialidad, cmp)) {
                JOptionPane.showMessageDialog(this, "Médico agregado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El CMP ingresado ya existe o los datos son inválidos.",
                        "Error al agregar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un médico de la tabla.");
                return;
            }

            String nombre       = txtNombre.getText().trim();
            String apellidos    = txtApellidos.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String cmp          = txtCmp.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre, Apellidos y Especialidad son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo      = (int) modelo.getValueAt(fila, 0);
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorMedico.modificar(codigo, nombre, apellidos, especialidad, cmp, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Médico modificado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El CMP ya está en uso por otro médico.",
                        "Error al modificar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un médico de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este médico?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int codigo = (int) modelo.getValueAt(fila, 0);

                if (ControladorMedico.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Médico eliminado correctamente.");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo eliminar el médico.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CLICK EN TABLA — carga los datos del médico seleccionado en los campos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return; // evitar doble disparo
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtNombre.setText((String) modelo.getValueAt(fila, 1));
                txtApellidos.setText((String) modelo.getValueAt(fila, 2));
                txtEspecialidad.setText((String) modelo.getValueAt(fila, 3));
                txtCmp.setText((String) modelo.getValueAt(fila, 4));
                String estadoStr = (String) modelo.getValueAt(fila, 5);
                cboEstado.setSelectedIndex(estadoStr.equals("Activo") ? 0 : 1);
            }
        });

        // LIMPIAR
        btnLimpiar.addActionListener(e -> limpiar());

        // Cargar datos al abrir
        cargarTabla();
    }

    // ====================================================================
    // CARGAR TABLA — muestra los 6 campos de cada médico
    // ====================================================================
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Medico m : ControladorMedico.listar()) {
            modelo.addRow(new Object[]{
                    m.getCodigo(),
                    m.getNombre(),
                    m.getApellidos(),
                    m.getEspecialidad(),
                    m.getCmp(),
                    m.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    // ====================================================================
    // LIMPIAR — resetea todos los campos del formulario
    // ====================================================================
    private void limpiar() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEspecialidad.setText("");
        txtCmp.setText("");
        cboEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
