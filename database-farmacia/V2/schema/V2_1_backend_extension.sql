-- ============================================================
-- database-farmacia / V2.1 Backend Extension
-- Archivo: V2_1_backend_extension.sql
-- Objetivo:
--   Extender el esquema V2 3FN con tablas operativas que el backend
--   necesita para autenticación administrativa y media.
--
-- Nota de diseño:
--   Este archivo NO reemplaza el V2_3FN base.
--   Lo complementa sin alterar el rastro de normalización del núcleo
--   del subdominio farmacia.
-- ============================================================

CREATE SCHEMA IF NOT EXISTS farmacia;
SET search_path TO farmacia;

-- ============================================================
-- SECCIÓN 1. TABLA USUARIO_ADMIN
--
-- Esta tabla pertenece a la operación del backend administrativo.
-- No rompe el núcleo normalizado del catálogo; lo complementa.
-- ============================================================

CREATE TABLE IF NOT EXISTS usuario_admin (
    usuario_admin_id        BIGSERIAL PRIMARY KEY,
    username                VARCHAR(100) NOT NULL,
    password_hash           VARCHAR(255) NOT NULL,
    email                   VARCHAR(150),
    estado                  VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    rol_codigo              VARCHAR(50) NOT NULL DEFAULT 'ADMIN_FARMACIA',
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT ck_usuario_admin_username_no_vacio
        CHECK (btrim(username) <> ''),

    CONSTRAINT ck_usuario_admin_password_hash_no_vacio
        CHECK (btrim(password_hash) <> ''),

    CONSTRAINT ck_usuario_admin_estado
        CHECK (estado IN ('ACTIVO', 'INACTIVO')),

    CONSTRAINT ck_usuario_admin_rol_codigo
        CHECK (rol_codigo IN ('ADMIN_FARMACIA')),

    CONSTRAINT ck_usuario_admin_email_no_vacio
        CHECK (email IS NULL OR btrim(email) <> ''),

    CONSTRAINT uq_usuario_admin_username
        UNIQUE (username),

    CONSTRAINT uq_usuario_admin_email
        UNIQUE (email)
);

COMMENT ON TABLE usuario_admin IS
'Usuarios administrativos del backend de farmacia.';

COMMENT ON COLUMN usuario_admin.password_hash IS
'Hash BCrypt de la contraseña del usuario administrativo.';

COMMENT ON COLUMN usuario_admin.rol_codigo IS
'Rol principal operativo del usuario admin.';

CREATE INDEX IF NOT EXISTS idx_usuario_admin_estado
    ON usuario_admin (estado);

CREATE INDEX IF NOT EXISTS idx_usuario_admin_rol_codigo
    ON usuario_admin (rol_codigo);

-- ============================================================
-- SECCIÓN 2. TABLA MEDIA_RECURSO
--
-- Esta tabla registra metadatos y asociación de recursos de media.
-- El archivo vive en storage; la BD guarda referencia y contexto.
-- ============================================================

CREATE TABLE IF NOT EXISTS media_recurso (
    media_recurso_id        BIGSERIAL PRIMARY KEY,
    producto_id             BIGINT,
    tipo_recurso            VARCHAR(50) NOT NULL DEFAULT 'IMAGEN_PRODUCTO',
    nombre_original         VARCHAR(255) NOT NULL,
    nombre_archivo          VARCHAR(255) NOT NULL,
    mime_type               VARCHAR(120) NOT NULL,
    extension               VARCHAR(20) NOT NULL,
    tamano_bytes            INTEGER NOT NULL,
    ruta_relativa           VARCHAR(500) NOT NULL,
    url_publica             VARCHAR(500) NOT NULL,
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_media_recurso_producto
        FOREIGN KEY (producto_id)
        REFERENCES producto (producto_id)
        ON DELETE SET NULL,

    CONSTRAINT ck_media_recurso_tipo
        CHECK (tipo_recurso IN ('IMAGEN_PRODUCTO')),

    CONSTRAINT ck_media_recurso_nombre_original_no_vacio
        CHECK (btrim(nombre_original) <> ''),

    CONSTRAINT ck_media_recurso_nombre_archivo_no_vacio
        CHECK (btrim(nombre_archivo) <> ''),

    CONSTRAINT ck_media_recurso_mime_type_no_vacio
        CHECK (btrim(mime_type) <> ''),

    CONSTRAINT ck_media_recurso_extension_no_vacia
        CHECK (btrim(extension) <> ''),

    CONSTRAINT ck_media_recurso_tamano_positivo
        CHECK (tamano_bytes > 0),

    CONSTRAINT ck_media_recurso_ruta_no_vacia
        CHECK (btrim(ruta_relativa) <> ''),

    CONSTRAINT ck_media_recurso_url_no_vacia
        CHECK (btrim(url_publica) <> ''),

    CONSTRAINT uq_media_recurso_nombre_archivo
        UNIQUE (nombre_archivo),

    CONSTRAINT uq_media_recurso_ruta_relativa
        UNIQUE (ruta_relativa)
);

COMMENT ON TABLE media_recurso IS
'Recurso de media asociado o asociable a productos del catálogo de farmacia.';

COMMENT ON COLUMN media_recurso.producto_id IS
'Producto actualmente asociado al recurso. Puede ser nulo si el archivo ya fue subido pero aún no se asocia.';

COMMENT ON COLUMN media_recurso.ruta_relativa IS
'Ruta relativa dentro del storage físico local.';

COMMENT ON COLUMN media_recurso.url_publica IS
'URL pública calculada por el backend para exponer el recurso.';

CREATE INDEX IF NOT EXISTS idx_media_recurso_producto_id
    ON media_recurso (producto_id);

CREATE INDEX IF NOT EXISTS idx_media_recurso_tipo_recurso
    ON media_recurso (tipo_recurso);

CREATE INDEX IF NOT EXISTS idx_media_recurso_fecha_creacion
    ON media_recurso (fecha_creacion);
