package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "acreedores")
@Data
public class Acreedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social")
    private String razonSocial; // Para empresas

    // Para personas físicas
    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "apellido_paterno_contacto")
    private String apellidoPaternoContacto;

    @Column(name = "apellido_materno_contacto")
    private String apellidoMaternoContacto;

    private String telefono;
    private String email;

    @Column(name = "tipo_servicio")
    private String tipoServicio;
}