-- ============================================================
-- database-farmacia / Seed demo masivo
-- Archivo: 04_seed_demo_masivo.sql
-- Objetivo:
--   Poblar el catálogo de farmacia con datos abundantes y
--   realistas para demostración, pruebas y desarrollo.
--
-- Contenido:
--   - 12 categorías de farmacia ecuatoriana
--   - 65 productos con datos comerciales realistas
--   - 2 usuarios admin adicionales
--   - 15 registros de media simulados
--
-- Ejecutar DESPUÉS de:
--   1. V2_3FN.sql
--   2. V2_1_backend_extension.sql
--   3. 02_seed_demo_3FN.sql (opcional, este archivo usa ON CONFLICT)
--   4. 03_seed_backend_extension.sql
-- ============================================================

SET search_path TO farmacia;

-- ============================================================
-- SECCIÓN 1. CATEGORÍAS MASIVAS (12 categorías reales)
-- ============================================================

INSERT INTO categoria (categoria_id, nombre_categoria, descripcion_breve) VALUES
(6,  'Antibióticos', 'Medicamentos antibacterianos de uso común bajo receta'),
(7,  'Antiinflamatorios', 'Productos para reducir inflamación y dolor articular'),
(8,  'Antialérgicos', 'Medicamentos para control de alergias y síntomas asociados'),
(9,  'Medicamentos cardiovasculares', 'Productos para control de presión y salud cardíaca'),
(10, 'Medicamentos para diabetes', 'Insumos y medicamentos para control glucémico'),
(11, 'Cuidado infantil', 'Productos de higiene y salud para bebés y niños'),
(12, 'Equipos médicos básicos', 'Instrumentos de medición y primeros auxilios avanzados')
ON CONFLICT (categoria_id) DO NOTHING;

-- Actualizar secuencia
SELECT setval(pg_get_serial_sequence('categoria', 'categoria_id'),
    (SELECT MAX(categoria_id) FROM categoria));

-- ============================================================
-- SECCIÓN 2. PRODUCTOS MASIVOS (65 productos realistas)
--
-- Distribución estratégica:
--   ~45 publicados y disponibles (visibles en catálogo público)
--   ~8 publicados pero agotados (visible como "agotado")
--   ~7 no publicados (solo admin puede verlos)
--   ~5 inactivos (fuera de circulación)
-- ============================================================

INSERT INTO producto (
    producto_id, categoria_id, nombre_producto, presentacion,
    descripcion_breve, precio_visible, estado_producto,
    es_publicable, estado_disponibilidad
) VALUES
-- ========================
-- Analgésicos (cat 1)
-- ========================
(13, 1, 'Aspirina', 'Caja 40 tabletas 500 mg',
 'Ácido acetilsalicílico para dolor de cabeza y malestar general.', 4.80, 'ACTIVO', true, 'DISPONIBLE'),

(14, 1, 'Naproxeno', 'Caja 20 tabletas 250 mg',
 'Antiinflamatorio y analgésico para dolores musculares y articulares.', 6.50, 'ACTIVO', true, 'DISPONIBLE'),

(15, 1, 'Ketorolaco', 'Caja 10 tabletas 10 mg',
 'Analgésico potente para dolor agudo de corta duración.', 5.90, 'ACTIVO', true, 'DISPONIBLE'),

(16, 1, 'Metamizol sódica', 'Caja 20 tabletas 500 mg',
 'Analgésico y antiespasmódico para cólicos y dolor moderado.', 3.90, 'ACTIVO', true, 'DISPONIBLE'),

(17, 1, 'Tramadol', 'Caja 20 cápsulas 50 mg',
 'Analgésico opioide para dolor moderado a severo. Requiere receta.', 12.50, 'ACTIVO', false, 'NO_PUBLICADO'),

-- ========================
-- Vitaminas (cat 2)
-- ========================
(18, 2, 'Vitamina D3', 'Frasco 60 cápsulas blandas 1000 UI',
 'Suplemento para absorción de calcio y salud ósea.', 9.20, 'ACTIVO', true, 'DISPONIBLE'),

(19, 2, 'Vitamina E', 'Frasco 30 cápsulas 400 UI',
 'Antioxidante para cuidado de la piel y sistema inmune.', 8.70, 'ACTIVO', true, 'DISPONIBLE'),

