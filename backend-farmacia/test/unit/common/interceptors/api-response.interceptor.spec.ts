import { ExecutionContext } from '@nestjs/common';
import { of, firstValueFrom } from 'rxjs';

import { ApiResponseInterceptor } from '../../../../src/app/common/interceptors/api-response.interceptor';

describe('ApiResponseInterceptor', () => {
  let interceptor: ApiResponseInterceptor;

  beforeEach(() => {
    interceptor = new ApiResponseInterceptor();
  });

  function buildExecutionContext(correlationId = 'corr-123'): ExecutionContext {
    return {
      switchToHttp: () => ({
        getRequest: () => ({
          correlationId,
        }),
      }),
    } as ExecutionContext;
  }

  it('debe envolver una respuesta simple como data', async () => {
    const context = buildExecutionContext();
    const next = {
      handle: () => of({ hola: 'mundo' }),
    };

    const result = await firstValueFrom(interceptor.intercept(context, next as any));

    expect(result).toMatchObject({
      ok: true,
      data: { hola: 'mundo' },
      correlationId: 'corr-123',
    });
    expect(typeof (result as any).timestamp).toBe('string');
  });

  it('debe respetar un body con data y message', async () => {
    const context = buildExecutionContext('corr-abc');
    const next = {
      handle: () =>
        of({
          message: 'Operación correcta.',
          data: { id: 1 },
          meta: { total: 1 },
        }),
    };

    const result = await firstValueFrom(interceptor.intercept(context, next as any));

    expect(result).toMatchObject({
      ok: true,
      message: 'Operación correcta.',
      data: { id: 1 },
      meta: { total: 1 },
      correlationId: 'corr-abc',
    });
    expect(typeof (result as any).timestamp).toBe('string');
  });

  it('debe completar correlationId y timestamp si la respuesta ya está estandarizada', async () => {
    const context = buildExecutionContext('corr-final');
    const next = {
      handle: () =>
        of({
          ok: true,
          data: { id: 10 },
        }),
    };

    const result = await firstValueFrom(interceptor.intercept(context, next as any));

    expect(result).toMatchObject({
      ok: true,
      data: { id: 10 },
      correlationId: 'corr-final',
    });
    expect(typeof (result as any).timestamp).toBe('string');
  });
});
