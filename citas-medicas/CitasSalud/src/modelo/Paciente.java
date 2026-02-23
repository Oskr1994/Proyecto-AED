package modelo;

public class Paciente {

    // Atributos 
    private int    codigo;    
    private String nombre;    
    private String apellidos; 
    private String dni;       
    private int    edad;      
    private int    celular;  
    private String email;    
    private int    estado;    

    // Constructor 
    public Paciente(int codigo, String nombre, String apellidos, String dni,
                    int edad, int celular, String email, int estado) {
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.apellidos = apellidos;
        this.dni       = dni;
        this.edad      = edad;
        this.celular   = celular;
        this.email     = email;
        this.estado    = estado;
    }

    // Getters
    public int    getCodigo()    {
    	return codigo; 
    }
    
    public String getNombre()    {
    	return nombre; 
    }
    public String getApellidos() {
    	return apellidos; 
    }
    public String getDni()       {
    	return dni; 
    }
    public int    getEdad()      {
    	return edad; 
    }
    public int    getCelular()   {
    	return celular; 
    }
    public String getEmail()     {
    	return email; 
    }
    public int    getEstado()    {
    	return estado; 
    }

    // Setters
    public void setCodigo(int codigo)  {
    	this.codigo    = codigo; 
    }
    public void setNombre(String nombre) {
    	this.nombre    = nombre; 
    }
    public void setApellidos(String apellidos) {
    	this.apellidos = apellidos; 
    }
    public void setDni(String dni)  {
    	this.dni       = dni; 
    }
    public void setEdad(int edad)   {
    	this.edad      = edad; 
    }
    public void setCelular(int celular) {
    	this.celular   = celular; }
    public void setEmail(String email) {
    	this.email     = email; 
    }
    public void setEstado(int estado) {
    	this.estado    = estado; 
    }

    // guardar en pacientes.txt 
    // Formato: codigo|nombre|apellidos|dni|edad|celular|email|estado
    @Override
    public String toString() {
        return codigo + "|" + nombre + "|" + apellidos + "|" + dni + "|"
             + edad + "|" + celular + "|" + email + "|" + estado;
    }
}