(20, 2, 'Zinc', 'Frasco 60 tabletas 15 mg',
 'Mineral esencial para defensas y cicatrización.', 6.40, 'ACTIVO', true, 'DISPONIBLE'),

(21, 2, 'Hierro + Ácido fólico', 'Caja 30 tabletas',
 'Suplemento para prevención de anemia, especialmente en embarazo.', 5.60, 'ACTIVO', true, 'DISPONIBLE'),

(22, 2, 'Omega 3', 'Frasco 60 cápsulas blandas',
 'Ácidos grasos esenciales para salud cardiovascular.', 14.90, 'ACTIVO', true, 'DISPONIBLE'),

-- ========================
-- Higiene personal (cat 3)
-- ========================
(23, 3, 'Jabón antibacterial', 'Barra 120 g',
 'Jabón de tocador con acción antibacteriana para uso diario.', 2.50, 'ACTIVO', true, 'DISPONIBLE'),

(24, 3, 'Crema dental', 'Tubo 100 g',
 'Pasta dental con flúor para prevención de caries.', 3.20, 'ACTIVO', true, 'DISPONIBLE'),

(25, 3, 'Enjuague bucal', 'Frasco 250 ml',
 'Solución antiséptica para higiene bucal profunda.', 4.90, 'ACTIVO', true, 'DISPONIBLE'),

(26, 3, 'Shampoo anticaspa', 'Frasco 200 ml',
 'Tratamiento capilar para control de caspa y picazón.', 7.80, 'ACTIVO', true, 'DISPONIBLE'),

(27, 3, 'Protector solar FPS 50', 'Frasco 120 ml',
 'Protección solar de amplio espectro para uso diario.', 12.50, 'ACTIVO', true, 'DISPONIBLE'),

-- ========================
-- Digestivos (cat 4)
-- ========================
(28, 4, 'Omeprazol', 'Caja 14 cápsulas 20 mg',
 'Inhibidor de bomba de protones para gastritis y reflujo.', 7.20, 'ACTIVO', true, 'DISPONIBLE'),

(29, 4, 'Loperamida', 'Caja 12 cápsulas 2 mg',
 'Antidiarreico para control de diarrea aguda.', 3.80, 'ACTIVO', true, 'DISPONIBLE'),

(30, 4, 'Simeticona', 'Frasco 30 ml gotas',
 'Antiflatulento para alivio de gases e hinchazón.', 5.40, 'ACTIVO', true, 'DISPONIBLE'),

(31, 4, 'Subsalicilato de bismuto', 'Frasco 120 ml suspensión',
 'Antidiarreico y antiácido para malestares estomacales.', 6.90, 'ACTIVO', true, 'DISPONIBLE'),

(32, 4, 'Probioral', 'Caja 10 sobres',
 'Probiótico para restaurar flora intestinal.', 9.80, 'ACTIVO', true, 'AGOTADO'),

-- ========================
-- Primeros auxilios (cat 5)
-- ========================
(33, 5, 'Curitas adhesivas', 'Caja 100 unidades surtidas',
 'Banditas adhesivas de diferentes medidas para heridas menores.', 3.50, 'ACTIVO', true, 'DISPONIBLE'),

(34, 5, 'Agua oxigenada', 'Frasco 250 ml 3%',
 'Solución antiséptica para limpieza de heridas.', 2.20, 'ACTIVO', true, 'DISPONIBLE'),

(35, 5, 'Cinta micropore', 'Rollo 5 cm x 10 m',
 'Cinta adhesiva hipoalergénica para fijar vendajes.', 2.80, 'ACTIVO', true, 'DISPONIBLE'),

(36, 5, 'Guantes de látex', 'Caja 50 pares',
 'Guantes desechables para procedimientos y limpieza.', 8.90, 'ACTIVO', true, 'DISPONIBLE'),

(37, 5, 'Termómetro digital', 'Unidad con estuche',
 'Termómetro electrónico de lectura rápida con punta flexible.', 12.50, 'ACTIVO', true, 'DISPONIBLE'),

-- ========================
-- Antibióticos (cat 6)
-- ========================
(38, 6, 'Amoxicilina', 'Caja 21 cápsulas 500 mg',
 'Antibiótico de amplio espectro para infecciones bacterianas. Requiere receta.', 8.50, 'ACTIVO', false, 'NO_PUBLICADO'),

