package com.example.webapp.Controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.webapp.models.Cliente;
import com.example.webapp.service.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class homeController {

	@Autowired
	clienteService uService;

	@GetMapping({"/home", "/"})
	public ArrayList<Cliente> getAll() {
		return uService.getAll();
	}

	@PostMapping("/guardar")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente){
		return uService.add(cliente);

	}

	@GetMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getOne(@PathVariable Long id){
		Cliente cliente = uService.getOne(id);
		Map<String, Object> response = new HashMap<>();
		if(cliente==null){
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@DeleteMapping("/borrar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		uService.delete(id);
	}

	@PutMapping("/editar/{id}")
	public Cliente saveEdit(@RequestBody Cliente cliente,@PathVariable Long id){
		Cliente clienteActual = uService.getOne(id);

		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setCorreo(cliente.getCorreo());

		return uService.add(clienteActual);
	}



}
