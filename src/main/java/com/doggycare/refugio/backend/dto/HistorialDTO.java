package com.doggycare.refugio.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistorialDTO {
    private String tipo;
    private String descripcion;
    private Long mascotaId;
    private Long veterinarioId;
    private LocalDate fecha;
}
