package vista;

import controlador.ControladorPaciente;
import modelo.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmPaciente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtDni, txtNombre;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmPaciente() {

        setTitle("Mantenimiento de Paciente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // ===== CAMPOS =====
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 20, 80, 25);
        getContentPane().add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(100, 20, 150, 25);
        getContentPane().add(txtDni);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 200, 25);
        getContentPane().add(txtNombre);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 100, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[] { "Activo", "Inactivo" });
        cboEstado.setBounds(100, 100, 120, 25);
        getContentPane().add(cboEstado);

        // ===== BOTONES =====
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(350, 20, 100, 25);
        getContentPane().add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(350, 60, 100, 25);
        getContentPane().add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 100, 25);
        getContentPane().add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(460, 20, 100, 25);
        getContentPane().add(btnLimpiar);

        // ===== TABLA =====
        modelo = new DefaultTableModel(
                new String[] { "Código", "DNI", "Nombre", "Estado" }, 0);

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 540, 180);
        getContentPane().add(scroll);

        // ===== EVENTOS =====

        // AGREGAR
        btnAgregar.addActionListener(e -> {
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();

            if (ControladorPaciente.agregar(dni, nombre)) {
                JOptionPane.showMessageDialog(this, "Paciente agregado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error: DNI ya existe o campos vacíos");
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente");
                return;
            }

            String dni = (String) modelo.getValueAt(fila, 1);
            String nuevoNombre = txtNombre.getText();
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorPaciente.modificar(dni, nuevoNombre, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Paciente modificado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar paciente");
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este paciente?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String dni = (String) modelo.getValueAt(fila, 1);

                if (ControladorPaciente.eliminar(dni)) {
                    JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar paciente");
                }
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String dni = (String) modelo.getValueAt(fila, 1);
                String nombre = (String) modelo.getValueAt(fila, 2);
                String estadoStr = (String) modelo.getValueAt(fila, 3);

                txtDni.setText(dni);
                txtNombre.setText(nombre);
                cboEstado.setSelectedIndex(estadoStr.equals("Activo") ? 0 : 1);
                txtDni.setEditable(false);
            }
        });

        // LIMPIAR
        btnLimpiar.addActionListener(e -> {
            limpiar();
        });

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Paciente p : ControladorPaciente.listar()) {
            modelo.addRow(new Object[] {
                    p.getCodigo(),
                    p.getDni(),
                    p.getNombre(),
                    p.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    private void limpiar() {
        txtDni.setText("");
        txtNombre.setText("");
        cboEstado.setSelectedIndex(0);
        txtDni.setEditable(true);
    }
}