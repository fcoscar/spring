package com.example.webapp.service;

import com.example.webapp.dao.PrestamoDao;
import com.example.webapp.models.Prestamos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    PrestamoDao pDao;

    public PrestamoService(PrestamoDao pDao){
        this.pDao=pDao;
    }

    @Transactional
    public Prestamos addPrestamo(Prestamos prestamo){
        return pDao.save(prestamo);
    }

    @Transactional(readOnly = true)
    public Prestamos findOne(Long prestamoId){
        return pDao.findById(prestamoId).orElse(null);
    }

    public void eliminar(Long id){
        pDao.deleteById(id);
    }
}
