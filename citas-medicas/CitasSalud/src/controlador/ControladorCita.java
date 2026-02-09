package controlador;

import modelo.*;
import java.util.ArrayList;

public class ControladorCita {

    private static ArrayList<Cita> citas = new ArrayList<>();
    private static int contador = 1;

    public static boolean registrar(Paciente p, Medico m,
                                    Consultorio c,
                                    String fecha, String hora,
                                    String motivo) {

        // validar doble cita
        for (Cita ct : citas) {
            if (ct.getFecha().equals(fecha)
                && ct.getHora().equals(hora)
                && (ct.getMedico() == m || ct.getConsultorio() == c)) {
                return false;
            }
        }

        citas.add(new Cita(contador++, p, m, c,
                fecha, hora, 0, motivo));
        return true;
    }

    public static ArrayList<Cita> listar() {
        return citas;
    }
}