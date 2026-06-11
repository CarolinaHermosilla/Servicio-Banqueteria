package com.banqueteria.service_solicitud.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCotizacion;

    private String cliente;
    private String tipoEvento;
    private int totalInvitados;
    private LocalDate fechaEvento;
    private String estado;
    private String varianteMenu;

    // Relaciones (El MER real)
    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private SeleccionMenu seleccionMenu;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private ExcepcionAlimentaria excepcionAlimentaria;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private CalculoGastronomico calculoGastronomico;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private LogisticaStaff logisticaStaff;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private CalculoBebestibles calculoBebestibles;
}