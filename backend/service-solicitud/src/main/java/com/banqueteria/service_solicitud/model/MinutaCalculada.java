package com.banqueteria.service_solicitud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "minutas_calculadas")
public class MinutaCalculada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // coctel
    private Integer canapesSurtidos;
    private Integer empanaditasCoctel;

    // fondo
    private Integer gramosFilete;
    private Integer porcionesPapasGratinadas;

    // postre
    private Integer porcionesMousse;

    @OneToOne
    @JoinColumn(name = "solicitud_id")
    @JsonIgnore
    private Solicitud solicitud;

}


