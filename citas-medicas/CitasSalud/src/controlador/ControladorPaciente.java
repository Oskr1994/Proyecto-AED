package controlador;

import modelo.Paciente;
import java.util.ArrayList;

public class ControladorPaciente {

    private static ArrayList<Paciente> lista = new ArrayList<>();
    private static int contador = 1;

    public static boolean agregar(String dni, String nombre) {
        dni = dni.trim();
        nombre = nombre.trim();
        if (dni.isEmpty() || nombre.isEmpty())
            return false;
        if (buscarPorDni(dni) != null)
            return false;
        lista.add(new Paciente(contador++, dni, nombre, 1));
        return true;
    }

    public static boolean modificar(String dni, String nuevoNombre, int nuevoEstado) {
        Paciente p = buscarPorDni(dni);
        if (p == null)
            return false;

        nuevoNombre = nuevoNombre.trim();
        if (nuevoNombre.isEmpty())
            return false;

        p.setNombre(nuevoNombre);
        p.setEstado(nuevoEstado);
        return true;
    }

    public static ArrayList<Paciente> listar() {
        return new ArrayList<>(lista);
    }

    public static Paciente buscarPorDni(String dni) {
        for (Paciente p : lista) {
            if (p.getDni().equals(dni))
                return p;
        }
        return null;
    }

    public static boolean eliminar(String dni) {
        Paciente p = buscarPorDni(dni);
        if (p == null)
            return false;
        lista.remove(p);
        return true;
    }
}
