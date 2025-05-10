package ManejoTablas;

import Conexion.Conexion;
import java.sql.*;
import java.util.Date;

public class InsertarProducto {
    
    /**
     * Inserta un nuevo producto en la base de datos con todos sus datos relacionados
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param tipo Tipo de producto (Construcción/Ferreteria)
     * @param codigoBarras Código de barras generado
     * @param precioCompra Precio de compra unitario
     * @param precioVenta Precio de venta unitario (ya calculado)
     * @param cantidad Cantidad inicial en inventario
     * @param unidadMedida Unidad de medida (litro, kg, etc.)
     * @param fechaIngreso Fecha de ingreso al inventario
     * @param nombreProveedor Nombre del proveedor (null o vacío si no aplica)
     * @param urlImagen URL de la imagen del producto (opcional)
     * @param stockMinimo Stock minimo a usar 
     * @return true si se insertó correctamente, false en caso contrario
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public boolean insertarProductoCompleto(
            String nombre,
            String descripcion,
            String tipo,
            String codigoBarras,
            float precioCompra,
            float precioVenta,
            int cantidad,
            String unidadMedida,
            Date fechaIngreso,
            String nombreProveedor,
            String urlImagen,
            int stockMinimo) throws SQLException {
        
        Connection con = null;
        boolean exito = false;
        
        try {
            con = Conexion.getConexion();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                
                // 1. Insertar producto
                int idProducto = insertarProducto(
                        con,
                        nombre,
                        descripcion,
                        codigoBarras,
                        precioCompra,
                        precioVenta,
                        tipo,
                        unidadMedida,
                        urlImagen,
                        stockMinimo);
                
                if (idProducto > 0) {
                    // 2. Insertar en inventario
                    boolean inventarioOk = insertarInventario(
                            con,
                            idProducto,
                            cantidad,
                            fechaIngreso,
                            stockMinimo);
                    
                    // 3. Registrar compra con proveedor (si hay proveedor)
                    if (inventarioOk && nombreProveedor != null && !nombreProveedor.trim().isEmpty()) {
                        int idProveedor = obtenerOInsertarProveedor(con, nombreProveedor);
                        if (idProveedor > 0) {
                            registrarCompra(
                                    con,
                                    idProducto,
                                    idProveedor,
                                    cantidad,
                                    precioCompra,
                                    fechaIngreso);
                        }
                    }
                    
                    con.commit();
                    exito = true;
                } else {
                    con.rollback();
                }
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
        
        return exito;
    }
    
    /**
     * Obtiene el ID de un proveedor por nombre. Si no existe, lo crea.
     */
    private int obtenerOInsertarProveedor(Connection con, String nombreProveedor) throws SQLException {
        // Primero intentamos encontrar el proveedor existente
        String sqlBuscar = "SELECT id_proveedor FROM PROVEEDOR WHERE nombre_proveedor = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlBuscar)) {
            stmt.setString(1, nombreProveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_proveedor");
                }
            }
        }
        
        // Si no existe, lo insertamos
        String sqlInsertar = "INSERT INTO PROVEEDOR (nombre) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombreProveedor);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    /**
     * Inserta el producto en la tabla PRODUCTO
     */
    private int insertarProducto(
            Connection con,
            String nombre,
            String descripcion,
            String codigoBarras,
            float costoCompra,
            float costoVenta,
            String tipo,
            String unidadMedida,
            String urlImagen,
            int stockMinimo) throws SQLException {
        
        // Obtener ID de la unidad de medida
        int idUnidadMedida = obtenerIdUnidadMedida(con, unidadMedida);
        if (idUnidadMedida == 0) {
            throw new SQLException("Unidad de medida no encontrada: " + unidadMedida);
        }
        
        String sql = "INSERT INTO PRODUCTO ("
                + "nombre, descripcion, codigo_barras, costo_compra, costo_venta, "
                + "tipo, id_unidad_medida, url_imagen, stock_minimo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setString(3, codigoBarras);
            stmt.setFloat(4, costoCompra);
            stmt.setFloat(5, costoVenta);
            stmt.setString(6, tipo);
            stmt.setInt(7, idUnidadMedida);
            stmt.setString(8, urlImagen);
            stmt.setInt(9, stockMinimo);
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    /**
     * Obtiene el ID de una unidad de medida
     */
    private int obtenerIdUnidadMedida(Connection con, String unidadMedida) throws SQLException {
        String sql = "SELECT id_unidad_medida FROM UNIDAD_MEDIDA WHERE nombre_unidad = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, unidadMedida);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_unidad_medida");
                }
            }
        }
        return 0;
    }
    
    /**
     * Inserta el registro en la tabla INVENTARIO
     */
    private boolean insertarInventario(
            Connection con,
            int idProducto,
            int cantidad,
            Date fechaIngreso,
            int stockMinimo) throws SQLException {
        
        String sql = "INSERT INTO INVENTARIO (id_producto, cantidad, ultima_actualizacion, stock_minimo) "
                + "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, cantidad);
            stmt.setDate(3, new java.sql.Date(fechaIngreso.getTime()));
            stmt.setInt(4, stockMinimo);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Registra la compra en la tabla COMPRA
     */
    private void registrarCompra(
            Connection con,
            int idProducto,
            int idProveedor,
            int cantidad,
            float precioUnitario,
            Date fechaCompra) throws SQLException {
        
        float montoTotal = (precioUnitario * cantidad);
        
        String sql = "INSERT INTO COMPRA ("
                + "monto_total, cantidad, fecha_compra, estado, id_producto, id_proveedor) "
                + "VALUES (?, ?, ?, 'recibido', ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setFloat(1, montoTotal);
            stmt.setInt(2, cantidad);
            stmt.setDate(3, new java.sql.Date(fechaCompra.getTime()));
            stmt.setInt(4, idProducto);
            stmt.setInt(5, idProveedor);
            
            stmt.executeUpdate();
        }
    }
    
    /**
     * Verifica si un código de barras ya existe
     */
    public boolean codigoBarrasExiste(String codigoBarras) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM PRODUCTO WHERE codigo_barras = ?";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, codigoBarras);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }
}