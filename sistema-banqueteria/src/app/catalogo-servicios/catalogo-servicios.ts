import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface ExperienciaBanqueteria {
  titulo: string;
  descripcion: string;
  imagenUrl: string;
  caracteristicas: string[];
}



@Component({
  selector: 'app-catalogo-servicios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './catalogo-servicios.html',
  styleUrl: './catalogo-servicios.css',
})
export class CatalogoServicios {

  constructor(private router: Router){}

  experiencias: ExperienciaBanqueteria[] = [
    {
      titulo: 'Matrimonios',
      descripcion: 'Servicio integral de alta gastronomía diseñado para grandes galas y celebraciones nupciales memorables.',
      imagenUrl: 'https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?q=80&w=600&auto=format&fit=crop',
      caracteristicas: ['Cena de 3 tiempos', 'Cóctel de bienvenida', 'Bar abierto personalizado']
    },
    {
      titulo: 'Eventos Corporativos',
      descripcion: 'Soluciones ágiles y sofisticadas para lanzamientos de marcas, congresos, seminarios y reuniones de negocios.',
      imagenUrl: 'https://images.unsplash.com/photo-1511578314322-379afb476865?q=80&w=600&auto=format&fit=crop',
      caracteristicas: ['Coffee break continuo', 'Almuerzo Ejecutivo buffet', 'Estaciones de finger-food']
    },
    {
      titulo: 'Cocktail Lounge',
      descripcion: 'Una experiencia moderna, fluida e interactiva ideal para celebraciones informales de alta sofisticación.',
      imagenUrl: 'https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?q=80&w=600&auto=format&fit=crop',
      caracteristicas: ['Bocados fríos y calientes', 'Mixología de autor', 'Estaciones temáticas en vivo']
    }
  ];

  iniciarCotizacion(Servicio: string): void {
    console.log('Cliente ingresado en: ${servicio}');
    let tipoMapeado =  'Matrimonio';
    if(Servicio.includes('Corporativo')) tipoMapeado='Corporativo';
    if(Servicio.includes('Cocktail')) tipoMapeado='Cóctel';
    this.router.navigate(['/cotizar'],{queryParams: {evento: tipoMapeado}});
  }
}
