package org.example.proyectofx.dao;

import org.example.proyectofx.db.Conexion;
import org.example.proyectofx.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO implements CRUD<Usuario> {
    public Usuario autenticar(String correo, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            null,
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean guardar(Usuario u) {
        int nuevoId = generarIdUnico();
        u.setId(nuevoId);

        String sql = "INSERT INTO usuarios (id_usuario, nombre, correo, contrasena, rol) VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoId);
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getRol());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    private int generarIdUnico() {
        java.util.Random random = new java.util.Random();
        int idGenerado;
        do {
            idGenerado = 100000 + random.nextInt(900000); // número aleatorio de 6 dígitos
        } while (existeId(idGenerado));
        return idGenerado;
    }

    private boolean existeId(int id) {
        String sql = "SELECT 1 FROM usuarios WHERE id_usuario = ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar id: " + e.getMessage());
            return true;
        }
    }

    @Override
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, rol=? WHERE id_usuario=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getRol());
            ps.setInt(4, u.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id_usuario";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
}