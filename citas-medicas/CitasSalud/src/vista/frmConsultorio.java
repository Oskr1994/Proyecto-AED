package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmConsultorio extends JFrame {

    public frmConsultorio() {
        setTitle("Mantenimiento de Consultorio");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 25);
        add(txtNombre);

        JButton btnAgregar = new JButton("Adicionar");
        btnAgregar.setBounds(20, 70, 100, 30);
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(130, 70, 100, 30);
        add(btnEliminar);

        JTable tabla = new JTable(new DefaultTableModel(
                new Object[]{"Código", "Consultorio", "Estado"}, 0
        ));
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 120, 490, 200);
        add(sp);
    }
}