package vista;

import controlador.ControladorConsultorio;
import modelo.Consultorio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmConsultorio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmConsultorio() {
        setTitle("Mantenimiento de Consultorio");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ===== CAMPOS =====
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        getContentPane().add(txtNombre);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 60, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[] { "Activo", "Inactivo" });
        cboEstado.setBounds(100, 60, 120, 25);
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
                new String[] { "Código", "Nombre", "Estado" }, 0);

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 140, 560, 210);
        getContentPane().add(scroll);

        // ===== EVENTOS =====

        // AGREGAR
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText();

            if (ControladorConsultorio.agregar(nombre)) {
                JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Nombre ya existe o está vacío");
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un consultorio");
                return;
            }

            int codigo = (int) modelo.getValueAt(fila, 0);
            String nuevoNombre = txtNombre.getText();
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorConsultorio.modificar(codigo, nuevoNombre, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Consultorio modificado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar consultorio");
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un consultorio");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este consultorio?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int codigo = (int) modelo.getValueAt(fila, 0);

                if (ControladorConsultorio.eliminar(codigo)) {
                    JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar consultorio");
                }
            }
        });

        // CLICK TABLA
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String nombre = (String) modelo.getValueAt(fila, 1);
                String estadoStr = (String) modelo.getValueAt(fila, 2);

                txtNombre.setText(nombre);
                cboEstado.setSelectedIndex(estadoStr.equals("Activo") ? 0 : 1);
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
        for (Consultorio c : ControladorConsultorio.listar()) {
            modelo.addRow(new Object[] {
                    c.getCodigo(),
                    c.getNombre(),
                    c.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        cboEstado.setSelectedIndex(0);
    }
}