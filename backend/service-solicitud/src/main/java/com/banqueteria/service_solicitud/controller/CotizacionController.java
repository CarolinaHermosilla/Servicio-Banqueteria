package com.banqueteria.service_solicitud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banqueteria.service_solicitud.model.MinutaCalculada;
import com.banqueteria.service_solicitud.model.Solicitud;
import com.banqueteria.service_solicitud.service.MinutaMotorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/cotizaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class CotizacionController {

    @Autowired
    MinutaMotorService mms;

    @PostMapping()
    public Solicitud registrarCotizacion (@RequestBody Solicitud soli){
        MinutaCalculada minutaCalculada = mms.procesarReglasNegocio(soli);
        soli.setMinutaCalculada(minutaCalculada);

        return mms.registrarSolicitud(soli);
    }

    @GetMapping("/pendientes")
    public List<Solicitud> obtenerSolicitudesPendientes(){
        return mms.listarSolicitudes();
    }


    
    
    
}
