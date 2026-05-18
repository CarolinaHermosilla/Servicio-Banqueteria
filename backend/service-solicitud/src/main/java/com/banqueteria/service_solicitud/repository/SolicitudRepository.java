package com.banqueteria.service_solicitud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banqueteria.service_solicitud.model.Solicitud;


public interface SolicitudRepository extends JpaRepository < Solicitud, Long>{

}
