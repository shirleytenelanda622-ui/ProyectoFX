package org.example.proyectofx.dao;

import java.util.List;

public interface CRUD<T> {

    boolean guardar(T objeto);

    boolean actualizar(T objeto);

    boolean eliminar(int id);

    List<T> listar();
}
