package com.example.curso.service;

import com.example.curso.dao.PrestamoDao;
import com.example.curso.models.Prestamos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    PrestamoDao pDao;

    public PrestamoService(PrestamoDao pDao){
        this.pDao=pDao;
    }

    @Transactional
    public void addPrestamo(Prestamos prestamo){
        pDao.save(prestamo);
    }

    public Prestamos findOne(Prestamos prestamo){
        return pDao.findById(prestamo.getPrestamoId()).orElse(null);
    }

    public void eliminar(Prestamos prestamo){
        pDao.deleteById(prestamo.getPrestamoId());
    }
}
