/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author salin
 */
import java.sql.*;

public class MostrarPedido {
    private String estEntrega;
    private String estPago;
    private Date fecha;
    private Double monto;
    private String direccion;
    private String nombreCliente;
    private String numero;
    private int idVenta;
    private int idPedido;

    public MostrarPedido() {
    }

    public MostrarPedido(String estEntrega, String estPago, Date fecha, Double monto, String direccion, String nombreCliente, String numero, int idVenta, int idPedido) {
        this.estEntrega = estEntrega;
        this.estPago = estPago;
        this.fecha = fecha;
        this.monto = monto;
        this.direccion = direccion;
        this.nombreCliente = nombreCliente;
        this.numero = numero;
        this.idVenta = idVenta;
        this.idPedido = idPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getEstEntrega() {
        return estEntrega;
    }

    public void setEstEntrega(String estEntrega) {
        this.estEntrega = estEntrega;
    }

    public String getEstPago() {
        return estPago;
    }

    public void setEstPago(String estPago) {
        this.estPago = estPago;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
    
}
