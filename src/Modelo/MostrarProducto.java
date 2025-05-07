/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author salin
 */
public class MostrarProducto {
    private String nombre;
    private float costoCompra;
    private float costoVenta;
    private String tipo;
    private String img;
    private int cantidadVendida;
    private String unidadMedida;
    private String descripcion;

    public MostrarProducto() {
    }

    public MostrarProducto(String nombre, float costoCompra, float costoVenta, String tipo, String img) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.costoVenta = costoVenta;
        this.tipo = tipo;
        this.img = img;
    }

    public MostrarProducto(String nombre, float costoCompra, float costoVenta, String tipo, String img, int cantidadVendida, String unidadMedida) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.costoVenta = costoVenta;
        this.tipo = tipo;
        this.img = img;
        this.cantidadVendida = cantidadVendida;
        this.unidadMedida = unidadMedida;
    }

    public MostrarProducto(String nombre, float costoCompra, float costoVenta, String tipo, String img, int cantidadVendida, String unidadMedida, String descripcion) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.costoVenta = costoVenta;
        this.tipo = tipo;
        this.img = img;
        this.cantidadVendida = cantidadVendida;
        this.unidadMedida = unidadMedida;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(float costoCompra) {
        this.costoCompra = costoCompra;
    }

    public float getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(float costoVenta) {
        this.costoVenta = costoVenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return nombre + " - $" + costoCompra + " c/u";
    }
    
    
}
