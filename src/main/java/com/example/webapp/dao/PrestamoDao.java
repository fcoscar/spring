package com.example.webapp.dao;

import com.example.webapp.models.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PrestamoDao extends JpaRepository<Prestamos, Long> {
}
