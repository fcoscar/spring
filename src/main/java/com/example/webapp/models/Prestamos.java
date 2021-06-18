package com.example.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Cliente cliente;
    private double saldoXmes;
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleado;
    private String fecha;



    public void setSaldoXmes(int cuotas, double monto) {
        this.saldoXmes =  monto/cuotas;
    }

    public double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

