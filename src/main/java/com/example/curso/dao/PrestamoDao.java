package com.example.curso.dao;

import com.example.curso.models.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PrestamoDao extends JpaRepository<Prestamos, Long> {
}
