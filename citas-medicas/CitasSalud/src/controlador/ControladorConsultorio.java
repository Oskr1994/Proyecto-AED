package controlador;

import modelo.Consultorio;
import java.io.*;
import java.util.ArrayList;

public class ControladorConsultorio {

    // ── Ruta del archivo de persistencia ──
    // Formato de cada línea: codConsultorio|nombre|piso|ubicacion|capacidad|estado
    private static final String ARCHIVO = "consultorios.txt";

    // ── Lista en memoria ──
    private static ArrayList<Consultorio> lista = new ArrayList<>();

    // ── Contador para generar códigos correlativos (301, 302, ...) ───
    private static int contadorCodigo = 301;

    // ── Bloque estático: carga los datos al iniciar la aplicación ───
    static {
        cargarDesdeArchivo();
    }

    // AGREGAR
    // Recibe todos los campos del formulario y crea un nuevo consultorio

    public static boolean agregar(String nombre, int piso, String ubicacion, int capacidad) {

        // Validar que el nombre no esté vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del nombre (regla de negocio)
        for (Consultorio c : lista) {
            if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
                return false; // nombre ya existe
            }
        }

        // Validar que piso y capacidad sean positivos
        if (piso <= 0 || capacidad <= 0) {
            return false;
        }

        // Crear el consultorio con estado activo por defecto
        Consultorio nuevo = new Consultorio(
                contadorCodigo++,
                nombre.trim(),
                piso,
                ubicacion.trim(),
                capacidad,
                1 // activo
        );

        lista.add(nuevo);
        guardarEnArchivo();
        return true;
    }
 
    // MODIFICAR
    // Actualiza todos los campos del consultorio seleccionado

    public static boolean modificar(int codigo, String nuevoNombre, int nuevoPiso,
                                     String nuevaUbicacion, int nuevaCapacidad, int nuevoEstado) {

        // Validar nombre no vacío
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del nombre (excepto el mismo consultorio)
        for (Consultorio c : lista) {
            if (c.getNombre().equalsIgnoreCase(nuevoNombre.trim())
                    && c.getCodigo() != codigo) {
                return false; // otro consultorio ya tiene ese nombre
            }
        }

        // Buscar y actualizar
        for (Consultorio c : lista) {
            if (c.getCodigo() == codigo) {
                c.setNombre(nuevoNombre.trim());
                c.setPiso(nuevoPiso);
                c.setUbicacion(nuevaUbicacion.trim());
                c.setCapacidad(nuevaCapacidad);
                c.setEstado(nuevoEstado);
                guardarEnArchivo();
                return true;
            }
        }
        return false; // no encontrado
    }


    // ELIMINAR
    // Solo elimina si el consultorio no tiene citas futuras pendientes
    // (la validación cruzada con citas se puede añadir aquí si se necesita)
    
    public static boolean eliminar(int codigo) {
        for (Consultorio c : lista) {
            if (c.getCodigo() == codigo) {
                lista.remove(c);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // LISTAR — devuelve todos los consultorios en memoria

    public static ArrayList<Consultorio> listar() {
        return lista;
    }


    // BUSCAR POR CÓDIGO

    public static Consultorio buscarPorCodigo(int codigo) {
        for (Consultorio c : lista) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }


    // GUARDAR EN ARCHIVO — escribe toda la lista en consultorios.txt
    // Formato por línea: codConsultorio|nombre|piso|ubicacion|capacidad|estado

    private static void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Consultorio c : lista) {
                bw.write(c.toString()); // usa el toString() de Consultorio
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar consultorios: " + e.getMessage());
        }
    }


    // CARGAR DESDE ARCHIVO — lee consultorios.txt al iniciar
    // Formato esperado: codConsultorio|nombre|piso|ubicacion|capacidad|estado

    private static void cargarDesdeArchivo() {
        lista.clear();
        File archivo = new File(ARCHIVO);

        // Si el archivo no existe todavía, no hay nada que cargar
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; // ignorar líneas vacías

                String[] partes = linea.split("\\|"); // separador pipe

                // Verificar que la línea tenga exactamente 6 campos
                if (partes.length != 6) continue;

                int    codigo    = Integer.parseInt(partes[0].trim());
                String nombre    = partes[1].trim();
                int    piso      = Integer.parseInt(partes[2].trim());
                String ubicacion = partes[3].trim();
                int    capacidad = Integer.parseInt(partes[4].trim());
                int    estado    = Integer.parseInt(partes[5].trim());

                lista.add(new Consultorio(codigo, nombre, piso, ubicacion, capacidad, estado));

                // Actualizar el contador para que el siguiente código sea mayor
                if (codigo >= contadorCodigo) {
                    contadorCodigo = codigo + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar consultorios: " + e.getMessage());
        }
    }
}
