import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CatalogoServicios } from './catalogo-servicios/catalogo-servicios';
import { CotizacionForm } from './cotizacion-form/cotizacion-form';
import { AdminDashboard } from './admin-dashboard/admin-dashboard';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CotizacionForm, CatalogoServicios, AdminDashboard],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('sistema-banqueteria');
}