(39, 6, 'Azitromicina', 'Caja 6 tabletas 500 mg',
 'Antibiótico macrólido para infecciones respiratorias. Requiere receta.', 14.20, 'ACTIVO', false, 'NO_PUBLICADO'),

(40, 6, 'Ciprofloxacino', 'Caja 14 tabletas 500 mg',
 'Antibiótico fluoroquinolona para infecciones urinarias. Requiere receta.', 11.80, 'ACTIVO', false, 'NO_PUBLICADO'),

(41, 6, 'Cefalexina', 'Caja 24 cápsulas 500 mg',
 'Antibiótico cefalosporina para infecciones de piel. Requiere receta.', 10.50, 'ACTIVO', false, 'NO_PUBLICADO'),

-- ========================
-- Antiinflamatorios (cat 7)
-- ========================
(42, 7, 'Dexametasona', 'Caja 20 tabletas 4 mg',
 'Corticoide antiinflamatorio potente. Requiere receta médica.', 6.80, 'ACTIVO', true, 'DISPONIBLE'),

(43, 7, 'Prednisolona', 'Caja 20 tabletas 5 mg',
 'Corticoide para inflamación y procesos alérgicos severos.', 5.90, 'ACTIVO', true, 'DISPONIBLE'),

(44, 7, 'Piroxicam gel', 'Tubo 30 g',
 'Gel antiinflamatorio tópico para dolores musculares.', 7.40, 'ACTIVO', true, 'DISPONIBLE'),

(45, 7, 'Indometacina', 'Caja 30 cápsulas 25 mg',
 'Antiinflamatorio no esteroideo para artritis y dolor crónico.', 8.20, 'ACTIVO', true, 'AGOTADO'),

-- ========================
-- Antialérgicos (cat 8)
-- ========================
(46, 8, 'Loratadina', 'Caja 30 tabletas 10 mg',
 'Antihistamínico no sedante para alergias estacionales.', 4.50, 'ACTIVO', true, 'DISPONIBLE'),

(47, 8, 'Cetirizina', 'Caja 30 tabletas 10 mg',
 'Antihistamínico para rinitis alérgica y urticaria.', 5.20, 'ACTIVO', true, 'DISPONIBLE'),

(48, 8, 'Clorfenamina', 'Caja 20 tabletas 4 mg',
 'Antihistamínico clásico para alergias y resfriados.', 2.90, 'ACTIVO', true, 'DISPONIBLE'),

(49, 8, 'Fexofenadina', 'Caja 10 tabletas 180 mg',
 'Antihistamínico de segunda generación sin somnolencia.', 9.80, 'ACTIVO', true, 'AGOTADO'),

-- ========================
-- Cardiovasculares (cat 9)
-- ========================
(50, 9, 'Losartán', 'Caja 30 tabletas 50 mg',
 'Antihhipertensivo para control de presión arterial.', 12.80, 'ACTIVO', true, 'DISPONIBLE'),

(51, 9, 'Atorvastatina', 'Caja 30 tabletas 20 mg',
 'Estatina para reducción de colesterol LDL.', 18.50, 'ACTIVO', true, 'DISPONIBLE'),

(52, 9, 'Enalapril', 'Caja 30 tabletas 10 mg',
 'IECA para hipertensión e insuficiencia cardíaca.', 9.60, 'ACTIVO', true, 'DISPONIBLE'),

(53, 9, 'Amlodipino', 'Caja 30 tabletas 5 mg',
 'Bloqueador de canales de calcio para hipertensión.', 8.90, 'ACTIVO', true, 'AGOTADO'),

(54, 9, 'Aspirina protect', 'Caja 30 tabletas 100 mg',
 'Aspirina dosis baja para prevención cardiovascular.', 6.20, 'ACTIVO', true, 'DISPONIBLE'),

-- ========================
-- Diabetes (cat 10)
-- ========================
(55, 10, 'Metformina', 'Caja 30 tabletas 850 mg',
 'Antidiabético oral de primera línea para diabetes tipo 2.', 5.80, 'ACTIVO', true, 'DISPONIBLE'),

(56, 10, 'Glibenclamida', 'Caja 30 tabletas 5 mg',
 'Sulfonilurea para estimular secreción de insulina.', 4.90, 'ACTIVO', true, 'DISPONIBLE'),

