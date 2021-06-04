package com.example.curso.Controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.curso.models.Usuario;
import com.example.curso.service.usuarioService;

@Controller
public class homeController {

	@Autowired
	usuarioService uService;

	@GetMapping({"/home", "/"})
	public String getAll(Model model) {
		var usuarios = new ArrayList<>(uService.getAll());
		model.addAttribute("usuarios", usuarios);//mandar info a la pagina html
		return "home";
	}

	@PostMapping("/guardar")
	public String save(Usuario usuario){
		uService.add(usuario);
		return "redirect:/home";
	}

	@GetMapping("/agregar")
	public String add(Usuario usuario){
		var fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		usuario.setFecha(fecha);
		return "lista";
	}

	@GetMapping("/borrar/{Id}")
	public String delete(Usuario usuario){
		uService.delete(usuario.getId());
		return "redirect:/home";
	}

	@GetMapping("/editar/{Id}")
	public String edit(Model model, Usuario usuario){
		usuario = uService.getOne(usuario);
		model.addAttribute("usuario", usuario);
		return "lista";
	}

	@PutMapping("/editar/{Id}")
	public String saveEdit(Usuario usuario){
		uService.add(usuario);
		return "redirect:/home";
	}

	@GetMapping("/ver/{Id}")
	public String ver(Usuario usuario, Model model){
		usuario = uService.getOne(usuario);
		model.addAttribute("usuario", usuario);
		return "ver";
	}

}
