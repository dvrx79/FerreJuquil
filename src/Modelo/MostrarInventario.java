package Modelo;

import java.util.Date;

/**
 *
 * @author joelmatadamas
 */
public class MostrarInventario {
    private String nombre;
    private String tipo;
    private String descripcion;
    private int cantidad;
    private String codigo;
    private float precioCompra;
    private float preciVenta;
    private Date ultimaActualizacion;
    private int stockMinimo;  // Nuevo campo para stock mínimo

    public MostrarInventario() {
    }

    // Constructor actualizado con stock mínimo
    public MostrarInventario(String nombre, String tipo, String descripcion, 
                           int cantidad, String codigo, float precioCompra, 
                           float preciVenta, Date ultimaActualizacion, 
                           int stockMinimo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.precioCompra = precioCompra;
        this.preciVenta = preciVenta;
        this.ultimaActualizacion = ultimaActualizacion;
        this.stockMinimo = stockMinimo;
    }
    
    // Getters y Setters
    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPreciVenta() {
        return preciVenta;
    }

    public void setPreciVenta(float preciVenta) {
        this.preciVenta = preciVenta;
    }

    // Nuevos getter y setter para stock mínimo
    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    // Método para verificar si el stock está por debajo del mínimo
    public boolean estaBajoStockMinimo() {
        return cantidad < stockMinimo;
    }
}