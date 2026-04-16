import { Injectable, inject } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { storefrontAppConfig } from '../config/app.config';

@Injectable({
  providedIn: 'root',
})
export class PageTitleService {
  private readonly title = inject(Title);

  setTitle(pageTitle: string): void {
    this.title.setTitle(`${pageTitle} | ${storefrontAppConfig.appName}`);
  }
}
