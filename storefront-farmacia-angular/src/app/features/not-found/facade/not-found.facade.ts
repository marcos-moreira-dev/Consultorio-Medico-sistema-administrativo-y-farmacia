import { Injectable } from '@angular/core';

export interface NotFoundActionModel {
  label: string;
  routerLink: string;
}

@Injectable({
  providedIn: 'root',
})
export class NotFoundFacade {
  getActions(): NotFoundActionModel[] {
    return [
      {
        label: 'Ir al inicio',
        routerLink: '/',
      },
      {
        label: 'Ver catálogo',
        routerLink: '/catalogo',
      },
    ];
  }
}
