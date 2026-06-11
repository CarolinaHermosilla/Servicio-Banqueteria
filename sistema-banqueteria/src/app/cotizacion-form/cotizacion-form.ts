import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Cotizacion } from '../cotizacion'; // Tu servicio de conexión con el backend
import { Solicitud } from '../solicitud.model';

@Component({
  selector: 'app-cotizacion-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './cotizacion-form.html',
  styleUrl: './cotizacion-form.css',
})
export class CotizacionForm implements OnInit {

  cotizacionForm!: FormGroup;
  tiposDeEvento = ['Matrimonio', 'Corporativo', 'Cóctel', 'Cena Privada'];
  tiposDeMenu = ['Basico', 'Normal', 'Grande'];

  // Listas maestras para alimentar los selectores dinámicos del menú culinario y líquidos
  opcionesCoctel = ['Canapés de Salmón y Ciboulette', 'Tapaditos Ave-Pimentón', 'Brochetas Caprese'];
  opcionesEntrada = ['Empanadas de Pino Mini', 'Quiche de Espinaca', 'Ceviche de Reineta'];
  opcionesFondo = ['Papas Duquesas Caseras', 'Arroz Árabe Perfumado', 'Verduras Salteadas al Wok'];
  opcionesProteina = ['Medallón de Filete', 'Pechuga de Ave Rellena', 'Salmón a la Plancha'];
  opcionesPostre = ['Mousse de Maracuyá', 'Suspiro Limeño', 'Torta Pompadour'];
  opcionesBebestibles = [
    'Solo Jugos y Bebidas Analcohólicas', 
    'Vino Reserva y Bebidas', 
    'Bar Abierto Básico (Pisco, Ron, Vodka)', 
    'Bar Abierto Premium (Mixología y Destilados)'
  ];

  constructor(
    private fb: FormBuilder, 
    private route: ActivatedRoute, 
    private cotizacionService: Cotizacion
  ) {}

  ngOnInit(): void {
    this.cotizacionForm = this.fb.group({
      // Datos Generales del Evento
      tipoEvento: ['', Validators.required],
      cantidadPersonas: ['', [Validators.required, Validators.min(10)]],
      restriccionesAlimentarias: [''],
      cantRestricciones: [''],
      tipoMenu: ['', Validators.required],
      fechaEvento: ['', Validators.required],

      // Desglose del Menú y Bebidas
      seleccionCoctel: ['', Validators.required],
      seleccionEntrada: ['', Validators.required],
      seleccionFondo: ['', Validators.required],
      seleccionProteina: ['', Validators.required],
      seleccionPostre: ['', Validators.required],
      seleccionBebestibles: ['', Validators.required],

      // Subgrupo de Contacto
      datosContacto: this.fb.group({
        nombre: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        telefono: ['', [Validators.required, Validators.pattern('^[0-9+]{9,12}$')]]
      })
    });

    // Capturar parámetro de la URL si el usuario viene de otra vista
    this.route.queryParams.subscribe(params => {
      const eventoDesdeUrl = params['evento'];
      if (eventoDesdeUrl) {
        this.cotizacionForm.patchValue({
          tipoEvento: eventoDesdeUrl
        });
      }
    });
  }

  enviarSolicitud(): void {
    if (this.cotizacionForm.valid) {
      const formValue = this.cotizacionForm.value;
      
      // ⬇️ Al declarar ": Solicitud", TypeScript nos obliga a que quede perfecto
      const payloadPlano = {
        cliente: formValue.datosContacto.nombre, 
        tipoEvento: formValue.tipoEvento,
        totalInvitados: formValue.cantidadPersonas,
        invitadosConRestriccion: Number(formValue.cantRestricciones) || 0, 
        fechaEvento: formValue.fechaEvento,
        varianteMenu: formValue.tipoMenu.toUpperCase(), 

        // Selecciones de platillos y bebestibles (asegúrate de que termine en 's')
        seleccionCoctel: formValue.seleccionCoctel,
        seleccionEntrada: formValue.seleccionEntrada,
        seleccionFondo: formValue.seleccionFondo,
        seleccionProteina: formValue.seleccionProteina,
        seleccionPostre: formValue.seleccionPostre,
        seleccionBebestibles: formValue.seleccionBebestibles,

        // Agregamos el estado inicial por defecto
        estado: 'PENDIENTE'
      } as Solicitud;

      // Llamada al servicio HTTP
      this.cotizacionService.enviarCotizacion(payloadPlano).subscribe({
        next: (res: any) => {
          alert(`¡Cotización enviada con éxito! ID #${res.idCotizacion || res.id}`);
          this.cotizacionForm.reset();
        },
        error: (err: any) => {
          console.error('Error al enviar la cotización:', err);
          alert('Hubo un error al procesar tu solicitud. Revisa la consola para más detalles.');
        }
      });
    } else {
      this.cotizacionForm.markAllAsTouched();
    }
  }
}