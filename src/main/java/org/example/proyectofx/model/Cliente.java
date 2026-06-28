package org.example.proyectofx.model;

import org.example.proyectofx.dao.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona implements CRUD<Cliente> {
    private String direccion;
    public Cliente(){
    }
    @Override
    public String TipoPersona(){
        return "Cliente";
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    @Override
    public void guardar(Cliente objeto){

    }
    @Override
    public void actualizar(Cliente objeto){

    }
    @Override
    public void eliminar(int id){

    }
    @Override
    public List<Cliente> listar(){
        List<Cliente> clientes = new ArrayList<>();
        return clientes;
    }
}
