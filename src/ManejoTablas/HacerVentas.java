/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

/**
 *
 * @author salin
 */
import Conexion.Conexion;
import Modelo.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class HacerVentas {
    
     public int insertarPago(double monto, Date fecha, String estado, int idMetodoPago) throws SQLException {
        int idPagoGenerado = -1;
        Connection con = Conexion.getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (con != null) {
            try {
                String sql = "INSERT INTO PAGO (monto, fecha, estado, id_metodo_pago) VALUES (?, ?, ?, ?)";
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setDouble(1, monto);
                stmt.setDate(2, fecha);
                stmt.setString(3, estado);
                stmt.setInt(4, idMetodoPago);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        idPagoGenerado = rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return idPagoGenerado; // Retorna el ID del pago insertado, -1 si hubo un error
    }
     
     public int insertarVenta(double montoTotal, Date fechaVenta, int idPago, int idCliente) throws SQLException {
        int idVentaGenerada = -1;
        Connection con = Conexion.getConexion(); 
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (con != null) {
            try {
                String sql = "INSERT INTO VENTA (monto_total, fecha_venta, id_pago, id_cliente) VALUES (?, ?, ?, ?)";
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setDouble(1, montoTotal);
                stmt.setDate(2, fechaVenta);
                stmt.setInt(3,idPago );
                stmt.setInt(4, idCliente);

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
     
      public boolean insertarDetalleVenta(int idInventario, int idVenta, int cantidad) throws SQLException {
        Connection con = Conexion.getConexion();
        PreparedStatement stmt = null;
        boolean insercionExitosa = false;

        if (con != null) {
            try {
                String sql = "INSERT INTO DETALLE_VENTA (id_inventario, id_venta, cantidad) VALUES (?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, idInventario);
                stmt.setInt(2, idVenta);
                stmt.setInt(3, cantidad);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    insercionExitosa = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return insercionExitosa;
    }
}

