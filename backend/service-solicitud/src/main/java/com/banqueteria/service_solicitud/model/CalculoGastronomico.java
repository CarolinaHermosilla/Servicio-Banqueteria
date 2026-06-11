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
@Table(name = "calculos_gastronomicos")
public class CalculoGastronomico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalCoctelUnidades;     
    private int totalEntradasUnidades;   
    private int totalFondosPorciones;    
    private int totalProteinaGramos;     
    private int totalPostresPorciones;
    @OneToOne @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;
}
