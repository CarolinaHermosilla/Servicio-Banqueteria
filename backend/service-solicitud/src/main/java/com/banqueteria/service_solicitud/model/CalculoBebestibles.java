package com.banqueteria.service_solicitud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "calculos_bebestibles")
public class CalculoBebestibles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoBebidaGaseosa;
    private String saborJugo;
    private String tipoAlcohol;
    private int totalPorcionesGaseosas;
    private int totalPorcionesJugos;
    private int totalPorcionesAlcohol;
    @OneToOne @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;
}
