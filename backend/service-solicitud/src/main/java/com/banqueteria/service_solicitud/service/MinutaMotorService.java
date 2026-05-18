package com.banqueteria.service_solicitud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banqueteria.service_solicitud.model.MinutaCalculada;
import com.banqueteria.service_solicitud.model.Solicitud;
import com.banqueteria.service_solicitud.repository.MinutaCalculadaRepository;
import com.banqueteria.service_solicitud.repository.SolicitudRepository;

@Service
public class MinutaMotorService {
    @Autowired
    MinutaCalculadaRepository mcr;

    @Autowired
    SolicitudRepository sr;

    public MinutaCalculada procesarReglasNegocio(Solicitud soli){
        MinutaCalculada minuta = new MinutaCalculada();
        int invitados = soli.getTotalInvitados();

        minuta.setCanapesSurtidos(invitados * 5);
        minuta.setEmpanaditasCoctel(invitados * 3);

        minuta.setGramosFilete(invitados * 200);
        minuta.setPorcionesPapasGratinadas(invitados);

        minuta.setPorcionesMousse(invitados);

        minuta.setSolicitud(soli);
        return minuta;
    }

    public Solicitud registrarSolicitud(Solicitud solo){
        return sr.save(solo);
    }

    public List<Solicitud> listarSolicitudes(){
        return sr.findAll();
    }
}
