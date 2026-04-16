-- ============================================================
-- database-consultorio / Seeds demo V2 3FN corregidos por multidoctor
-- Archivo: 02_seed_demo_3FN.sql
-- Objetivo:
--   Cargar datos demo coherentes para estudio, pruebas locales
--   y demostración del subdominio consultorio multidoctor.
-- ============================================================

SET search_path TO consultorio;

-- ============================================================
-- SECCIÓN 1. ROLES
-- ============================================================

INSERT INTO rol (rol_id, nombre_rol, descripcion_breve) VALUES
(1, 'ADMIN_CONSULTORIO', 'Administrador interno del consultorio'),
(2, 'OPERADOR_CONSULTORIO', 'Usuario de recepción y operación administrativa'),
(3, 'PROFESIONAL_CONSULTORIO', 'Profesional clínico del consultorio');

SELECT setval(pg_get_serial_sequence('rol', 'rol_id'), COALESCE(MAX(rol_id), 1), true) FROM rol;

-- ============================================================
-- SECCIÓN 2. USUARIOS
-- ============================================================

INSERT INTO usuario (usuario_id, rol_id, username, password_hash, estado_usuario) VALUES
(1, 1, 'admin.consultorio', 'HASH_ADMIN_DEMO', 'ACTIVO'),
(2, 2, 'recepcion.ana', 'HASH_OPERADOR_DEMO', 'ACTIVO'),
(3, 3, 'dr.paredes', 'HASH_MEDICO_1', 'ACTIVO'),
(4, 3, 'dra.mora', 'HASH_MEDICO_2', 'ACTIVO');

SELECT setval(pg_get_serial_sequence('usuario', 'usuario_id'), COALESCE(MAX(usuario_id), 1), true) FROM usuario;

-- ============================================================
-- SECCIÓN 3. PROFESIONALES
-- ============================================================

INSERT INTO profesional (
    profesional_id, usuario_id, nombres, apellidos,
    especialidad_breve, registro_profesional, estado_profesional
) VALUES
(1, 3, 'Jorge Luis', 'Paredes Molina', 'Medicina general', 'REG-001', 'ACTIVO'),
(2, 4, 'Elena Sofía', 'Mora Cedeño', 'Medicina interna', 'REG-002', 'ACTIVO');

SELECT setval(pg_get_serial_sequence('profesional', 'profesional_id'), COALESCE(MAX(profesional_id), 1), true) FROM profesional;

-- ============================================================
-- SECCIÓN 4. PACIENTES
-- ============================================================

INSERT INTO paciente (
    paciente_id, nombres, apellidos, telefono, cedula,
    fecha_nacimiento, direccion_basica
) VALUES
(1, 'Carlos Alberto', 'Mendoza Vera', '0991001001', '0912345678', '1990-04-12', 'Guayaquil, sur'),
(2, 'María Fernanda', 'Solís Paredes', '0991001002', '0923456789', '1988-09-03', 'Guayaquil, centro'),
(3, 'José Luis', 'Villacrés Cedeño', '0991001003', NULL, '1979-01-19', 'Guayaquil, norte'),
(4, 'Ana Lucía', 'Peñafiel Torres', '0991001004', '0934567890', '1995-06-28', 'Guayaquil, norte'),
(5, 'Miguel Ángel', 'Cevallos Rivas', '0991001005', NULL, '1983-11-10', 'Durán, centro'),
(6, 'Rosa Elena', 'Gómez Salvatierra', '0991001006', '0945678901', '2000-02-15', 'Guayaquil, sur');

SELECT setval(pg_get_serial_sequence('paciente', 'paciente_id'), COALESCE(MAX(paciente_id), 1), true) FROM paciente;

-- ============================================================
-- SECCIÓN 5. CITAS
-- ============================================================

INSERT INTO cita (
    cita_id, paciente_id, profesional_id, fecha_hora_inicio,
    estado_cita, motivo_breve, observacion_operativa
) VALUES
(1, 1, 1, '2026-04-01 09:00:00', 'ATENDIDA',   'Control general',         'Paciente llegó puntual'),
(2, 2, 1, '2026-04-01 10:00:00', 'ATENDIDA',   'Dolor de garganta',       'Se registró atención completa'),
(3, 3, 1, '2026-04-01 11:30:00', 'CANCELADA',  'Chequeo presión',         'Paciente avisó que no asistiría'),
(4, 4, 2, '2026-04-02 08:30:00', 'ATENDIDA',   'Malestar estomacal',      'Atención realizada el mismo día'),
(5, 5, 2, '2026-04-02 09:15:00', 'PROGRAMADA', 'Control de seguimiento',  'Pendiente de confirmación'),
(6, 6, 2, '2026-04-03 14:00:00', 'PROGRAMADA', 'Cefalea recurrente',      'Paciente nueva en agenda');

