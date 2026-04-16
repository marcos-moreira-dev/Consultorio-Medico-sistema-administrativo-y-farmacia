import { PublicarProductoUseCase } from '../../../src/app/modules/disponibilidad-publicacion/use-cases/publicar-producto.use-case';
import { DisponibilidadPublicacionService } from '../../../src/app/modules/disponibilidad-publicacion/services/disponibilidad-publicacion.service';
import { EstadoDisponibilidadEnum } from '../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../src/app/common/enums/estado-producto.enum';
import { PublicarProductoRequestDto } from '../../../src/app/modules/disponibilidad-publicacion/dto/publicar-producto.request.dto';

describe('PublicarProductoUseCase', () => {
  let useCase: PublicarProductoUseCase;
  let disponibilidadPublicacionService: jest.Mocked<DisponibilidadPublicacionService>;

  beforeEach(() => {
    disponibilidadPublicacionService = {
      publicar: jest.fn(),
    } as unknown as jest.Mocked<DisponibilidadPublicacionService>;

    useCase = new PublicarProductoUseCase(disponibilidadPublicacionService);
  });

  it('debe delegar la publicación al servicio y devolver su resultado', async () => {
    const productoId = 25;
    const request: PublicarProductoRequestDto = {
      estadoDisponibilidadInicial: EstadoDisponibilidadEnum.DISPONIBLE,
    };

    const expectedResponse = {
      productoId,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: true,
      estadoDisponibilidad: EstadoDisponibilidadEnum.DISPONIBLE,
      estaPublicadoPublicamente: true,
      puedePublicarse: true,
      fechaActualizacion: '2026-04-08T15:30:00.000Z',
    };

    disponibilidadPublicacionService.publicar.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(productoId, request);

    expect(disponibilidadPublicacionService.publicar).toHaveBeenCalledTimes(1);
    expect(disponibilidadPublicacionService.publicar).toHaveBeenCalledWith(
      productoId,
      request,
    );
    expect(result).toEqual(expectedResponse);
  });
});
