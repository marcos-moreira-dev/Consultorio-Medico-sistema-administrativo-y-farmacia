import { Routes } from '@angular/router';

import { AdminPageComponent } from './features/admin/pages/admin-page.component';
import { CatalogoPageComponent } from './features/catalogo/pages/catalogo-page.component';
import { HomePageComponent } from './features/home/pages/home-page.component';
import { NotFoundPageComponent } from './features/not-found/pages/not-found-page.component';
import { ProductoDetallePageComponent } from './features/producto-detalle/pages/producto-detalle-page.component';
import { PublicLayoutComponent } from './layouts/public-layout/public-layout.component';

export const routes: Routes = [
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      {
        path: '',
        component: HomePageComponent,
        title: 'La Alameda Farma',
      },
      {
        path: 'catalogo',
        component: CatalogoPageComponent,
        title: 'Catálogo | La Alameda Farma',
      },
      {
        path: 'producto/:productoId',
        component: ProductoDetallePageComponent,
        title: 'Producto | La Alameda Farma',
      },
      {
        path: 'admin',
        component: AdminPageComponent,
        title: 'Admin | La Alameda Farma',
      },
      {
        path: '**',
        component: NotFoundPageComponent,
        title: 'Página no encontrada | La Alameda Farma',
      },
    ],
  },
];
