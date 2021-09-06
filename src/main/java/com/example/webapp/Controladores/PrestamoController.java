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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPrestamo(@RequestBody Prestamos prestamo /*, Principal principal*/){
        Prestamos prestamoNew = null;
        Map<String, Object> response = new HashMap<>();
        try{
            prestamo.config(prestamo);
            prestamoNew = pService.addPrestamo(prestamo);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al guardar el prestamo");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El prestamo se creo correctamente");
        response.put("prestamo", prestamoNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{prestamoId}")
    public ResponseEntity<?> getOne(@PathVariable Long prestamoId){
        Prestamos prestamo = null;
        Map<String, Object> response = new HashMap<>();

        try{
            prestamo = pService.findOne(prestamoId);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al obtener prestamo");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(prestamo==null){
            response.put("mensaje", "El prestamo ID: ".concat(prestamoId.toString()).concat(" no existe"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
    }

    @PutMapping("/pago/{prestamoId}/{abonado}")
    public ResponseEntity<?> pagoPrestamo(@PathVariable(value="prestamoId") Long prestamoId, @PathVariable(value="abonado") double abonado){
        Prestamos prestamoActual = null;
        Map<String, Object> response = new HashMap<>();
        try {
            prestamoActual = pService.findOne(prestamoId);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el pago");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(abonado<prestamoActual.getSaldoXmes() && prestamoActual.getMonto() > prestamoActual.getSaldoXmes()){
            response.put("mensaje", "El pago tiene que ser mayor a " + prestamoActual.getSaldoXmes());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        MovimientosPrestamo movPrestamo = new MovimientosPrestamo();
        movPrestamo.setAbonado(abonado);
        movPrestamo.setFecha(new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm:ss").format(Calendar.getInstance().getTime()));

        prestamoActual.setMonto(prestamoActual.round(prestamoActual.getMonto()-abonado));
        int cuotas = (int) ((int)abonado/prestamoActual.getSaldoXmes());
        prestamoActual.setCuotas(prestamoActual.getCuotas()-cuotas) ;
        movPrestamo.setMonto(prestamoActual.getMonto());
        movPrestamo.setPrestamo(prestamoActual);
        mDao.save(movPrestamo);

        if(prestamoActual.getCuotas()<=0 || prestamoActual.getMonto()<=0) pService.eliminar(prestamoActual.getPrestamoId());

        else pService.addPrestamo(prestamoActual);


        response.put("mensaje", "Pago realizado correctamente");
        response.put("prestamo",prestamoActual);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/detalles/{prestamoId}")
    public Prestamos detallesPrestamo(@PathVariable(value = "prestamoId") Long prestamoId, Prestamos prestamo, Model model){


        return prestamo = pService.findOne(prestamoId);

    }
}

