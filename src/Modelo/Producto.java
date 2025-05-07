/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joelmatadamas
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private String codigoBarras;
    private String descripcion;
    private double costoVenta;
    private String tipo; // Puede ser "Construcción" o "Ferreteria"
    private String imagen;
    private String UnidadMedida;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String codigoBarras, double costoVenta, String tipo, String imagen, String UnidadMedida) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.costoVenta = costoVenta;
        this.tipo = tipo;
        this.imagen = imagen;
        this.UnidadMedida = UnidadMedida;
    }

    public Producto(int idProducto, String nombre, String codigoBarras, String descripcion, double costoVenta, String tipo, String imagen, String UnidadMedida) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.descripcion = descripcion;
        this.costoVenta = costoVenta;
        this.tipo = tipo;
        this.imagen = imagen;
        this.UnidadMedida = UnidadMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public double getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(double costoVenta) {
        this.costoVenta = costoVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
    }

   
    
}
