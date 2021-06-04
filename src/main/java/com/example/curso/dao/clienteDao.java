package com.example.curso.dao;
import com.example.curso.models.Cliente;


import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

@Component
public interface clienteDao extends JpaRepository<Cliente, Long> {
    
}
