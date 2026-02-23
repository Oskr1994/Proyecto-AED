package vista;

import controlador.ControladorConsultorio;
import modelo.Consultorio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;

public class frmConsultorio extends JDialog {

    // ── Campos del formulario ────────────────────────────────────────────
    private JTextField txtNombre;
    private JTextField txtPiso;
    private JTextField txtUbicacion;
    private JTextField txtCapacidad;
    private JComboBox<String> cboEstado;

    // ── Tabla ────────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmConsultorio(Frame owner) {
        super(owner, "Mantenimiento de Consultorio", true);
        setSize(680, 530);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ================================================================
        // ETIQUETAS Y CAMPOS — todos declarados aquí desde el inicio
        // ================================================================

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 180, 25);
        getContentPane().add(txtNombre);

        // Piso
        JLabel lblPiso = new JLabel("Piso:");
        lblPiso.setBounds(20, 55, 80, 25);
        getContentPane().add(lblPiso);

        txtPiso = new JTextField();
        txtPiso.setBounds(100, 55, 180, 25);
        getContentPane().add(txtPiso);

        // Ubicación
        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setBounds(20, 90, 80, 25);
        getContentPane().add(lblUbicacion);

        txtUbicacion = new JTextField();
        txtUbicacion.setBounds(100, 90, 180, 25);
        getContentPane().add(txtUbicacion);

        // Capacidad
        JLabel lblCapacidad = new JLabel("Capacidad:");
        lblCapacidad.setBounds(20, 125, 80, 25);
        getContentPane().add(lblCapacidad);

        txtCapacidad = new JTextField();
        txtCapacidad.setBounds(100, 125, 180, 25);
        getContentPane().add(txtCapacidad);

        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 160, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cboEstado.setBounds(100, 160, 120, 25);
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
        // TABLA — ahora muestra los 6 campos del consultorio
        // ================================================================
        modelo = new DefaultTableModel(
                new String[]{"Código", "Nombre", "Piso", "Ubicación", "Capacidad", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(55);  // Código
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(40);  // Piso
        tabla.getColumnModel().getColumn(3).setPreferredWidth(160); // Ubicación
        tabla.getColumnModel().getColumn(4).setPreferredWidth(70);  // Capacidad
        tabla.getColumnModel().getColumn(5).setPreferredWidth(65);  // Estado

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 210, 630, 270);
        getContentPane().add(scroll);

        // ================================================================
        // EVENTOS
        // ================================================================

        // AGREGAR
        btnAgregar.addActionListener(e -> {

            String nombre    = txtNombre.getText().trim();
            String pisoTxt   = txtPiso.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String capTxt    = txtCapacidad.getText().trim();

            // Validar que todos los campos estén completos
            if (nombre.isEmpty() || pisoTxt.isEmpty() || ubicacion.isEmpty() || capTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar que piso y capacidad sean números enteros
            int piso, capacidad;
            try {
                piso      = Integer.parseInt(pisoTxt);
                capacidad = Integer.parseInt(capTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Piso y Capacidad deben ser números enteros.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (ControladorConsultorio.agregar(nombre, piso, ubicacion, capacidad)) {
                JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El nombre ya existe, o piso/capacidad deben ser mayores a 0.",
                        "Error al agregar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un consultorio de la tabla.");
                return;
            }

            String nombre    = txtNombre.getText().trim();
            String pisoTxt   = txtPiso.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String capTxt    = txtCapacidad.getText().trim();

            if (nombre.isEmpty() || pisoTxt.isEmpty() || ubicacion.isEmpty() || capTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int piso, capacidad;
            try {
                piso      = Integer.parseInt(pisoTxt);
                capacidad = Integer.parseInt(capTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Piso y Capacidad deben ser números enteros.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo     = (int) modelo.getValueAt(fila, 0);
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorConsultorio.modificar(codigo, nombre, piso, ubicacion, capacidad, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Consultorio modificado correctamente.");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: El nombre ya está en uso por otro consultorio.",
                        "Error al modificar",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un consultorio de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este consultorio?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int codigo = (int) modelo.getValueAt(fila, 0);

                if (ControladorConsultorio.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente.");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo eliminar el consultorio.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CLICK EN TABLA — carga los datos del consultorio seleccionado en los campos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return; // evitar doble disparo
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtNombre.setText((String) modelo.getValueAt(fila, 1));
                txtPiso.setText(String.valueOf(modelo.getValueAt(fila, 2)));
                txtUbicacion.setText((String) modelo.getValueAt(fila, 3));
                txtCapacidad.setText(String.valueOf(modelo.getValueAt(fila, 4)));
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
    // CARGAR TABLA — muestra los 6 campos de cada consultorio
    // ====================================================================
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Consultorio c : ControladorConsultorio.listar()) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    c.getNombre(),
                    c.getPiso(),
                    c.getUbicacion(),
                    c.getCapacidad(),
                    c.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    // ====================================================================
    // LIMPIAR — resetea todos los campos del formulario
    // ====================================================================
    private void limpiar() {
        txtNombre.setText("");
        txtPiso.setText("");
        txtUbicacion.setText("");
        txtCapacidad.setText("");
        cboEstado.setSelectedIndex(0);
        tabla.clearSelection(); // deselecciona la fila de la tabla
    }
}
