package com.example.webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference// https://stackoverflow.com/questions/36983215/failed-to-write-http-message-org-springframework-http-converter-httpmessagenotw
    private Prestamos prestamo;
}
