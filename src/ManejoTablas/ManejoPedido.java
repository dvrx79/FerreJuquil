package ManejoTablas;

import Conexion.Conexion;
import Modelo.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManejoPedido {
    
    public List<MostrarPedido> obtenerPedidos() throws SQLException {
        List<MostrarPedido> listaPedidos = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                    "ped.estado_envio AS Estado_envio, " +
                    "ped.estado_pago AS Estado_pago, " +
                    "ped.fecha AS Fecha_pedido, " +
                    "ven.monto_total AS monto_pedido, " +
                    "e.nombre AS nombre_estado, " +
                    "m.nombre AS nombre_municipio, " +
                    "cp.codigo AS codigo_postal, " +
                    "d.calle AS calle_dir, " +
                    "d.numero AS num_dir, " +
                    "ped.id_venta AS venta, " +
                    "ped.id_pedido AS ID_pedido, " +
                    "cli.nombre_cliente AS Nombre_cliente, " +
                    "cli.apellido_paterno AS Ap_Paterno, " +
                    "cli.numero AS numero_cliente, " +
                    "ped.nombre_recibe AS nombre_recibe, " +
                    "ped.fecha_entrega AS fecha_entrega " +
                    "FROM PEDIDO AS ped " +
                    "INNER JOIN VENTA AS ven ON ped.id_venta = ven.id_venta "+
                    "INNER JOIN CLIENTE AS cli ON ven.id_cliente = cli.id_cliente " +
                    "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                    "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                    "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                    "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                    "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estEnvio = rs.getString("Estado_envio");
                        String estPago = rs.getString("Estado_pago");
                        Date fecha = rs.getDate("Fecha_pedido");
                        Double monto = rs.getDouble("monto_pedido");
                        String estado = rs.getString("nombre_estado");
                        String municipio = rs.getString("nombre_municipio");
                        String codPos = rs.getString("codigo_postal");
                        String calle = rs.getString("calle_dir");
                        String numero = rs.getString("num_dir");
                        String nombre = rs.getString("Nombre_cliente");
                        String apellido = rs.getString("Ap_paterno");
                        String numeroTelefono = rs.getString("numero_cliente");
                        int idVenta = rs.getInt("venta");
                        int idPedido = rs.getInt("ID_pedido");
                        String nombreRecibe = rs.getString("nombre_recibe");
                        Date fechaEntrega = rs.getDate("fecha_entrega");
                        
                        String nombreCliente = nombre+" "+apellido;
                        String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                        MostrarPedido pedido = new MostrarPedido(estEnvio, estPago, fecha, monto, direccion, 
                                nombreCliente, numeroTelefono, idVenta, idPedido, nombreRecibe, fechaEntrega);
                        listaPedidos.add(pedido);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return listaPedidos;
    }
    
    public List<MostrarPedido> obtenerPedidosPorEstadoEntrega(String estadoEntrega) throws SQLException {
        List<MostrarPedido> listaPedidos = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                    "ped.estado_envio AS Estado_envio, " +
                    "ped.estado_pago AS Estado_pago, " +
                    "ped.fecha AS Fecha_pedido, " +
                    "ven.monto_total AS monto_pedido, " +
                    "e.nombre AS nombre_estado, " +
                    "m.nombre AS nombre_municipio, " +
                    "cp.codigo AS codigo_postal, " +
                    "d.calle AS calle_dir, " +
                    "d.numero AS num_dir, " +
                    "ped.id_venta AS venta, " +
                    "ped.id_pedido AS ID_pedido, " +
                    "cli.nombre_cliente AS Nombre_cliente, " +
                    "cli.apellido_paterno AS Ap_Paterno, " +
                    "cli.numero AS numero_cliente, " +
                    "ped.nombre_recibe AS nombre_recibe, " +
                    "ped.fecha_entrega AS fecha_entrega " +
                    "FROM PEDIDO AS ped " +
                    "INNER JOIN VENTA AS ven ON ped.id_venta = ven.id_venta "+
                    "INNER JOIN CLIENTE AS cli ON ven.id_cliente = cli.id_cliente " +
                    "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                    "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                    "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                    "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                    "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                    "WHERE ped.estado_envio = ?")) {
                
                stmt.setString(1, estadoEntrega);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estEnvio = rs.getString("Estado_envio");
                        String estPago = rs.getString("Estado_pago");
                        Date fecha = rs.getDate("Fecha_pedido");
                        Double monto = rs.getDouble("monto_pedido");
                        String estado = rs.getString("nombre_estado");
                        String municipio = rs.getString("nombre_municipio");
                        String codPos = rs.getString("codigo_postal");
                        String calle = rs.getString("calle_dir");
                        String numero = rs.getString("num_dir");
                        String nombre = rs.getString("Nombre_cliente");
                        String apellido = rs.getString("Ap_paterno");
                        String numeroTelefono = rs.getString("numero_cliente");
                        int idVenta = rs.getInt("venta");
                        int idPedido = rs.getInt("ID_pedido");
                        String nombreRecibe = rs.getString("nombre_recibe");
                        Date fechaEntrega = rs.getDate("fecha_entrega");
                        
                        String nombreCliente = nombre+" "+apellido;
                        String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                        MostrarPedido pedido = new MostrarPedido(estEnvio, estPago, fecha, monto, direccion, 
                                nombreCliente, numeroTelefono, idVenta, idPedido, nombreRecibe, fechaEntrega);
                        listaPedidos.add(pedido);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return listaPedidos;
    }
    
    public List<MostrarPedido> obtenerPedidosPorEstadoPago(String estadoPago) throws SQLException {
        List<MostrarPedido> listaPedidos = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                    "ped.estado_envio AS Estado_envio, " +
                    "ped.estado_pago AS Estado_pago, " +
                    "ped.fecha AS Fecha_pedido, " +
                    "ven.monto_total AS monto_pedido, " +
                    "e.nombre AS nombre_estado, " +
                    "m.nombre AS nombre_municipio, " +
                    "cp.codigo AS codigo_postal, " +
                    "d.calle AS calle_dir, " +
                    "d.numero AS num_dir, " +
                    "ped.id_venta AS venta, " +
                    "ped.id_pedido AS ID_pedido, " +
                    "cli.nombre_cliente AS Nombre_cliente, " +
                    "cli.apellido_paterno AS Ap_Paterno, " +
                    "cli.numero AS numero_cliente, " +
                    "ped.nombre_recibe AS nombre_recibe, " +
                    "ped.fecha_entrega AS fecha_entrega " +
                    "FROM PEDIDO AS ped " +
                    "INNER JOIN VENTA AS ven ON ped.id_venta = ven.id_venta "+
                    "INNER JOIN CLIENTE AS cli ON ven.id_cliente = cli.id_cliente " +
                    "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                    "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                    "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                    "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                    "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                    "WHERE ped.estado_pago = ?")) {
                
                stmt.setString(1, estadoPago);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estEnvio = rs.getString("Estado_envio");
                        String estPago = rs.getString("Estado_pago");
                        Date fecha = rs.getDate("Fecha_pedido");
                        Double monto = rs.getDouble("monto_pedido");
                        String estado = rs.getString("nombre_estado");
                        String municipio = rs.getString("nombre_municipio");
                        String codPos = rs.getString("codigo_postal");
                        String calle = rs.getString("calle_dir");
                        String numero = rs.getString("num_dir");
                        String nombre = rs.getString("Nombre_cliente");
                        String apellido = rs.getString("Ap_paterno");
                        String numeroTelefono = rs.getString("numero_cliente");
                        int idVenta = rs.getInt("venta");
                        int idPedido = rs.getInt("ID_pedido");
                        String nombreRecibe = rs.getString("nombre_recibe");
                        Date fechaEntrega = rs.getDate("fecha_entrega");
                        
                        String nombreCliente = nombre+" "+apellido;
                        String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                        MostrarPedido pedido = new MostrarPedido(estEnvio, estPago, fecha, monto, direccion, 
                                nombreCliente, numeroTelefono, idVenta, idPedido, nombreRecibe, fechaEntrega);
                        listaPedidos.add(pedido);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return listaPedidos;
    }
     
    public List<MostrarPedido> obtenerPedidosPorCliente(String nC, String ApC) throws SQLException {
        List<MostrarPedido> listaPedidos = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                    "ped.estado_envio AS Estado_envio, " +
                    "ped.estado_pago AS Estado_pago, " +
                    "ped.fecha AS Fecha_pedido, " +
                    "ven.monto_total AS monto_pedido, " +
                    "e.nombre AS nombre_estado, " +
                    "m.nombre AS nombre_municipio, " +
                    "cp.codigo AS codigo_postal, " +
                    "d.calle AS calle_dir, " +
                    "d.numero AS num_dir, " +
                    "ped.id_venta AS venta, " +
                    "ped.id_pedido AS ID_pedido, " +
                    "cli.nombre_cliente AS Nombre_cliente, " +
                    "cli.apellido_paterno AS Ap_Paterno, " +
                    "cli.numero AS numero_cliente, " +
                    "ped.nombre_recibe AS nombre_recibe, " +
                    "ped.fecha_entrega AS fecha_entrega " +
                    "FROM PEDIDO AS ped " +
                    "INNER JOIN VENTA AS ven ON ped.id_venta = ven.id_venta "+
                    "INNER JOIN CLIENTE AS cli ON ven.id_cliente = cli.id_cliente " +
                    "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                    "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                    "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                    "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                    "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                    "WHERE cli.nombre_cliente = ? AND cli.apellido_paterno = ?")) {
                
                stmt.setString(1, nC);
                stmt.setString(2, ApC);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estEnvio = rs.getString("Estado_envio");
                        String estPago = rs.getString("Estado_pago");
                        Date fecha = rs.getDate("Fecha_pedido");
                        Double monto = rs.getDouble("monto_pedido");
                        String estado = rs.getString("nombre_estado");
                        String municipio = rs.getString("nombre_municipio");
                        String codPos = rs.getString("codigo_postal");
                        String calle = rs.getString("calle_dir");
                        String numero = rs.getString("num_dir");
                        String nombre = rs.getString("Nombre_cliente");
                        String apellido = rs.getString("Ap_paterno");
                        String numeroTelefono = rs.getString("numero_cliente");
                        int idVenta = rs.getInt("venta");
                        int idPedido = rs.getInt("ID_pedido");
                        String nombreRecibe = rs.getString("nombre_recibe");
                        Date fechaEntrega = rs.getDate("fecha_entrega");
                        
                        String nombreCliente = nombre+" "+apellido;
                        String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                        MostrarPedido pedido = new MostrarPedido(estEnvio, estPago, fecha, monto, direccion, 
                                nombreCliente, numeroTelefono, idVenta, idPedido, nombreRecibe, fechaEntrega);
                        listaPedidos.add(pedido);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return listaPedidos;
    }
     
    public List<MostrarPedido> obtenerPedidosPorFecha(Date fechaP) throws SQLException {
        List<MostrarPedido> listaPedidos = new ArrayList<>();
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT " +
                    "ped.estado_envio AS Estado_envio, " +
                    "ped.estado_pago AS Estado_pago, " +
                    "ped.fecha AS Fecha_pedido, " +
                    "ven.monto_total AS monto_pedido, " +
                    "e.nombre AS nombre_estado, " +
                    "m.nombre AS nombre_municipio, " +
                    "cp.codigo AS codigo_postal, " +
                    "d.calle AS calle_dir, " +
                    "d.numero AS num_dir, " +
                    "ped.id_venta AS venta, " +
                    "ped.id_pedido AS ID_pedido, " +
                    "cli.nombre_cliente AS Nombre_cliente, " +
                    "cli.apellido_paterno AS Ap_Paterno, " +
                    "cli.numero AS numero_cliente, " +
                    "ped.nombre_recibe AS nombre_recibe, " +
                    "ped.fecha_entrega AS fecha_entrega " +
                    "FROM PEDIDO AS ped " +
                    "INNER JOIN VENTA AS ven ON ped.id_venta = ven.id_venta "+
                    "INNER JOIN CLIENTE AS cli ON ven.id_cliente = cli.id_cliente " +
                    "INNER JOIN DIRECCION AS d ON cli.id_direccion = d.id_direccion " +
                    "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                    "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                    "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                    "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " +
                    "WHERE ped.fecha = ?")) {
                
                stmt.setDate(1, fechaP);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String estEnvio = rs.getString("Estado_envio");
                        String estPago = rs.getString("Estado_pago");
                        Date fecha = rs.getDate("Fecha_pedido");
                        Double monto = rs.getDouble("monto_pedido");
                        String estado = rs.getString("nombre_estado");
                        String municipio = rs.getString("nombre_municipio");
                        String codPos = rs.getString("codigo_postal");
                        String calle = rs.getString("calle_dir");
                        String numero = rs.getString("num_dir");
                        String nombre = rs.getString("Nombre_cliente");
                        String apellido = rs.getString("Ap_paterno");
                        String numeroTelefono = rs.getString("numero_cliente");
                        int idVenta = rs.getInt("venta");
                        int idPedido = rs.getInt("ID_pedido");
                        String nombreRecibe = rs.getString("nombre_recibe");
                        Date fechaEntrega = rs.getDate("fecha_entrega");
                        
                        String nombreCliente = nombre+" "+apellido;
                        String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                        MostrarPedido pedido = new MostrarPedido(estEnvio, estPago, fecha, monto, direccion, 
                                nombreCliente, numeroTelefono, idVenta, idPedido, nombreRecibe, fechaEntrega);
                        listaPedidos.add(pedido);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return listaPedidos;
    }
     
    public boolean insertarPedido(int cantidad, Double monto, String estadoPago, String estadoEnvio, 
                                Date fecha, int idVenta, String nombreRecibe, Date fechaEntrega) throws SQLException {
        boolean inserccionExitosa = false;
        Connection con = Conexion.getConexion(); 
        PreparedStatement stmt = null;

        if (con != null) {
            try {
                String sql = "INSERT INTO PEDIDO (cantidad, monto_total, estado_pago, estado_envio, " +
                            "fecha, id_venta, nombre_recibe, fecha_entrega) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, cantidad);
                stmt.setDouble(2, monto);
                stmt.setString(3, estadoPago);
                stmt.setString(4, estadoEnvio);
                stmt.setDate(5, fecha);
                stmt.setInt(6, idVenta);
                stmt.setString(7, nombreRecibe);
                stmt.setDate(8, fechaEntrega);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    inserccionExitosa = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
        return inserccionExitosa;
    }
     
    public boolean actualizarEstadoPago(int IdPed, String estadoPago) throws SQLException {
        boolean actualizado = false;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE PEDIDO " +
                    "SET estado_pago = ? " +
                    "WHERE id_pedido = ?")) {
                
                stmt.setString(1, estadoPago);
                stmt.setInt(2, IdPed);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    actualizado = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return actualizado;
    }
     
    public boolean actualizarEstadoEntrega(int IdPed, String estEntrega) throws SQLException {
        boolean actualizado = false;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE PEDIDO " +
                    "SET estado_envio = ? " +
                    "WHERE id_pedido = ?")) {
                
                stmt.setString(1, estEntrega);
                stmt.setInt(2, IdPed);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    actualizado = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return actualizado;
    }
     
    public boolean cancelarPedido(int IdPed, String estEntrega) throws SQLException {
        boolean actualizado = false;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE PEDIDO " +
                    "SET estado_envio = ? " +
                    "WHERE id_pedido = ?")) {
                
                stmt.setString(1, estEntrega);
                stmt.setInt(2, IdPed);
                
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    actualizado = true;
                }
            } 
            catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return actualizado;
    }
        public  boolean regresarStock(int idProducto, int stock) throws SQLException{
            boolean regreso = false;
            Connection con = Conexion.getConexion();
            
            if(con != null){
                try(PreparedStatement stmt = con.prepareStatement(
                    "UDATE INVENTARIO " +
                    "SET cantidad = ? " +
                    "WHERE id_producto = ?")){
                    
                stmt.setInt(1, stock);
                stmt.setInt(2, idProducto);
                
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0){
                    regreso = true;
                }
            }catch(SQLException e){
                throw e;
            }    
        }
            return regreso;
    }
     
    public boolean actualizarEntregaPedido(int IdPed, String nombreRecibe, Date fechaEntrega) throws SQLException {
        boolean actualizado = false;
        Connection con = Conexion.getConexion();

        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE PEDIDO " +
                    "SET nombre_recibe = ?, fecha_entrega = ? " +
                    "WHERE id_pedido = ?")) {
                
                stmt.setString(1, nombreRecibe);
                stmt.setDate(2, fechaEntrega);
                stmt.setInt(3, IdPed);

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    actualizado = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return actualizado;
    }
}
