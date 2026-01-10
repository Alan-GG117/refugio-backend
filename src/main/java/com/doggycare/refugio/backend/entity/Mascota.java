package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie;

    private String raza;

    @Enumerated(EnumType.STRING)
    private ETamano tamano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ESexo sexo;

    @Column(name = "fecha_nacimiento_aprox")
    private LocalDate fechaNacimientoAprox;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(columnDefinition = "TEXT")
    private String descripcionPerfil;

    @Column(name = "peso_actual")
    private BigDecimal pesoActual;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_adopcion")
    private EEstadoAdopcion estadoAdopcion = EEstadoAdopcion.DISPONIBLE;

    @Column(name = "apto_para_adopcion")
    private Boolean aptoParaAdopcion = false;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    // Relación con DetalleRescate (1 a 1)
    // "mappedBy" indica que la clave foránea está en la otra tabla (detalles_rescate)
    @OneToOne(mappedBy = "mascota", cascade = CascadeType.ALL)
    private DetalleRescate detalleRescate;

}
