package Controlador;

import Conexion.Conexion;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class InsertarProveedor {
    
    /**
     * Inserta un nuevo proveedor en la base de datos con todos sus datos relacionados
     * @param nombre Nombre del proveedor
     * @param telefono Número de teléfono
     * @param correo Correo electrónico
     * @param estado Estado del proveedor
     * @param calle Calle de la dirección
     * @param numero Número de la dirección
     * @param pueblo Nombre del pueblo
     * @param municipio Nombre del municipio
     * @param estadoUbicacion Nombre del estado
     * @param codigoPostal Código postal
     * @return true si se insertó correctamente, false en caso contrario
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public boolean insertarProveedorCompleto(
            String nombre,
            String telefono,
            String correo,
            String estado,
            String calle,
            String numero,
            String pueblo,
            String municipio,
            String estadoUbicacion,
            String codigoPostal) throws SQLException {
        
        Connection con = null;
        boolean exito = false;
        
        try {
            con = Conexion.getConexion();
            if (con != null) {
                con.setAutoCommit(false); // Iniciar transacción
                
                // 1. Insertar o obtener ubicación (estado, municipio, pueblo, código postal)
                int idDireccion = insertarUbicacion(
                        con,
                        calle,
                        numero,
                        pueblo,
                        municipio,
                        estadoUbicacion,
                        codigoPostal);
                
                if (idDireccion > 0) {
                    // 2. Insertar proveedor
                    boolean proveedorOk = insertarProveedor(
                            con,
                            nombre,
                            telefono,
                            correo,
                            estado,
                            idDireccion);
                    
                    if (proveedorOk) {
                        con.commit();
                        exito = true;
                    } else {
                        con.rollback();
                    }
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
     * Inserta o obtiene todos los datos de ubicación y devuelve el ID de la dirección
     */
    private int insertarUbicacion(
            Connection con,
            String calle,
            String numero,
            String pueblo,
            String municipio,
            String estadoUbicacion,
            String codigoPostal) throws SQLException {
        
        // 1. Insertar o obtener estado
        int idEstado = insertarObtenerEstado(con, estadoUbicacion);
        if (idEstado == 0) return 0;
        
        // 2. Insertar o obtener municipio
        int idMunicipio = insertarObtenerMunicipio(con, municipio, idEstado);
        if (idMunicipio == 0) return 0;
        
        // 3. Insertar o obtener pueblo
        int idPueblo = insertarObtenerPueblo(con, pueblo, idMunicipio);
        if (idPueblo == 0) return 0;
        
        // 4. Insertar o obtener código postal
        int idCodigoPostal = insertarObtenerCodigoPostal(con, codigoPostal);
        if (idCodigoPostal == 0) return 0;
        
        // 5. Insertar dirección
        return insertarDireccion(con, calle, numero, idPueblo, idCodigoPostal);
    }
    
    private int insertarObtenerEstado(Connection con, String nombreEstado) throws SQLException {
        // Primero verificar si ya existe
        String sqlSelect = "SELECT id_estado FROM ESTADO WHERE nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
            stmt.setString(1, nombreEstado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_estado");
            }
        }
        
        // Si no existe, insertarlo
        String sqlInsert = "INSERT INTO ESTADO (nombre) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombreEstado);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private int insertarObtenerMunicipio(Connection con, String nombreMunicipio, int idEstado) throws SQLException {
        String sqlSelect = "SELECT id_municipio FROM MUNICIPIO WHERE nombre = ? AND id_estado = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
            stmt.setString(1, nombreMunicipio);
            stmt.setInt(2, idEstado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_municipio");
            }
        }
        
        String sqlInsert = "INSERT INTO MUNICIPIO (nombre, id_estado) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombreMunicipio);
            stmt.setInt(2, idEstado);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private int insertarObtenerPueblo(Connection con, String nombrePueblo, int idMunicipio) throws SQLException {
        String sqlSelect = "SELECT id_pueblo FROM PUEBLO WHERE nombre = ? AND id_municipio = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
            stmt.setString(1, nombrePueblo);
            stmt.setInt(2, idMunicipio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pueblo");
            }
        }
        
        String sqlInsert = "INSERT INTO PUEBLO (nombre, id_municipio) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombrePueblo);
            stmt.setInt(2, idMunicipio);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private int insertarObtenerCodigoPostal(Connection con, String codigoPostal) throws SQLException {
        String sqlSelect = "SELECT id_codigo_postal FROM CODIGO_POSTAL WHERE codigo = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
            stmt.setString(1, codigoPostal);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_codigo_postal");
            }
        }
        
        String sqlInsert = "INSERT INTO CODIGO_POSTAL (codigo) VALUES (?)";
        try (PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, codigoPostal);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private int insertarDireccion(Connection con, String calle, String numero, int idPueblo, int idCodigoPostal) throws SQLException {
        String sql = "INSERT INTO DIRECCION (calle, numero, id_pueblo, id_codigo_postal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, calle);
            stmt.setString(2, numero);
            stmt.setInt(3, idPueblo);
            stmt.setInt(4, idCodigoPostal);
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return 0;
    }
    
    private boolean insertarProveedor(
            Connection con,
            String nombre,
            String telefono,
            String correo,
            String estado,
            int idDireccion) throws SQLException {
        
        String sql = "INSERT INTO PROVEEDOR ("
                + "nombre_proveedor, numero_telefono, correo, estado_proveedor, id_direccion) "
                + "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, correo);
            stmt.setString(4, estado);
            stmt.setInt(5, idDireccion);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Verifica si un proveedor con el mismo nombre ya existe
     */
    public boolean proveedorExiste(String nombreProveedor) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM PROVEEDOR WHERE nombre_proveedor = ?";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, nombreProveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }
}
