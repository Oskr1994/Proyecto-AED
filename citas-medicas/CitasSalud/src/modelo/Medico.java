package modelo;

public class Medico {
    private int codigo;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String cmp;
    private int estado;

    public Medico(int codigo, String nombre, String apellidos, String especialidad, String cmp, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
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
    
    public String getApellidos() {
        return apellidos;
    }
    
    public String getEspecialidad() {
        return especialidad;
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

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public void setCmp(String cmp) {
    	this.cmp = cmp; 
    }
    @Override
    public String toString() {
        return codigo + "|" + nombre + "|" + apellidos + "|"
             + especialidad + "|" + cmp + "|" + estado;
    }
}