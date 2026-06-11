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
@Table(name = "excepciones_alimentarias")
public class ExcepcionAlimentaria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int invitadosConRestriccion;
    private String tipoRestriccion;
    @OneToOne @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;
}
