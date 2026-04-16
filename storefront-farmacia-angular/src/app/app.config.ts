import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig } from '@angular/core';
import { provideRouter, withInMemoryScrolling } from '@angular/router';

import { apiErrorInterceptor } from './core/http/interceptors/api-error.interceptor';
import { loadingInterceptor } from './core/http/interceptors/loading.interceptor';
import { requestIdInterceptor } from './core/http/interceptors/request-id.interceptor';
import { traceContextInterceptor } from './core/http/interceptors/trace-context.interceptor';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(
      routes,
      withInMemoryScrolling({
        anchorScrolling: 'enabled',
        scrollPositionRestoration: 'enabled',
      }),
    ),
    provideHttpClient(
      withInterceptors([
        traceContextInterceptor,
        requestIdInterceptor,
        loadingInterceptor,
        apiErrorInterceptor,
      ]),
    ),
  ],
};
