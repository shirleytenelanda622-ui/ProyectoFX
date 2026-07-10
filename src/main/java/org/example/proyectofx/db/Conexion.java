package org.example.proyectofx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/veterinaria";
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "123456";

    private static Conexion instancia;
    private Connection conexion;

    private Conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de PostgreSQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                instancia = new Conexion();
                return instancia.conexion;
            }
        } catch (SQLException e) {
            System.err.println("Error verificando el estado de la conexión: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
