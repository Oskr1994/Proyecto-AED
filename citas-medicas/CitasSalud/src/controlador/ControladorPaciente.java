package controlador;

import modelo.Paciente;
import java.util.ArrayList;

public class ControladorPaciente {

    private static ArrayList<Paciente> lista = new ArrayList<>();
    private static int contador = 1;

    public static void agregar(String dni, String nombre) {
        lista.add(new Paciente(contador++, dni, nombre, 1));
    }

    public static ArrayList<Paciente> listar() {
        return lista;
    }

    public static Paciente buscarPorDni(String dni) {
        for (Paciente p : lista) {
            if (p.getDni().equals(dni)) return p;
        }
        return null;
    }
}