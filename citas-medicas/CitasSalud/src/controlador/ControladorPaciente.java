package controlador;

import modelo.Paciente;
import java.io.*;
import java.util.ArrayList;

public class ControladorPaciente {

    // Ruta del archivo de persistencia
    // Formato de cada línea: codigo|nombre|apellidos|dni|edad|celular|email|estado
    private static final String ARCHIVO = "pacientes.txt";

    // Lista en memoria
    private static ArrayList<Paciente> lista = new ArrayList<>();

    // Contador para generar códigos correlativos (1001, 1002, ...)
    private static int contadorCodigo = 1001;

    // Bloque estático: carga los datos al iniciar la aplicación
    static {
        cargarDesdeArchivo();
    }


    // AGREGAR
 
    public static boolean agregar(String nombre, String apellidos, String dni,
                                   int edad, int celular, String email) {

        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            apellidos == null || apellidos.trim().isEmpty() ||
            dni == null || dni.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del DNI (regla de negocio)
        for (Paciente p : lista) {
            if (p.getDni().equalsIgnoreCase(dni.trim())) {
                return false; // DNI ya existe
            }
        }

        // Validar edad no negativa
        if (edad < 0) {
            return false;
        }

        Paciente nuevo = new Paciente(
                contadorCodigo++,
                nombre.trim(),
                apellidos.trim(),
                dni.trim(),
                edad,
                celular,
                email != null ? email.trim() : "",
                1 // activo por defecto
        );

        lista.add(nuevo);
        guardarEnArchivo();
        return true;
    }

    // MODIFICAR

    public static boolean modificar(int codigo, String nuevoNombre, String nuevosApellidos,
                                     String nuevoDni, int nuevaEdad, int nuevoCelular,
                                     String nuevoEmail, int nuevoEstado) {

        // Validar campos obligatorios
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() ||
            nuevosApellidos == null || nuevosApellidos.trim().isEmpty() ||
            nuevoDni == null || nuevoDni.trim().isEmpty()) {
            return false;
        }

        // Validar unicidad del DNI
        for (Paciente p : lista) {
            if (p.getDni().equalsIgnoreCase(nuevoDni.trim())
                    && p.getCodigo() != codigo) {
                return false; 
            }
        }

        // Validar edad no negativa
        if (nuevaEdad < 0) {
            return false;
        }

        // Buscar y actualizar
        for (Paciente p : lista) {
            if (p.getCodigo() == codigo) {
                p.setNombre(nuevoNombre.trim());
                p.setApellidos(nuevosApellidos.trim());
                p.setDni(nuevoDni.trim());
                p.setEdad(nuevaEdad);
                p.setCelular(nuevoCelular);
                p.setEmail(nuevoEmail != null ? nuevoEmail.trim() : "");
                p.setEstado(nuevoEstado);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // ELIMINAR

    public static boolean eliminar(int codigo) {
        for (Paciente p : lista) {
            if (p.getCodigo() == codigo) {
                lista.remove(p);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    // LISTAR

    public static ArrayList<Paciente> listar() {
        return lista;
    }

    // BUSCAR POR DNI

    public static Paciente buscarPorDni(String dni) {
        for (Paciente p : lista) {
            if (p.getDni().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        return null;
    }

    // BUSCAR POR CÓDIGO

    public static Paciente buscarPorCodigo(int codigo) {
        for (Paciente p : lista) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    // GUARDAR EN ARCHIVO
    // Formato: codigo|nombre|apellidos|dni|edad|celular|email|estado

    private static void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Paciente p : lista) {
                bw.write(p.toString()); // usa el toString() de Paciente
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    // CARGAR DESDE ARCHIVO
    // Formato esperado: codigo|nombre|apellidos|dni|edad|celular|email|estado

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

                // Verificar que la línea tenga exactamente 8 campos
                if (partes.length != 8) continue;

                int    codigo    = Integer.parseInt(partes[0].trim());
                String nombre    = partes[1].trim();
                String apellidos = partes[2].trim();
                String dni       = partes[3].trim();
                int    edad      = Integer.parseInt(partes[4].trim());
                int    celular   = Integer.parseInt(partes[5].trim());
                String email     = partes[6].trim();
                int    estado    = Integer.parseInt(partes[7].trim());

                lista.add(new Paciente(codigo, nombre, apellidos, dni,
                                       edad, celular, email, estado));

                // Actualizar contador
                if (codigo >= contadorCodigo) {
                    contadorCodigo = codigo + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
    }
}
