package org.example.proyectofx.model;
abstract class Persona {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String rol;
    private String telefono;

    public Persona() {
    }

    public Persona(int id, String nombre, String correo, String contraseña, String rol, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public abstract String TipoPersona();
}
