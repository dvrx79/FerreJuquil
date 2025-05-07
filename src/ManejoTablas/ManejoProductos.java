/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

/**
 *
 * @author joelmatadamas
 */
import Conexion.Conexion;
import Modelo.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ManejoProductos {
    public List<MostrarProducto> ObtenerProductoPorProveedor(String nombreProv, String CorreoProv)throws SQLException {
    List<MostrarProducto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.costo_compra AS costo_Compra, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto " +
    "FROM COMPRA AS c " +
    "INNER JOIN PRODUCTO AS pdto ON c.id_producto = pdto.id_producto "+
    "INNER JOIN PROVEEDOR AS pvdr ON c.id_proveedor = pvdr.id_proveedor "+
    "WHERE pvdr.nombre_proveedor = ? AND pvdr.correo = ? "
        )){
       stmt.setString(1, nombreProv);
       stmt.setString(2, CorreoProv);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    float costoC = rs.getFloat("costo_Compra");
                    float costoV = rs.getFloat("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");

                    MostrarProducto producto = new MostrarProducto(nombre, costoC, costoV, tipo, img);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    public List<MostrarProducto> ObtenerProductosPorVentaExclusivo(int id_venta)throws SQLException {
    List<MostrarProducto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.costo_compra AS costo_Compra, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "dv.cantidad AS cantidad_vendida, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM DETALLE_VENTA AS dv " +
    "INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
    "INNER JOIN PRODUCTO AS pdto ON inv.id_producto = pdto.id_producto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida " +
    "WHERE dv.id_venta = ? "
        )){
       stmt.setInt(1, id_venta);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    float costoC = rs.getFloat("costo_Compra");
                    float costoV = rs.getFloat("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    int cantidadVend = rs.getInt("cantidad_vendida");
                    String unidadMedida = rs.getString("unidad_medida");
                    float cantidadTotal = cantidadVend*costoV;
                    String UM;
                    if(cantidadVend == 1){
                     UM = unidadMedida;}
                    else{UM = unidadMedida+"s";}

                    MostrarProducto producto = new MostrarProducto(nombre, costoC, cantidadTotal, tipo, img, cantidadVend, UM);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    
    
    public List<Producto> ObtenerProductos()throws SQLException {
    List<Producto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.id_producto AS id_Producto, "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.codigo_barras AS codigo_producto, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM PRODUCTO AS pdto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida " 
        )){
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idP = rs.getInt("id_Producto");
                    String nombre = rs.getString("nombre_producto");
                    String codigoBarras = rs.getString("codigo_producto");
                    Double costoV = rs.getDouble("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    String unidadMedida = rs.getString("unidad_medida");

                    Producto producto = new Producto(idP, nombre, codigoBarras, costoV, tipo, img, unidadMedida);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    
    public List<Producto> ObtenerProductosPorNombre(String nombreProducto)throws SQLException {
    List<Producto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.id_producto AS id_Producto, "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.codigo_barras AS codigo_producto, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM PRODUCTO AS pdto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida "+
    "WHERE pdto.nombre = ? "
        )){
            stmt.setString(1, nombreProducto);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idP = rs.getInt("id_Producto");
                    String nombre = rs.getString("nombre_producto");
                    String codigoBarras = rs.getString("codigo_producto");
                    Double costoV = rs.getDouble("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    String unidadMedida = rs.getString("unidad_medida");

                    Producto producto = new Producto(idP, nombre, codigoBarras, costoV, tipo, img, unidadMedida);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    
    public List<Producto> ObtenerProductosPorTipoProducto(String TipoProducto)throws SQLException {
    List<Producto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.id_producto AS id_Producto, "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.codigo_barras AS codigo_producto, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM PRODUCTO AS pdto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida "+
    "WHERE pdto.tipo = ? "
        )){
            stmt.setString(1, TipoProducto);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idP = rs.getInt("id_Producto");
                    String nombre = rs.getString("nombre_producto");
                    String codigoBarras = rs.getString("codigo_producto");
                    Double costoV = rs.getDouble("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    String unidadMedida = rs.getString("unidad_medida");

                    Producto producto = new Producto(idP, nombre, codigoBarras, costoV, tipo, img, unidadMedida);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    public Producto ObtenerProductoPorid(int id)throws SQLException {
    Producto producto = null;
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.id_producto AS id_Producto, "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.codigo_barras AS codigo_producto, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM PRODUCTO AS pdto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida "+
    "WHERE pdto.id_producto = ? "
        )){
            stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idP = rs.getInt("id_Producto");
                    String nombre = rs.getString("nombre_producto");
                    String codigoBarras = rs.getString("codigo_producto");
                    Double costoV = rs.getDouble("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    String unidadMedida = rs.getString("unidad_medida");

                    producto = new Producto(idP, nombre, codigoBarras, costoV, tipo, img, unidadMedida);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return producto;
    }
    
    public List<MostrarProducto> ObtenerProductosPorDetalleVenta(int id_venta)throws SQLException {
    List<MostrarProducto> listaproductos = new ArrayList<>();
    Connection con = Conexion.getConexion();
    
    if(con != null){
        try(PreparedStatement stmt = con.prepareStatement(
    "SELECT "+
    "pdto.nombre AS nombre_producto, " +
    "pdto.costo_compra AS costo_Compra, " +
    "pdto.costo_venta AS costo_Venta, " +
    "pdto.tipo AS tipo_producto, " + 
    "pdto.url_imagen AS img_producto, " +
    "pdto.descripcion AS descripcion_producto, " +
    "dv.cantidad AS cantidad_vendida, " +
    "um.nombre_unidad AS unidad_medida "+
    "FROM DETALLE_VENTA AS dv " +
    "INNER JOIN INVENTARIO AS inv ON dv.id_inventario = inv.id_inventario " +
    "INNER JOIN PRODUCTO AS pdto ON inv.id_producto = pdto.id_producto " +
    "INNER JOIN UNIDAD_MEDIDA AS um ON pdto.id_unidad_medida = um.id_unidad_medida " +
    "WHERE dv.id_venta = ? "
        )){
       stmt.setInt(1, id_venta);
      try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    float costoC = rs.getFloat("costo_Compra");
                    float costoV = rs.getFloat("costo_Venta");
                    String tipo = rs.getString("tipo_producto");
                    String img = rs.getString("img_producto");
                    int cantidadVend = rs.getInt("cantidad_vendida");
                    String unidadMedida = rs.getString("unidad_medida");
                    String des = rs.getString("descripcion_producto");
                    float cantidadTotal = cantidadVend*costoV;
                    String UM;
                    if(cantidadVend == 1){
                     UM = unidadMedida;}
                    else{UM = unidadMedida+"s";}

                    MostrarProducto producto = new MostrarProducto(nombre, costoC, cantidadTotal, tipo, img, cantidadVend, UM, des);
                    listaproductos.add(producto);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
            }
    return listaproductos;
    }
    
    
}
