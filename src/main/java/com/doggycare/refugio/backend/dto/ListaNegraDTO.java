package com.doggycare.refugio.backend.dto;

import lombok.Data;

@Data
public class ListaNegraDTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String identificacionOficial;
    private String motivo;  // Asegúrate que en Angular envíes "motivo", no "motivoBloqueo"
    private Long adminId;
}
