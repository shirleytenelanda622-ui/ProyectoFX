package org.example.proyectofx.dao;


import org.example.proyectofx.db.Conexion;
import org.example.proyectofx.model.Mascota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MascotaDAO implements CRUD<Mascota> {

    @Override
    public boolean guardar(Mascota m) {
        String sql = "INSERT INTO mascotas (nombre, especie, raza, edad, sexo, peso, id_propietario) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setString(3, m.getRaza());
            ps.setInt(4, m.getEdad());
            ps.setString(5, m.getSexo());
            ps.setDouble(6, m.getPeso());
            ps.setInt(7, m.getIdPropietario());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar mascota: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Mascota m) {
        String sql = "UPDATE mascotas SET nombre=?, especie=?, raza=?, edad=?, sexo=?, peso=?, id_propietario=? WHERE id_mascota=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setString(3, m.getRaza());
            ps.setInt(4, m.getEdad());
            ps.setString(5, m.getSexo());
            ps.setDouble(6, m.getPeso());
            ps.setInt(7, m.getIdPropietario());
            ps.setInt(8, m.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar mascota: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id_mascota=?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar mascota: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Mascota> listar() {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT m.*, p.nombre AS nombre_propietario " +
                "FROM mascotas m JOIN propietarios p ON m.id_propietario = p.id_propietario " +
                "ORDER BY m.id_mascota";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mascota m = new Mascota(
                        rs.getInt("id_mascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getDouble("peso"),
                        rs.getInt("id_propietario")
                );
                m.setNombrePropietario(rs.getString("nombre_propietario"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar mascotas: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeNombreParaPropietario(String nombre, int idPropietario) {
        String sql = "SELECT 1 FROM mascotas WHERE LOWER(nombre) = LOWER(?) AND id_propietario = ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idPropietario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar nombre de mascota: " + e.getMessage());
            return false;
        }
    }
}

