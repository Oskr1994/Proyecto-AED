package vista;

import modelo.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class frmPaciente extends JFrame {

    private ArrayList<Paciente> lista = new ArrayList<>();
    private int contador = 1;

    private JTextField txtDni, txtNombre;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

    public frmPaciente() {

        setTitle("Mantenimiento de Paciente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        // ===== CAMPOS =====
        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(20, 20, 80, 25);
        add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(100, 20, 150, 25);
        add(txtDni);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 200, 25);
        add(txtNombre);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 100, 80, 25);
        add(lblEstado);

        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cboEstado.setBounds(100, 100, 120, 25);
        add(cboEstado);

        // ===== BOTONES =====
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(350, 20, 100, 25);
        add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(350, 60, 100, 25);
        add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 100, 25);
        add(btnEliminar);

        // ===== TABLA =====
        modelo = new DefaultTableModel(
            new String[]{"Código", "DNI", "Nombre", "Estado"}, 0
        );

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 540, 180);
        add(scroll);

        // ===== EVENTOS =====

        // AGREGAR
        btnAgregar.addActionListener(e -> {
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            int estado = cboEstado.getSelectedIndex() == 0 ? 1 : 0;

            if (dni.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos");
                return;
            }

            // validar DNI único
            for (Paciente p : lista) {
                if (p.getDni().equals(dni)) {
                    JOptionPane.showMessageDialog(this, "DNI ya existe");
                    return;
                }
            }

            Paciente p = new Paciente(contador++, dni, nombre, estado);
            lista.add(p);
            cargarTabla();
            limpiar();
        });

        // MODIFICAR
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) return;

            Paciente p = lista.get(fila);
            p.setNombre(txtNombre.getText());
            p.setEstado(cboEstado.getSelectedIndex() == 0 ? 1 : 0);
            cargarTabla();
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) return;

            lista.remove(fila);
            cargarTabla();
            limpiar();
        });

        // CLICK TABLA
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                Paciente p = lista.get(fila);
                txtDni.setText(p.getDni());
                txtNombre.setText(p.getNombre());
                cboEstado.setSelectedIndex(p.getEstado() == 1 ? 0 : 1);
                txtDni.setEditable(false);
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Paciente p : lista) {
            modelo.addRow(new Object[]{
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