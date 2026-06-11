import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // 👈 Agregamos ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { Cotizacion } from '../cotizacion'; 
import { Solicitud } from '../solicitud.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard implements OnInit {

  solicitudes: Solicitud[] = [];
  solicitudSeleccionada: Solicitud | null = null;

  // 👈 Inyectamos 'cdr' en el constructor
  constructor(
    private cotizacionService: Cotizacion,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    this.cotizacionService.obtenerSolicitudesPendientes().subscribe({
      next: (data: any) => {
        console.log('📦 Paquete recibido desde Spring Boot:', data);
        
        // Asignamos la data dentro de un set-timeout para asegurar el ciclo de Angular
        setTimeout(() => {
          this.solicitudes = data;
          this.cdr.detectChanges(); // 👈 OBLIGAMOS A ANGULAR A PINTAR LA TABLA
        }, 0);
      },
      error: (err) => console.error('🚨 Error al cargar solicitudes:', err)
    });
  }

  verDetalle(solicitud: Solicitud): void {
    this.solicitudSeleccionada = solicitud;
    this.cdr.detectChanges();
  }

  cerrarDetalle(): void {
    this.solicitudSeleccionada = null;
    this.cdr.detectChanges();
  }

  imprimirDetalle(): void{
    window.print();
  }

  ponerEnEspera(idCotizacion: number | undefined): void {
    if (!idCotizacion) return;

    if (confirm(`¿Estás seguro de poner la cotización #${idCotizacion} EN ESPERA para revisión de inventario?`)) {
      this.cotizacionService.cambiarEstado(idCotizacion, 'EN_ESPERA').subscribe({
        next: (solicitudActualizada) => {
          alert(`La solicitud #${idCotizacion} está ahora en estado EN ESPERA.`);
          
          if (this.solicitudSeleccionada && this.solicitudSeleccionada.idCotizacion === idCotizacion) {
            this.solicitudSeleccionada.estado = 'EN_ESPERA';
          }
          
          this.cargarSolicitudes();
        },
        error: (err) => {
          console.error('Error al cambiar estado:', err);
          alert('No se pudo actualizar el estado de la cotización.');
        }
      });
    }
  }

  aprobarMinuta(idCotizacion: number | undefined): void {
    if (!idCotizacion) return;

    if (confirm(`¿Estás seguro de APROBAR formalmente la cotización #${idCotizacion}?`)) {
      this.cotizacionService.cambiarEstado(idCotizacion, 'CONFIRMADO').subscribe({
        next: (solicitudActualizada) => {
          alert(`La solicitud #${idCotizacion} ha sido APROBADA y confirmada con éxito.`);
          
          // Actualiza el estado visual inmediatamente en el panel de detalle
          if (this.solicitudSeleccionada && this.solicitudSeleccionada.idCotizacion === idCotizacion) {
            this.solicitudSeleccionada.estado = 'CONFIRMADO';
          }
          
          // Refresca la tabla principal automáticamente
          this.cargarSolicitudes();
        },
        error: (err) => {
          console.error('Error al aprobar la cotización:', err);
          alert('No se pudo actualizar el estado a CONFIRMADO.');
        }
      });
    }
  }
}