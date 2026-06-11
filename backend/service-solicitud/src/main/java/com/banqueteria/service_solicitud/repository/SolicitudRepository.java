package com.banqueteria.service_solicitud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banqueteria.service_solicitud.model.Solicitud;



public interface SolicitudRepository extends JpaRepository < Solicitud, Long>{

    List<Solicitud> findAllByOrderByFechaEventoAsc();
}