(57, 10, 'Tiras reactivas glucosa', 'Caja 50 unidades',
 'Tiras para medición de glucosa en sangre con glucómetro.', 22.50, 'ACTIVO', true, 'DISPONIBLE'),

(58, 10, 'Insulina NPH', 'Frasco 10 ml 100 UI/ml',
 'Insulina de acción intermedia para diabetes. Requiere refrigeración.', 15.80, 'ACTIVO', true, 'AGOTADO'),

-- ========================
-- Cuidado infantil (cat 11)
-- ========================
(59, 11, 'Pañales recién nacido', 'Paquete 30 unidades',
 'Pañales desechables de absorción rápida para recién nacidos.', 12.90, 'ACTIVO', true, 'DISPONIBLE'),

(60, 11, 'Crema para pañalitis', 'Tubo 100 g',
 'Crema protectora con óxido de zinc para irritación del pañal.', 5.80, 'ACTIVO', true, 'DISPONIBLE'),

(61, 11, 'Suero fisiológico', 'Caja 10 ampollas 5 ml',
 'Solución salina estéril para limpieza nasal y ocular infantil.', 3.90, 'ACTIVO', true, 'DISPONIBLE'),

(62, 11, 'Biberón anticólicos', 'Unidad 260 ml',
 'Biberón con válvula anticólicos de flujo lento.', 9.50, 'ACTIVO', true, 'AGOTADO'),

(63, 11, 'Termómetro bebé', 'Unidad digital con chupete',
 'Termómetro digital con forma de chupete para bebés.', 11.20, 'ACTIVO', true, 'DISPONIBLE'),

-- ========================
-- Equipos médicos (cat 12)
-- ========================
(64, 12, 'Tensiómetro digital', 'Unidad con brazalete',
 'Monitor de presión arterial automático con pantalla LCD.', 35.90, 'ACTIVO', true, 'DISPONIBLE'),

(65, 12, 'Glucómetro', 'Kit con lancetas y 10 tiras',
 'Medidor de glucosa en sangre con estuche portátil.', 28.50, 'ACTIVO', true, 'DISPONIBLE'),

(66, 12, 'Oxímetro de pulso', 'Unidad con pilas',
 'Medidor de saturación de oxígeno y frecuencia cardíaca.', 22.90, 'ACTIVO', true, 'DISPONIBLE'),

(67, 12, 'Nebulizador', 'Unidad con mascarilla',
 'Equipo de nebulización para tratamientos respiratorios.', 32.50, 'ACTIVO', true, 'AGOTADO'),

-- ========================
-- Productos INACTIVOS (varias categorías)
-- ========================
(68, 1, 'Ácido mefenámico', 'Caja 20 cápsulas 250 mg',
 'AINE para dolor menstrual. Producto discontinuado por proveedor.', 7.60, 'INACTIVO', false, 'NO_PUBLICADO'),

(69, 3, 'Talco para pies', 'Frasco 100 g',
 'Talco antifúngico para higiene de pies. Producto descontinuado.', 3.40, 'INACTIVO', false, 'NO_PUBLICADO'),

(70, 5, 'Botiquín portátil', 'Unidad con insumos básicos',
 'Botiquín compacto con insumos de primeros auxilios. Descatalogado.', 18.90, 'INACTIVO', false, 'NO_PUBLICADO'),

(71, 7, 'Fenilbutazona', 'Caja 20 tabletas 100 mg',
 'Antiinflamatorio veterinario. No apto para uso humano. Retirado.', 9.50, 'INACTIVO', false, 'NO_PUBLICADO'),

(72, 9, 'Atenolol', 'Caja 30 tabletas 50 mg',
 'Betabloqueante descontinuado en favor de alternativas más modernas.', 7.80, 'INACTIVO', false, 'NO_PUBLICADO'),

(73, 10, 'Gliclazida', 'Caja 30 tabletas 80 mg',
 'Sulfonilurea reemplazada por alternativas más seguras.', 11.20, 'INACTIVO', false, 'NO_PUBLICADO'),

(74, 11, 'Polvos de talco bebé', 'Frasco 200 g',
 'Talco clásico para bebé. Retirado por recomendación pediátrica actual.', 4.20, 'INACTIVO', false, 'NO_PUBLICADO');

