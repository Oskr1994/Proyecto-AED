package classes;


public class clsLaptop {

    private int codigo, cantidad;
    private String descripcion, marca;
    private double precio;

    public clsLaptop(int codigo, String descripcion, String marca, int cantidad, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precio = precio;
    }


    public int getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public String getMarca() { return marca; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }

  
    public double montoPagado() {
        return cantidad * precio;
    }

    public double descuento() {
        return montoPagado() < 5000 ? montoPagado() * 0.05 : montoPagado() * 0.0725;
    }

    public double montoNeto() {
        return montoPagado() - descuento();
    }


	public double getPrecioventa() {
		// TODO Auto-generated method stub
		return 0;
	}


	public String getCantvendida() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setPrecioventa(double d) {
		// TODO Auto-generated method stub
		
	}


	public void setCantvendida(String string) {
		// TODO Auto-generated method stub
		
	}
}