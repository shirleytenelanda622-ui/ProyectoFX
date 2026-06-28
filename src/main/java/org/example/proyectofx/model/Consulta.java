package org.example.proyectofx.model;

import org.example.proyectofx.dao.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Consulta implements CRUD<Consulta> {
    private String fecha;
    private String mascota;
    private String veterinario;
    private String diagnostico;
    private String tratamiento;
    private String estado;

    public String getFecha() {
        return fecha;
    }

    public String getMascota() {
        return mascota;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    @Override
    public void guardar(Consulta objeto){

    }
    @Override
    public void actualizar(Consulta objeto){

    }
    @Override
    public void eliminar(int id){

    }
    @Override
    public List<Consulta> listar(){
        List<Consulta> consultas = new ArrayList<>();
        return consultas;
    }
}