-- Actualizar secuencia
SELECT setval(pg_get_serial_sequence('producto', 'producto_id'),
    (SELECT MAX(producto_id) FROM producto));

-- ============================================================
-- SECCIÓN 3. USUARIOS ADMIN ADICIONALES
-- ============================================================

-- Operador de farmacia (rol: ADMIN_FARMACIA, para uso operativo diario)
INSERT INTO usuario_admin (
    usuario_admin_id, username, password_hash, email, estado, rol_codigo
) VALUES (
    2,
    'operador.farmacia',
    '$2b$10$49gLr/bOurpiX1MJLe3wZOvAby8K8cCV.K6M5AYFjWyKuQtySYN.y',
    'operador@farmacia.local',
    'ACTIVO',
    'ADMIN_FARMACIA'
)
ON CONFLICT (username) DO NOTHING;

-- Supervisor (mismo rol pero con nombre diferenciador)
INSERT INTO usuario_admin (
    usuario_admin_id, username, password_hash, email, estado, rol_codigo
) VALUES (
    3,
    'supervisor.farmacia',
    '$2b$10$49gLr/bOurpiX1MJLe3wZOvAby8K8cCV.K6M5AYFjWyKuQtySYN.y',
    'supervisor@farmacia.local',
    'ACTIVO',
    'ADMIN_FARMACIA'
)
ON CONFLICT (username) DO NOTHING;

SELECT setval(pg_get_serial_sequence('usuario_admin', 'usuario_admin_id'),
    (SELECT MAX(usuario_admin_id) FROM usuario_admin));

-- ============================================================
-- SECCIÓN 4. MEDIA RECURSOS SIMULADOS
--
-- Referencias placeholder que apuntan a imágenes genéricas.
-- En producción estas rutas serían reemplazadas por archivos reales
-- subidos mediante el módulo de media del backend.
-- ============================================================

INSERT INTO media_recurso (
    media_recurso_id, producto_id, tipo_recurso,
    nombre_original, nombre_archivo, mime_type, extension,
    tamano_bytes, ruta_relativa, url_publica
) VALUES
-- Paracetamol
(1, 1, 'IMAGEN_PRODUCTO', 'paracetamol.jpg', 'prod_001_paracetamol.jpg',
 'image/jpeg', 'jpg', 45200, 'productos/001/paracetamol.jpg',
 '/media/productos/001/paracetamol.jpg'),

-- Ibuprofeno
(2, 2, 'IMAGEN_PRODUCTO', 'ibuprofeno.jpg', 'prod_002_ibuprofeno.jpg',
 'image/jpeg', 'jpg', 52100, 'productos/002/ibuprofeno.jpg',
 '/media/productos/002/ibuprofeno.jpg'),

-- Vitamina C
(3, 3, 'IMAGEN_PRODUCTO', 'vitamina_c.jpg', 'prod_003_vitamina_c.jpg',
 'image/jpeg', 'jpg', 38900, 'productos/003/vitamina_c.jpg',
 '/media/productos/003/vitamina_c.jpg'),

-- Aspirina
(4, 13, 'IMAGEN_PRODUCTO', 'aspirina.jpg', 'prod_013_aspirina.jpg',
 'image/jpeg', 'jpg', 41200, 'productos/013/aspirina.jpg',
 '/media/productos/013/aspirina.jpg'),

-- Loratadina
(5, 46, 'IMAGEN_PRODUCTO', 'loratadina.jpg', 'prod_046_loratadina.jpg',
 'image/jpeg', 'jpg', 35800, 'productos/046/loratadina.jpg',
 '/media/productos/046/loratadina.jpg'),

-- Omeprazol
(6, 28, 'IMAGEN_PRODUCTO', 'omeprazol.jpg', 'prod_028_omeprazol.jpg',
 'image/jpeg', 'jpg', 48500, 'productos/028/omeprazol.jpg',
 '/media/productos/028/omeprazol.jpg'),

-- Tensiómetro
(7, 64, 'IMAGEN_PRODUCTO', 'tensiometro.jpg', 'prod_064_tensiometro.jpg',
 'image/jpeg', 'jpg', 62300, 'productos/064/tensiometro.jpg',
 '/media/productos/064/tensiometro.jpg'),

