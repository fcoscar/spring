package com.example.curso.dao;
import com.example.curso.models.Usuario;

import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

@Component
public interface usuarioDao extends JpaRepository<Usuario, Long> {
    
}
