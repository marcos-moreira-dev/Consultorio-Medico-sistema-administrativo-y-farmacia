-- ============================================================
-- database-consultorio / Fase 3 corregida por multidoctor
-- Archivo: V1_1FN.sql
-- Objetivo:
--   Representar una PRIMERA VERSIÓN RELACIONAL en 1FN del
--   subdominio consultorio bajo un escenario multidoctor.
--
-- Importante:
--   Este archivo NO es el esquema final en 3FN.
--   Aquí se permiten redundancias controladas e incluso algunas
--   decisiones menos elegantes a propósito, para que luego el
--   informe de normalización tenga sentido pedagógico.
-- ============================================================

CREATE SCHEMA IF NOT EXISTS consultorio_v1_1fn;
SET search_path TO consultorio_v1_1fn;

-- ============================================================
-- SECCIÓN 1. TABLA ROL_1FN
-- ============================================================

CREATE TABLE rol_1fn (
    rol_id                  BIGSERIAL PRIMARY KEY,
    nombre_rol              VARCHAR(50) NOT NULL,
    descripcion_breve       VARCHAR(200),
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_rol_nombre_no_vacio CHECK (btrim(nombre_rol) <> '')
);

-- ============================================================
-- SECCIÓN 2. TABLA USUARIO_1FN
--
-- Tabla de acceso al sistema. En esta fase ya existe, pero el
-- ajuste fino del modelo llegará en 3FN.
-- ============================================================

CREATE TABLE usuario_1fn (
    usuario_id              BIGSERIAL PRIMARY KEY,
    rol_id                  BIGINT NOT NULL,
    username                VARCHAR(100) NOT NULL,
    password_hash           VARCHAR(255) NOT NULL,
    estado_usuario          VARCHAR(20) NOT NULL,
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario_1fn_rol
        FOREIGN KEY (rol_id)
        REFERENCES rol_1fn (rol_id),

    CONSTRAINT uq_usuario_1fn_username UNIQUE (username),
    CONSTRAINT ck_usuario_1fn_estado
        CHECK (estado_usuario IN ('ACTIVO', 'INACTIVO'))
);

-- ============================================================
-- SECCIÓN 3. TABLA PROFESIONAL_1FN
--
-- Profesional clínico del consultorio. En esta fase se guarda
-- con su referencia a usuario, pero aún hay decisiones didácticas
-- que luego se limpian en 3FN.
-- ============================================================

CREATE TABLE profesional_1fn (
    profesional_id          BIGSERIAL PRIMARY KEY,
    usuario_id              BIGINT,
    nombres                 VARCHAR(100) NOT NULL,
    apellidos               VARCHAR(100) NOT NULL,
    especialidad_breve      VARCHAR(120),
    registro_profesional    VARCHAR(50),
    estado_profesional      VARCHAR(20) NOT NULL,
    fecha_creacion          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profesional_1fn_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario_1fn (usuario_id),

    CONSTRAINT uq_profesional_1fn_usuario UNIQUE (usuario_id),
    CONSTRAINT ck_profesional_1fn_nombres CHECK (btrim(nombres) <> ''),
    CONSTRAINT ck_profesional_1fn_apellidos CHECK (btrim(apellidos) <> ''),
    CONSTRAINT ck_profesional_1fn_estado
        CHECK (estado_profesional IN ('ACTIVO', 'INACTIVO'))
);

-- ============================================================
-- SECCIÓN 4. TABLA PACIENTE_1FN
-- ============================================================

CREATE TABLE paciente_1fn (
    paciente_id              BIGSERIAL PRIMARY KEY,
    nombres                  VARCHAR(100) NOT NULL,
    apellidos                VARCHAR(100) NOT NULL,
    telefono                 VARCHAR(20),
    cedula                   VARCHAR(20),
    fecha_nacimiento         DATE,
    direccion_basica         VARCHAR(200),
    fecha_creacion           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_paciente_nombres_no_vacio CHECK (btrim(nombres) <> ''),
    CONSTRAINT ck_paciente_apellidos_no_vacio CHECK (btrim(apellidos) <> '')
);

-- ============================================================
-- SECCIÓN 5. TABLA CITA_1FN
--
-- PK compuesta y redundancias intencionales para estudio.
-- La regla de agenda ya es por profesional.
-- ============================================================

CREATE TABLE cita_1fn (
    profesional_id                 BIGINT NOT NULL,
    fecha_hora_inicio              TIMESTAMP NOT NULL,

    paciente_id                    BIGINT NOT NULL,

    -- Redundancias intencionales del profesional
    profesional_nombres            VARCHAR(100) NOT NULL,
    profesional_apellidos          VARCHAR(100) NOT NULL,

    -- Redundancias intencionales del paciente
    paciente_nombres               VARCHAR(100) NOT NULL,
    paciente_apellidos             VARCHAR(100) NOT NULL,
    paciente_telefono              VARCHAR(20),

    estado_cita                    VARCHAR(20) NOT NULL,
    motivo_breve                   VARCHAR(200),
    observacion_operativa          VARCHAR(300),
    fecha_creacion                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_cita_1fn PRIMARY KEY (profesional_id, fecha_hora_inicio),
    CONSTRAINT fk_cita_1fn_profesional FOREIGN KEY (profesional_id) REFERENCES profesional_1fn (profesional_id),
    CONSTRAINT fk_cita_1fn_paciente FOREIGN KEY (paciente_id) REFERENCES paciente_1fn (paciente_id),
    CONSTRAINT ck_cita_1fn_estado CHECK (estado_cita IN ('PROGRAMADA', 'ATENDIDA', 'CANCELADA'))
);

