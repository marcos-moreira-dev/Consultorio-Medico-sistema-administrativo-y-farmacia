import { LoggerService } from '../../../core/observability/logging/logger.service';

describe('LoggerService', () => {
  let service: LoggerService;

  beforeEach(() => {
    service = new LoggerService();
  });

  it('debe delegar mensajes informativos a console.info', () => {
    const spy = spyOn(console, 'info');

    service.info('Prueba de log', { ok: true });

    expect(spy).toHaveBeenCalled();
  });

  it('debe delegar errores a console.error', () => {
    const spy = spyOn(console, 'error');

    service.error('Prueba de error', { ok: false });

    expect(spy).toHaveBeenCalled();
  });
});
