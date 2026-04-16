import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';

import { PageTitleService } from '../../../core/services/page-title.service';
import { RouteAnalyticsService } from '../../../core/services/route-analytics.service';
import { SeoMetaService } from '../../../core/services/seo-meta.service';
import { BreadcrumbItemModel } from '../../../shared/models/breadcrumb-item.model';
import { BreadcrumbsComponent } from '../../../shared/ui/navigation/breadcrumbs/breadcrumbs.component';
import { NotFoundActionModel, NotFoundFacade } from '../facade/not-found.facade';

@Component({
  selector: 'app-not-found-page',
  standalone: true,
  imports: [CommonModule, RouterLink, BreadcrumbsComponent],
  templateUrl: './not-found-page.component.html',
  styleUrls: ['./not-found-page.component.css'],
})
export class NotFoundPageComponent {
  private readonly facade = inject(NotFoundFacade);
  private readonly pageTitleService = inject(PageTitleService);
  private readonly seoMetaService = inject(SeoMetaService);
  private readonly routeAnalyticsService = inject(RouteAnalyticsService);

  protected readonly actions: NotFoundActionModel[] = this.facade.getActions();
  protected readonly breadcrumbs: BreadcrumbItemModel[] = [
    {
      label: 'Inicio',
      routerLink: ['/'],
    },
    {
      label: '404',
      current: true,
    },
  ];

  constructor() {
    this.pageTitleService.setTitle('Página no encontrada');
    this.seoMetaService.updateDescription(
      'La ruta solicitada no existe dentro del storefront público de La Alameda Farma.',
    );
    this.routeAnalyticsService.trackView('not_found_page_viewed');
  }

  protected trackByLabel(_index: number, item: NotFoundActionModel): string {
    return item.label;
  }
}
