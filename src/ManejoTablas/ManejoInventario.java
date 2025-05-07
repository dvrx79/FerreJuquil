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

public class ManejoInventario {
    
    public List<MostrarInventario> obtenerInventario() throws SQLException {
    List<MostrarInventario> listaInventario = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " 
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                    MostrarInventario Inventario = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                    listaInventario.add(Inventario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaInventario;
}
    
   public List<MostrarInventario> obtenerPorTipo(String tipoProd) throws SQLException
   {
       List<MostrarInventario> listaInventario = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE p.tipo = ?"
                )) {
                stmt.setString(1, tipoProd);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                    MostrarInventario Inventario = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                    listaInventario.add(Inventario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaInventario;
   }
   
   
    public MostrarInventario obtenerPorIdProducto(int idProd) throws SQLException
   {
       MostrarInventario Inventario = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE p.id_producto = ?"
                )) {
                stmt.setInt(1, idProd);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                     Inventario = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return Inventario;
   }
    
     public int obtenerIDInventarioPorIdProducto(int idProd) throws SQLException
   {
       int IDInventario = 0;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "i.id_inventario AS id_Inv " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE p.id_producto = ?"
                )) {
                stmt.setInt(1, idProd);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){IDInventario = rs.getInt("id_Inv");}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return IDInventario;
   }
    
    
   public List<MostrarInventario> obtenerMenor(int ctd) throws SQLException
   {
    List<MostrarInventario> listaInventario = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE i.cantidad <= ?"
                )) {
            stmt.setInt(1, ctd);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                    MostrarInventario Inventario = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                    listaInventario.add(Inventario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaInventario;
   }
   
   public List<MostrarInventario> obtenerMayor(int ctd) throws SQLException
   {
    List<MostrarInventario> listaInventario = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE i.cantidad >= ?"
                )) {
            stmt.setInt(1, ctd);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                    MostrarInventario Inventario = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                    listaInventario.add(Inventario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaInventario;
   }
   
    public MostrarInventario obtenerPorCodigo(String cod) throws SQLException
   {
    MostrarInventario mi = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre AS nombre_producto, " +
                "p.tipo AS tipo_producto, " +
                "p.descripcion AS descripcion_producto, " +
                "p.codigo_barras AS codigo_producto, " +
                "p.costo_compra AS coscom_producto, " +
                "p.costo_venta AS cosven_producto, " +
                "i.cantidad AS cantidad_producto " +
                "FROM INVENTARIO AS i " +
                "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                "WHERE p.codigo_barras = ?"
                )) {
            stmt.setString(1, cod);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_producto");
                    String tipo = rs.getString("tipo_producto");
                    String descripcion = rs.getString("descripcion_producto");
                    int cantidad = rs.getInt("cantidad_producto");
                    String codigo = rs.getString("codigo_producto");
                    float cosCompra = rs.getFloat("coscom_producto");
                    float cosVenta = rs.getFloat("cosven_producto");

                    mi = new MostrarInventario(nombre, tipo, descripcion, cantidad, codigo, cosCompra, cosVenta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return mi;
   }
    
    
    public boolean actualizarCantidadPorCodigo(String cod, int nuevaCantidad) throws SQLException {
    boolean actualizado = false;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE INVENTARIO " +
                "SET cantidad = ? " +
                "WHERE id_producto = (SELECT id_producto FROM PRODUCTO WHERE codigo_barras = ?)"
        )) {
            stmt.setInt(1, nuevaCantidad);
            stmt.setString(2, cod);

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return actualizado;
}

       
    public boolean actualizarCostoCompraPorCodigo(String cod, float nuevaCantidad, float costoventa) throws SQLException {
    boolean actualizado = false;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE PRODUCTO " +
                "SET costo_compra = ?, " +
                "costo_venta = ? " +
                "WHERE codigo_barras = ?"
        )) {
            stmt.setFloat(1, nuevaCantidad);
            stmt.setFloat(2, costoventa);
            stmt.setString(3, cod);

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return actualizado;
}
    
    
       public boolean actualizarCantidadPorIdProd(int idProd, int cantidadNueva) throws SQLException {
    boolean actualizado = false;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE INVENTARIO " +
                "SET cantidad = ? " +
                "WHERE id_producto = ?"
        )) {
            stmt.setInt(1, cantidadNueva);
            stmt.setInt(2, idProd);
            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return actualizado;
}
       public boolean eliminarProductoPorCodigo(String codigoBarras) throws SQLException {
         boolean eliminado = false;
    Connection con = null;
    
    try {
        con = Conexion.getConexion();
        if (con != null) {
            con.setAutoCommit(false);
            
            // 1. Obtener ID de inventario para el producto
            int idInventario = 0;
            try (PreparedStatement stmtGetId = con.prepareStatement(
                    "SELECT i.id_inventario FROM INVENTARIO i " +
                    "JOIN PRODUCTO p ON i.id_producto = p.id_producto " +
                    "WHERE p.codigo_barras = ?")) {
                
                stmtGetId.setString(1, codigoBarras);
                ResultSet rs = stmtGetId.executeQuery();
                if (rs.next()) {
                    idInventario = rs.getInt("id_inventario");
                }
            }
            
            // 2. Si existe en inventario, eliminar relaciones primero
            if (idInventario > 0) {
                // Eliminar de details_venta si la tabla existe
                try (PreparedStatement stmtDetails = con.prepareStatement(
                        "DELETE FROM details_venta WHERE id_inventario = ?")) {
                    
                    stmtDetails.setInt(1, idInventario);
                    stmtDetails.executeUpdate();
                } catch (SQLException e) {
                    // Ignorar si la tabla no existe (según tu error anterior)
                    if (!e.getMessage().contains("doesn't exist")) {
                        throw e;
                    }
                }
                
                // Eliminar de INVENTARIO
                try (PreparedStatement stmtInventario = con.prepareStatement(
                        "DELETE FROM INVENTARIO WHERE id_inventario = ?")) {
                    
                    stmtInventario.setInt(1, idInventario);
                    stmtInventario.executeUpdate();
                }
            }
            
            // 3. Finalmente eliminar de PRODUCTO
            try (PreparedStatement stmtProducto = con.prepareStatement(
                    "DELETE FROM PRODUCTO WHERE codigo_barras = ?")) {
                
                stmtProducto.setString(1, codigoBarras);
                int filasAfectadas = stmtProducto.executeUpdate();
                eliminado = filasAfectadas > 0;
            }
            
            con.commit();
        }
    } catch (SQLException e) {
        if (con != null) {
            con.rollback();
        }
        throw e;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    return eliminado;
       
}
       public boolean eliminarSoloProductoPorCodigo(String codigoBarras) throws SQLException {
    boolean eliminado = false;
    Connection con = null;
    
    try {
        con = Conexion.getConexion();
        if (con != null) {
            // Desactivar temporalmente las restricciones de clave foránea
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            }
            
            // Eliminar solo de PRODUCTO
            try (PreparedStatement stmtProducto = con.prepareStatement(
                    "DELETE FROM PRODUCTO WHERE codigo_barras = ?")) {
                
                stmtProducto.setString(1, codigoBarras);
                int filasAfectadas = stmtProducto.executeUpdate();
                eliminado = filasAfectadas > 0;
            }
            
            // Reactivar las restricciones de clave foránea
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            }
        }
    } catch (SQLException e) {
        if (con != null) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS=1"); // Asegurar que se reactiven
            }
        }
        throw e;
    } finally {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    return eliminado;
}       
}
