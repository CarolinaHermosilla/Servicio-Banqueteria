import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Solicitud } from './solicitud.model';

@Injectable({
  providedIn: 'root',
})
export class Cotizacion {

  private apiUrl = 'http://localhost:4419/api/v1/solicitudes'

  constructor(private http: HttpClient){}

  enviarCotizacion(datosFormulario : any): Observable<any>{
    return this.http.post(this.apiUrl, datosFormulario)
  }

  obtenerSolicitudesPendientes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }


  //cambiar estado de la solicitud
  cambiarEstado(id: number, nuevoEstado: string): Observable<Solicitud>{
    return this.http.put<Solicitud>(`${this.apiUrl}/${id}/estado?nuevoEstado=${nuevoEstado}`, {});
  }

}
