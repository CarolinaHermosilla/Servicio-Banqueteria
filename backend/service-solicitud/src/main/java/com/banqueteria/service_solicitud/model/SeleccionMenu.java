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
@Table(name = "selecciones_menu")
public class SeleccionMenu {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seleccionCoctel;
    private String seleccionEntrada;
    private String seleccionFondo;
    private String seleccionProteina;
    private String seleccionPostre;
    private String seleccionBebestibles;
    @OneToOne @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

}
