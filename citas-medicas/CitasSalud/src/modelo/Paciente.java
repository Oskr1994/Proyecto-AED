package modelo;

public class Paciente {

    private int codigo;
    private String dni;
    private String nombre;
    private int estado; // 1 = Activo, 0 = Inactivo

    public Paciente(int codigo, String dni, String nombre, int estado) {
        this.codigo = codigo;
        this.dni = dni;
        this.nombre = nombre;
        this.estado = estado;
    }

    // GETTERS
    public int getCodigo() { return codigo; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public int getEstado() { return estado; }

    // SETTERS
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEstado(int estado) { this.estado = estado; }
}