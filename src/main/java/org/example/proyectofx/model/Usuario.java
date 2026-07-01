package org.example.proyectofx.model;

public class Usuario extends Persona {

    private String correo;
    private String contrasena;
    private String rol;

    public Usuario() {
        super();
    }

    public Usuario(int id, String nombre, String correo, String contrasena, String rol) {
        super(id, nombre);
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(rol);
    }

    public boolean esCajero() {
        return "CAJERO".equalsIgnoreCase(rol);
    }

    public boolean esReportes() {
        return "REPORTES".equalsIgnoreCase(rol);
    }

    @Override
    public String describir() {
        return "Usuario: " + getNombre() + " (" + rol + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " - " + rol;
    }
}
