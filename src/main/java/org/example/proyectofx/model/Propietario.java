package org.example.proyectofx.model;

public class Propietario extends Persona {

    private String cedula;
    private String telefono;
    private String direccion;
    private String correo;

    public Propietario() {
        super();
    }

    public Propietario(int id, String cedula, String nombre, String telefono,
                       String direccion, String correo) {
        super(id, nombre);
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String describir() {
        return "Propietario: " + getNombre() + " (CI: " + cedula + ")";
    }

    @Override
    public String toString() {
        return getNombre() + " - " + cedula;
    }
}
