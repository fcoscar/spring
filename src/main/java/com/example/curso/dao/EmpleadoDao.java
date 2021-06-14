package com.example.curso.dao;

import com.example.curso.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoDao extends JpaRepository<Empleado, Long> {

    public Empleado findByUsuario(String usuario);
}
