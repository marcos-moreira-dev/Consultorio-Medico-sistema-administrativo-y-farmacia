CREATE SCHEMA IF NOT EXISTS consultorio;
SET search_path TO consultorio;

CREATE TABLE IF NOT EXISTS rol (
    rol_id BIGSERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion_breve VARCHAR(200),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_rol_nombre UNIQUE (nombre_rol),
    CONSTRAINT ck_rol_nombre_no_vacio CHECK (btrim(nombre_rol) <> '')
);

CREATE TABLE IF NOT EXISTS usuario (
    usuario_id BIGSERIAL PRIMARY KEY,
    rol_id BIGINT NOT NULL,
    username VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    estado_usuario VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol (rol_id),
    CONSTRAINT uq_usuario_username UNIQUE (username),
    CONSTRAINT ck_usuario_estado CHECK (estado_usuario IN ('ACTIVO', 'INACTIVO')),
    CONSTRAINT ck_usuario_username_no_vacio CHECK (btrim(username) <> '')
);

CREATE TABLE IF NOT EXISTS profesional (
    profesional_id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    especialidad_breve VARCHAR(120),
    registro_profesional VARCHAR(50),
    estado_profesional VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_profesional_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    CONSTRAINT uq_profesional_usuario UNIQUE (usuario_id),
    CONSTRAINT ck_profesional_estado CHECK (estado_profesional IN ('ACTIVO', 'INACTIVO')),
    CONSTRAINT ck_profesional_nombres CHECK (btrim(nombres) <> ''),
    CONSTRAINT ck_profesional_apellidos CHECK (btrim(apellidos) <> '')
);

CREATE TABLE IF NOT EXISTS paciente (
    paciente_id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    cedula VARCHAR(20),
    fecha_nacimiento DATE,
    direccion_basica VARCHAR(200),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_paciente_nombres_no_vacio CHECK (btrim(nombres) <> ''),
    CONSTRAINT ck_paciente_apellidos_no_vacio CHECK (btrim(apellidos) <> '')
);

CREATE TABLE IF NOT EXISTS cita (
    cita_id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    profesional_id BIGINT NOT NULL,
    fecha_hora_inicio TIMESTAMP NOT NULL,
    estado_cita VARCHAR(20) NOT NULL,
    motivo_breve VARCHAR(200),
    observacion_operativa VARCHAR(300),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cita_paciente FOREIGN KEY (paciente_id) REFERENCES paciente (paciente_id),
    CONSTRAINT fk_cita_profesional FOREIGN KEY (profesional_id) REFERENCES profesional (profesional_id),
    CONSTRAINT ck_cita_estado CHECK (estado_cita IN ('PROGRAMADA', 'ATENDIDA', 'CANCELADA')),
    CONSTRAINT uq_cita_profesional_fecha UNIQUE (profesional_id, fecha_hora_inicio)
);

CREATE TABLE IF NOT EXISTS atencion (
    atencion_id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    profesional_id BIGINT NOT NULL,
    cita_id BIGINT,
    fecha_hora_atencion TIMESTAMP NOT NULL,
    nota_breve VARCHAR(500) NOT NULL,
    indicaciones_breves VARCHAR(500),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_atencion_paciente FOREIGN KEY (paciente_id) REFERENCES paciente (paciente_id),
    CONSTRAINT fk_atencion_profesional FOREIGN KEY (profesional_id) REFERENCES profesional (profesional_id),
    CONSTRAINT fk_atencion_cita FOREIGN KEY (cita_id) REFERENCES cita (cita_id),
    CONSTRAINT uq_atencion_cita UNIQUE (cita_id),
    CONSTRAINT ck_atencion_nota_no_vacia CHECK (btrim(nota_breve) <> '')
);

CREATE TABLE IF NOT EXISTS cobro (
    cobro_id BIGSERIAL PRIMARY KEY,
    atencion_id BIGINT NOT NULL,
    registrado_por_usuario_id BIGINT,
    monto NUMERIC(10,2) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL,
    estado_cobro VARCHAR(20) NOT NULL,
    observacion_administrativa VARCHAR(300),
    fecha_hora_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cobro_atencion FOREIGN KEY (atencion_id) REFERENCES atencion (atencion_id),
    CONSTRAINT fk_cobro_usuario FOREIGN KEY (registrado_por_usuario_id) REFERENCES usuario (usuario_id),
    CONSTRAINT uq_cobro_atencion UNIQUE (atencion_id),
    CONSTRAINT ck_cobro_monto_positivo CHECK (monto >= 0),
    CONSTRAINT ck_cobro_metodo CHECK (metodo_pago IN ('EFECTIVO', 'TRANSFERENCIA', 'TARJETA')),
    CONSTRAINT ck_cobro_estado CHECK (estado_cobro IN ('PAGADO', 'PENDIENTE'))
);

CREATE INDEX IF NOT EXISTS idx_usuario_rol_id ON usuario (rol_id);
CREATE INDEX IF NOT EXISTS idx_profesional_estado ON profesional (estado_profesional);
CREATE INDEX IF NOT EXISTS idx_paciente_apellidos ON paciente (apellidos);
CREATE INDEX IF NOT EXISTS idx_paciente_telefono ON paciente (telefono);
CREATE INDEX IF NOT EXISTS idx_paciente_cedula ON paciente (cedula);
CREATE INDEX IF NOT EXISTS idx_cita_paciente_id ON cita (paciente_id);
CREATE INDEX IF NOT EXISTS idx_cita_profesional_fecha ON cita (profesional_id, fecha_hora_inicio);
CREATE INDEX IF NOT EXISTS idx_atencion_paciente_id ON atencion (paciente_id);
CREATE INDEX IF NOT EXISTS idx_atencion_profesional_id ON atencion (profesional_id);
CREATE INDEX IF NOT EXISTS idx_atencion_fecha_hora ON atencion (fecha_hora_atencion);
CREATE INDEX IF NOT EXISTS idx_cobro_estado ON cobro (estado_cobro);
CREATE INDEX IF NOT EXISTS idx_cobro_usuario_id ON cobro (registrado_por_usuario_id);

-- ============================================================
-- TABLA AUDITORÍA: Registro de eventos operativos
-- ============================================================

CREATE TABLE IF NOT EXISTS audit_log (
    evento_id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT,
    username VARCHAR(100),
    tipo_evento VARCHAR(50) NOT NULL,
    modulo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(500),
    entidad_id BIGINT,
    entidad_tipo VARCHAR(50),
    correlation_id VARCHAR(100),
    direccion_ip VARCHAR(45),
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    CONSTRAINT ck_audit_tipo_no_vacio CHECK (btrim(tipo_evento) <> ''),
    CONSTRAINT ck_audit_modulo_no_vacio CHECK (btrim(modulo) <> '')
);

CREATE INDEX IF NOT EXISTS idx_audit_fecha_hora ON audit_log (fecha_hora);
CREATE INDEX IF NOT EXISTS idx_audit_usuario_id ON audit_log (usuario_id);
CREATE INDEX IF NOT EXISTS idx_audit_username ON audit_log (username);
CREATE INDEX IF NOT EXISTS idx_audit_modulo ON audit_log (modulo);
CREATE INDEX IF NOT EXISTS idx_audit_tipo_evento ON audit_log (tipo_evento);
CREATE INDEX IF NOT EXISTS idx_audit_correlation_id ON audit_log (correlation_id);
