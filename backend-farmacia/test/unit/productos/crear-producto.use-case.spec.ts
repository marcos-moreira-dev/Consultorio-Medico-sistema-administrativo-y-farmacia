import { CrearProductoUseCase } from '../../../src/app/modules/productos/use-cases/crear-producto.use-case';
import { ProductosService } from '../../../src/app/modules/productos/services/productos.service';
import { EstadoDisponibilidadEnum } from '../../../src/app/common/enums/estado-disponibilidad.enum';
import { EstadoProductoEnum } from '../../../src/app/common/enums/estado-producto.enum';

describe('CrearProductoUseCase', () => {
  let useCase: CrearProductoUseCase;
  let productosService: jest.Mocked<ProductosService>;

  beforeEach(() => {
    productosService = {
      crear: jest.fn(),
    } as unknown as jest.Mocked<ProductosService>;

    useCase = new CrearProductoUseCase(productosService);
  });

  it('debe delegar la creación al servicio y devolver su resultado', async () => {
    const request = {
      categoriaId: 1,
      nombreProducto: 'Paracetamol',
      presentacion: 'Caja x 20 tabletas',
      descripcionBreve: 'Analgésico y antipirético de uso común.',
      precioVisible: 3.5,
    };

    const expectedResponse = {
      productoId: 1,
      categoriaId: 1,
      nombreCategoria: 'Analgésicos',
      nombreProducto: 'Paracetamol',
      presentacion: 'Caja x 20 tabletas',
      descripcionBreve: 'Analgésico y antipirético de uso común.',
      precioVisible: 3.5,
      estadoProducto: EstadoProductoEnum.ACTIVO,
      esPublicable: false,
      estadoDisponibilidad: EstadoDisponibilidadEnum.NO_PUBLICADO,
      fechaCreacion: '2026-04-08T15:30:00.000Z',
      fechaActualizacion: '2026-04-08T15:30:00.000Z',
    };

    productosService.crear.mockResolvedValue(expectedResponse);

    const result = await useCase.execute(request);

    expect(productosService.crear).toHaveBeenCalledTimes(1);
    expect(productosService.crear).toHaveBeenCalledWith(request);
    expect(result).toEqual(expectedResponse);
  });
});
