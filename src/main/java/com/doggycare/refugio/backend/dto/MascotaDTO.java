package com.doggycare.refugio.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String especie; // Perro, Gato
    private String raza;
    private String tamano;  // PEQUENO, MEDIANO, GRANDE
    private String sexo;    // MACHO, HEMBRA
    private BigDecimal pesoActual;
    private LocalDate fechaNacimientoAprox;
    private String fotoUrl; // Por ahora pegaremos links de internet
    private String descripcionPerfil;
    private String estadoAdopcion; // DISPONIBLE, ADOPTADO...
    private Boolean aptoParaAdopcion;
}
