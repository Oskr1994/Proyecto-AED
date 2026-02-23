package modelo;

public class Consultorio {

    // ── Atributos según especificación del proyecto ──────────────────────
    private int    codigo;     
    private String nombre;     
    private int    piso;    
    private String ubicacion; 
    private int    capacidad;  
    private int    estado;     

    // ── Constructor completo ─────────────────────────────────────────────
    public Consultorio(int codigo, String nombre, int piso,
                       String ubicacion, int capacidad, int estado) {
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.piso      = piso;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.estado    = estado;
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public int    getCodigo()    { return codigo; }
    public String getNombre()    { return nombre; }
    public int    getPiso()      { return piso; }
    public String getUbicacion() { return ubicacion; }
    public int    getCapacidad() { return capacidad; }
    public int    getEstado()    { return estado; }

    // ── Setters ──────────────────────────────────────────────────────────
    public void setCodigo(int codigo)          { this.codigo    = codigo; }
    public void setNombre(String nombre)       { this.nombre    = nombre; }
    public void setPiso(int piso)              { this.piso      = piso; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setCapacidad(int capacidad)    { this.capacidad = capacidad; }
    public void setEstado(int estado)          { this.estado    = estado; }

    // ── toString — formato pipe para guardar en consultorios.txt ─────────
    // Formato: codigo|nombre|piso|ubicacion|capacidad|estado
    @Override
    public String toString() {
        return codigo + "|" + nombre + "|" + piso + "|"
             + ubicacion + "|" + capacidad + "|" + estado;
    }
}
