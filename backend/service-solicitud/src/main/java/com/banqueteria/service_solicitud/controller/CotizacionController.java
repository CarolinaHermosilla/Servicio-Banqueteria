package com.banqueteria.service_solicitud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// IMPORTAMOS EL DTO en lugar de la entidad directa
import com.banqueteria.service_solicitud.dto.SolicitudDTO;
import com.banqueteria.service_solicitud.service.MinutaMotorService;

@RestController
@RequestMapping("/api/v1/solicitudes")
@CrossOrigin
public class CotizacionController {

    @Autowired
    MinutaMotorService mms;

    // Cambiamos 'Solicitud' por 'SolicitudDTO'
    @PostMapping()
    public ResponseEntity<SolicitudDTO> crearSolicitud (@RequestBody SolicitudDTO solicitudDTO){
        if (solicitudDTO.getEstado() == null) {
            solicitudDTO.setEstado("PENDIENTE");
        }

        // El servicio toma el DTO plano, lo divide en tus nuevas tablas, lo guarda
        // y te devuelve el DTO plano de nuevo para Angular.
        SolicitudDTO guardada = mms.procesarReglasNegocioYGuardar(solicitudDTO);

        return ResponseEntity.ok(guardada);
    }

    // Cambiamos List<Solicitud> por List<SolicitudDTO>
    @GetMapping()
    public ResponseEntity<List<SolicitudDTO>> listarSolicitudes(){
        List<SolicitudDTO> ordenadas = mms.listarPorFechaEvento();
        return ResponseEntity.ok(ordenadas);
    }

    // Cambiamos el PutMapping para que también devuelva un DTO
    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudDTO> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado){
        // Delegamos esta lógica al servicio para mantener el controlador limpio
        SolicitudDTO actualizada = mms.actualizarEstado(id, nuevoEstado);
        
        if(actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }
}