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
}