package controlador;

import modelo.Medico;
import java.io.*;
import java.util.ArrayList;

public class ControladorMedico {

    // ── Ruta del archivo de persistencia ────────────────────────────────
    // Formato de cada línea: codigo|nombre|apellidos|especialidad|cmp|estado
    private static final String ARCHIVO = "medicos.txt";

    // ── Lista en memoria ─────────────────────────────────────────────────
    private static ArrayList<Medico> lista = new ArrayList<>();

    // ── Contador para generar códigos correlativos (501, 502, ...) ───────
    private static int contadorCodigo = 501;

    // ── Bloque estático: carga los datos al iniciar la aplicación ────────
    static {
        cargarDesdeArchivo();
    }

    // ====================================================================
    // AGREGAR
    // ====================================================================
    public static boolean agregar(String nombre, String apellidos,
                                   String especialidad, String cmp) {

        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            apellidos == null || apellidos.trim().isEmpty() ||
            especialidad == null || especialidad.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del CMP si fue ingresado
        if (cmp != null && !cmp.trim().isEmpty()) {
            for (Medico m : lista) {
                if (m.getCmp().equalsIgnoreCase(cmp.trim())) {
                    return false; // CMP ya existe
                }
            }
        }

        Medico nuevo = new Medico(
                contadorCodigo++,
                nombre.trim(),
                apellidos.trim(),
                especialidad.trim(),
                cmp != null ? cmp.trim() : "",
                1 // activo por defecto
        );

        lista.add(nuevo);
        guardarEnArchivo();
        return true;
    }

    // ====================================================================
    // MODIFICAR
    // ====================================================================
    public static boolean modificar(int codigo, String nuevoNombre, String nuevosApellidos,
                                     String nuevaEspecialidad, String nuevoCmp, int nuevoEstado) {

        // Validar campos obligatorios
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() ||
            nuevosApellidos == null || nuevosApellidos.trim().isEmpty() ||
            nuevaEspecialidad == null || nuevaEspecialidad.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del CMP (excepto el mismo médico)
        if (nuevoCmp != null && !nuevoCmp.trim().isEmpty()) {
            for (Medico m : lista) {
                if (m.getCmp().equalsIgnoreCase(nuevoCmp.trim())
                        && m.getCodigo() != codigo) {
                    return false; // otro médico ya tiene ese CMP
                }
            }
        }

        // Buscar y actualizar
        for (Medico m : lista) {
            if (m.getCodigo() == codigo) {
                m.setNombre(nuevoNombre.trim());
                m.setApellidos(nuevosApellidos.trim());
                m.setEspecialidad(nuevaEspecialidad.trim());
                m.setCmp(nuevoCmp != null ? nuevoCmp.trim() : "");
                m.setEstado(nuevoEstado);
                guardarEnArchivo();
                return true;
            }
        }
        return false; // no encontrado
    }

    // ====================================================================
    // ELIMINAR
    // ====================================================================
    public static boolean eliminar(int codigo) {
        for (Medico m : lista) {
            if (m.getCodigo() == codigo) {
                lista.remove(m);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // ====================================================================
    // LISTAR
    // ====================================================================
    public static ArrayList<Medico> listar() {
        return lista;
    }

    // ====================================================================
    // BUSCAR POR CMP
    // ====================================================================
    public static Medico buscarPorCmp(String cmp) {
        for (Medico m : lista) {
            if (m.getCmp().equalsIgnoreCase(cmp)) {
                return m;
            }
        }
        return null;
    }

    // ====================================================================
    // BUSCAR POR CÓDIGO
    // ====================================================================
    public static Medico buscarPorCodigo(int codigo) {
        for (Medico m : lista) {
            if (m.getCodigo() == codigo) {
                return m;
            }
        }
        return null;
    }

    // ====================================================================
    // GUARDAR EN ARCHIVO
    // Formato: codigo|nombre|apellidos|especialidad|cmp|estado
    // ====================================================================
    private static void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Medico m : lista) {
                bw.write(m.toString()); // usa el toString() de Medico
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar médicos: " + e.getMessage());
        }
    }

    // ====================================================================
    // CARGAR DESDE ARCHIVO
    // Formato esperado: codigo|nombre|apellidos|especialidad|cmp|estado
    // ====================================================================
    private static void cargarDesdeArchivo() {
        lista.clear();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split("\\|"); // separador pipe

                // Verificar que la línea tenga exactamente 6 campos
                if (partes.length != 6) continue;

                int    codigo       = Integer.parseInt(partes[0].trim());
                String nombre       = partes[1].trim();
                String apellidos    = partes[2].trim();
                String especialidad = partes[3].trim();
                String cmp          = partes[4].trim();
                int    estado       = Integer.parseInt(partes[5].trim());

                lista.add(new Medico(codigo, nombre, apellidos, especialidad, cmp, estado));

                // Actualizar contador
                if (codigo >= contadorCodigo) {
                    contadorCodigo = codigo + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }
}
