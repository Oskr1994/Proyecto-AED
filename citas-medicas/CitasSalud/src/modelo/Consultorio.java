package modelo;

public class Consultorio {
    private int codigo;
    private String nombre;
    private int estado;

    public Consultorio(int codigo, String nombre, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    // GETTERS
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEstado() {
        return estado;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}