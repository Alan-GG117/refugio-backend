package com.doggycare.refugio.backend.dto;

import lombok.Data;

@Data
public class DireccionDTO {
    private String cp;
    private String estado;
    private String municipioAlcaldia;
    private String colonia;
    private String tipoCalle;
    private String calle;
    private String numExterior;
    private String numInterior;
    private String referencias;
}
