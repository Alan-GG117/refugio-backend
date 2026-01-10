package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "direcciones")
@Data
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_postal", nullable = false, length = 5)
    private String codigoPostal;

    @Column(nullable = false)
    private String estado;

    @Column(name = "municipio_alcaldia", nullable = false)
    private String municipioAlcaldia;

    @Column(nullable = false)
    private String colonia;

    @Column(name = "tipo_calle")
    private String tipoCalle; // Calle, Avenida, Blvd...

    @Column(nullable = false)
    private String calle;

    @Column(name = "num_exterior", nullable = false, length = 20)
    private String numExterior;

    @Column(name = "num_interior", length = 20)
    private String numInterior;

    @Column(columnDefinition = "TEXT")
    private String referencias;
}
