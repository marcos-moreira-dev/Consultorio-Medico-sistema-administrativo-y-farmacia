import { TestBed } from '@angular/core/testing';

import { LoadingStateService } from '../../../core/services/loading-state.service';

describe('LoadingStateService', () => {
  let service: LoadingStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingStateService);
  });

  it('should start and finish tracked requests', () => {
    service.startRequest();
    service.startRequest();

    expect(service.activeRequests()).toBe(2);
    expect(service.isLoading()).toBeTrue();

    service.finishRequest();
    service.finishRequest();

    expect(service.activeRequests()).toBe(0);
    expect(service.isLoading()).toBeFalse();
  });

  it('should never go below zero', () => {
    service.finishRequest();

    expect(service.activeRequests()).toBe(0);
    expect(service.isLoading()).toBeFalse();
  });
});
