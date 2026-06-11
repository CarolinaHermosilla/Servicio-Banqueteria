package com.banqueteria.service_solicitud.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banqueteria.service_solicitud.dto.SolicitudDTO;
import com.banqueteria.service_solicitud.model.*;
import com.banqueteria.service_solicitud.repository.SolicitudRepository;

@Service
public class MinutaMotorService {

    @Autowired
    private SolicitudRepository sr;

    public SolicitudDTO procesarReglasNegocioYGuardar(SolicitudDTO dto) {
        int invitados = dto.getTotalInvitados();

        // --- 1. CONFIGURACIÓN DE MULTIPLICADORES ---
        double factorComida = 1.0;
        double factorAlcohol = 1.0;
        int factorJugosGaseosas = 2;
        String variante = dto.getVarianteMenu() != null ? dto.getVarianteMenu().toUpperCase() : "BASICO";

        if ("INTERMEDIO".equals(variante) || "MEDIO".equals(variante)) {
            factorComida = 1.5; factorAlcohol = 1.5; factorJugosGaseosas = 3;
        } else if ("PREMIUM".equals(variante) || "GRANDE".equals(variante)) {
            factorComida = 2.0; factorAlcohol = 2.0; factorJugosGaseosas = 4;
        }

        // --- 2. MAPEO A ENTIDADES (CREAR TABLAS NORMALIZADAS) ---
        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(dto.getCliente());
        solicitud.setTipoEvento(dto.getTipoEvento());
        solicitud.setTotalInvitados(invitados);
        solicitud.setFechaEvento(dto.getFechaEvento());
        solicitud.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");
        solicitud.setVarianteMenu(variante);

        // Tabla: selecciones_menu
        SeleccionMenu menu = new SeleccionMenu();
        menu.setSeleccionCoctel(dto.getSeleccionCoctel());
        menu.setSeleccionEntrada(dto.getSeleccionEntrada());
        menu.setSeleccionFondo(dto.getSeleccionFondo());
        menu.setSeleccionProteina(dto.getSeleccionProteina());
        menu.setSeleccionPostre(dto.getSeleccionPostre());
        menu.setSeleccionBebestibles(dto.getSeleccionBebestibles());
        menu.setSolicitud(solicitud);
        solicitud.setSeleccionMenu(menu);

        // Tabla: excepciones_alimentarias
        ExcepcionAlimentaria excepcion = new ExcepcionAlimentaria();
        excepcion.setInvitadosConRestriccion(dto.getInvitadosConRestriccion());
        excepcion.setTipoRestriccion(dto.getTipoRestriccion());
        excepcion.setSolicitud(solicitud);
        solicitud.setExcepcionAlimentaria(excepcion);

        // Tabla: calculos_gastronomicos
        CalculoGastronomico gastro = new CalculoGastronomico();
        gastro.setTotalCoctelUnidades((int) Math.ceil(invitados * 2 * factorComida));
        gastro.setTotalEntradasUnidades((int) Math.ceil(invitados * 1 * factorComida));
        gastro.setTotalFondosPorciones(invitados);
        gastro.setTotalProteinaGramos((int) Math.ceil(invitados * 200 * factorComida));
        gastro.setTotalPostresPorciones(invitados);
        gastro.setSolicitud(solicitud);
        solicitud.setCalculoGastronomico(gastro);

        // Tabla: logistica_staff
        LogisticaStaff logistica = new LogisticaStaff();
        logistica.setCantidadGarzones((int) Math.ceil(invitados / 10.0));
        logistica.setCantidadChefs((int) Math.ceil(invitados / 50.0));
        logistica.setTotalMesas((int) Math.ceil(invitados / 10.0));
        logistica.setTotalCubiertos(invitados * 3);
        logistica.setSolicitud(solicitud);
        solicitud.setLogisticaStaff(logistica);

        // Tabla: calculos_bebestibles
        CalculoBebestibles bebestibles = new CalculoBebestibles();
        bebestibles.setTipoBebidaGaseosa(dto.getTipoBebidaGaseosa());
        bebestibles.setSaborJugo(dto.getSaborJugo());
        bebestibles.setTipoAlcohol(dto.getTipoAlcohol());
        bebestibles.setTotalPorcionesAlcohol((int) Math.ceil(invitados * factorAlcohol));
        bebestibles.setTotalPorcionesJugos(invitados * factorJugosGaseosas);
        bebestibles.setTotalPorcionesGaseosas(invitados * factorJugosGaseosas);
        bebestibles.setSolicitud(solicitud);
        solicitud.setCalculoBebestibles(bebestibles);

        // --- 3. GUARDAR EN BASE DE DATOS (Todo se guarda en cascada) ---
        Solicitud guardada = sr.save(solicitud);

        // --- 4. CONVERTIR DE VUELTA A DTO PARA ANGULAR ---
        return mapearADTO(guardada);
    }