-- Glucómetro
(8, 65, 'IMAGEN_PRODUCTO', 'glucometro.jpg', 'prod_065_glucometro.jpg',
 'image/jpeg', 'jpg', 55800, 'productos/065/glucometro.jpg',
 '/media/productos/065/glucometro.jpg'),

-- Protector solar
(9, 27, 'IMAGEN_PRODUCTO', 'protector_solar.jpg', 'prod_027_protector_solar.jpg',
 'image/jpeg', 'jpg', 44100, 'productos/027/protector_solar.jpg',
 '/media/productos/027/protector_solar.jpg'),

-- Pañales
(10, 59, 'IMAGEN_PRODUCTO', 'panales_rn.jpg', 'prod_059_panales_rn.jpg',
 'image/jpeg', 'jpg', 58200, 'productos/059/panales_rn.jpg',
 '/media/productos/059/panales_rn.jpg'),

-- Losartán
(11, 50, 'IMAGEN_PRODUCTO', 'losartan.jpg', 'prod_050_losartan.jpg',
 'image/jpeg', 'jpg', 39600, 'productos/050/losartan.jpg',
 '/media/productos/050/losartan.jpg'),

-- Metformina
(12, 55, 'IMAGEN_PRODUCTO', 'metformina.jpg', 'prod_055_metformina.jpg',
 'image/jpeg', 'jpg', 42700, 'productos/055/metformina.jpg',
 '/media/productos/055/metformina.jpg'),

-- Oxímetro
(13, 66, 'IMAGEN_PRODUCTO', 'oximetro.jpg', 'prod_066_oximetro.jpg',
 'image/jpeg', 'jpg', 51400, 'productos/066/oximetro.jpg',
 '/media/productos/066/oximetro.jpg'),

-- Gasas estériles
(14, 10, 'IMAGEN_PRODUCTO', 'gasas.jpg', 'prod_010_gasas.jpg',
 'image/jpeg', 'jpg', 33200, 'productos/010/gasas.jpg',
 '/media/productos/010/gasas.jpg'),

-- Amoxicilina
(15, 38, 'IMAGEN_PRODUCTO', 'amoxicilina.jpg', 'prod_038_amoxicilina.jpg',
 'image/jpeg', 'jpg', 46800, 'productos/038/amoxicilina.jpg',
 '/media/productos/038/amoxicilina.jpg');

SELECT setval(pg_get_serial_sequence('media_recurso', 'media_recurso_id'),
    (SELECT MAX(media_recurso_id) FROM media_recurso));

-- ============================================================
-- SECCIÓN 5. CONSULTAS DE VERIFICACIÓN
-- ============================================================

-- Resumen por categoría
-- SELECT c.nombre_categoria,
--        COUNT(p.producto_id) AS total_productos,
--        SUM(CASE WHEN p.es_publicable AND p.estado_producto = 'ACTIVO' THEN 1 ELSE 0 END) AS visibles_publicos,
--        SUM(CASE WHEN p.estado_disponibilidad = 'AGOTADO' THEN 1 ELSE 0 END) AS agotados,
--        SUM(CASE WHEN p.es_publicable = false THEN 1 ELSE 0 END) AS no_publicados,
--        SUM(CASE WHEN p.estado_producto = 'INACTIVO' THEN 1 ELSE 0 END) AS inactivos
-- FROM categoria c
-- LEFT JOIN producto p ON p.categoria_id = c.categoria_id
-- GROUP BY c.nombre_categoria
-- ORDER BY c.nombre_categoria;

-- Totales generales
-- SELECT
--   (SELECT COUNT(*) FROM categoria) AS total_categorias,
--   (SELECT COUNT(*) FROM producto) AS total_productos,
--   (SELECT COUNT(*) FROM producto WHERE es_publicable AND estado_producto = 'ACTIVO') AS productos_visibles,
--   (SELECT COUNT(*) FROM producto WHERE estado_disponibilidad = 'AGOTADO') AS productos_agotados,
--   (SELECT COUNT(*) FROM producto WHERE es_publicable = false) AS productos_no_publicados,
--   (SELECT COUNT(*) FROM producto WHERE estado_producto = 'INACTIVO') AS productos_inactivos,
--   (SELECT COUNT(*) FROM usuario_admin WHERE estado = 'ACTIVO') AS usuarios_activos,
--   (SELECT COUNT(*) FROM media_recurso) AS registros_media;
