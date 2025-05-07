/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

import Conexion.Conexion;
import Modelo.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salin
 */
public class ManejoClientes {
    public List<Cliente> obtenerClientes() throws SQLException{
         List<Cliente> listaClientes = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "cli.id_cliente AS ID_cliente, " +
                "cli.rfc AS rfc_cliente, "+
                "cli.nombre_cliente AS Nombre_cliente, " +
                "cli.apellido_paterno AS Ap_Paterno, " +
                "cli.apellido_materno AS Ap_Materno, " +
                "cli.numero AS numero_telefono, " +
                "cli.numero_compras AS num_compras, "+
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM CLIENTE AS cli " +
                "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado "  
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCli = rs.getInt("ID_cliente");
                    String rfc = rs.getString("rfc_cliente");
                    String nombre = rs.getString("Nombre_cliente");
                    String aPaterno = rs.getString("Ap_paterno");
                    String aMaterno = rs.getString("Ap_Materno");
                    String numeroTel = rs.getString("numero_telefono");
                    int numCompras = rs.getInt("num_compras");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;
                    String RFC;
                    if(rfc == null){
                        RFC = "Sin RFC";
                    }else{
                        RFC =rfc;
                    }
                    
                    Cliente cliente = new Cliente(idCli, RFC, nombre, aPaterno, aMaterno, numeroTel, numCompras, direccion );
                    listaClientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaClientes;
    }
    
    
    public Cliente buscarClientePorId(int ID) throws SQLException{
         Cliente cliente = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "cli.id_cliente AS ID_cliente, " +
                "cli.rfc AS rfc_cliente, "+
                "cli.nombre_cliente AS Nombre_cliente, " +
                "cli.apellido_paterno AS Ap_Paterno, " +
                "cli.apellido_materno AS Ap_Materno, " +
                "cli.numero AS numero_telefono, " +
                "cli.numero_compras AS num_compras, "+
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM CLIENTE AS cli " +
                "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                "WHERE cli.id_cliente = ?"
                )) {
                stmt.setInt(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCli = rs.getInt("ID_cliente");
                    String rfc = rs.getString("rfc_cliente");
                    String nombre = rs.getString("Nombre_cliente");
                    String aPaterno = rs.getString("Ap_paterno");
                    String aMaterno = rs.getString("Ap_Materno");
                    String numeroTel = rs.getString("numero_telefono");
                    int numCompras = rs.getInt("num_compras");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;
                    
                    String RFC;
                    if(rfc == null){
                        RFC = "Sin RFC";
                    }else{
                        RFC =rfc;
                    }
     
                    cliente = new Cliente(idCli, RFC, nombre, aPaterno, aMaterno, numeroTel, numCompras, direccion );
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return cliente;
    }
    
    public List<Cliente> buscarClientePorNombre(String nom) throws SQLException{
         List<Cliente> listaClientes = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "cli.id_cliente AS ID_cliente, " +
                "cli.rfc AS rfc_cliente, "+
                "cli.nombre_cliente AS Nombre_cliente, " +
                "cli.apellido_paterno AS Ap_Paterno, " +
                "cli.apellido_materno AS Ap_Materno, " +
                "cli.numero AS numero_telefono, " +
                "cli.numero_compras AS num_compras, "+
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM CLIENTE AS cli " +
                "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                "WHERE cli.nombre_cliente = ?"
                )) {
                stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCli = rs.getInt("ID_cliente");
                    String rfc = rs.getString("rfc_cliente");
                    String nombre = rs.getString("Nombre_cliente");
                    String aPaterno = rs.getString("Ap_paterno");
                    String aMaterno = rs.getString("Ap_Materno");
                    String numeroTel = rs.getString("numero_telefono");
                    int numCompras = rs.getInt("num_compras");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;
                    
                    String RFC;
                    if(rfc == null){
                        RFC = "Sin RFC";
                    }else{
                        RFC =rfc;
                    }
     
                    Cliente cliente = new Cliente(idCli, RFC, nombre, aPaterno, aMaterno, numeroTel, numCompras, direccion );
                    listaClientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaClientes;
    }
    
    
    public Cliente buscarClientePorIdFactura(int ID) throws SQLException{
         Cliente cliente = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "cli.id_cliente AS ID_cliente, " +
                "cli.rfc AS rfc_cliente, "+
                "cli.nombre_cliente AS Nombre_cliente, " +
                "cli.apellido_paterno AS Ap_Paterno, " +
                "cli.apellido_materno AS Ap_Materno, " +
                "cli.numero AS numero_telefono, " +
                "cli.numero_compras AS num_compras, "+
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "pueb.nombre AS nombre_pueblo, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM CLIENTE AS cli " +
                "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                "WHERE cli.id_cliente = ?"
                )) {
                stmt.setInt(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idCli = rs.getInt("ID_cliente");
                    String rfc = rs.getString("rfc_cliente");
                    String nombre = rs.getString("Nombre_cliente");
                    String aPaterno = rs.getString("Ap_paterno");
                    String aMaterno = rs.getString("Ap_Materno");
                    String numeroTel = rs.getString("numero_telefono");
                    int numCompras = rs.getInt("num_compras");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    String pueblo = rs.getString("nombre_pueblo");
                    
                    String direccion = calle +" No Ext. "+numero+" "+pueblo;
                    String dir2 = municipio+", "+estado+", C.P. "+codPos+", México";
                    
                    String RFC;
                    if(rfc == null){
                        RFC = "Sin RFC";
                    }else{
                        RFC =rfc;
                    }
     
                    cliente = new Cliente(idCli, RFC, nombre, aPaterno, aMaterno, numeroTel, numCompras, direccion, dir2 );
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return cliente;
    }
    
    
    
    // Nota aqui se removera el id CodigoPostal
    public int insertarDireccion(String calle, String numero, int idPueblo, int idCodigoPostal) throws SQLException {
        Connection con = Conexion.getConexion();
        int idDireccionGenerado = -1;

        if (con != null) {
            String sql = "INSERT INTO DIRECCION (calle, numero, id_pueblo, id_codigo_postal) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, calle);
                stmt.setString(2, numero);
                stmt.setInt(3, idPueblo);
                stmt.setInt(4, idCodigoPostal);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            idDireccionGenerado = generatedKeys.getInt(1);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return idDireccionGenerado;
    }

     public int insertarCliente(String rfc, String nombreC, String aPaterno, String aMaterno, String numero, int IDdireccion) throws SQLException {
        int idVentaGenerada = -1;
        Connection con = Conexion.getConexion(); 
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (con != null) {
            try {
                String sql = "INSERT INTO CLIENTE (rfc, nombre_cliente, apellido_paterno, apellido_materno, numero, id_direccion) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1, rfc);
                stmt.setString(2, nombreC);
                stmt.setString(3, aPaterno);
                stmt.setString(4, aMaterno);
                stmt.setString(5, numero);
                stmt.setInt(6,IDdireccion );

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        idVentaGenerada = rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return idVentaGenerada;
    }
     
      public int insertarClienteVentaNormalSinRfc(String nombreC, String numero) throws SQLException {
        int idVentaGenerada = -1;
        Connection con = Conexion.getConexion(); 
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (con != null) {
            try {
                String sql = "INSERT INTO CLIENTE (nombre_cliente, numero) VALUES (?, ?)";
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setString(1, nombreC);
                stmt.setString(2, numero);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        idVentaGenerada = rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return idVentaGenerada;
    }
      
    
}
