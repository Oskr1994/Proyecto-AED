package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmConsultaCitas extends JFrame {

    public frmConsultaCitas() {
        setTitle("Consulta de Citas");
        setSize(650, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(10, 10, 610, 320);
        add(tabs);

        JTable tabla = new JTable(new DefaultTableModel(
                new Object[]{"Cita", "Paciente", "Médico", "Fecha", "Estado"}, 0
        ));

        tabs.add("Por Paciente", new JScrollPane(tabla));
        tabs.add("Por Médico", new JScrollPane(tabla));
        tabs.add("Por Fecha", new JScrollPane(tabla));
        tabs.add("Por Consultorio", new JScrollPane(tabla));
    }
}