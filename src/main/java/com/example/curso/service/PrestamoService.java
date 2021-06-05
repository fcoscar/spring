package com.example.curso.service;

import com.example.curso.dao.PrestamoDao;
import com.example.curso.models.Prestamos;
import org.springframework.stereotype.Service;

@Service
public class PrestamoService {

    PrestamoDao pDao;

    public PrestamoService(PrestamoDao pDao){
        this.pDao=pDao;
    }

    public void addPrestamo(Prestamos prestamo){
        pDao.save(prestamo);
    }
}
