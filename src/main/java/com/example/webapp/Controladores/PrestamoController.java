package com.example.webapp.Controladores;

import com.example.webapp.dao.EmpleadoDao;
import com.example.webapp.dao.MovimientosDao;
import com.example.webapp.models.Cliente;
import com.example.webapp.models.Empleado;
import com.example.webapp.models.MovimientosPrestamo;
import com.example.webapp.models.Prestamos;
import com.example.webapp.service.PrestamoService;
import com.example.webapp.service.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/prestamo")
@SessionAttributes("prestamo")
public class PrestamoController {

    @Autowired
    PrestamoService pService;
    @Autowired
    clienteService cService;
    @Autowired
    EmpleadoDao eDao;
    @Autowired
    MovimientosDao mDao;


    public PrestamoController(PrestamoService pService, clienteService cService, EmpleadoDao eDao, MovimientosDao mDao){
        this.pService=pService;
        this.cService=cService;
        this.eDao=eDao;
        this.mDao=mDao;
    }

    @GetMapping("/eliminar")
    public void eliminar(Prestamos prestamo){
        pService.eliminar(prestamo);
    }



    @GetMapping("/agregar/{Id}")
    public String addPrestamo(@PathVariable(value="Id") Long clienteId, Model model){
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente = cService.getOne(cliente);
        Prestamos prestamo = new Prestamos();
        prestamo.setCliente(cliente);
        model.addAttribute("prestamo", prestamo);
        return "prestamo form";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(Prestamos prestamo, Principal principal){
        prestamo.setMonto(prestamo.round(prestamo.getMonto()));
        prestamo.setSaldoXmes(prestamo.getCuotas(), prestamo.getMonto());
        prestamo.setSaldoXmes(prestamo.round(prestamo.getSaldoXmes()));
        Empleado empleado = eDao.findByUsuario(principal.getName());
        prestamo.setEmpleado(empleado);
        prestamo.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
        prestamo.setCuota_inicial(prestamo.getCuotas());
        prestamo.setMonto_inicial(prestamo.getMonto());
        pService.addPrestamo(prestamo);
        return "redirect:/ver/" + prestamo.getCliente().getId();
    }

    @GetMapping("/pago/{prestamoId}")
    public String pagoPrestamo(@PathVariable(value="prestamoId") Long prestamoId, Prestamos prestamo, MovimientosPrestamo movPrestamo){
        prestamo.setPrestamoId(prestamoId);
        prestamo = pService.findOne(prestamo);



        movPrestamo.setAbonado(prestamo.getSaldoXmes());
        movPrestamo.setFecha(new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss").format(Calendar.getInstance().getTime()));



        prestamo.setMonto(prestamo.getMonto()-prestamo.getSaldoXmes());
        prestamo.setCuotas(prestamo.getCuotas()-1);
        movPrestamo.setMonto(prestamo.getMonto());
        movPrestamo.setPrestamo(prestamo);
        mDao.save(movPrestamo);

        if(prestamo.getCuotas()<=0 || prestamo.getMonto()<=0){
            pService.eliminar(prestamo);
            return "redirect:/ver/" + prestamo.getCliente().getId();
        }else {
            pService.addPrestamo(prestamo);
            return "redirect:/ver/" + prestamo.getCliente().getId();
        }
    }

    @GetMapping("/detalles/{prestamoId}")
    public String detallesPrestamo(@PathVariable(value = "prestamoId") Long prestamoId, Prestamos prestamo, Model model){

        prestamo.setPrestamoId(prestamoId);
        prestamo = pService.findOne(prestamo);
        model.addAttribute("prestamo",prestamo);
        return "detallesPrestamo";
    }
}

