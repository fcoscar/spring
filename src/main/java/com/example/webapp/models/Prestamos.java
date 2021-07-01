package com.example.webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prestamoId;
    private String descripcion;
    private double monto;
    private double monto_inicial;
    private int cuotas;
    private int cuota_inicial;
    @ManyToOne(fetch =FetchType.LAZY)
    @JsonBackReference// https://stackoverflow.com/questions/36983215/failed-to-write-http-message-org-springframework-http-converter-httpmessagenotw
    private Cliente cliente;
    private double saldoXmes;
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado;
    private String fecha;
    @OneToMany(mappedBy = "prestamo",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference//https://stackoverflow.com/questions/36983215/failed-to-write-http-message-org-springframework-http-converter-httpmessagenotw
    private List<MovimientosPrestamo> movimientos;

    @PrePersist
    public void fecha(){
        this.fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }

   public void config(Prestamos prestamo){
       prestamo.setMonto(round(prestamo.getMonto()));
       prestamo.setSaldoXmes(prestamo.getCuotas(), prestamo.getMonto());
       prestamo.setSaldoXmes(prestamo.round(prestamo.getSaldoXmes()));
       //Empleado empleado = eDao.findByUsuario(principal.getName());
       //prestamo.setEmpleado(empleado);
       prestamo.setCuota_inicial(prestamo.getCuotas());
       prestamo.setMonto_inicial(prestamo.getMonto());
   }
    public void setSaldoXmes(int cuotas, double monto) {
        this.saldoXmes =  monto/cuotas;
    }

    public double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

