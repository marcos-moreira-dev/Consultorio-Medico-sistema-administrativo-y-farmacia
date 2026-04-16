import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { CategoriaPublicaModel } from '../../../../shared/models/categoria-publica.model';

@Component({
  selector: 'app-featured-categories',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './featured-categories.component.html',
  styleUrls: ['./featured-categories.component.css'],
})
export class FeaturedCategoriesComponent {
  @Input() categories: CategoriaPublicaModel[] = [];
}
