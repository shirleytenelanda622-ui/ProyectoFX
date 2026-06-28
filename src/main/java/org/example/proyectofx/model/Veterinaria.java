package org.example.proyectofx.model;

public class Veterinaria extends Persona{
    public String especialidad;
    public Veterinaria(){
    }
    @Override
    public String TipoPersona(){
        return "Veterinario";
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
