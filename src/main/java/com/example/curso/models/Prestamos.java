package com.example.curso.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prestamoId;
    private double monto;
    private int cuotas;
    @ManyToOne(fetch =FetchType.LAZY)
    private Cliente cliente;
    private double saldoXmes;


    public void setSaldoXmes(int cuotas, double monto) {
        this.saldoXmes =  monto/cuotas;
    }

    public double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
