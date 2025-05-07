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

public class ManejoProveedores {
   public List<MostrarProveedor> obtenerProveedores() throws SQLException{
    List<MostrarProveedor> listaProveedores = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre_proveedor AS nombre_proveedor, " +
                "p.numero_telefono AS telefono_proveedor, " +
                "p.correo AS correo_proveedor, " +
                "p.estado_proveedor AS estado_proveedor, " +  // Cambiado a estado_proveedor
                "e.nombre AS nombre_estado_geo, " +  // Renombrado para evitar confusión
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM PROVEEDOR AS p " +
                "INNER JOIN DIRECCION AS d ON p.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado"  
                )) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_proveedor");
                    String telefono = rs.getString("telefono_proveedor");
                    String correo = rs.getString("correo_proveedor");
                    String estadoProv = rs.getString("estado_proveedor"); // Estado del proveedor
                    String estadoGeo = rs.getString("nombre_estado_geo"); // Estado geográfico
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estadoGeo;

                    MostrarProveedor proveedor = new MostrarProveedor(nombre, telefono, correo, direccion, estadoProv);
                    listaProveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaProveedores;
}
    
    
    public List<MostrarProveedor> obtenerProveedoresPorEstado(String est) throws SQLException{
         List<MostrarProveedor> listaProveedores = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre_proveedor AS nombre_proveedor, " +
                "p.numero_telefono AS telefono_proveedor, " +
                "p.correo AS correo_proveedor, " +
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM PROVEEDOR AS p " +
                "INNER JOIN DIRECCION AS d ON p.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " + 
                "WHERE e.nombre = ? "  
                )) {
                stmt.setString(1, est);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_proveedor");
                    String telefono = rs.getString("telefono_proveedor");
                    String correo = rs.getString("correo_proveedor");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                    MostrarProveedor proveedor = new MostrarProveedor(nombre, telefono, correo, direccion,estado);
                    listaProveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaProveedores;
    }
    
   
    
    public List<MostrarProveedor> obtenerProveedoresSinUnEstado(String est) throws SQLException{
         List<MostrarProveedor> listaProveedores = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre_proveedor AS nombre_proveedor, " +
                "p.numero_telefono AS telefono_proveedor, " +
                "p.correo AS correo_proveedor, " +
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM PROVEEDOR AS p " +
                "INNER JOIN DIRECCION AS d ON p.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " + 
                "WHERE e.nombre != ? "  
                )) {
                stmt.setString(1, est);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_proveedor");
                    String telefono = rs.getString("telefono_proveedor");
                    String correo = rs.getString("correo_proveedor");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                    MostrarProveedor proveedor = new MostrarProveedor(nombre, telefono, correo, direccion,estado);
                    listaProveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaProveedores;
    }
    
    
     public List<MostrarProveedor> obtenerProveedoresPorTipo(String tipo) throws SQLException{
         List<MostrarProveedor> listaProveedores = new ArrayList<>();
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre_proveedor AS nombre_proveedor, " +
                "p.numero_telefono AS telefono_proveedor, " +
                "p.correo AS correo_proveedor, " +
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM COMPRA AS c " +
                "INNER JOIN PROVEEDOR AS p ON c.id_proveedor = p.id_proveedor "+
                "INNER JOIN DIRECCION AS d ON p.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " + 
                "INNER JOIN PRODUCTO AS prod ON c.id_producto = prod.id_producto "+
                "WHERE prod.tipo != ? "  
                )) {
                stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_proveedor");
                    String telefono = rs.getString("telefono_proveedor");
                    String correo = rs.getString("correo_proveedor");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                    MostrarProveedor proveedor = new MostrarProveedor(nombre, telefono, correo, direccion,estado);
                    listaProveedores.add(proveedor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return listaProveedores;
    }
    
    
     public MostrarProveedor obtenerProveedoresPorNombre(String nombreProv) throws SQLException{
         MostrarProveedor proveedor = null;
    Connection con = Conexion.getConexion();

    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT " +
                "p.nombre_proveedor AS nombre_proveedor, " +
                "p.numero_telefono AS telefono_proveedor, " +
                "p.correo AS correo_proveedor, " +
                "e.nombre AS nombre_estado, " +
                "m.nombre AS nombre_municipio, " +
                "cp.codigo AS codigo_postal, " +
                "d.calle AS calle_dir, " +
                "d.numero AS num_dir " +
                "FROM COMPRA AS c " +
                "INNER JOIN PROVEEDOR AS p ON c.id_proveedor = p.id_proveedor "+
                "INNER JOIN DIRECCION AS d ON p.id_direccion = d.id_direccion " +
                "INNER JOIN CODIGO_POSTAL AS cp ON d.id_codigo_postal = cp.id_codigo_postal " +
                "INNER JOIN PUEBLO AS pueb ON d.id_pueblo = pueb.id_pueblo " +
                "INNER JOIN MUNICIPIO AS m ON pueb.id_municipio = m.id_municipio " +
                "INNER JOIN ESTADO AS e ON m.id_estado = e.id_estado " + 
                "WHERE p.nombre_proveedor = ? "  
                )) {
                stmt.setString(1, nombreProv);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre_proveedor");
                    String telefono = rs.getString("telefono_proveedor");
                    String correo = rs.getString("correo_proveedor");
                    String estado = rs.getString("nombre_estado");
                    String municipio = rs.getString("nombre_municipio");
                    String codPos = rs.getString("codigo_postal");
                    String calle = rs.getString("calle_dir");
                    String numero = rs.getString("num_dir");
                    
                    String direccion = calle+", "+numero+", C.P. "+codPos+", "+municipio+", "+estado;

                    proveedor = new MostrarProveedor(nombre, telefono, correo, direccion,estado);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return proveedor;
    }
     
     public boolean cambiarEstadoProveedor(String nombreProveedor, String nuevoEstado) throws SQLException {
    Connection con = Conexion.getConexion();
    if (con != null) {
        try (PreparedStatement stmt = con.prepareStatement(
                "UPDATE PROVEEDOR SET estado_proveedor = ? WHERE nombre_proveedor = ?")) {
            stmt.setString(1, nuevoEstado);
            stmt.setString(2, nombreProveedor);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    return false;
}
    
}

