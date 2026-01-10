package com.doggycare.refugio.backend.dto;

import lombok.Data;

@Data
public class SolicitudDTO {
    private Long usuarioId;
    private Long mascotaId;
    private String comentarios;
}
