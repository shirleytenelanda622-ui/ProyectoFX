package org.example.proyectofx.dao;

import org.example.proyectofx.db.Conexion;
import org.example.proyectofx.model.Propietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PropietarioDAO implements CRUD<Propietario> {

    @Override
    public boolean guardar(Propietario p) {
        String sql = "INSERT INTO propietarios (cedula, nombre, telefono, direccion, correo) VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getCorreo());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar propietario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Propietario p) {
        String sql = "UPDATE propietarios SET cedula=?, nombre=?, telefono=?, direccion=?, correo=? WHERE id_propietario=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getCorreo());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar propietario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM propietarios WHERE id_propietario=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar propietario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Propietario> listar() {
        List<Propietario> lista = new ArrayList<>();
        String sql = "SELECT * FROM propietarios ORDER BY id_propietario";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Propietario(
                        rs.getInt("id_propietario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("correo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar propietarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeCedula(String cedula) {
        String sql = "SELECT 1 FROM propietarios WHERE cedula = ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar cédula: " + e.getMessage());
            return false;
        }
    }
}
