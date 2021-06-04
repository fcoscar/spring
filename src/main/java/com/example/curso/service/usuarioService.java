package com.example.curso.service;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Optional;
import com.example.curso.dao.usuarioDao;
import com.example.curso.models.*;
import org.springframework.transaction.annotation.Transactional;

@Component
public class usuarioService {
    
	usuarioDao uDao;


	public usuarioService(usuarioDao uDao){
		this.uDao = uDao;
	}

	@Transactional(readOnly = true)
	public ArrayList<Usuario> getAll(){
		return new ArrayList<Usuario>(uDao.findAll());
	}

	@Transactional
	public void add(Usuario usuario){
		uDao.save(usuario);
	}

	@Transactional
	public void delete(Long Id){
		uDao.deleteById(Id);
	}

	@Transactional(readOnly = true)
	public Usuario getOne(Usuario usuario){
		return uDao.findById(usuario.getId()).orElse(null);
	}

}
