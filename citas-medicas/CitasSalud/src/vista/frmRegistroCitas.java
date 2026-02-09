package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmRegistroCitas extends JFrame {

    public frmRegistroCitas() {
        setTitle("Registro de Citas");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 25);
        add(lblPaciente);

        JComboBox<String> cboPaciente = new JComboBox<>();
        cboPaciente.setBounds(100, 20, 200, 25);
        add(cboPaciente);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(350, 20, 80, 25);
        add(lblMedico);

        JComboBox<String> cboMedico = new JComboBox<>();
        cboMedico.setBounds(430, 20, 200, 25);
        add(cboMedico);

        JButton btnAgregar = new JButton("Agregar Cita");
        btnAgregar.setBounds(100, 70, 140, 35);
        add(btnAgregar);

        JButton btnCancelar = new JButton("Cancelar Cita");
        btnCancelar.setBounds(260, 70, 140, 35);
        add(btnCancelar);

        JTable tabla = new JTable(new DefaultTableModel(
                new Object[]{"N°", "Paciente", "Médico", "Fecha", "Hora", "Estado"}, 0
        ));
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 130, 650, 220);
        add(sp);
    }
}