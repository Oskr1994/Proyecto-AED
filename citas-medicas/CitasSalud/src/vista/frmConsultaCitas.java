package vista;

import controlador.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class frmConsultaCitas extends JFrame {

    private static final long serialVersionUID = 1L;

    public frmConsultaCitas() {
        setTitle("Consulta de Citas");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(10, 10, 670, 390);
        getContentPane().add(tabs);

        // Pestaña Por Paciente
        tabs.add("Por Paciente", crearPanelPorPaciente());

        // Pestaña Por Médico
        tabs.add("Por Médico", crearPanelPorMedico());

        // Pestaña Por Fecha
        tabs.add("Por Fecha", crearPanelPorFecha());

        // Pestaña Por Consultorio
        tabs.add("Por Consultorio", crearPanelPorConsultorio());
    }

    private JPanel crearPanelPorPaciente() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 25);
        panel.add(lblPaciente);

        JComboBox<String> cbPaciente = new JComboBox<>();
        cbPaciente.setBounds(100, 20, 250, 25);
        cbPaciente.addItem("-- Seleccione Paciente --");
        for (Paciente p : ControladorPaciente.listar()) {
            cbPaciente.addItem(p.getDni() + " - " + p.getNombre());
        }
        panel.add(cbPaciente);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 620, 270);
        panel.add(scroll);

        cbPaciente.addActionListener(e -> {
            if (cbPaciente.getSelectedIndex() > 0) {
                String seleccionado = (String) cbPaciente.getSelectedItem();
                String dni = seleccionado.split(" - ")[0];
                Paciente paciente = ControladorPaciente.buscarPorDni(dni);

                modelo.setRowCount(0);
                for (Cita cita : ControladorCita.listar()) {
                    if (cita.getPaciente() == paciente) {
                        modelo.addRow(new Object[] {
                                cita.getNumCita(),
                                cita.getPaciente().getNombre(),
                                cita.getMedico().getNombre(),
                                cita.getFecha(),
                                cita.getHora(),
                                cita.getEstado() == 0 ? "Pendiente" : cita.getEstado() == 1 ? "Atendida" : "Cancelada"
                        });
                    }
                }
            }
        });

        return panel;
    }

    private JPanel crearPanelPorMedico() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(20, 20, 80, 25);
        panel.add(lblMedico);

        JComboBox<String> cbMedico = new JComboBox<>();
        cbMedico.setBounds(100, 20, 250, 25);
        cbMedico.addItem("-- Seleccione Médico --");
        for (Medico m : ControladorMedico.listar()) {
            cbMedico.addItem(m.getCmp() + " - " + m.getNombre());
        }
        panel.add(cbMedico);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 620, 270);
        panel.add(scroll);

        cbMedico.addActionListener(e -> {
            if (cbMedico.getSelectedIndex() > 0) {
                String seleccionado = (String) cbMedico.getSelectedItem();
                String cmp = seleccionado.split(" - ")[0];
                Medico medico = ControladorMedico.buscarPorCmp(cmp);

                modelo.setRowCount(0);
                for (Cita cita : ControladorCita.listar()) {
                    if (cita.getMedico() == medico) {
                        modelo.addRow(new Object[] {
                                cita.getNumCita(),
                                cita.getPaciente().getNombre(),
                                cita.getMedico().getNombre(),
                                cita.getFecha(),
                                cita.getHora(),
                                cita.getEstado() == 0 ? "Pendiente" : cita.getEstado() == 1 ? "Atendida" : "Cancelada"
                        });
                    }
                }
            }
        });

        return panel;
    }

    private JPanel crearPanelPorFecha() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 20, 80, 25);
        panel.add(lblFecha);

        JFormattedTextField txtFecha = null;
        try {
            MaskFormatter formatoFecha = new MaskFormatter("##/##/####");
            formatoFecha.setPlaceholderCharacter('_');
            txtFecha = new JFormattedTextField(formatoFecha);
            txtFecha.setBounds(100, 20, 100, 25);
            panel.add(txtFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(210, 20, 100, 25);
        panel.add(btnBuscar);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 620, 270);
        panel.add(scroll);

        JFormattedTextField finalTxtFecha = txtFecha;
        btnBuscar.addActionListener(e -> {
            String fecha = finalTxtFecha.getText().trim();
            if (!fecha.contains("_") && !fecha.isEmpty()) {
                modelo.setRowCount(0);
                for (Cita cita : ControladorCita.listar()) {
                    if (cita.getFecha().equals(fecha)) {
                        modelo.addRow(new Object[] {
                                cita.getNumCita(),
                                cita.getPaciente().getNombre(),
                                cita.getMedico().getNombre(),
                                cita.getFecha(),
                                cita.getHora(),
                                cita.getEstado() == 0 ? "Pendiente" : cita.getEstado() == 1 ? "Atendida" : "Cancelada"
                        });
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Ingrese una fecha válida");
            }
        });

        return panel;
    }

    private JPanel crearPanelPorConsultorio() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblConsultorio = new JLabel("Consultorio:");
        lblConsultorio.setBounds(20, 20, 80, 25);
        panel.add(lblConsultorio);

        JComboBox<String> cbConsultorio = new JComboBox<>();
        cbConsultorio.setBounds(100, 20, 250, 25);
        cbConsultorio.addItem("-- Seleccione Consultorio --");
        for (Consultorio c : ControladorConsultorio.listar()) {
            cbConsultorio.addItem(c.getNombre());
        }
        panel.add(cbConsultorio);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 620, 270);
        panel.add(scroll);

        cbConsultorio.addActionListener(e -> {
            if (cbConsultorio.getSelectedIndex() > 0) {
                String nombreConsultorio = (String) cbConsultorio.getSelectedItem();
                Consultorio consultorio = null;
                for (Consultorio c : ControladorConsultorio.listar()) {
                    if (c.getNombre().equals(nombreConsultorio)) {
                        consultorio = c;
                        break;
                    }
                }

                if (consultorio != null) {
                    modelo.setRowCount(0);
                    for (Cita cita : ControladorCita.listar()) {
                        if (cita.getConsultorio() == consultorio) {
                            modelo.addRow(new Object[] {
                                    cita.getNumCita(),
                                    cita.getPaciente().getNombre(),
                                    cita.getMedico().getNombre(),
                                    cita.getFecha(),
                                    cita.getHora(),
                                    cita.getEstado() == 0 ? "Pendiente"
                                            : cita.getEstado() == 1 ? "Atendida" : "Cancelada"
                            });
                        }
                    }
                }
            }
        });

        return panel;
    }
}