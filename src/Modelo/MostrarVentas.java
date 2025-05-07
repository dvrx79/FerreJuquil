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

public class MostrarVentas {
    private int idVenta;
    private Date fecha;
    private Double monto;
    private String tipo;
    private String formaPago;
    private String estado;
    private String cliente;
    private String numeroCliente;

    public MostrarVentas() {
    }

    public MostrarVentas(int idVenta, Date fecha, Double monto, String formaPago, String estado, String cliente, String numeroCliente) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.monto = monto;
        this.formaPago = formaPago;
        this.estado = estado;
        this.cliente = cliente;
        this.numeroCliente = numeroCliente;
    }

    
    
    public MostrarVentas(int idVenta, Date fecha, Double monto, String tipo, String formaPago, String estado, String cliente, String numeroCliente) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.formaPago = formaPago;
        this.estado = estado;
        this.cliente = cliente;
        this.numeroCliente = numeroCliente;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
   
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    
    
}
