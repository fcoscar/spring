package com.example.webapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String usuario;
    private String nombre;
    private String apellido;
    private String clave;
    private boolean activado;
    @OneToMany(mappedBy = "empleado",fetch = FetchType.LAZY)
    private List<Prestamos> prestamos;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private List<Roles> rol;
}

