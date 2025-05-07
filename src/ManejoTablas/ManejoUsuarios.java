/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManejoTablas;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joelmatadamas
 */
public class ManejoUsuarios {
    
    public Usuario getUsuarios(String nombreUsuario) throws SQLException {
        Usuario usuario = null;
        Connection con = Conexion.getConexion();
        if (con != null) {
            try (PreparedStatement stmt = con.prepareStatement("SELECT id_usuario, usuario, contrasena FROM USUARIO WHERE usuario = ?")) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id_usuario");
                        String user = rs.getString("usuario");
                        String contrasenia = rs.getString("contrasena");
                        usuario = new Usuario(id, user, contrasenia);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
    
}
