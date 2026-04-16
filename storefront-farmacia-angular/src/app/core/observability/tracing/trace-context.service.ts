import { Injectable } from '@angular/core';

import { createTraceId } from './trace-id.util';

@Injectable({
  providedIn: 'root',
})
export class TraceContextService {
  private readonly sessionId = createTraceId();

  getSessionId(): string {
    return this.sessionId;
  }

  createRequestId(): string {
    return createTraceId();
  }

  createTraceId(): string {
    return createTraceId();
  }
}
