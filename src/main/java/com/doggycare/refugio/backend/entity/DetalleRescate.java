package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "detalles_rescate")
@Data
public class DetalleRescate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @Column(name = "direccion_rescate")
    private String direccionRescate;

    @Column(name = "fecha_rescate", nullable = false)
    private LocalDate fechaRescate;

    @Column(columnDefinition = "TEXT")
    private String condicionInicial;

    @Column(name = "rescatado_por")
    private String rescatadoPor;
}