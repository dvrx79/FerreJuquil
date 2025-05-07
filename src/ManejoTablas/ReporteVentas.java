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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class ReporteVentas { // Sugerencia de nombre de clase

    public static Map<String, Double> obtenerTotalVentasPorDiaSemana() throws SQLException {
        Map<String, Double> totalVentasPorDia = new HashMap<>();
        Connection con = Conexion.getConexion();
        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                                "DAYNAME(v.fecha_venta) AS dia_semana, " +
                                "DAYOFWEEK(v.fecha_venta) AS numero_dia, " +
                                "SUM(v.monto_total) AS total_ventas " +
                                "FROM VENTA AS v " +
                                "WHERE v.fecha_venta >= CURDATE() - INTERVAL (DAYOFWEEK(CURDATE()) - 1) DAY " +
                                "AND v.fecha_venta < CURDATE() + INTERVAL (7 - DAYOFWEEK(CURDATE()) + 1) DAY " +
                                "GROUP BY numero_dia, dia_semana " +
                                "ORDER BY numero_dia"
                                )) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String dia = rs.getString("dia_semana");
                        double totalVenta = rs.getDouble("total_ventas");
                        totalVentasPorDia.put(dia, totalVenta);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Relanzar la excepción para que la capa superior la maneje
            }
        }
        return totalVentasPorDia;
    }
    
    public static Map<String, Double> obtenerTotalVentasPorDiaMes() throws SQLException {
    Map<String, Double> totalVentasPorMes = new HashMap<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "DATE(v.fecha_venta) AS fecha, " +
                "SUM(v.monto_total) AS total_ventas " +
                "FROM VENTA AS v " +
                "WHERE MONTH(v.fecha_venta) = MONTH(CURDATE()) " +
                "AND YEAR(v.fecha_venta) = YEAR(CURDATE()) " +
                "GROUP BY DATE(v.fecha_venta) " +
                "ORDER BY DATE(v.fecha_venta)"
        )) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String fecha = rs.getString("fecha");
                    double totalVenta = rs.getDouble("total_ventas");
                    totalVentasPorMes.put(fecha, totalVenta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    return totalVentasPorMes;
}

public static Map<String, Double> obtenerTotalVentasPorAnio(int año) throws SQLException {
    Map<String, Double> totalVentasPorMes = new HashMap<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "MONTH(v.fecha_venta) AS mes, " +
                "SUM(v.monto_total) AS total_ventas " +
                "FROM VENTA AS v " +
                "WHERE YEAR(v.fecha_venta) = ? " +
                "GROUP BY MONTH(v.fecha_venta) " +
                "ORDER BY MONTH(v.fecha_venta)"
        )) {
            stmt.setInt(1, año);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int mes = rs.getInt("mes");
                    double totalVenta = rs.getDouble("total_ventas");
                    totalVentasPorMes.put(String.format("%02d", mes), totalVenta); // Mes como "01", "02", etc.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    return totalVentasPorMes;
}


public static Map<String, double[]> obtenerVentasPorTipoProducto(int anio, int mes) throws SQLException {
    // Mapa donde la clave es el tipo de producto y el valor un arreglo de dos elementos:
    // [0] -> total de unidades vendidas, [1] -> total de monto de ventas.
    Map<String, double[]> ventasPorTipo = new HashMap<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        String sql = "SELECT pdto.tipo AS tipo_producto, " +
                     "SUM(dv.cantidad) AS total_vendido, " +
                     "SUM(v.monto_total) AS total_ventas " +
                     "FROM DETALLE_VENTA dv " +
                     "INNER JOIN INVENTARIO inv ON dv.id_inventario = inv.id_inventario " +
                     "INNER JOIN PRODUCTO pdto ON inv.id_producto = pdto.id_producto " +
                     "INNER JOIN VENTA v ON dv.id_venta = v.id_venta " +
                     "WHERE YEAR(v.fecha_venta) = ? AND MONTH(v.fecha_venta) = ? " +
                     "GROUP BY pdto.tipo " +
                     "ORDER BY total_vendido DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            stmt.setInt(2, mes);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo_producto");
                    double totalVendido = rs.getDouble("total_vendido");
                    double totalVentas = rs.getDouble("total_ventas");
                    ventasPorTipo.put(tipo, new double[]{totalVendido, totalVentas});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    return ventasPorTipo;
}


public static Map<Integer, Map<String, Double>> obtenerVentasMensualesPorTipoProducto(int anio) throws SQLException {
    // Mapa por mes -> tipo producto -> total ventas
    Map<Integer, Map<String, Double>> ventasPorMes = new HashMap<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        String sql = "SELECT MONTH(v.fecha_venta) AS mes, pdto.tipo AS tipo_producto, " +
                     "SUM(v.monto_total) AS total_ventas " +
                     "FROM DETALLE_VENTA dv " +
                     "INNER JOIN INVENTARIO inv ON dv.id_inventario = inv.id_inventario " +
                     "INNER JOIN PRODUCTO pdto ON inv.id_producto = pdto.id_producto " +
                     "INNER JOIN VENTA v ON dv.id_venta = v.id_venta " +
                     "WHERE YEAR(v.fecha_venta) = ? AND (pdto.tipo = 'Construcción' OR pdto.tipo = 'Ferretería') " +
                     "GROUP BY mes, tipo_producto " +
                     "ORDER BY mes";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int mes = rs.getInt("mes");
                    String tipo = rs.getString("tipo_producto");
                    double totalVentas = rs.getDouble("total_ventas");

                    ventasPorMes.putIfAbsent(mes, new HashMap<>());
                    ventasPorMes.get(mes).put(tipo, totalVentas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    return ventasPorMes;
}


}