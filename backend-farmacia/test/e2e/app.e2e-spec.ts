import { Controller, Get, INestApplication, NotFoundException } from '@nestjs/common';
import { Test, TestingModule } from '@nestjs/testing';
import request from 'supertest';

import { AppConstants } from '../../src/app/common/constants/app.constants';
import { HttpExceptionFilter } from '../../src/app/common/filters/http-exception.filter';
import { ApiResponseInterceptor } from '../../src/app/common/interceptors/api-response.interceptor';
import { CorrelationIdInterceptor } from '../../src/app/common/interceptors/correlation-id.interceptor';

/**
 * Controlador de prueba para validar el pipeline HTTP global.
 */
@Controller()
class TestAppController {
  /**
   * Endpoint exitoso simple.
   *
   * @returns Cuerpo base que luego será envuelto por el interceptor global.
   */
  @Get('salud')
  getHealth() {
    return {
      data: {
        status: 'ok',
      },
      message: 'Servicio operativo.',
    };
  }

  /**
   * Endpoint que dispara una excepción HTTP controlada.
   */
  @Get('error')
  getError() {
    throw new NotFoundException('Recurso de prueba no encontrado.');
  }
}

/**
 * Módulo de prueba para e2e aislado.
 */
@Reflect.metadata('design:paramtypes', [])
class TestAppModule {}

describe('App e2e', () => {
  let app: INestApplication;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      controllers: [TestAppController],
    }).compile();

    app = moduleFixture.createNestApplication();

    app.useGlobalInterceptors(
      new CorrelationIdInterceptor(),
      new ApiResponseInterceptor(),
    );

    app.useGlobalFilters(new HttpExceptionFilter());

    await app.init();
  });

  afterAll(async () => {
    await app.close();
  });

  it('GET /salud debe responder con envelope estándar y correlationId', async () => {
    const response = await request(app.getHttpServer())
      .get('/salud')
      .expect(200);

    expect(response.headers[AppConstants.HEADERS.CORRELATION_ID]).toBeDefined();
    expect(response.body.ok).toBe(true);
    expect(response.body.message).toBe('Servicio operativo.');
    expect(response.body.data).toEqual({
      status: 'ok',
    });
    expect(response.body.correlationId).toBe(
      response.headers[AppConstants.HEADERS.CORRELATION_ID],
    );
    expect(typeof response.body.timestamp).toBe('string');
  });

  it('GET /error debe responder con envelope estándar de error', async () => {
    const response = await request(app.getHttpServer())
      .get('/error')
      .expect(404);

    expect(response.headers[AppConstants.HEADERS.CORRELATION_ID]).toBeDefined();
    expect(response.body.ok).toBe(false);
    expect(response.body.statusCode).toBe(404);
    expect(response.body.message).toContain('Recurso de prueba no encontrado');
    expect(response.body.path).toBe('/error');
    expect(response.body.correlationId).toBe(
      response.headers[AppConstants.HEADERS.CORRELATION_ID],
    );
    expect(typeof response.body.timestamp).toBe('string');
  });
});
