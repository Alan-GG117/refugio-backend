package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lista_negra")
public class ListaNegra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(name = "apellido_paterno", length = 50)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 50)
    private String apellidoMaterno;

    @Column(length = 100)
    private String email;

    @Column(name = "identificacion_oficial", length = 50)
    private String identificacionOficial; // INE, DNI, Pasaporte

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Quién lo reportó (registrado_por_id)
    @ManyToOne
    @JoinColumn(name = "registrado_por_id")
    private Usuario registradoPor;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
