package ManejoTablas;

import Conexion.Conexion;
import Modelo.MostrarInventario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejoInventario {
    
    public List<MostrarInventario> obtenerInventario() throws SQLException {
        List<MostrarInventario> listaInventario = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "ORDER BY i.ultima_actualizacion ASC")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        listaInventario.add(crearMostrarInventarioDesdeResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return listaInventario;
    }
    
    public List<MostrarInventario> obtenerPorTipo(String tipoProd) throws SQLException {
        List<MostrarInventario> listaInventario = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE p.tipo = ? " +
                    "ORDER BY i.ultima_actualizacion ASC")) {
                
                stmt.setString(1, tipoProd);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        listaInventario.add(crearMostrarInventarioDesdeResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return listaInventario;
    }
   
    public MostrarInventario obtenerPorIdProducto(int idProd) throws SQLException {
        MostrarInventario inventario = null;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE p.id_producto = ?")) {
                
                stmt.setInt(1, idProd);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        inventario = crearMostrarInventarioDesdeResultSet(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return inventario;
    }
    
    public int obtenerIDInventarioPorIdProducto(int idProd) throws SQLException {
        int IDInventario = 0;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT i.id_inventario AS id_Inv " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE p.id_producto = ?")) {
                
                stmt.setInt(1, idProd);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        IDInventario = rs.getInt("id_Inv");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return IDInventario;
    }
    
    public List<MostrarInventario> obtenerMenor(int ctd) throws SQLException {
        List<MostrarInventario> listaInventario = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE i.cantidad <= ? " +
                    "ORDER BY i.ultima_actualizacion ASC")) {
                
                stmt.setInt(1, ctd);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        listaInventario.add(crearMostrarInventarioDesdeResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return listaInventario;
    }
   
    public List<MostrarInventario> obtenerMayor(int ctd) throws SQLException {
        List<MostrarInventario> listaInventario = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE i.cantidad >= ? " +
                    "ORDER BY i.ultima_actualizacion ASC")) {
                
                stmt.setInt(1, ctd);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        listaInventario.add(crearMostrarInventarioDesdeResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return listaInventario;
    }
   
    public MostrarInventario obtenerPorCodigo(String cod) throws SQLException {
        MostrarInventario inventario = null;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE p.codigo_barras = ?")) {
                
                stmt.setString(1, cod);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        inventario = crearMostrarInventarioDesdeResultSet(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return inventario;
    }
    
    public boolean actualizarCantidadPorCodigo(String cod, int nuevaCantidad) throws SQLException {
        boolean actualizado = false;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE INVENTARIO " +
                    "SET cantidad = ?, " +
                    "ultima_actualizacion = CURRENT_DATE() " +
                    "WHERE id_producto = (SELECT id_producto FROM PRODUCTO WHERE codigo_barras = ?)")) {
                
                stmt.setInt(1, nuevaCantidad);
                stmt.setString(2, cod);

                actualizado = stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
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
                    "WHERE codigo_barras = ?")) {
                
                stmt.setFloat(1, nuevaCantidad);
                stmt.setFloat(2, costoventa);
                stmt.setString(3, cod);

                actualizado = stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
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
                    "SET cantidad = ?, " +
                    "ultima_actualizacion = CURRENT_DATE() " +
                    "WHERE id_producto = ?")) {
                
                stmt.setInt(1, cantidadNueva);
                stmt.setInt(2, idProd);
                
                actualizado = stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
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
                    // Eliminar de DETALLE_VENTA
                    try (PreparedStatement stmtDetails = con.prepareStatement(
                            "DELETE FROM DETALLE_VENTA WHERE id_inventario = ?")) {
                        
                        stmtDetails.setInt(1, idInventario);
                        stmtDetails.executeUpdate();
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
                    eliminado = stmtProducto.executeUpdate() > 0;
                }
                
                con.commit();
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
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
                    eliminado = stmtProducto.executeUpdate() > 0;
                }
                
                // Reactivar las restricciones de clave foránea
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                }
            }
        } catch (SQLException e) {
            if (con != null) {
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("SET FOREIGN_KEY_CHECKS=1");
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
    
    // Métodos adicionales para FEFO
    
    public List<MostrarInventario> obtenerInventarioFEFO() throws SQLException {
        List<MostrarInventario> listaInventario = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT p.nombre AS nombre_producto, " +
                    "p.tipo AS tipo_producto, " +
                    "p.descripcion AS descripcion_producto, " +
                    "p.codigo_barras AS codigo_producto, " +
                    "p.costo_compra AS coscom_producto, " +
                    "p.costo_venta AS cosven_producto, " +
                    "i.cantidad AS cantidad_producto, " +
                    "i.ultima_actualizacion AS fecha_actualizacion " +
                    "FROM INVENTARIO AS i " +
                    "INNER JOIN PRODUCTO AS p ON i.id_producto = p.id_producto " +
                    "WHERE i.cantidad > 0 " +
                    "ORDER BY i.ultima_actualizacion ASC")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        listaInventario.add(crearMostrarInventarioDesdeResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (con != null) con.close();
            }
        }
        return listaInventario;
    }
    
    public boolean reducirInventarioFEFO(int idProducto, int cantidadAReducir) throws SQLException {
        Connection con = null;
        boolean exito = false;
        
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            
            // 1. Obtener los lotes ordenados por FEFO
            List<LoteInventario> lotes = obtenerLotesPorProducto(idProducto, con);
            
            int cantidadRestante = cantidadAReducir;
            
            // 2. Procesar cada lote hasta satisfacer la cantidad requerida
            for (LoteInventario lote : lotes) {
                if (cantidadRestante <= 0) break;
                
                int cantidadEnLote = lote.getCantidad();
                int cantidadARetirar = Math.min(cantidadEnLote, cantidadRestante);
                
                // 3. Actualizar el lote
                actualizarCantidadLote(lote.getIdInventario(), cantidadEnLote - cantidadARetirar, con);
                
                cantidadRestante -= cantidadARetirar;
            }
            
            if (cantidadRestante == 0) {
                con.commit();
                exito = true;
            } else {
                con.rollback();
                throw new SQLException("No hay suficiente inventario para el producto ID: " + idProducto);
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
        
        return exito;
    }
    
    // Métodos auxiliares privados
    
    private MostrarInventario crearMostrarInventarioDesdeResultSet(ResultSet rs) throws SQLException {
        return new MostrarInventario(
            rs.getString("nombre_producto"),
            rs.getString("tipo_producto"),
            rs.getString("descripcion_producto"),
            rs.getInt("cantidad_producto"),
            rs.getString("codigo_producto"),
            rs.getFloat("coscom_producto"),
            rs.getFloat("cosven_producto"),
            rs.getDate("fecha_actualizacion")
        );
    }
    
    private List<LoteInventario> obtenerLotesPorProducto(int idProducto, Connection con) throws SQLException {
        List<LoteInventario> lotes = new ArrayList<>();
        
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT id_inventario, cantidad, ultima_actualizacion " +
                "FROM INVENTARIO " +
                "WHERE id_producto = ? AND cantidad > 0 " +
                "ORDER BY ultima_actualizacion ASC")) {
            
            stmt.setInt(1, idProducto);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lotes.add(new LoteInventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("cantidad"),
                        rs.getDate("ultima_actualizacion")
                    ));
                }
            }
        }
        
        return lotes;
    }
    
    private boolean actualizarCantidadLote(int idInventario, int nuevaCantidad, Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE INVENTARIO " +
                "SET cantidad = ?, " +
                "ultima_actualizacion = CASE WHEN ? = 0 THEN CURRENT_DATE() ELSE ultima_actualizacion END " +
                "WHERE id_inventario = ?")) {
            
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, nuevaCantidad);
            stmt.setInt(3, idInventario);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Clase interna para manejar lotes de inventario
    private static class LoteInventario {
        private final int idInventario;
        private final int cantidad;
        private final Date ultimaActualizacion;
        
        public LoteInventario(int idInventario, int cantidad, Date ultimaActualizacion) {
            this.idInventario = idInventario;
            this.cantidad = cantidad;
            this.ultimaActualizacion = ultimaActualizacion;
        }
        
        public int getIdInventario() { return idInventario; }
        public int getCantidad() { return cantidad; }
        public Date getUltimaActualizacion() { return ultimaActualizacion; }
    }
}