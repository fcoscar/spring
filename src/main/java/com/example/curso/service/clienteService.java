package com.example.curso.service;

import java.util.ArrayList;

import com.example.curso.dao.clienteDao;
import com.example.curso.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class clienteService {
    
	clienteDao cDao;

	public clienteService(clienteDao cDao){
		this.cDao = cDao;
	}

	@Transactional(readOnly = true)
	public ArrayList<Cliente> getAll(){
		return new ArrayList<Cliente>(cDao.findAll());
	}

	@Transactional
	public void add(Cliente cliente){

		cDao.save(cliente);
	}

	@Transactional
	public void delete(Long Id){
		cDao.deleteById(Id);
	}

	@Transactional(readOnly = true)
	public Cliente getOne(Cliente cliente){
		return cDao.findById(cliente.getId()).orElse(null);
	}

}
