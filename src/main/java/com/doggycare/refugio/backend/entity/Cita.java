package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la tabla usuarios (adoptante_id)
    @ManyToOne
    @JoinColumn(name = "adoptante_id", nullable = false)
    private Usuario adoptante;

    // Relación con la tabla mascotas (mascota_interes_id)
    // Puede ser null si la cita es general, pero tu tabla sugiere que es por mascota
    @ManyToOne
    @JoinColumn(name = "mascota_interes_id")
    private Mascota mascota;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EEstadoCita estado;

    private String comentarios;

    @PrePersist
    public void prePersist() {
        if(this.estado == null) this.estado = EEstadoCita.PENDIENTE;
    }
}
