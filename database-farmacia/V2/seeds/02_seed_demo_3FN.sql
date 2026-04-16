-- ============================================================
-- database-farmacia / Seeds demo V2 3FN
-- Archivo: 02_seed_demo_3FN.sql
-- Objetivo:
--   Cargar datos demo coherentes para estudio, pruebas locales
--   y demostración del subdominio farmacia.
--
-- Características del seed:
--   - Datos ficticios
--   - Categorías variadas
--   - Productos activos e inactivos
--   - Productos publicables y no publicables
--   - Disponibilidad variada
--   - Reservas activas, canceladas y concretadas
-- ============================================================

SET search_path TO farmacia;

-- ============================================================
-- SECCIÓN 1. CATEGORÍAS
-- ============================================================

INSERT INTO categoria (
    categoria_id, nombre_categoria, descripcion_breve
) VALUES
(1, 'Analgésicos', 'Productos orientados al alivio del dolor y molestias generales'),
(2, 'Vitaminas', 'Suplementos y complejos vitamínicos de venta habitual'),
(3, 'Higiene personal', 'Productos de cuidado e higiene de uso frecuente'),
(4, 'Digestivos', 'Productos orientados a malestares digestivos leves'),
(5, 'Primeros auxilios', 'Insumos y productos básicos de atención inicial');

SELECT setval(pg_get_serial_sequence('categoria', 'categoria_id'), COALESCE(MAX(categoria_id), 1), true)
FROM categoria;

-- ============================================================
-- SECCIÓN 2. PRODUCTOS
--
-- Se mezclan productos activos/inactivos, publicables/no publicables
-- y distintos estados de disponibilidad para practicar filtros.
-- ============================================================

INSERT INTO producto (
    producto_id, categoria_id, nombre_producto, presentacion,
    descripcion_breve, precio_visible, estado_producto,
    es_publicable, estado_disponibilidad
) VALUES
(1, 1, 'Paracetamol', 'Caja 20 tabletas 500 mg',
 'Analgésico y antipirético de uso común.', 3.50, 'ACTIVO', true, 'DISPONIBLE'),

(2, 1, 'Ibuprofeno', 'Caja 10 cápsulas 400 mg',
 'Antiinflamatorio y analgésico de uso frecuente.', 4.20, 'ACTIVO', true, 'DISPONIBLE'),

(3, 2, 'Vitamina C', 'Frasco 100 tabletas',
 'Suplemento vitamínico de consumo habitual.', 6.80, 'ACTIVO', true, 'DISPONIBLE'),

(4, 2, 'Complejo B', 'Caja 30 tabletas',
 'Suplemento vitamínico para apoyo general.', 7.90, 'ACTIVO', true, 'AGOTADO'),

(5, 3, 'Alcohol antiséptico', 'Botella 250 ml',
 'Producto de higiene y desinfección básica.', 2.75, 'ACTIVO', true, 'DISPONIBLE'),

(6, 3, 'Mascarilla desechable', 'Paquete x 10 unidades',
 'Elemento básico de protección e higiene.', 1.90, 'ACTIVO', false, 'NO_PUBLICADO'),

(7, 4, 'Antiácido masticable', 'Caja 12 tabletas',
 'Producto para malestares digestivos leves.', 5.10, 'ACTIVO', true, 'DISPONIBLE'),

(8, 4, 'Sales de rehidratación', 'Caja 4 sobres',
 'Apoyo de hidratación oral en cuadros leves.', 4.60, 'ACTIVO', true, 'AGOTADO'),

(9, 5, 'Venda elástica', 'Unidad mediana',
 'Insumo básico de primeros auxilios.', 3.10, 'ACTIVO', true, 'DISPONIBLE'),

(10, 5, 'Gasas estériles', 'Paquete x 20',
 'Insumo de uso común para curación básica.', 2.40, 'ACTIVO', true, 'DISPONIBLE'),

(11, 1, 'Diclofenaco gel', 'Tubo 30 g',
 'Gel tópico para molestias musculares.', 6.20, 'INACTIVO', false, 'NO_PUBLICADO'),

(12, 2, 'Multivitamínico infantil', 'Frasco 60 gomitas',
 'Suplemento orientado a público infantil.', 8.90, 'ACTIVO', true, 'DISPONIBLE');

SELECT setval(pg_get_serial_sequence('producto', 'producto_id'), COALESCE(MAX(producto_id), 1), true)
FROM producto;

-- ============================================================
-- SECCIÓN 3. RESERVAS
--
-- Aunque reserva es más propia de V1.1, se cargan datos demo para
-- estudiar el comportamiento del modelo final y sus consultas.
-- ============================================================

INSERT INTO reserva (
    reserva_id, producto_id, cantidad_reservada,
    estado_reserva, referencia_operativa, fecha_hora_reserva
) VALUES
(1, 1, 2, 'ACTIVA', 'Reserva mostrador cliente A-001', '2026-04-05 09:10:00'),
(2, 4, 1, 'CANCELADA', 'Reserva telefónica no confirmada', '2026-04-05 10:30:00'),
(3, 7, 3, 'CONCRETADA', 'Entrega realizada en caja principal', '2026-04-05 11:45:00'),
(4, 9, 2, 'ACTIVA', 'Apartado temporal por retiro en tarde', '2026-04-05 13:00:00'),
(5, 12, 1, 'ACTIVA', 'Reserva para cliente frecuente', '2026-04-05 15:25:00');

SELECT setval(pg_get_serial_sequence('reserva', 'reserva_id'), COALESCE(MAX(reserva_id), 1), true)
FROM reserva;

-- ============================================================
-- SECCIÓN 4. CONSULTAS DE VERIFICACIÓN OPCIONALES
--
-- Estas consultas no son obligatorias para el seed, pero te sirven
-- para estudiar rápidamente cómo quedó conectado el modelo.
-- Puedes ejecutarlas después del INSERT.
-- ============================================================

-- Ver categorías cargadas
-- SELECT * FROM categoria ORDER BY categoria_id;

-- Ver catálogo completo
-- SELECT p.producto_id, c.nombre_categoria, p.nombre_producto, p.presentacion,
--        p.estado_producto, p.es_publicable, p.estado_disponibilidad, p.precio_visible
-- FROM producto p
-- JOIN categoria c ON c.categoria_id = p.categoria_id
-- ORDER BY c.nombre_categoria, p.nombre_producto;

-- Ver solo productos visibles al público
-- SELECT p.producto_id, p.nombre_producto, p.presentacion, p.estado_disponibilidad
-- FROM producto p
-- WHERE p.es_publicable = true
--   AND p.estado_producto = 'ACTIVO'
-- ORDER BY p.nombre_producto;

-- Ver reservas con su producto
-- SELECT r.reserva_id, p.nombre_producto, r.cantidad_reservada,
--        r.estado_reserva, r.referencia_operativa, r.fecha_hora_reserva
-- FROM reserva r
-- JOIN producto p ON p.producto_id = r.producto_id
-- ORDER BY r.fecha_hora_reserva;
