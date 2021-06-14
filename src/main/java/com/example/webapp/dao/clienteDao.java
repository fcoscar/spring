package com.example.webapp.dao;
import com.example.webapp.models.Cliente;


import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;

@Component
public interface clienteDao extends JpaRepository<Cliente, Long> {
    
}
