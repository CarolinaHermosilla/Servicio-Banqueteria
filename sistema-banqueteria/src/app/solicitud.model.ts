export interface Solicitud {
    // --- 1. DATOS GENERALES Y CONFIGURACIÓN DEL EVENTO ---
    idCotizacion?: number;
    cliente: string;
    tipoEvento: string;
    totalInvitados: number;
    fechaEvento: string;
    estado: string;        // "PENDIENTE", "EN_ESPERA", "CONFIRMADO"
    varianteMenu: string;  // "BASICO", "INTERMEDIO", "PREMIUM"

    // --- 2. EXCEPCIONES ALIMENTARIAS ---
    invitadosConRestriccion: number;
    tipoRestriccion?: string;

    // --- 3. SELECCIÓN DINÁMICA DE PLATILLOS ---
    seleccionCoctel: string;
    seleccionEntrada: string;
    seleccionFondo: string;
    seleccionProteina: string;
    seleccionPostre: string;
    seleccionBebestibles: string;

    // Bebestibles antiguos en Java (opcionales)
    tipoBebidaGaseosa?: string;
    saborJugo?: string;
    tipoAlcohol?: string;

    // --- 4. CÁLCULOS GASTRONÓMICOS (OUTPUT JAVA) ---
    totalCoctelUnidades: number;     
    totalEntradasUnidades: number;   
    totalFondosPorciones: number;    
    totalProteinaGramos: number;     
    totalPostresPorciones: number;   

    // --- 5. CÁLCULOS DE MOBILIARIO Y LOGÍSTICA ---
    totalMesas: number;
    totalCubiertos: number; // En tu Java se llama totalCubiertos, en el HTML decía totalUtensilios

    // --- 6. CÁLCULOS DE LÍQUIDOS Y BAR (OUTPUT JAVA) ---
    totalPorcionesGaseosas: number;
    totalPorcionesJugos: number;
    totalPorcionesAlcohol: number;

    // --- 7. RECURSOS HUMANOS / STAFFING (OUTPUT JAVA) ---
    cantidadGarzones: number; // En tu Java se llama cantidadGarzones, antes decía totalGarzones
    cantidadChefs: number;    // En tu Java se llama cantidadChefs, antes decía totalChefs
}