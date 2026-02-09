package modelo;

public class Cita {
    private int numCita;
    private Paciente paciente;
    private Medico medico;
    private Consultorio consultorio;
    private String fecha;
    private String hora;
    private int estado; // 0 pendiente, 1 atendida, 2 cancelada
    private String motivo;

    public Cita(int numCita, Paciente paciente, Medico medico,
                Consultorio consultorio, String fecha,
                String hora, int estado, String motivo) {
        this.numCita = numCita;
        this.paciente = paciente;
        this.medico = medico;
        this.consultorio = consultorio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.motivo = motivo;
    }

	public Object getFecha() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getHora() {
		// TODO Auto-generated method stub
		return null;
	}

	public Medico getMedico() {
		// TODO Auto-generated method stub
		return null;
	}

	public Consultorio getConsultorio() {
		// TODO Auto-generated method stub
		return null;
	}
}