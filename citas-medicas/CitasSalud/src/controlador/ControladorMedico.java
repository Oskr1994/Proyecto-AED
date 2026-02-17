package controlador;

import modelo.Medico;
import java.util.ArrayList;

public class ControladorMedico {

    private static ArrayList<Medico> lista = new ArrayList<>();
    private static int contador = 1;

    public static boolean agregar(String cmp, String nombre) {
        cmp = cmp.trim();
        nombre = nombre.trim();
        if (cmp.isEmpty() || nombre.isEmpty())
            return false;
        if (buscarPorCmp(cmp) != null)
            return false;
        lista.add(new Medico(contador++, nombre, cmp, 1));
        return true;
    }

    public static boolean modificar(String cmp, String nuevoNombre, int nuevoEstado) {
        Medico m = buscarPorCmp(cmp);
        if (m == null)
            return false;

        nuevoNombre = nuevoNombre.trim();
        if (nuevoNombre.isEmpty())
            return false;

        m.setNombre(nuevoNombre);
        m.setEstado(nuevoEstado);
        return true;
    }

    public static ArrayList<Medico> listar() {
        return new ArrayList<>(lista);
    }

    public static Medico buscarPorCmp(String cmp) {
        for (Medico m : lista) {
            if (m.getCmp().equals(cmp))
                return m;
        }
        return null;
    }

    public static boolean eliminar(String cmp) {
        Medico m = buscarPorCmp(cmp);
        if (m == null)
            return false;
        lista.remove(m);
        return true;
    }
}
