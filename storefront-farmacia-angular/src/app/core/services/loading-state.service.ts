import { Injectable, Signal, computed, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoadingStateService {
  private readonly pendingRequests = signal(0);

  readonly activeRequests: Signal<number> = computed(() => this.pendingRequests());
  readonly isLoading: Signal<boolean> = computed(() => this.pendingRequests() > 0);

  startRequest(): void {
    this.pendingRequests.update((value) => value + 1);
  }

  finishRequest(): void {
    this.pendingRequests.update((value) => Math.max(0, value - 1));
  }

  reset(): void {
    this.pendingRequests.set(0);
  }
}
