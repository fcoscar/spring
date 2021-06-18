package com.example.webapp.dao;

import com.example.webapp.models.Empleado;
import com.example.webapp.models.MovimientosPrestamo;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MovimientosDao extends JpaRepository<MovimientosPrestamo, Long> {
}
