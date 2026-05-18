package com.banqueteria.service_solicitud.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCotizacion;

    private String cliente;
    private String tipoEvento;
    private Integer totalInvitados;
    private Integer invitadosConRestriccion;
    private LocalDate fechaEvento;
    private String estado = "Pendiente";

    //datos de contacto simplificados para MVP
    private String contactoNombre;
    private String contactoEmail;
    private String contactoTelefono;
    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private MinutaCalculada minutaCalculada;

}
