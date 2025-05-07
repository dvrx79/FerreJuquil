/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joelmatadamas
 */
import java.sql.*;

public class Venta {
    private int id;
    private int cantidad;
    private double monto;
    private Date fecha;
    private int idPago;

    public Venta() {}

    public Venta(int id, int cantidad, double monto, Date fecha, int idPago) {
        this.id = id;
        this.cantidad = cantidad;
        this.monto = monto;
        this.fecha = fecha;
        this.idPago = idPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    
    
    
    
    
}
