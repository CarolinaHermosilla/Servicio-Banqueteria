package com.banqueteria.service_solicitud.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SolicitudDTO {

    private Long idCotizacion;
    // 1. DATOS GENERALES
    private String cliente;
    private String tipoEvento;
    private int totalInvitados;
    private LocalDate fechaEvento;
    private String estado;
    private String varianteMenu;
    // 2. EXCEPCIONES
    private int invitadosConRestriccion;
    private String tipoRestriccion;
    // 3. SELECCIÓN MENÚ
    private String seleccionCoctel;
    private String seleccionEntrada;
    private String seleccionFondo;
    private String seleccionProteina;
    private String seleccionPostre;
    private String seleccionBebestibles;
    private String tipoBebidaGaseosa;
    private String saborJugo;
    private String tipoAlcohol;
    // 4. CÁLCULOS GASTRONÓMICOS
    private int totalCoctelUnidades;     
    private int totalEntradasUnidades;   
    private int totalFondosPorciones;    
    private int totalProteinaGramos;     
    private int totalPostresPorciones;   
    // 5. CÁLCULOS LOGÍSTICA
    private int totalMesas;
    private int totalCubiertos;
    // 6. CÁLCULOS LÍQUIDOS
    private int totalPorcionesGaseosas;
    private int totalPorcionesJugos;
    private int totalPorcionesAlcohol;
    // 7. STAFFING
    private int cantidadGarzones;
    private int cantidadChefs;
}