SELECT setval(pg_get_serial_sequence('cita', 'cita_id'), COALESCE(MAX(cita_id), 1), true) FROM cita;

-- ============================================================
-- SECCIÓN 6. ATENCIONES
--
-- Se mezclan atenciones provenientes de cita con una atención
-- espontánea sin cita previa.
-- ============================================================

INSERT INTO atencion (
    atencion_id, paciente_id, profesional_id, cita_id,
    fecha_hora_atencion, nota_breve, indicaciones_breves
) VALUES
(1, 1, 1, 1, '2026-04-01 09:10:00',
 'Paciente refiere cansancio general y molestias leves.',
 'Reposo, hidratación y control en una semana.'),

(2, 2, 1, 2, '2026-04-01 10:12:00',
 'Molestia en garganta con irritación moderada.',
 'Evitar bebidas frías y regresar si persiste fiebre.'),

(3, 4, 2, 4, '2026-04-02 08:45:00',
 'Consulta por malestar estomacal con evolución corta.',
 'Dieta blanda e hidratación controlada.'),

(4, 6, 2, NULL, '2026-04-03 12:40:00',
 'Atención espontánea por cefalea intensa sin cita previa.',
 'Observación en casa y retorno si aumenta intensidad.');

SELECT setval(pg_get_serial_sequence('atencion', 'atencion_id'), COALESCE(MAX(atencion_id), 1), true) FROM atencion;

-- ============================================================
-- SECCIÓN 7. COBROS
-- ============================================================

INSERT INTO cobro (
    cobro_id, atencion_id, registrado_por_usuario_id,
    monto, metodo_pago, estado_cobro, observacion_administrativa
) VALUES
(1, 1, 2, 20.00, 'EFECTIVO', 'PAGADO', 'Pago realizado al finalizar la consulta'),
(2, 2, 2, 25.00, 'TRANSFERENCIA', 'PAGADO', 'Comprobante validado en recepción'),
(3, 3, 2, 22.00, 'TARJETA', 'PAGADO', 'Pago aprobado sin novedades'),
(4, 4, 2, 18.00, 'EFECTIVO', 'PENDIENTE', 'Atención espontánea con pago pendiente');

SELECT setval(pg_get_serial_sequence('cobro', 'cobro_id'), COALESCE(MAX(cobro_id), 1), true) FROM cobro;

-- ============================================================
-- SECCIÓN 8. CONSULTAS DE VERIFICACIÓN OPCIONALES
-- ============================================================

-- Agenda por profesional
-- SELECT c.cita_id, p2.nombres || ' ' || p2.apellidos AS profesional,
--        p.nombres || ' ' || p.apellidos AS paciente,
--        c.fecha_hora_inicio, c.estado_cita
-- FROM cita c
-- JOIN profesional pr ON pr.profesional_id = c.profesional_id
-- JOIN paciente p ON p.paciente_id = c.paciente_id
-- JOIN profesional p2ref ON p2ref.profesional_id = pr.profesional_id
-- JOIN profesional p2 ON p2.profesional_id = pr.profesional_id
-- ORDER BY c.fecha_hora_inicio;

-- Atenciones con profesional
-- SELECT a.atencion_id,
--        pac.nombres || ' ' || pac.apellidos AS paciente,
--        pro.nombres || ' ' || pro.apellidos AS profesional,
--        a.cita_id, a.fecha_hora_atencion
-- FROM atencion a
-- JOIN paciente pac ON pac.paciente_id = a.paciente_id
-- JOIN profesional pro ON pro.profesional_id = a.profesional_id
-- ORDER BY a.fecha_hora_atencion;

-- Cobros con usuario registrador
-- SELECT co.cobro_id, co.monto, co.estado_cobro, u.username
-- FROM cobro co
-- LEFT JOIN usuario u ON u.usuario_id = co.registrado_por_usuario_id
-- ORDER BY co.cobro_id;
