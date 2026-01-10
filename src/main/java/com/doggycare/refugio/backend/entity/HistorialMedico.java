package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "historial_medico")
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String tipo;

    @Column(length = 1000)
    private String descripcion;

    // RELACIÓN: Muchas historias pertenecen a UNA mascota
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    // RELACIÓN: Muchas historias son escritas por UN veterinario
    @ManyToOne
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Usuario veterinario;

    @PrePersist
    public void prePersist() {
        if (this.fecha == null) this.fecha = LocalDate.now();
    }
}
