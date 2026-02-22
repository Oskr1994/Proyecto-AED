package vista;

import controlador.ControladorCita;
import controlador.ControladorMedico;
import modelo.Cita;
import modelo.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class frmReporteCitas extends JDialog {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JLabel lblTituloReporte;

    public frmReporteCitas(Frame owner) {
        super(owner, "Reporte de Citas", true);
        setSize(670, 430);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // ===== BOTONES =====
        JButton btnPendientes = new JButton("Citas Pendientes");
        btnPendientes.setBounds(20, 15, 160, 35);
        add(btnPendientes);

        JButton btnMedico = new JButton("Citas por Médico");
        btnMedico.setBounds(200, 15, 160, 35);
        add(btnMedico);

        JButton btnAgenda = new JButton("Agenda del Día");
        btnAgenda.setBounds(380, 15, 160, 35);
        add(btnAgenda);

        JButton btnTodas = new JButton("Todas");
        btnTodas.setBounds(555, 15, 80, 35);
        add(btnTodas);

        // ===== ETIQUETA TÍTULO REPORTE =====
        lblTituloReporte = new JLabel("Mostrando: Todas las citas");
        lblTituloReporte.setBounds(20, 58, 620, 20);
        add(lblTituloReporte);

        // ===== TABLA =====
        modeloTabla = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla de solo lectura
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 85, 620, 295);
        add(sp);

        // ===== EVENTOS =====

        // CITAS PENDIENTES
        btnPendientes.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Citas Pendientes");
            for (Cita cita : ControladorCita.listar()) {
                if (cita.getEstado() == 0) {
                    modeloTabla.addRow(new Object[] {
                            cita.getNumCita(),
                            cita.getPaciente().getNombre(),
                            cita.getMedico().getNombre(),
                            cita.getFecha(),
                            cita.getHora(),
                            "Pendiente"
                    });
                }
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay citas pendientes.");
            }
        });

        // CITAS POR MÉDICO
        btnMedico.addActionListener(e -> {
            ArrayList<Medico> medicos = ControladorMedico.listar();
            if (medicos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay médicos registrados.");
                return;
            }

            // Armar array de nombres para el JOptionPane
            String[] opciones = medicos.stream()
                    .map(m -> m.getCmp() + " - " + m.getNombre())
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Seleccione un médico:",
                    "Citas por Médico",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion == null)
                return; // canceló

            String cmpSeleccionado = seleccion.split(" - ")[0];
            Medico medicoFiltro = ControladorMedico.buscarPorCmp(cmpSeleccionado);

            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Citas del Médico " + medicoFiltro.getNombre());

            for (Cita cita : ControladorCita.listar()) {
                if (cita.getMedico() == medicoFiltro) {
                    String estadoStr = cita.getEstado() == 0 ? "Pendiente"
                            : cita.getEstado() == 1 ? "Atendida" : "Cancelada";
                    modeloTabla.addRow(new Object[] {
                            cita.getNumCita(),
                            cita.getPaciente().getNombre(),
                            cita.getMedico().getNombre(),
                            cita.getFecha(),
                            cita.getHora(),
                            estadoStr
                    });
                }
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "El médico seleccionado no tiene citas registradas.");
            }
        });

        // AGENDA DEL DÍA
        btnAgenda.addActionListener(e -> {
            // Fecha de hoy por defecto en formato dd/MM/yyyy
            String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String fecha = JOptionPane.showInputDialog(
                    this,
                    "Ingrese la fecha (dd/MM/yyyy):",
                    hoy);

            if (fecha == null || fecha.trim().isEmpty())
                return;
            fecha = fecha.trim();

            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Agenda del día " + fecha);

            for (Cita cita : ControladorCita.listar()) {
                if (cita.getFecha().equals(fecha)) {
                    String estadoStr = cita.getEstado() == 0 ? "Pendiente"
                            : cita.getEstado() == 1 ? "Atendida" : "Cancelada";
                    modeloTabla.addRow(new Object[] {
                            cita.getNumCita(),
                            cita.getPaciente().getNombre(),
                            cita.getMedico().getNombre(),
                            cita.getFecha(),
                            cita.getHora(),
                            estadoStr
                    });
                }
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No hay citas registradas para la fecha: " + fecha);
            }
        });

        // TODAS LAS CITAS
        btnTodas.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Todas las citas");
            for (Cita cita : ControladorCita.listar()) {
                String estadoStr = cita.getEstado() == 0 ? "Pendiente"
                        : cita.getEstado() == 1 ? "Atendida" : "Cancelada";
                modeloTabla.addRow(new Object[] {
                        cita.getNumCita(),
                        cita.getPaciente().getNombre(),
                        cita.getMedico().getNombre(),
                        cita.getFecha(),
                        cita.getHora(),
                        estadoStr
                });
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay citas registradas aún.");
            }
        });

        // Cargar todas al abrir
        cargarTodas();
    }

    private void cargarTodas() {
        modeloTabla.setRowCount(0);
        for (Cita cita : ControladorCita.listar()) {
            String estadoStr = cita.getEstado() == 0 ? "Pendiente"
                    : cita.getEstado() == 1 ? "Atendida" : "Cancelada";
            modeloTabla.addRow(new Object[] {
                    cita.getNumCita(),
                    cita.getPaciente().getNombre(),
                    cita.getMedico().getNombre(),
                    cita.getFecha(),
                    cita.getHora(),
                    estadoStr
            });
        }
    }
}