package com.example.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientosPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mov_id;
    private String fecha;
    private double monto;
    private double abonado;
    @ManyToOne(fetch = FetchType.LAZY)
    private Prestamos prestamo;
}
