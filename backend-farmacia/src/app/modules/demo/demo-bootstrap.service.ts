import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { RolAdminEnum } from '../../common/enums/rol-admin.enum';
import { UsuarioAdminEntity } from '../auth-admin/entities/usuario-admin.entity';
import { CategoriaEntity } from '../categorias/entities/categoria.entity';
import { ProductoEntity } from '../productos/entities/producto.entity';

/**
 * Semillas de productos para el catálogo demo.
 */
interface DemoProductSeed {
  categoria: string;
  nombre: string;
  presentacion: string;
  descripcion: string;
  precio: number;
  disponibilidad: string;
}

const DEMO_PRODUCTS: DemoProductSeed[] = [
  { categoria: 'Analgésicos', nombre: 'Paracetamol', presentacion: 'Caja 20 tabletas 500 mg', descripcion: 'Analgésico y antipirético de uso común.', precio: 3.5, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Analgésicos', nombre: 'Ibuprofeno', presentacion: 'Caja 10 cápsulas 400 mg', descripcion: 'Antiinflamatorio y analgésico de uso frecuente.', precio: 4.2, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Vitaminas', nombre: 'Vitamina C', presentacion: 'Frasco 100 tabletas', descripcion: 'Suplemento vitamínico de consumo habitual.', precio: 6.8, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Vitaminas', nombre: 'Complejo B', presentacion: 'Caja 30 tabletas', descripcion: 'Suplemento vitamínico para apoyo general.', precio: 7.9, disponibilidad: 'AGOTADO' },
  { categoria: 'Higiene personal', nombre: 'Alcohol antiséptico', presentacion: 'Botella 250 ml', descripcion: 'Producto de higiene y desinfección básica.', precio: 2.75, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Digestivos', nombre: 'Antiácido masticable', presentacion: 'Caja 12 tabletas', descripcion: 'Producto para malestares digestivos leves.', precio: 5.1, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Primeros auxilios', nombre: 'Venda elástica', presentacion: 'Unidad mediana', descripcion: 'Insumo básico de primeros auxilios.', precio: 3.1, disponibilidad: 'DISPONIBLE' },
  { categoria: 'Primeros auxilios', nombre: 'Gasas estériles', presentacion: 'Paquete x 20', descripcion: 'Insumo de uso común para curación básica.', precio: 2.4, disponibilidad: 'DISPONIBLE' },
];

const CATEGORY_DESCRIPTIONS: Record<string, string> = {
  'Analgésicos': 'Productos orientados al alivio del dolor y molestias generales',
  'Vitaminas': 'Suplementos y complejos vitamínicos de venta habitual',
  'Higiene personal': 'Productos de cuidado e higiene de uso frecuente',
  'Digestivos': 'Productos orientados a malestares digestivos leves',
  'Primeros auxilios': 'Insumos y productos básicos de atención inicial',
};

/**
 * Servicio de bootstrap de datos demo para farmacia.
 *
 * <p>Solo ejecuta seeds cuando la aplicación corre en entornos de desarrollo
 * o demo (NODE_ENV=development|demo). En producción (NODE_ENV=production),
 * este servicio no inserta ningún dato para evitar contaminación del
 * catálogo real con información de prueba.</p>
 *
 * <p>La decisión de usar profile en vez de eliminar DemoModule completamente
 * del graph de NestJS permite que el módulo exista en todos los entornos
 * pero su comportamiento sea un no-op en producción, simplificando la
 * configuración de imports sin sacrificar seguridad.</p>
 */
@Injectable()
export class DemoBootstrapService implements OnModuleInit {
  private readonly logger = new Logger(DemoBootstrapService.name);

  constructor(
    private readonly configService: ConfigService,
    @InjectRepository(CategoriaEntity)
    private readonly categoriaRepository: Repository<CategoriaEntity>,
    @InjectRepository(ProductoEntity)
    private readonly productoRepository: Repository<ProductoEntity>,
    @InjectRepository(UsuarioAdminEntity)
    private readonly usuarioAdminRepository: Repository<UsuarioAdminEntity>,
  ) {}

  /**
   * Hook de inicialización del módulo.
   *
   * <p>Verifica que el entorno permita bootstrap demo antes de ejecutar
   * cualquier inserción. Esto previene que datos de prueba aparezcan
   * en producción aunque el módulo esté importado en AppModule.</p>
   */
  async onModuleInit(): Promise<void> {
    const nodeEnv = this.configService.get<string>('NODE_ENV', 'development');

    /*
     * Solo ejecutar seeds en desarrollo o demo.
     * En producción, este servicio se silencia completamente.
     */
    if (nodeEnv === 'production') {
      this.logger.debug('DemoBootstrapService deshabilitado en producción.');
      return;
    }

    try {
      await this.ensureAdminUser();
      await this.ensurePublicCatalog();
    } catch (error) {
      this.logger.warn(`No se pudo completar el bootstrap demo de farmacia: ${String(error)}`);
    }
  }

  private async ensureAdminUser(): Promise<void> {
    const existing = await this.usuarioAdminRepository.findOne({ where: { username: 'admin.farmacia' } });
    if (existing) return;

    const admin = this.usuarioAdminRepository.create({
      username: 'admin.farmacia',
      passwordHash: '$2b$10$49gLr/bOurpiX1MJLe3wZOvAby8K8cCV.K6M5AYFjWyKuQtySYN.y',
      email: 'admin@farmacia.local',
      estado: 'ACTIVO',
      rolCodigo: RolAdminEnum.ADMIN_FARMACIA,
    });
    await this.usuarioAdminRepository.save(admin);
    this.logger.log('Usuario admin demo de farmacia sembrado automáticamente.');
  }

  private async ensurePublicCatalog(): Promise<void> {
    const publicCount = await this.productoRepository.count({
      where: {
        estadoProducto: 'ACTIVO',
        esPublicable: true,
      },
    });

    if (publicCount >= 7) {
      this.logger.log(`Catálogo público de farmacia ya disponible con ${publicCount} productos.`);
      return;
    }

    const categoryMap = new Map<string, CategoriaEntity>();
    for (const [nombre, descripcion] of Object.entries(CATEGORY_DESCRIPTIONS)) {
      let categoria = await this.categoriaRepository.findOne({ where: { nombreCategoria: nombre } });
      if (!categoria) {
        categoria = await this.categoriaRepository.save(
          this.categoriaRepository.create({ nombreCategoria: nombre, descripcionBreve: descripcion ?? null }),
        );
      }
      categoryMap.set(nombre, categoria);
    }

    for (const seed of DEMO_PRODUCTS) {
      const categoria = categoryMap.get(seed.categoria);
      if (!categoria) continue;

      const existing = await this.productoRepository.findOne({
        where: {
          categoriaId: categoria.categoriaId,
          nombreProducto: seed.nombre,
          presentacion: seed.presentacion,
        },
      });

      if (existing) {
        existing.descripcionBreve = seed.descripcion;
        existing.precioVisible = seed.precio;
        existing.estadoProducto = 'ACTIVO';
        existing.esPublicable = true;
        existing.estadoDisponibilidad = seed.disponibilidad;
        await this.productoRepository.save(existing);
        continue;
      }

      await this.productoRepository.save(
        this.productoRepository.create({
          categoriaId: categoria.categoriaId,
          nombreProducto: seed.nombre,
          presentacion: seed.presentacion,
          descripcionBreve: seed.descripcion,
          precioVisible: seed.precio,
          estadoProducto: 'ACTIVO',
          esPublicable: true,
          estadoDisponibilidad: seed.disponibilidad,
        }),
      );
    }

    const refreshedCount = await this.productoRepository.count({
      where: {
        estadoProducto: 'ACTIVO',
        esPublicable: true,
      },
    });
    this.logger.log(`Catálogo demo de farmacia asegurado con ${refreshedCount} productos visibles.`);
  }
}
