package vista;

import controlador.ControladorCita;
import controlador.ControladorMedico;
import modelo.Cita;
import modelo.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmReporteCitas extends JDialog implements ActionListener {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JLabel lblTituloReporte;
    private JButton btnImprimir;

    public frmReporteCitas(Frame owner) {
        super(owner, "Reporte de Citas", true);
        setSize(670, 430);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ===== BOTONES FILTRO =====
        JButton btnPendientes = new JButton("Citas Pendientes");
        btnPendientes.setBounds(20, 12, 145, 35);
        getContentPane().add(btnPendientes);

        JButton btnMedico = new JButton("Citas por Médico");
        btnMedico.setBounds(175, 12, 145, 35);
        getContentPane().add(btnMedico);

        JButton btnAgenda = new JButton("Agenda del Día");
        btnAgenda.setBounds(330, 12, 145, 35);
        getContentPane().add(btnAgenda);

        JButton btnTodas = new JButton("Todas");
        btnTodas.setBounds(485, 12, 145, 35);
        getContentPane().add(btnTodas);

        // ===== ETIQUETA TÍTULO REPORTE =====
        lblTituloReporte = new JLabel("Mostrando: Todas las citas");
        lblTituloReporte.setBounds(20, 58, 488, 20);
        getContentPane().add(lblTituloReporte);

        // ===== BOTÓN IMPRIMIR =====
        btnImprimir = new JButton("Guardar");
        btnImprimir.setBounds(485, 51, 145, 35);
        btnImprimir.addActionListener(this);
        getContentPane().add(btnImprimir);

        // ===== TABLA =====
        modeloTabla = new DefaultTableModel(
                new Object[]{"N°", "Paciente", "Médico", "Fecha", "Hora", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 89, 620, 291);
        getContentPane().add(sp);

        // ===== EVENTOS DE FILTRO =====

        // CITAS PENDIENTES
        btnPendientes.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Citas Pendientes");
            for (Cita cita : ControladorCita.listar()) {
                if (cita.getEstado() == 0) {
                    modeloTabla.addRow(new Object[]{
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

            if (seleccion == null) return;

            String cmpSeleccionado = seleccion.split(" - ")[0];
            Medico medicoFiltro = ControladorMedico.buscarPorCmp(cmpSeleccionado);

            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Citas del Médico " + medicoFiltro.getNombre());

            for (Cita cita : ControladorCita.listar()) {
                if (cita.getMedico() == medicoFiltro) {
                    String estadoStr = estadoTexto(cita.getEstado());
                    modeloTabla.addRow(new Object[]{
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
                JOptionPane.showMessageDialog(this, "El médico seleccionado no tiene citas registradas.");
            }
        });

        // AGENDA DEL DÍA
        btnAgenda.addActionListener(e -> {
            String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String fecha = JOptionPane.showInputDialog(this, "Ingrese la fecha (dd/MM/yyyy):", hoy);

            if (fecha == null || fecha.trim().isEmpty()) return;
            fecha = fecha.trim();

            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Agenda del día " + fecha);

            for (Cita cita : ControladorCita.listar()) {
                if (cita.getFecha().equals(fecha)) {
                    modeloTabla.addRow(new Object[]{
                            cita.getNumCita(),
                            cita.getPaciente().getNombre(),
                            cita.getMedico().getNombre(),
                            cita.getFecha(),
                            cita.getHora(),
                            estadoTexto(cita.getEstado())
                    });
                }
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay citas registradas para la fecha: " + fecha);
            }
        });

        // TODAS LAS CITAS
        btnTodas.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            lblTituloReporte.setText("Mostrando: Todas las citas");
            for (Cita cita : ControladorCita.listar()) {
                modeloTabla.addRow(new Object[]{
                        cita.getNumCita(),
                        cita.getPaciente().getNombre(),
                        cita.getMedico().getNombre(),
                        cita.getFecha(),
                        cita.getHora(),
                        estadoTexto(cita.getEstado())
                });
            }
            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay citas registradas aún.");
            }
        });

        // Cargar todas al abrir
        cargarTodas();
    }

     // MÉTODO IMPRIMIR — Exporta la tabla actual a un archivo .txt
    // Formato: separado por | (pipe), igual que citas.txt del sistema
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnImprimir) {
            actionPerformedBtnImprimir(e);
        }
    }

    protected void actionPerformedBtnImprimir(ActionEvent e) {

        // 1. Verificar que haya datos en la tabla
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay datos en el reporte para imprimir.",
                    "Reporte vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Obtener el título actual del reporte para el nombre del archivo
        String tituloReporte = lblTituloReporte.getText()
                .replace("Mostrando: ", "")  
                .replace(" ", "_")           
                .replace("/", "-")           
                .replace(":", "")            
                .replace(".", "")           
                .trim();

        // 3. Generar nombre de archivo con fecha y hora para evitar sobreescrituras
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = "Reporte_" + tituloReporte + "_" + timestamp + ".txt";

        // 4. Abrir diálogo para que el usuario elija dónde guardar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte como archivo de texto");
        fileChooser.setSelectedFile(new File(nombreArchivo));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de texto (*.txt)", "txt"));

        int opcion = fileChooser.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) return; // usuario canceló

        File archivo = fileChooser.getSelectedFile();

        // Asegurar extensión .txt
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        }

        // 5. Escribir el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            // ── ENCABEZADO DEL REPORTE ──────────────────────────────────────
            String separador = "=".repeat(92);
            String separadorFino = "-".repeat(92);

            bw.write(separador);
            bw.newLine();
            bw.write("  SISTEMA DE RESERVA DE CITAS MÉDICAS — CIBERTEC 2026");
            bw.newLine();
            bw.write(separador);
            bw.newLine();
            bw.write("  " + lblTituloReporte.getText().toUpperCase());
            bw.newLine();
            bw.write("  Generado el: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            bw.newLine();
            bw.write("  Total de registros: " + modeloTabla.getRowCount());
            bw.newLine();
            bw.write(separador);
            bw.newLine();
            bw.newLine();

            // ── ENCABEZADO DE COLUMNAS (formato pipe | igual que citas.txt) ─
            // Cabecera visual alineada
            bw.write(String.format("%-6s | %-22s | %-22s | %-12s | %-6s | %-10s",
                    "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado"));
            bw.newLine();
            bw.write(separadorFino);
            bw.newLine();

            // ── FILAS DE DATOS ───────────────────────────────────────────────
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String numCita    = String.valueOf(modeloTabla.getValueAt(i, 0));
                String paciente   = String.valueOf(modeloTabla.getValueAt(i, 1));
                String medico     = String.valueOf(modeloTabla.getValueAt(i, 2));
                String fecha      = String.valueOf(modeloTabla.getValueAt(i, 3));
                String hora       = String.valueOf(modeloTabla.getValueAt(i, 4));
                String estado     = String.valueOf(modeloTabla.getValueAt(i, 5));

                // Línea visual alineada (para lectura humana)
                bw.write(String.format("%-3s | %-22s | %-22s | %-12s | %-6s | %-10s",
                        numCita,
                        truncar(paciente, 22),
                        truncar(medico, 22),
                        fecha,
                        hora,
                        estado));
                bw.newLine();

                // Línea en formato pipe puro (igual a citas.txt — para integración)
                // Formato: numCita|paciente|medico|fecha|hora|estado
                // Esta línea está comentada; descomentarla si se necesita exportar
                // para reimportar al sistema:
                // bw.write(numCita + "|" + paciente + "|" + medico + "|" + fecha + "|" + hora + "|" + estado);
                // bw.newLine();
            }

            // ── PIE DEL REPORTE ──────────────────────────────────────────────
            bw.newLine();
            bw.write(separador);
            bw.write(System.lineSeparator());
            bw.write("  FIN DEL REPORTE — Total: " + modeloTabla.getRowCount() + " registro(s)");
            bw.newLine();
            bw.write(separador);
            bw.newLine();

            // 6. Confirmar al usuario
            int verArchivo = JOptionPane.showConfirmDialog(this,
                    "Reporte guardado exitosamente en:\n" + archivo.getAbsolutePath()
                            + "\n\n¿Desea abrir el archivo ahora?",
                    "Reporte generado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            // 7. Abrir el archivo con el visor del sistema si el usuario lo desea
            if (verArchivo == JOptionPane.YES_OPTION) {
                try {
                    java.awt.Desktop.getDesktop().open(archivo);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo abrir el archivo automáticamente.\n"
                                    + "Ruta: " + archivo.getAbsolutePath(),
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el reporte:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================================================================
    // MÉTODOS AUXILIARES
    // ===================================================================

    /** Convierte el int de estado al texto correspondiente. */
    private String estadoTexto(int estado) {
        return estado == 0 ? "Pendiente" : estado == 1 ? "Atendida" : "Cancelada";
    }

    /** Trunca un String para que no exceda el ancho de columna en el .txt. */
    private String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        return texto.length() > maxLen ? texto.substring(0, maxLen - 1) + "…" : texto;
    }

    /** Carga todas las citas al iniciar el formulario. */
    private void cargarTodas() {
        modeloTabla.setRowCount(0);
        for (Cita cita : ControladorCita.listar()) {
            modeloTabla.addRow(new Object[]{
                    cita.getNumCita(),
                    cita.getPaciente().getNombre(),
                    cita.getMedico().getNombre(),
                    cita.getFecha(),
                    cita.getHora(),
                    estadoTexto(cita.getEstado())
            });
        }
    }
}