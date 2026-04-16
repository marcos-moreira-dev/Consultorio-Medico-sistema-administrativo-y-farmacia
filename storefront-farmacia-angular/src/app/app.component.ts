import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { LoadingStateService } from './core/services/loading-state.service';
import { LoadingBarComponent } from './shared/ui/loaders/loading-bar/loading-bar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoadingBarComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  private readonly loadingStateService = inject(LoadingStateService);

  protected readonly isLoading = this.loadingStateService.isLoading;
}
