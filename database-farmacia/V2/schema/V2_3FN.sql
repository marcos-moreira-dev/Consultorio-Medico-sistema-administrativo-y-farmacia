-- ============================================================
-- database-farmacia / Fase 5
-- Archivo: V2_3FN.sql
-- Objetivo:
--   Definir el ESQUEMA FINAL EN 3FN del subdominio farmacia.
--
-- Contexto:
--   - Base de datos EXCLUSIVA de farmacia.
--   - Separada físicamente de consultorio.
--   - Sin claves foráneas cruzadas ni dependencias relacionales
--     hacia otro subdominio.
--
-- Alcance funcional de esta versión:
--   - Categoria
--   - Producto
--   - Reserva
--
-- Criterios de diseño aplicados:
--   - PK sustitutas estables
--   - Separación clara entre clasificación, catálogo y reserva
--   - Integridad referencial explícita
--   - Estados simples resueltos con CHECK
--   - Comentarios pedagógicos para estudio
-- ============================================================

-- ============================================================
-- SECCIÓN 1. ESQUEMA DE TRABAJO
-- ============================================================

CREATE SCHEMA IF NOT EXISTS farmacia;
SET search_path TO farmacia;

-- ============================================================
-- SECCIÓN 2. TABLA CATEGORIA
--
-- Esta tabla concentra exclusivamente la clasificación básica
-- del catálogo. Ya no se repiten sus datos en producto.
-- ============================================================

CREATE TABLE categoria (
    categoria_id            BIGSERIAL PRIMARY KEY,
    nombre_categoria        VARCHAR(100) NOT NULL,
    descripcion_breve       VARCHAR(200),
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT ck_categoria_nombre_no_vacio
        CHECK (btrim(nombre_categoria) <> ''),

    CONSTRAINT uq_categoria_nombre
        UNIQUE (nombre_categoria)
);

COMMENT ON TABLE categoria IS
'Entidad de clasificación simple del catálogo de farmacia.';

COMMENT ON COLUMN categoria.nombre_categoria IS
'Nombre estable de la categoría. Se marca único para evitar duplicados obvios en V1.';

-- ============================================================
-- SECCIÓN 3. TABLA PRODUCTO
--
-- Esta tabla representa el núcleo comercial de farmacia.
--
-- Decisiones importantes:
--   - Producto es la entidad eje.
--   - Puede existir sin estar publicado.
--   - Publicación y disponibilidad no son exactamente lo mismo.
-- ============================================================

CREATE TABLE producto (
    producto_id               BIGSERIAL PRIMARY KEY,
    categoria_id              BIGINT NOT NULL,
    nombre_producto           VARCHAR(150) NOT NULL,
    presentacion              VARCHAR(100) NOT NULL,
    descripcion_breve         VARCHAR(300),
    precio_visible            NUMERIC(10,2),
    estado_producto           VARCHAR(20) NOT NULL,
    es_publicable             BOOLEAN NOT NULL,
    estado_disponibilidad     VARCHAR(20) NOT NULL,
    fecha_creacion            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria (categoria_id),

    CONSTRAINT ck_producto_nombre_no_vacio
        CHECK (btrim(nombre_producto) <> ''),

    CONSTRAINT ck_producto_presentacion_no_vacia
        CHECK (btrim(presentacion) <> ''),

    CONSTRAINT ck_producto_estado
        CHECK (estado_producto IN ('ACTIVO', 'INACTIVO')),

    CONSTRAINT ck_producto_disponibilidad
        CHECK (estado_disponibilidad IN ('DISPONIBLE', 'AGOTADO', 'NO_PUBLICADO')),

    CONSTRAINT ck_producto_precio_no_negativo
        CHECK (precio_visible IS NULL OR precio_visible >= 0),

    CONSTRAINT uq_producto_categoria_nombre_presentacion
        UNIQUE (categoria_id, nombre_producto, presentacion)
);

COMMENT ON TABLE producto IS
'Entidad eje de farmacia. Representa el producto comercial administrado por el catálogo.';

COMMENT ON COLUMN producto.es_publicable IS
'Bandera que expresa si el producto puede exponerse a la capa pública.';

COMMENT ON COLUMN producto.estado_disponibilidad IS
'Estado operativo del producto. No equivale exactamente a publicabilidad.';

-- ============================================================
-- SECCIÓN 4. TABLA RESERVA
--
-- Esta tabla representa el apartado formal de unidades.
--
-- Decisiones importantes:
--   - Reserva depende de producto.
--   - Producto puede existir sin reservas.
--   - Reserva es útil como expansión natural de V1.1, pero aquí
--     ya queda modelada en el esquema final para estudio.
-- ============================================================

CREATE TABLE reserva (
    reserva_id                BIGSERIAL PRIMARY KEY,
    producto_id               BIGINT NOT NULL,
    cantidad_reservada        INTEGER NOT NULL,
    estado_reserva            VARCHAR(20) NOT NULL,
    referencia_operativa      VARCHAR(200),
    fecha_hora_reserva        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reserva_producto
        FOREIGN KEY (producto_id)
        REFERENCES producto (producto_id),

    CONSTRAINT ck_reserva_cantidad_positiva
        CHECK (cantidad_reservada > 0),

    CONSTRAINT ck_reserva_estado
        CHECK (estado_reserva IN ('ACTIVA', 'CANCELADA', 'CONCRETADA')),

    CONSTRAINT ck_reserva_referencia_no_vacia
        CHECK (referencia_operativa IS NULL OR btrim(referencia_operativa) <> '')
);

COMMENT ON TABLE reserva IS
'Entidad operativa de apartado de unidades. Depende exclusivamente del producto.';

COMMENT ON COLUMN reserva.estado_reserva IS
'Estado del ciclo de vida de la reserva dentro del modelo de farmacia.';

-- ============================================================
-- SECCIÓN 5. ÍNDICES DE APOYO
--
-- Estos índices responden a consultas frecuentes esperadas en una
-- farmacia pequeña: búsqueda por nombre, filtros por estado,
-- disponibilidad y reservas por producto.
-- ============================================================

CREATE INDEX idx_producto_nombre_producto
    ON producto (nombre_producto);

CREATE INDEX idx_producto_categoria_id
    ON producto (categoria_id);

CREATE INDEX idx_producto_estado_producto
    ON producto (estado_producto);

CREATE INDEX idx_producto_es_publicable
    ON producto (es_publicable);

CREATE INDEX idx_producto_estado_disponibilidad
    ON producto (estado_disponibilidad);

CREATE INDEX idx_reserva_producto_id
    ON reserva (producto_id);

CREATE INDEX idx_reserva_estado_reserva
    ON reserva (estado_reserva);

CREATE INDEX idx_reserva_fecha_hora_reserva
    ON reserva (fecha_hora_reserva);

-- ============================================================
-- SECCIÓN 6. NOTAS DE LECTURA DEL MODELO
--
-- 1. Ya no se repiten datos de categoría dentro de producto.
-- 2. Ya no se repiten datos de producto dentro de reserva.
-- 3. Producto sigue siendo la entidad eje del subdominio.
-- 4. Publicación y disponibilidad quedan separadas.
-- 5. Los estados simples se resolvieron con CHECK para mantener
--    la V1 clara sin sobrediseñar catálogos innecesarios.
-- ============================================================
