/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author salin
 */
public class Cliente {
    private int id;
    private String rfc;
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String numero;
    private int numeroCompras;
    private String direccion;
    private String ColEstCP;

    public Cliente() {
    }

    public Cliente(int id, String rfc, String nombre, String aPaterno, String aMaterno, String numero, int numeroCompras, String direccion) {
        this.id = id;
        this.rfc = rfc;
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.numero = numero;
        this.numeroCompras = numeroCompras;
        this.direccion = direccion;
    }

    public Cliente(int id, String rfc, String nombre, String aPaterno, String aMaterno, String numero, int numeroCompras, String direccion, String ColEstCP) {
        this.id = id;
        this.rfc = rfc;
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.numero = numero;
        this.numeroCompras = numeroCompras;
        this.direccion = direccion;
        this.ColEstCP = ColEstCP;
    }

    public String getColEstCP() {
        return ColEstCP;
    }

    public void setColEstCP(String ColEstCP) {
        this.ColEstCP = ColEstCP;
    }

    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getNumeroCompras() {
        return numeroCompras;
    }

    public void setNumeroCompras(int numeroCompras) {
        this.numeroCompras = numeroCompras;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
