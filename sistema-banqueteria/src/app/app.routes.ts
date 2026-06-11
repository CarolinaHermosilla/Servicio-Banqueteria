import { Routes } from '@angular/router';
import { CatalogoServicios } from './catalogo-servicios/catalogo-servicios';
import { CotizacionForm } from './cotizacion-form/cotizacion-form';
import { AdminDashboard } from './admin-dashboard/admin-dashboard';

export const routes: Routes = [

    //en el path principa se mostrará el catálogo de experiencias

    {path: '', component: CatalogoServicios},

    //path /cotizar mostará el catalogo
    {path: 'cotizar', component:CotizacionForm},

    //path /intranet-admin mostará la vista de admin
    {path: 'intranet-admin', component:AdminDashboard},

    //path para cubrir errores
    {path: '**', redirectTo:''}
];