    public List<SolicitudDTO> listarPorFechaEvento() {
        return sr.findAllByOrderByFechaEventoAsc()
                 .stream()
                 .map(this::mapearADTO)
                 .collect(Collectors.toList());
    }

    // Método utilitario para convertir Entidades complejas al DTO plano
    private SolicitudDTO mapearADTO(Solicitud sol) {
        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdCotizacion(sol.getIdCotizacion());
        dto.setCliente(sol.getCliente());
        dto.setTipoEvento(sol.getTipoEvento());
        dto.setTotalInvitados(sol.getTotalInvitados());
        dto.setFechaEvento(sol.getFechaEvento());
        dto.setEstado(sol.getEstado());
        dto.setVarianteMenu(sol.getVarianteMenu());

        if (sol.getExcepcionAlimentaria() != null) {
            dto.setInvitadosConRestriccion(sol.getExcepcionAlimentaria().getInvitadosConRestriccion());
            dto.setTipoRestriccion(sol.getExcepcionAlimentaria().getTipoRestriccion());
        }
        if (sol.getSeleccionMenu() != null) {
            dto.setSeleccionCoctel(sol.getSeleccionMenu().getSeleccionCoctel());
            dto.setSeleccionEntrada(sol.getSeleccionMenu().getSeleccionEntrada());
            dto.setSeleccionFondo(sol.getSeleccionMenu().getSeleccionFondo());
            dto.setSeleccionProteina(sol.getSeleccionMenu().getSeleccionProteina());
            dto.setSeleccionPostre(sol.getSeleccionMenu().getSeleccionPostre());
            dto.setSeleccionBebestibles(sol.getSeleccionMenu().getSeleccionBebestibles());
        }
        if (sol.getCalculoGastronomico() != null) {
            dto.setTotalCoctelUnidades(sol.getCalculoGastronomico().getTotalCoctelUnidades());
            dto.setTotalEntradasUnidades(sol.getCalculoGastronomico().getTotalEntradasUnidades());
            dto.setTotalFondosPorciones(sol.getCalculoGastronomico().getTotalFondosPorciones());
            dto.setTotalProteinaGramos(sol.getCalculoGastronomico().getTotalProteinaGramos());
            dto.setTotalPostresPorciones(sol.getCalculoGastronomico().getTotalPostresPorciones());
        }
        if (sol.getLogisticaStaff() != null) {
            dto.setTotalMesas(sol.getLogisticaStaff().getTotalMesas());
            dto.setTotalCubiertos(sol.getLogisticaStaff().getTotalCubiertos());
            dto.setCantidadGarzones(sol.getLogisticaStaff().getCantidadGarzones());
            dto.setCantidadChefs(sol.getLogisticaStaff().getCantidadChefs());
        }
        if (sol.getCalculoBebestibles() != null) {
            dto.setTipoBebidaGaseosa(sol.getCalculoBebestibles().getTipoBebidaGaseosa());
            dto.setSaborJugo(sol.getCalculoBebestibles().getSaborJugo());
            dto.setTipoAlcohol(sol.getCalculoBebestibles().getTipoAlcohol());
            dto.setTotalPorcionesGaseosas(sol.getCalculoBebestibles().getTotalPorcionesGaseosas());
            dto.setTotalPorcionesJugos(sol.getCalculoBebestibles().getTotalPorcionesJugos());
            dto.setTotalPorcionesAlcohol(sol.getCalculoBebestibles().getTotalPorcionesAlcohol());
        }
        return dto;
    }

    public SolicitudDTO actualizarEstado(Long id, String nuevoEstado) {
        // Busca la solicitud, si la encuentra le cambia el estado, la guarda y la convierte a DTO
        return sr.findById(id).map(solicitud -> {
            solicitud.setEstado(nuevoEstado);
            Solicitud guardada = sr.save(solicitud);
            return mapearADTO(guardada); // mapearADTO es el método que te pasé antes
        }).orElse(null);
    }
}