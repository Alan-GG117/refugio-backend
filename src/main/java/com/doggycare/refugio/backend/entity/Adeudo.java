package com.doggycare.refugio.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "adeudos")
@Data
public class Adeudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acreedor_id", nullable = false)
    private Acreedor acreedor;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private String concepto;

    @Column(name = "fecha_generacion")
    private LocalDate fechaGeneracion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private EEstadoPago estado = EEstadoPago.PENDIENTE; // Enum: PENDIENTE, PAGADO, VENCIDO
}
