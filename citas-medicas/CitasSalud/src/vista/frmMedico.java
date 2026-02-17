package vista;

import controlador.ControladorMedico;
import modelo.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmMedico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtCmp, txtNombre;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmMedico() {
        setTitle("Mantenimiento de Médico");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ===== CAMPOS =====
        JLabel lblCmp = new JLabel("CMP:");
        lblCmp.setBounds(20, 20, 80, 25);
        getContentPane().add(lblCmp);

        txtCmp = new JTextField();
        txtCmp.setBounds(100, 20, 150, 25);
        getContentPane().add(txtCmp);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 250, 25);
        getContentPane().add(txtNombre);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 100, 80, 25);
        getContentPane().add(lblEstado);

        cboEstado = new JComboBox<>(new String[] { "Activo", "Inactivo" });
        cboEstado.setBounds(100, 100, 120, 25);
        getContentPane().add(cboEstado);

        // ===== BOTONES =====
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(380, 20, 100, 25);
        getContentPane().add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(380, 60, 100, 25);
        getContentPane().add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(380, 100, 100, 25);
        getContentPane().add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(490, 20, 100, 25);
        getContentPane().add(btnLimpiar);

        // ===== TABLA =====
        modelo = new DefaultTableModel(
                new String[] { "Código", "CMP", "Nombre", "Estado" }, 0);

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 560, 200);
        getContentPane().add(scroll);

        // ===== EVENTOS =====

        // AGREGAR
        btnAgregar.addActionListener(e -> {
            String cmp = txtCmp.getText();
            String nombre = txtNombre.getText();

            if (ControladorMedico.agregar(cmp, nombre)) {
                JOptionPane.showMessageDialog(this, "Médico agregado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error: CMP ya existe o campos vacíos");
            }
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un médico");
                return;
            }

            String cmp = (String) modelo.getValueAt(fila, 1);
            String nuevoNombre = txtNombre.getText();
            int nuevoEstado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (ControladorMedico.modificar(cmp, nuevoNombre, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "Médico modificado correctamente");
                cargarTabla();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar médico");
            }
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un médico");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este médico?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String cmp = (String) modelo.getValueAt(fila, 1);

                if (ControladorMedico.eliminar(cmp)) {
                    JOptionPane.showMessageDialog(this, "Médico eliminado correctamente");
                    cargarTabla();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar médico");
                }
            }
        });

        // CLICK TABLA
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String cmp = (String) modelo.getValueAt(fila, 1);
                String nombre = (String) modelo.getValueAt(fila, 2);
                String estadoStr = (String) modelo.getValueAt(fila, 3);

                txtCmp.setText(cmp);
                txtNombre.setText(nombre);
                cboEstado.setSelectedIndex(estadoStr.equals("Activo") ? 0 : 1);
                txtCmp.setEditable(false);
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
        for (Medico m : ControladorMedico.listar()) {
            modelo.addRow(new Object[] {
                    m.getCodigo(),
                    m.getCmp(),
                    m.getNombre(),
                    m.getEstado() == 1 ? "Activo" : "Inactivo"
            });
        }
    }

    private void limpiar() {
        txtCmp.setText("");
        txtNombre.setText("");
        cboEstado.setSelectedIndex(0);
        txtCmp.setEditable(true);
    }
}