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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    public void eliminar(@PathVariable Long id){
        pService.eliminar(id);
    }

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Prestamos guardarPrestamo(@RequestBody Prestamos prestamo /*, Principal principal*/){
        prestamo.config(prestamo);
        return pService.addPrestamo(prestamo);
    }

    @GetMapping("/{prestamoId}")
    public Prestamos getOne(@PathVariable Long prestamoId){
        return pService.findOne(prestamoId);
    }

    @PutMapping("/pago/{prestamoId}/{abonado}")
    public ResponseEntity<?> pagoPrestamo(Prestamos prestamoActual, MovimientosPrestamo movPrestamo, @PathVariable(value="prestamoId") Long prestamoId, @PathVariable(value="abonado") double abonado){

        prestamoActual = pService.findOne(prestamoId);

        if(abonado<prestamoActual.getSaldoXmes() && prestamoActual.getMonto() > prestamoActual.getSaldoXmes()){
            String response = "El pago tiene que ser mayor a " + prestamoActual.getSaldoXmes();
            return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
        }

        movPrestamo.setAbonado(abonado);
        movPrestamo.setFecha(new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss").format(Calendar.getInstance().getTime()));

        prestamoActual.setMonto(prestamoActual.round(prestamoActual.getMonto()-abonado));
        prestamoActual.setCuotas(prestamoActual.getCuotas()-1);
        movPrestamo.setMonto(prestamoActual.getMonto());
        movPrestamo.setPrestamo(prestamoActual);
        mDao.save(movPrestamo);

        if(prestamoActual.getCuotas()<=0 || prestamoActual.getMonto()<=0){
            pService.eliminar(prestamoActual.getPrestamoId());

        }else{
            pService.addPrestamo(prestamoActual);
        }

        return new ResponseEntity<>(prestamoActual,HttpStatus.OK);

    }

    @GetMapping("/detalles/{prestamoId}")
    public Prestamos detallesPrestamo(@PathVariable(value = "prestamoId") Long prestamoId, Prestamos prestamo, Model model){

        return prestamo = pService.findOne(prestamoId);

    }
}

