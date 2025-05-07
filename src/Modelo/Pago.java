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

public class Pago {
    private int id;
    private float monto;
    private Date fecha;
    private String estado;
    private int id_metodo_pago;

    public Pago() {
    }

    
    
    public Pago(int id, float monto, Date fecha, String estado, int id_metodo_pago) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.id_metodo_pago = id_metodo_pago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_metodo_pago() {
        return id_metodo_pago;
    }

    public void setId_metodo_pago(int id_metodo_pago) {
        this.id_metodo_pago = id_metodo_pago;
    }
    
    
    
}
