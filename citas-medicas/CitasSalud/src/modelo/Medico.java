package modelo;

public class Medico {
    private int codigo;
    private String nombre;
    private String cmp;
    private int estado;

    public Medico(int codigo, String nombre, String cmp, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cmp = cmp;
        this.estado = estado;
    }

    // GETTERS
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCmp() {
        return cmp;
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