-- ============================================================
-- SECCIÓN 6. TABLA ATENCION_1FN
--
-- Atención multidoctor. También repite paciente y profesional a
-- propósito. Puede nacer de una cita o ser espontánea.
-- ============================================================

CREATE TABLE atencion_1fn (
    profesional_id                 BIGINT NOT NULL,
    fecha_hora_atencion            TIMESTAMP NOT NULL,

    paciente_id                    BIGINT NOT NULL,

    -- Referencia opcional a cita previa
    cita_fecha_hora_inicio         TIMESTAMP,

    -- Redundancias intencionales del profesional
    profesional_nombres            VARCHAR(100) NOT NULL,
    profesional_apellidos          VARCHAR(100) NOT NULL,

    -- Redundancias intencionales del paciente
    paciente_nombres               VARCHAR(100) NOT NULL,
    paciente_apellidos             VARCHAR(100) NOT NULL,

    nota_breve                     VARCHAR(500) NOT NULL,
    indicaciones_breves            VARCHAR(500),
    fecha_creacion                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_atencion_1fn PRIMARY KEY (profesional_id, fecha_hora_atencion),
    CONSTRAINT fk_atencion_1fn_profesional FOREIGN KEY (profesional_id) REFERENCES profesional_1fn (profesional_id),
    CONSTRAINT fk_atencion_1fn_paciente FOREIGN KEY (paciente_id) REFERENCES paciente_1fn (paciente_id),
    CONSTRAINT fk_atencion_1fn_cita FOREIGN KEY (profesional_id, cita_fecha_hora_inicio) REFERENCES cita_1fn (profesional_id, fecha_hora_inicio),
    CONSTRAINT ck_atencion_1fn_nota_no_vacia CHECK (btrim(nota_breve) <> '')
);

-- ============================================================
-- SECCIÓN 7. TABLA COBRO_1FN
--
-- Cobro administrativo asociado a atención. También repite datos
-- a propósito para posterior normalización.
-- ============================================================

CREATE TABLE cobro_1fn (
    profesional_id                    BIGINT NOT NULL,
    fecha_hora_atencion               TIMESTAMP NOT NULL,
    registrado_por_usuario_id         BIGINT,

    -- Redundancias intencionales
    profesional_nombres               VARCHAR(100) NOT NULL,
    paciente_nombres                  VARCHAR(100) NOT NULL,
    nota_breve_atencion               VARCHAR(500) NOT NULL,

    monto                             NUMERIC(10,2) NOT NULL,
    metodo_pago                       VARCHAR(20) NOT NULL,
    estado_cobro                      VARCHAR(20) NOT NULL,
    observacion_administrativa        VARCHAR(300),
    fecha_hora_registro               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion                    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_cobro_1fn PRIMARY KEY (profesional_id, fecha_hora_atencion),
    CONSTRAINT fk_cobro_1fn_atencion FOREIGN KEY (profesional_id, fecha_hora_atencion) REFERENCES atencion_1fn (profesional_id, fecha_hora_atencion),
    CONSTRAINT fk_cobro_1fn_usuario FOREIGN KEY (registrado_por_usuario_id) REFERENCES usuario_1fn (usuario_id),
    CONSTRAINT ck_cobro_1fn_monto_positivo CHECK (monto >= 0),
    CONSTRAINT ck_cobro_1fn_metodo CHECK (metodo_pago IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA')),
    CONSTRAINT ck_cobro_1fn_estado CHECK (estado_cobro IN ('PAGADO', 'PENDIENTE'))
);

-- ============================================================
-- SECCIÓN 8. ÍNDICES BÁSICOS DE APOYO
-- ============================================================

CREATE INDEX idx_cita_1fn_paciente_id ON cita_1fn (paciente_id);
CREATE INDEX idx_cita_1fn_fecha_hora_inicio ON cita_1fn (fecha_hora_inicio);
CREATE INDEX idx_atencion_1fn_paciente_id ON atencion_1fn (paciente_id);
CREATE INDEX idx_atencion_1fn_fecha_hora_atencion ON atencion_1fn (fecha_hora_atencion);
CREATE INDEX idx_cobro_1fn_estado_cobro ON cobro_1fn (estado_cobro);

-- ============================================================
-- NOTAS FINALES
--
-- Este modelo ya expresa multidoctor, pero aún NO es 3FN porque:
--   - repite datos de profesional y paciente en cita/atención/cobro;
--   - repite parte de la atención dentro del cobro;
--   - usa PK compuestas a propósito para análisis didáctico.
-- ============================================================
