package org.example.proyectofx.model;

import org.example.proyectofx.dao.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Mascota implements CRUD <Mascota>{
    private String nombre;
    private String raza;
    private int edad;
    private double tamanio;
    private int vacunas;

    public Mascota(String nombre, String raza, int edad, double tamanio, int vacunas) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.tamanio = tamanio;
        this.vacunas = vacunas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        return edad;
    }

    public double getTamanio() {
        return tamanio;
    }

    public int getVacunas() {
        return vacunas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public void setVacunas(int vacunas) {
        this.vacunas = vacunas;
    }
    @Override
    public void guardar(Mascota objeto){

    }
    @Override
    public void actualizar(Mascota objeto){

    }
    @Override
    public void eliminar(int id){

    }
    @Override
    public List<Mascota> listar(){
        List<Mascota> mascotas = new ArrayList<>();
        return mascotas;
    }
}
