package controlador;

import modelo.Consultorio;
import java.util.ArrayList;

public class ControladorConsultorio {

    private static ArrayList<Consultorio> lista = new ArrayList<>();
    private static int contador = 1;

    public static boolean agregar(String nombre) {
        nombre = nombre.trim();
        if (nombre.isEmpty())
            return false;
        // Validar nombre único
        for (Consultorio c : lista) {
            if (c.getNombre().equalsIgnoreCase(nombre))
                return false;
        }
        lista.add(new Consultorio(contador++, nombre, 1));
        return true;
    }

    public static boolean modificar(int codigo, String nuevoNombre, int nuevoEstado) {
        Consultorio c = buscarPorCodigo(codigo);
        if (c == null)
            return false;

        nuevoNombre = nuevoNombre.trim();
        if (nuevoNombre.isEmpty())
            return false;

        c.setNombre(nuevoNombre);
        c.setEstado(nuevoEstado);
        return true;
    }

    public static ArrayList<Consultorio> listar() {
        return new ArrayList<>(lista);
    }

    public static Consultorio buscarPorCodigo(int codigo) {
        for (Consultorio c : lista) {
            if (c.getCodigo() == codigo)
                return c;
        }
        return null;
    }

    public static boolean eliminar(int codigo) {
        Consultorio c = buscarPorCodigo(codigo);
        if (c == null)
            return false;
        lista.remove(c);
        return true;
    }
}
