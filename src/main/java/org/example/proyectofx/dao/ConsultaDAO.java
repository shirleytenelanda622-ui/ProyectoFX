package org.example.proyectofx.dao;

import org.example.proyectofx.db.Conexion;
import org.example.proyectofx.model.Consulta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsultaDAO {


    public List<Consulta> listarReporte() {
        List<Consulta> lista = new ArrayList<>();
        String sql = """
                SELECT c.id_consulta, c.id_mascota, c.id_usuario, c.fecha,
                       c.diagnostico, c.tratamiento, c.costo,
                       m.nombre AS nombre_mascota,
                       p.nombre AS nombre_propietario,
                       u.nombre AS nombre_usuario
                FROM consultas c
                JOIN mascotas m ON c.id_mascota = m.id_mascota
                JOIN propietarios p ON m.id_propietario = p.id_propietario
                JOIN usuarios u ON c.id_usuario = u.id_usuario
                ORDER BY c.fecha DESC, c.id_consulta DESC
                """;

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta(
                        rs.getInt("id_consulta"),
                        rs.getInt("id_mascota"),
                        rs.getInt("id_usuario"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento"),
                        rs.getDouble("costo")
                );
                c.setNombreMascota(rs.getString("nombre_mascota"));
                c.setNombrePropietario(rs.getString("nombre_propietario"));
                c.setNombreUsuario(rs.getString("nombre_usuario"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar consultas para el reporte: " + e.getMessage());
        }
        return lista;
    }

    public int contarConsultas() {
        String sql = "SELECT COUNT(*) AS total FROM consultas";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar consultas: " + e.getMessage());
        }
        return 0;
    }

    public double sumarIngresos() {
        String sql = "SELECT COALESCE(SUM(costo),0) AS total FROM consultas";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al sumar ingresos: " + e.getMessage());
        }
        return 0.0;
    }

    public int contarMascotas() {
        String sql = "SELECT COUNT(*) AS total FROM mascotas";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar mascotas: " + e.getMessage());
        }
        return 0;
    }
}
