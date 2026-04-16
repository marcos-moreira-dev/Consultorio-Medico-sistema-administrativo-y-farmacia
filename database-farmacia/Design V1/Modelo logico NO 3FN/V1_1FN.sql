-- ============================================================
-- database-farmacia / Fase 3
-- Archivo: V1_1FN.sql
-- Objetivo:
--   Representar una PRIMERA VERSIÓN RELACIONAL en 1FN del
--   subdominio farmacia, pensada para ESTUDIO.
--
-- Importante:
--   Este archivo NO es el esquema final en 3FN.
--   Aquí se permiten redundancias controladas e incluso algunas
--   decisiones menos elegantes a propósito, para que luego el
--   informe de normalización tenga sentido pedagógico.
--
-- Contexto del subdominio:
--   - Base de datos EXCLUSIVA de farmacia.
--   - No mezcla entidades de consultorio.
--   - No asume claves foráneas cruzadas hacia otra base.
--
-- Dominio mínimo considerado:
--   - Categoria
--   - Producto
--   - Reserva (como expansión natural de V1.1, pero ya prevista
--     aquí para fines de estudio relacional)
-- ============================================================

-- ============================================================
-- SECCIÓN 1. ESQUEMA DE TRABAJO
-- ============================================================

CREATE SCHEMA IF NOT EXISTS farmacia_v1_1fn;
SET search_path TO farmacia_v1_1fn;

-- ============================================================
-- SECCIÓN 2. TABLA CATEGORIA_1FN
--
-- Esta tabla guarda una clasificación simple del catálogo.
-- En esta etapa todavía es posible que otras tablas repitan
-- algunos datos de categoría a propósito para luego normalizar.
-- ============================================================

CREATE TABLE categoria_1fn (
    categoria_id            BIGSERIAL PRIMARY KEY,
    nombre_categoria        VARCHAR(100) NOT NULL,
    descripcion_breve       VARCHAR(200),
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT ck_categoria_nombre_no_vacio
        CHECK (btrim(nombre_categoria) <> '')
);

COMMENT ON TABLE categoria_1fn IS
'Tabla base de categorías en versión 1FN de estudio. Puede coexistir con redundancias en producto para fines pedagógicos.';

-- ============================================================
-- SECCIÓN 3. TABLA PRODUCTO_1FN
--
-- Esta tabla representa el núcleo comercial de farmacia.
-- Se usa PK compuesta para dejar espacio al análisis de 2FN.
-- Además, se duplican ciertos datos de categoría a propósito.
--
-- ¿Por qué duplicar?
--   Porque luego el informe de normalización podrá explicar
--   anomalías de actualización y dependencias parciales.
-- ============================================================

CREATE TABLE producto_1fn (
    categoria_id                  BIGINT NOT NULL,
    nombre_producto               VARCHAR(150) NOT NULL,

    -- Datos repetidos de categoría (intencionales en esta fase)
    nombre_categoria              VARCHAR(100) NOT NULL,
    descripcion_categoria         VARCHAR(200),

    presentacion                  VARCHAR(100) NOT NULL,
    descripcion_breve             VARCHAR(300),
    precio_visible                NUMERIC(10,2),
    estado_producto               VARCHAR(20) NOT NULL,
    es_publicable                 BOOLEAN NOT NULL,
    estado_disponibilidad         VARCHAR(20) NOT NULL,
    fecha_creacion                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_producto_1fn
        PRIMARY KEY (categoria_id, nombre_producto),

    CONSTRAINT fk_producto_1fn_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria_1fn (categoria_id),

    CONSTRAINT ck_producto_1fn_nombre_no_vacio
        CHECK (btrim(nombre_producto) <> ''),

    CONSTRAINT ck_producto_1fn_presentacion_no_vacia
        CHECK (btrim(presentacion) <> ''),

    CONSTRAINT ck_producto_1fn_estado
        CHECK (estado_producto IN ('ACTIVO', 'INACTIVO')),

    CONSTRAINT ck_producto_1fn_disponibilidad
        CHECK (estado_disponibilidad IN ('DISPONIBLE', 'AGOTADO', 'NO_PUBLICADO')),

    CONSTRAINT ck_producto_1fn_precio_no_negativo
        CHECK (precio_visible IS NULL OR precio_visible >= 0)
);

