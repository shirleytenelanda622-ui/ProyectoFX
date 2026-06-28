package org.example.proyectofx.model;

import org.example.proyectofx.dao.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona implements CRUD<Usuario> {
    private String usuario;

    public Usuario(){

    }
    @Override
    public String TipoPersona(){
        return "Usuario";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    @Override
    public void guardar(Usuario objeto){

    }
    @Override
    public void actualizar(Usuario objeto){

    }
    @Override
    public void eliminar(int id){

    }
    @Override
    public List<Usuario> listar(){
        List<Usuario> usuarios = new ArrayList<>();
        return usuarios;
    }
}
