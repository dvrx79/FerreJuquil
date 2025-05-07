/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author salin
 */
public class Pueblo {
    private int id;
    private String nombre;
    private int id_Municipio;
    //private int id_codigoPostal;
    
    public Pueblo() {
    }

    public Pueblo(int id, String nombre, int id_Municipio) {
        this.id = id;
        this.nombre = nombre;
        this.id_Municipio = id_Municipio;
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

    public int getId_Municipio() {
        return id_Municipio;
    }

    public void setId_Municipio(int id_Municipio) {
        this.id_Municipio = id_Municipio;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