COMMENT ON TABLE producto_1fn IS
'Tabla de productos en 1FN. Mantiene datos duplicados de categoría y PK compuesta a propósito para posterior normalización.';

COMMENT ON COLUMN producto_1fn.es_publicable IS
'Bandera simple para distinguir existencia interna de visibilidad pública.';

COMMENT ON COLUMN producto_1fn.estado_disponibilidad IS
'Estado operativo del producto en esta versión inicial.';

-- ============================================================
-- SECCIÓN 4. TABLA RESERVA_1FN
--
-- Esta tabla representa el apartado formal de unidades.
-- En esta fase repite datos del producto y de la categoría a
-- propósito para dejar material de estudio para 3FN.
--
-- Aunque la reserva es más propia de V1.1, se incluye aquí para
-- fines pedagógicos de modelado y normalización.
-- ============================================================

CREATE TABLE reserva_1fn (
    categoria_id                  BIGINT NOT NULL,
    nombre_producto               VARCHAR(150) NOT NULL,
    fecha_hora_reserva            TIMESTAMP NOT NULL,

    -- Datos repetidos del producto (intencionales en esta fase)
    nombre_categoria              VARCHAR(100) NOT NULL,
    presentacion                  VARCHAR(100) NOT NULL,
    estado_producto               VARCHAR(20) NOT NULL,
    estado_disponibilidad         VARCHAR(20) NOT NULL,

    cantidad_reservada            INTEGER NOT NULL,
    estado_reserva                VARCHAR(20) NOT NULL,
    referencia_operativa          VARCHAR(200),
    fecha_creacion                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_reserva_1fn
        PRIMARY KEY (categoria_id, nombre_producto, fecha_hora_reserva),

    CONSTRAINT fk_reserva_1fn_producto
        FOREIGN KEY (categoria_id, nombre_producto)
        REFERENCES producto_1fn (categoria_id, nombre_producto),

    CONSTRAINT ck_reserva_1fn_cantidad_positiva
        CHECK (cantidad_reservada > 0),

    CONSTRAINT ck_reserva_1fn_estado
        CHECK (estado_reserva IN ('ACTIVA', 'CANCELADA', 'CONCRETADA')),

    CONSTRAINT ck_reserva_1fn_referencia_no_vacia
        CHECK (referencia_operativa IS NULL OR btrim(referencia_operativa) <> '')
);

COMMENT ON TABLE reserva_1fn IS
'Tabla de reservas en 1FN. Depende del producto, pero aún repite datos del producto y categoría para fines de estudio.';

-- ============================================================
-- SECCIÓN 5. ÍNDICES BÁSICOS DE APOYO
--
-- Aunque esta no es la versión final, dejamos algunos índices
-- razonables para consultas frecuentes de farmacia.
-- ============================================================

CREATE INDEX idx_categoria_1fn_nombre_categoria
    ON categoria_1fn (nombre_categoria);

CREATE INDEX idx_producto_1fn_nombre_producto
    ON producto_1fn (nombre_producto);

CREATE INDEX idx_producto_1fn_estado_producto
    ON producto_1fn (estado_producto);

CREATE INDEX idx_producto_1fn_es_publicable
    ON producto_1fn (es_publicable);

CREATE INDEX idx_producto_1fn_estado_disponibilidad
    ON producto_1fn (estado_disponibilidad);

CREATE INDEX idx_reserva_1fn_fecha_hora_reserva
    ON reserva_1fn (fecha_hora_reserva);

CREATE INDEX idx_reserva_1fn_estado_reserva
    ON reserva_1fn (estado_reserva);

-- ============================================================
-- SECCIÓN 6. NOTAS FINALES DE ESTUDIO
--
-- Este modelo cumple 1FN porque:
--   - no hay grupos repetidos dentro de una misma columna;
--   - los valores son atómicos;
--   - cada fila representa una ocurrencia identificable.
--
-- Pero todavía NO es el diseño final porque:
--   - hay redundancia de datos de categoría en producto;
--   - hay redundancia de datos de producto/categoría en reserva;
--   - hay PK compuestas pensadas para análisis didáctico;
--   - existen dependencias que luego convendrá separar.
--
-- El siguiente paso será redactar el informe markdown de
-- normalización para explicar cómo se evoluciona desde esta
-- versión inicial hacia una versión 3FN más limpia.
-- ============================================================
