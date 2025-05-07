/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

import Conexion.Conexion;
import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salin
 */
public class ManejoDirecciones {
    public List<Municipio> obtenerMunicipios() throws SQLException{
         List<Municipio> listaMunicipios = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "m.id_municipio AS id_mun,  " +      
                "m.nombre AS nombre_municipio " +
                "FROM MUNICIPIO AS m "
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idMun = rs.getInt("id_mun");
                    String nombreM = rs.getString("nombre_municipio");

                    Municipio municipio = new Municipio(idMun, nombreM);
                    listaMunicipios.add(municipio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaMunicipios;
    }
    
    
     public List<Municipio> obtenerMunicipiosPorEstado(int est) throws SQLException{
         List<Municipio> listaMunicipios = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "m.id_municipio AS id_mun,  " +      
                "m.nombre AS nombre_municipio " +
                "FROM MUNICIPIO AS m " +
                "WHERE m.id_estado = ? "
                )) {
                stmt.setInt(1, est);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idMun = rs.getInt("id_mun");
                    String nombreM = rs.getString("nombre_municipio");

                    Municipio municipio = new Municipio(idMun, nombreM);
                    listaMunicipios.add(municipio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaMunicipios;
    }
     
     public List<Pueblo> obtenerPueblos() throws SQLException{
         List<Pueblo> listaPueblos = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.id_pueblo AS IdPueblo, " +
                "p.nombre AS nombre_Pueblo,  " +      
                "p.id_municipio AS IdMun " +
                "FROM PUEBLO AS p "
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("IdPueblo");
                    int idMun = rs.getInt("IdMun");
                    String nombre = rs.getString("nombre_Pueblo");

                    Pueblo pueblo = new Pueblo(id, nombre, idMun);
                    listaPueblos.add(pueblo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaPueblos;
    }
     
     public List<Pueblo> obtenerPueblosPorMunicipio(int idMunicipio) throws SQLException{
         List<Pueblo> listaPueblos = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.id_pueblo AS IdPueblo, " +
                "p.nombre AS nombre_Pueblo,  " +      
                "p.id_municipio AS IdMun " +
                "FROM PUEBLO AS p " + 
                "WHERE p.id_municipio = ?"        
                )) {
                stmt.setInt(1, idMunicipio);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("IdPueblo");
                    int idMun = rs.getInt("Idmun");
                    String nombre = rs.getString("nombre_Pueblo");

                    Pueblo pueblo = new Pueblo(id, nombre, idMun);
                    listaPueblos.add(pueblo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaPueblos;
    }
     
     
     //Agregar mnetodo para recuperar el codigo postal por ID
}
