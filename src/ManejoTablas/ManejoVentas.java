/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

import Conexion.Conexion;
import Modelo.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author joelmatadamas
 */
public class ManejoVentas {
    
   public List<MostrarVentas> obtenerVentas() throws SQLException{
         List<MostrarVentas> listaVentas = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
                //"p.tipo AS tipo_producto, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
                //"INNER JOIN DETALLE_VENTA AS dv ON v.id_venta = dv.id_venta " +
                //"INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
                //"INNER JOIN VENTA AS v ON dv.id_venta = v.id_venta " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente "+
                //"INNER JOIN PRODUCTO AS p ON inv.id_producto = p.id_producto " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago "  
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
                    //String tipo = rs.getString("tipo_producto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC;
                    if(numeroCliente == null){
                        numC = "Sin numero";
                    }else{
                        numC = numeroCliente;
                    }
                    
                    MostrarVentas ventas = new MostrarVentas(idVenta,fecha, monto, metodoPago, estPago, ncliente, numC);
                    listaVentas.add(ventas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaVentas;
    }
   
   
   public List<MostrarVentas> obtenerVentasPorMetodoPago(String mtdpag) throws SQLException{
         List<MostrarVentas> listaVentas = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
                //"p.tipo AS tipo_producto, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
                //"INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
                //"INNER JOIN VENTA AS v ON dv.id_venta = v.id_venta " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente "+
                //"INNER JOIN PRODUCTO AS p ON inv.id_producto = p.id_producto " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago " +
                "WHERE mp.nombre = ?"
                )) {
                stmt.setString(1, mtdpag);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
                    //String tipo = rs.getString("tipo_producto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC;
                    if(numeroCliente == null){
                        numC = "Sin numero";
                    }else{
                        numC = numeroCliente;
                    }
                    
                    MostrarVentas ventas = new MostrarVentas(idVenta,fecha, monto, metodoPago, estPago, ncliente, numC);
                    listaVentas.add(ventas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaVentas;
    }
   
   public List<MostrarVentas> obtenerVentasPorTipoProducto(String tipoPago) throws SQLException{
         List<MostrarVentas> listaVentas = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
                //"p.tipo AS tipo_producto, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
                //"INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
                //"INNER JOIN VENTA AS v ON dv.id_venta = v.id_venta " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente "+
                //"INNER JOIN PRODUCTO AS p ON inv.id_producto = p.id_producto " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago " +
                "WHERE p.tipo = ?"
                )) {
                stmt.setString(1, tipoPago);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
                    //String tipo = rs.getString("tipo_producto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC;
                    if(numeroCliente == null){
                        numC = "Sin numero";
                    }else{
                        numC = numeroCliente;
                    }
                    
                    MostrarVentas ventas = new MostrarVentas(idVenta,fecha, monto, metodoPago, estPago, ncliente, numC);
                    listaVentas.add(ventas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaVentas;
    }

   
   public List<MostrarVentas> obtenerVentasPorMetodoPagoBancario(String mtdpag) throws SQLException{
         List<MostrarVentas> listaVentas = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
                //"p.tipo AS tipo_producto, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
                //"INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
                //"INNER JOIN VENTA AS v ON dv.id_venta = v.id_venta " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente "+
                //"INNER JOIN PRODUCTO AS p ON inv.id_producto = p.id_producto " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago " +
                "WHERE mp.nombre != ?"
                )) {
                stmt.setString(1, mtdpag);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
                    //String tipo = rs.getString("tipo_producto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC;
                    if(numeroCliente == null){
                        numC = "Sin numero";
                    }else{
                        numC = numeroCliente;
                    }
                    
                    MostrarVentas ventas = new MostrarVentas(idVenta,fecha, monto, metodoPago, estPago, ncliente, numC);
                    listaVentas.add(ventas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaVentas;
    }
   
   
     public List<MostrarVentas> obtenerVentasPorFecha(Date fechaV) throws SQLException{
         List<MostrarVentas> listaVentas = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
//                "p.tipo AS tipo_producto, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
//                "INNER JOIN DETALLE_VENTA AS dv on v.id_venta = dv.id_venta " +
//                "INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
                //"INNER JOIN VENTA AS v ON dv.id_venta = v.id_venta " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente "+
//                "INNER JOIN PRODUCTO AS p ON inv.id_producto = p.id_producto " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago " +
                "WHERE v.fecha_venta = ?"
                )) {
                stmt.setDate(1, fechaV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
//                    String tipo = rs.getString("tipo_producto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC;
                    if(numeroCliente == null){
                        numC = "Sin numero";
                    }else{
                        numC = numeroCliente;
                    }
                    
                    MostrarVentas ventas = new MostrarVentas(idVenta,fecha, monto, metodoPago, estPago, ncliente, numC);
                    listaVentas.add(ventas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaVentas;
    }
   
   
    public MostrarVentas obtenerVentaPorId(int idV) throws SQLException {
    MostrarVentas venta = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "v.fecha_venta AS fecha, " +
                "v.monto_total AS monto, " +
                "c.nombre_cliente AS nombre, " +
                "c.numero AS numero_cliente, " +
                "mp.nombre AS metodo_pago, " +
                "pago.estado AS estado_pago, " +        
                "v.id_venta AS id_venta " +
                "FROM VENTA AS v " +
                "INNER JOIN CLIENTE AS c ON v.id_cliente = c.id_cliente " +
                "INNER JOIN PAGO AS pago ON v.id_pago = pago.id_pago " +
                "INNER JOIN METODO_PAGO AS mp ON pago.id_metodo_pago = mp.id_metodo_pago " +
                "WHERE v.id_venta = ?"
                )) {
            stmt.setInt(1, idV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Date fecha = rs.getDate("fecha");
                    Double monto = rs.getDouble("monto");
                    String metodoPago = rs.getString("metodo_pago");
                    String estPago = rs.getString("estado_pago");
                    String ncliente = rs.getString("nombre");
                    String numeroCliente = rs.getString("numero_cliente");
                    String numC = (numeroCliente == null) ? "Sin numero" : numeroCliente;
                    
                    venta = new MostrarVentas(idVenta, fecha, monto, metodoPago, estPago, ncliente, numC);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Relanza la excepción para manejo superior
        }
    }
    return venta;
}
     
     
   public boolean actualizarEstadoPagoPorIdVenta(int id, String est) throws SQLException {
    boolean actualizado = false;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE PAGO " +
                "SET estado = ? " +
                "WHERE id_pago = (SELECT id_pago FROM VENTA WHERE id_venta = ?)"
        )) {
            stmt.setString(1, est);
            stmt.setInt(2, id);

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return actualizado;
}
    
}
