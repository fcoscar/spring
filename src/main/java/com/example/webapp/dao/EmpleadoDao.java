package com.example.webapp.dao;

import com.example.webapp.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoDao extends JpaRepository<Empleado, Long> {

    Empleado findByUsuario(String usuario);
}
