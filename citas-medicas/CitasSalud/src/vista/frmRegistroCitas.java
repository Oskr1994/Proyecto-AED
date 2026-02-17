package vista;

import controlador.ControladorPaciente;
import controlador.ControladorMedico;
import controlador.ControladorConsultorio;
import controlador.ControladorCita;
import modelo.Paciente;
import modelo.Medico;
import modelo.Consultorio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class frmRegistroCitas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> cbPaciente;
    private JComboBox<String> cbMedico;
    private JComboBox<String> cbConsultorio;
    private JFormattedTextField txtFecha;
    private JFormattedTextField txtHora;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public frmRegistroCitas() {
        setTitle("Registro de Citas");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(47, 20, 60, 25);
        getContentPane().add(lblPaciente);

        cbPaciente = new JComboBox<>();
        cbPaciente.setBounds(109, 20, 200, 25);
        getContentPane().add(cbPaciente);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(57, 81, 50, 25);
        getContentPane().add(lblMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBounds(109, 81, 200, 25);
        getContentPane().add(cbMedico);

        JButton btnAgregarCita = new JButton("Agregar Cita");
        btnAgregarCita.addActionListener(e -> {
            agregarCita();
        });
        btnAgregarCita.setBounds(119, 117, 140, 35);
        getContentPane().add(btnAgregarCita);

        JButton btnCancelarCita = new JButton("Cancelar Cita");
        btnCancelarCita.addActionListener(e -> {
            cancelarCita();
        });
        btnCancelarCita.setBounds(269, 117, 140, 35);
        getContentPane().add(btnCancelarCita);

        JButton btnAtendida = new JButton("Marcar Atendida");
        btnAtendida.addActionListener(e -> {
            marcarAtendida();
        });
        btnAtendida.setBounds(419, 117, 140, 35);
        getContentPane().add(btnAtendida);

        modeloTabla = new DefaultTableModel(
                new Object[] { "N°", "Paciente", "Médico", "Fecha", "Hora", "Estado" }, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 163, 650, 187);
        getContentPane().add(sp);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(365, 20, 40, 25);
        getContentPane().add(lblFecha);

        // Campo de Fecha con formato dd/MM/yyyy
        try {
            MaskFormatter formatoFecha = new MaskFormatter("##/##/####");
            formatoFecha.setPlaceholderCharacter('_');
            txtFecha = new JFormattedTextField(formatoFecha);
            txtFecha.setBounds(404, 20, 80, 25);
            getContentPane().add(txtFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(514, 20, 40, 25);
        getContentPane().add(lblHora);

        // Campo de Hora con formato HH:mm
        try {
            MaskFormatter formatoHora = new MaskFormatter("##:##");
            formatoHora.setPlaceholderCharacter('_');
            txtHora = new JFormattedTextField(formatoHora);
            txtHora.setBounds(553, 20, 80, 25);
            getContentPane().add(txtHora);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        JLabel lblConsultorio = new JLabel("Consultorio:");
        lblConsultorio.setBounds(365, 81, 80, 25);
        getContentPane().add(lblConsultorio);

        cbConsultorio = new JComboBox<>();
        cbConsultorio.setBounds(435, 81, 200, 25);
        getContentPane().add(cbConsultorio);

        // Cargar datos en los ComboBox
        cargarPacientes();
        cargarMedicos();
        cargarConsultorios();
    }

    private void cargarPacientes() {
        cbPaciente.removeAllItems();
        cbPaciente.addItem("-- Seleccione Paciente --");

        for (Paciente p : ControladorPaciente.listar()) {
            // Formato: "DNI - Nombre"
            String item = p.getDni() + " - " + p.getNombre();
            cbPaciente.addItem(item);
        }
    }

    private void cargarMedicos() {
        cbMedico.removeAllItems();
        cbMedico.addItem("-- Seleccione Médico --");

        for (Medico m : ControladorMedico.listar()) {
            // Formato: "CMP - Nombre"
            String item = m.getCmp() + " - " + m.getNombre();
            cbMedico.addItem(item);
        }
    }

    private void cargarConsultorios() {
        cbConsultorio.removeAllItems();
        cbConsultorio.addItem("-- Seleccione Consultorio --");

        for (Consultorio c : ControladorConsultorio.listar()) {
            cbConsultorio.addItem(c.getNombre());
        }
    }

    private void agregarCita() {
        // Validar selecciones
        if (cbPaciente.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente");
            return;
        }
        if (cbMedico.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico");
            return;
        }
        if (cbConsultorio.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un consultorio");
            return;
        }

        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();

        if (fecha.contains("_") || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha válida");
            return;
        }
        if (hora.contains("_") || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una hora válida");
            return;
        }

        // Extraer DNI del ComboBox (formato: "DNI - Nombre")
        String pacienteSeleccionado = (String) cbPaciente.getSelectedItem();
        String dni = pacienteSeleccionado.split(" - ")[0];
        Paciente paciente = ControladorPaciente.buscarPorDni(dni);

        // Extraer CMP del ComboBox (formato: "CMP - Nombre")
        String medicoSeleccionado = (String) cbMedico.getSelectedItem();
        String cmp = medicoSeleccionado.split(" - ")[0];
        Medico medico = ControladorMedico.buscarPorCmp(cmp);

        // Buscar consultorio por nombre
        String nombreConsultorio = (String) cbConsultorio.getSelectedItem();
        Consultorio consultorio = null;
        for (Consultorio c : ControladorConsultorio.listar()) {
            if (c.getNombre().equals(nombreConsultorio)) {
                consultorio = c;
                break;
            }
        }

        // Registrar cita
        if (ControladorCita.registrar(paciente, medico, consultorio, fecha, hora, "")) {
            JOptionPane.showMessageDialog(this, "Cita registrada correctamente");
            cargarTabla();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error: Ya existe una cita para ese médico o consultorio en esa fecha y hora");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (modelo.Cita cita : ControladorCita.listar()) {
            String estadoStr = "Pendiente";
            if (cita.getEstado() == 1)
                estadoStr = "Atendida";
            if (cita.getEstado() == 2)
                estadoStr = "Cancelada";

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

    private void limpiar() {
        cbPaciente.setSelectedIndex(0);
        cbMedico.setSelectedIndex(0);
        cbConsultorio.setSelectedIndex(0);
        txtFecha.setText("");
        txtHora.setText("");
    }

    private void cancelarCita() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita");
            return;
        }

        int numCita = (int) modeloTabla.getValueAt(fila, 0);
        if (ControladorCita.cancelarCita(numCita)) {
            JOptionPane.showMessageDialog(this, "Cita cancelada");
            cargarTabla();
        }
    }

    private void marcarAtendida() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita");
            return;
        }

        int numCita = (int) modeloTabla.getValueAt(fila, 0);
        if (ControladorCita.marcarAtendida(numCita)) {
            JOptionPane.showMessageDialog(this, "Cita marcada como atendida");
            cargarTabla();
        }
    }
}