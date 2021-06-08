package com.example.curso.Controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.example.curso.models.Cliente;
import com.example.curso.service.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class homeController {

	@Autowired
	clienteService uService;

	@GetMapping({"/home", "/"})
	public String getAll(Model model) {
		var clientes = new ArrayList<>(uService.getAll());
		model.addAttribute("clientes", clientes);//mandar info a la pagina html
		return "home";
	}

	@PostMapping("/guardar")
	public String save(Cliente cliente){
		uService.add(cliente);
		return "redirect:/home";
	}

	@GetMapping("/agregar")
	public String add(Cliente cliente){
		var fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		cliente.setFecha(fecha);
		return "lista";
	}

	@GetMapping("/borrar/{Id}")
	public String delete(Cliente cliente){
		uService.delete(cliente.getId());
		return "redirect:/home";
	}

	@GetMapping("/editar/{Id}")
	public String edit(Model model, Cliente cliente){
		cliente = uService.getOne(cliente);
		model.addAttribute("cliente", cliente);
		return "lista";
	}

	@PutMapping("/editar/{Id}")
	public String saveEdit(Cliente cliente){
		uService.add(cliente);
		return "redirect:/home";
	}

	@GetMapping("/ver/{Id}")
	public String ver(Cliente cliente, Model model){
		cliente = uService.getOne(cliente);
		model.addAttribute("cliente", cliente);
		return "ver";
	}


}
