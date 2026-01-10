package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "solicitudes_adopcion")
public class SolicitudAdopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    private EEstadoSolicitud estado;

    private String comentarios;

    // Asignar valores por defecto antes de guardar
    @PrePersist
    public void prePersist() {
        this.fechaSolicitud = LocalDateTime.now();
        if(this.estado == null) this.estado = EEstadoSolicitud.PENDIENTE;
    }
}
