package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmReporteCitas extends JFrame {

    public frmReporteCitas() {
        setTitle("Reporte de Citas");
        setSize(650, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JButton btnPendientes = new JButton("Citas Pendientes");
        btnPendientes.setBounds(20, 20, 160, 30);
        add(btnPendientes);

        JButton btnMedico = new JButton("Citas por Médico");
        btnMedico.setBounds(200, 20, 160, 30);
        add(btnMedico);

        JButton btnAgenda = new JButton("Agenda del Día");
        btnAgenda.setBounds(380, 20, 160, 30);
        add(btnAgenda);

        JTable tabla = new JTable(new DefaultTableModel(
                new Object[]{"Paciente", "Médico", "Fecha", "Hora", "Estado"}, 0
        ));
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 70, 600, 260);
        add(sp);
    }
}