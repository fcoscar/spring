package com.example.webapp.Controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.webapp.models.Cliente;
import com.example.webapp.service.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public ResponseEntity<?> save(@RequestBody Cliente cliente){
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		try{
			 clienteNew = uService.add(cliente);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al guaradar el cliente");
			response.put("erro", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se creo con exito");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/cliente/{id}")
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
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String,Object> response = new HashMap<>();
		try {
			uService.delete(id);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al eliminar cliente de la base de datos");
			response.put("error", e.getMessage().concat(":" ).concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente eliminado correctament");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> saveEdit(@RequestBody Cliente cliente,@PathVariable Long id){
		Cliente clienteActual = uService.getOne(id);
		Map<String,Object> response = new HashMap<>();
		Cliente clienteActualizado = null;
		if(clienteActual==null){
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setCorreo(cliente.getCorreo());
			clienteActualizado = uService.add(clienteActual);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al actulizar info del cliente");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente actualizado correctamente");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}



}
