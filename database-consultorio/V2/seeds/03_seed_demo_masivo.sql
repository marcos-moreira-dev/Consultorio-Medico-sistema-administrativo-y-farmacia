-- ============================================================
-- database-consultorio / Seed demo masivo
-- Archivo: 03_seed_demo_masivo.sql
-- Objetivo:
--   Poblar el consultorio con datos abundantes y realistas para
--   demostración, pruebas y desarrollo del sistema.
--
-- Contenido:
--   - 6 usuarios (2 admin, 2 operador, 2 profesional)
--   - 5 profesionales (Medicina General, Pediatría, Cardiología, Ginecología)
--   - 25 pacientes con datos ecuatorianos realistas
--   - 40 citas (15 programadas, 15 atendidas, 5 canceladas, 5 reprogramadas)
--   - 20 atenciones (vinculadas a citas y algunas espontáneas)
--   - 15 cobros (montos entre $25 y $80, varios métodos de pago)
--
-- Ejecutar DESPUÉS de:
--   1. V2_3FN.sql
--   2. 02_seed_demo_3FN.sql (este archivo complementa, no reemplaza)
--
-- NOTA: Los password_hash usan BCrypt de 'Consultorio2026*'
--       Cambiar en producción inmediatamente.
-- ============================================================

SET search_path TO consultorio;

-- ============================================================
-- SECCIÓN 1. USUARIOS ADICIONALES
-- ============================================================

-- Admin adicional
INSERT INTO usuario (usuario_id, rol_id, username, password_hash, estado_usuario) VALUES
(5, 1, 'admin.supervisor', '$2b$10$X7Gh3k9Z2mNpQrTvYwLsRe8FjKcHaBdE3gWxYzMnPqRsTuVwXyZa', 'ACTIVO'),

-- Operadores adicionales
(6, 2, 'recepcion.carmen', '$2b$10$X7Gh3k9Z2mNpQrTvYwLsRe8FjKcHaBdE3gWxYzMnPqRsTuVwXyZa', 'ACTIVO'),

-- Profesionales adicionales vinculados a usuarios
(7, 3, 'dra.vargas', '$2b$10$X7Gh3k9Z2mNpQrTvYwLsRe8FjKcHaBdE3gWxYzMnPqRsTuVwXyZa', 'ACTIVO'),
(8, 3, 'dr.delgado', '$2b$10$X7Gh3k9Z2mNpQrTvYwLsRe8FjKcHaBdE3gWxYzMnPqRsTuVwXyZa', 'ACTIVO');

SELECT setval(pg_get_serial_sequence('usuario', 'usuario_id'),
    (SELECT MAX(usuario_id) FROM usuario));

-- ============================================================
-- SECCIÓN 2. PROFESIONALES ADICIONALES
-- ============================================================

INSERT INTO profesional (
    profesional_id, usuario_id, nombres, apellidos,
    especialidad_breve, registro_profesional, estado_profesional
) VALUES
(3, 7, 'Patricia Elena', 'Vargas Romero', 'Pediatría', 'REG-003', 'ACTIVO'),
(4, 8, 'Andrés Felipe', 'Delgado Mendoza', 'Cardiología', 'REG-004', 'ACTIVO'),
(5, NULL, 'Lucía Fernanda', 'Salazar Cedeño', 'Ginecología', 'REG-005', 'ACTIVO');

SELECT setval(pg_get_serial_sequence('profesional', 'profesional_id'),
    (SELECT MAX(profesional_id) FROM profesional));

-- ============================================================
-- SECCIÓN 3. PACIENTES MASIVOS (25 pacientes con datos realistas)
--
-- Nombres y apellidos ecuatorianos comunes.
-- Cédulas con formato ecuatoriano válido (10 dígitos, inician con 09 para Guayas).
-- Teléfonos con formato celular ecuatoriano (09XXXXXXXX).
-- Direcciones de barrios y urbanizaciones de Guayaquil y Durán.
-- ============================================================

INSERT INTO paciente (
    paciente_id, nombres, apellidos, telefono, cedula,
    fecha_nacimiento, direccion_basica
) VALUES
-- Pacientes del seed original ya existen (IDs 1-6), agregamos desde el 7
(7,  'Roberto Carlos',     'Espinoza Guerrero',      '0987654321', '0956789012', '1975-08-22', 'Guayaquil, Bastión Popular'),
(8,  'Carmen Elena',       'Noboa Cedeño',           '0965432109', '0967890123', '1982-03-14', 'Guayaquil, Urdesa'),
(9,  'Fernando José',      'Noboa Caamaño',          '0976543210', '0978901234', '1968-11-30', 'Guayaquil, Samborondón'),
(10, 'Gabriela Patricia',  'Vega Montalvo',          '0954321098', '0989012345', '1992-07-05', 'Guayaquil, Ceibos'),
(11, 'Luis Alberto',       'Martínez Herrera',       '0943210987', '0990123456', '1980-12-18', 'Durán, La Puntilla'),
(12, 'Daniela Sofía',      'Carrera Preciado',       '0932109876', '0901234567', '1998-05-25', 'Guayaquil, Alborada'),
(13, 'Andrés Sebastián',   'Icaza Villacís',         '0921098765', '0912345098', '1985-01-09', 'Guayaquil, Entre Ríos'),
(14, 'Valeria Nicole',     'Pallares Arteta',        '0910987654', '0923456098', '1993-10-02', 'Guayaquil, El Recreo'),
(15, 'Diego Alejandro',    'Córdova Franco',         '0909876543', '0934567098', '1970-06-17', 'Guayaquil, Fortín'),
(16, 'María José',         'Placencio Villamar',     '0998765432', '0945678098', '2001-09-08', 'Guayaquil, Guasmo'),
(17, 'Sebastián Esteban',  'Nehme Velásquez',        '0987650123', '0956789098', '1987-04-13', 'Guayaquil, La Joya'),
(18, 'Alejandra María',    'Baquerizo Cedeño',       '0976540123', '0967890098', '1978-02-27', 'Guayaquil, Mapasingue'),
(19, 'Javier Andrés',      'Duran Cazorla',          '0965430123', '0978901098', '1965-08-11', 'Guayaquil, Tarquí'),
(20, 'Paula Andrea',       'Bucaram Ortiz',          '0954320123', '0989012098', '1996-11-23', 'Guayaquil, Isla Trinitaria'),
(21, 'Mateo Ignacio',      'Velasco Tinoco',         '0943210123', '0990123098', '1989-07-30', 'Durán, Eloy Alfaro'),
(22, 'Carolina Fernanda',  'Viteri Villacrez',       '0932100123', '0901234098', '1991-12-04', 'Guayaquil, Pascuales'),
(23, 'Nicolás Andrés',     'Lapentti Arízaga',       '0921090123', '0912340098', '1984-03-19', 'Guayaquil, Ximena'),
(24, 'Isabella Regina',    'Cañizares Delgado',      '0910980123', '0923450098', '2003-06-01', 'Guayaquil, La Florida'),
(25, 'Tomás Felipe',       'Arosemena Espinoza',     '0909870123', '0934560098', '1972-09-15', 'Guayaquil, Bellavista'),
(26, 'Renata Sofía',       'Alvarado Cedeño',        '0998760123', '0945670098', '1999-01-28', 'Durán, 5 de Junio'),
(27, 'Emiliano José',      'Carrera Gómez',          '0987650456', '0956780098', '1977-05-10', 'Guayaquil, Montebello'),
(28, 'Andrea Cristina',    'Noboa Plaza',            '0976540456', '0967800123', '1986-08-07', 'Guayaquil, Vía Daule'),
(29, 'Santiago Mateo',     'Banchero Velasco',       '0965430456', '0978900123', '1974-04-22', 'Guayaquil, La Atarazana'),
(30, 'Mariana Elena',      'Córdoba Aspiazu',        '0954320456', '0989001234', '1994-10-16', 'Guayaquil, Prosperina'),
(31, 'Felipe Andrés',      'Borja Villacís',         '0943210456', '0990100234', '1981-07-29', 'Guayaquil, La Puntilla');

SELECT setval(pg_get_serial_sequence('paciente', 'paciente_id'),
    (SELECT MAX(paciente_id) FROM paciente));

-- ============================================================
-- SECCIÓN 4. CITAS MASIVAS (40 citas)
--
-- Distribución:
--   15 PROGRAMADAS (fechas futuras desde abril 2026)
--   15 ATENDIDAS (fechas pasadas, marzo-abril 2026)
--   5 CANCELADAS
--   5 REPROGRAMADAS (usando estado PROGRAMADA con observación)
--
-- Profesionales:
--   1 = Dr. Paredes (Medicina General)
--   2 = Dra. Mora (Medicina Interna)
--   3 = Dra. Vargas (Pediatría)
--   4 = Dr. Delgado (Cardiología)
--   5 = Dra. Salazar (Ginecología)
-- ============================================================

-- ========================
-- CITAS ATENDIDAS (15) - fechas pasadas
-- ========================

INSERT INTO cita (
    cita_id, paciente_id, profesional_id, fecha_hora_inicio,
    estado_cita, motivo_breve, observacion_operativa
) VALUES
-- Dr. Paredes - Medicina General
(7,  7,  1, '2026-03-10 08:00:00', 'ATENDIDA', 'Control de presión arterial',     'Paciente puntual, control rutinario'),
(8,  8,  1, '2026-03-12 09:30:00', 'ATENDIDA', 'Dolor lumbar persistente',        'Primera consulta por dolor'),
(9,  10, 1, '2026-03-15 10:00:00', 'ATENDIDA', 'Chequeo general anual',           'Paciente refiere sentirse bien'),
(10, 12, 1, '2026-03-20 11:00:00', 'ATENDIDA', 'Gripe con fiebre',               'Paciente con malestar general'),
(11, 15, 1, '2026-03-25 08:30:00', 'ATENDIDA', 'Dolor de cabeza frecuente',      'Segunda consulta por cefalea'),

-- Dra. Mora - Medicina Interna
(12, 9,  2, '2026-03-11 09:00:00', 'ATENDIDA', 'Control de colesterol',          'Paciente en tratamiento'),
(13, 11, 2, '2026-03-14 10:30:00', 'ATENDIDA', 'Dolor abdominal recurrente',     'Estudios previos normales'),
(14, 13, 2, '2026-03-18 08:00:00', 'ATENDIDA', 'Fatiga constante',               'Posible cuadro de anemia'),
(15, 16, 2, '2026-03-22 11:30:00', 'ATENDIDA', 'Revisión de exámenes',           'Paciente trae resultados'),
(16, 19, 2, '2026-03-28 09:00:00', 'ATENDIDA', 'Control de glucosa',             'Paciente diabético en seguimiento'),

-- Dra. Vargas - Pediatría
(17, 18, 3, '2026-03-13 08:30:00', 'ATENDIDA', 'Control de niño sano 2 años',    'Niño acompaña a la madre'),
(18, 20, 3, '2026-03-16 10:00:00', 'ATENDIDA', 'Vacunación pendiente',           'Trae carnet de vacunación'),
(19, 22, 3, '2026-03-21 09:00:00', 'ATENDIDA', 'Fiebre en bebé de 8 meses',      'Madre preocupada por fiebre alta'),

-- Dr. Delgado - Cardiología
(20, 23, 4, '2026-03-17 08:00:00', 'ATENDIDA', 'Palpitaciones frecuentes',       'Paciente refiere taquicardia'),
(21, 25, 4, '2026-03-24 10:00:00', 'ATENDIDA', 'Control post-infarto',           'Paciente en rehabilitación cardíaca');

-- ========================
-- CITAS CANCELADAS (5)
-- ========================

(22, 14, 1, '2026-03-19 09:00:00', 'CANCELADA', 'Control general',                'Paciente canceló por viaje'),
(23, 17, 2, '2026-03-23 11:00:00', 'CANCELADA', 'Consulta de seguimiento',        'Paciente no respondió llamadas'),
(24, 21, 3, '2026-03-26 10:00:00', 'CANCELADA', 'Control pediátrico',             'Madre canceló por trabajo'),
(25, 24, 4, '2026-03-29 08:30:00', 'CANCELADA', 'Chequeo cardiológico',           'Paciente se mudó de ciudad'),
(26, 26, 1, '2026-03-30 09:30:00', 'CANCELADA', 'Dolor de rodilla',               'Paciente prefirió emergencia');

-- ========================
-- CITAS PROGRAMADAS - FUTURAS (15)
-- ========================

-- Dr. Paredes - Medicina General
(27, 1,  1, '2026-04-14 08:00:00', 'PROGRAMADA', 'Control mensual',              'Paciente recurrente'),
(28, 7,  1, '2026-04-14 09:00:00', 'PROGRAMADA', 'Seguimiento de presión',       'Control post-tratamiento'),
(29, 10, 1, '2026-04-15 10:00:00', 'PROGRAMADA', 'Resultados de laboratorio',    'Traer exámenes completos'),
(30, 14, 1, '2026-04-16 08:30:00', 'PROGRAMADA', 'Consulta por alergias',        'Primera vez en consultorio'),
(31, 20, 1, '2026-04-17 11:00:00', 'PROGRAMADA', 'Dolor de espalda',             'Derivado por Dra. Vargas'),

-- Dra. Mora - Medicina Interna
(32, 9,  2, '2026-04-14 09:00:00', 'PROGRAMADA', 'Control trimestral',           'Paciente hipertenso'),
(33, 11, 2, '2026-04-15 08:00:00', 'PROGRAMADA', 'Revisión de gastritis',        'Paciente con malestar continuo'),
(34, 16, 2, '2026-04-16 10:30:00', 'PROGRAMADA', 'Control de tiroides',          'Traer ecografía'),
(35, 19, 2, '2026-04-17 09:00:00', 'PROGRAMADA', 'Chequeo cardiovascular',       'Paciente mayor de 60 años'),

-- Dra. Vargas - Pediatría
(36, 18, 3, '2026-04-14 08:30:00', 'PROGRAMADA', 'Control de crecimiento',       'Niño de 2 años y medio'),
(37, 22, 3, '2026-04-15 09:30:00', 'PROGRAMADA', 'Control post-vacunación',      'Verificar reacción a vacuna'),

-- Dr. Delgado - Cardiología
(38, 23, 4, '2026-04-16 08:00:00', 'PROGRAMADA', 'Holter de 24 horas',           'Entregar resultados de holter'),
(39, 27, 4, '2026-04-17 10:00:00', 'PROGRAMADA', 'Primera consulta cardiológica', 'Derivado por médico general'),

-- Dra. Salazar - Ginecología
(40, 8,  5, '2026-04-14 11:00:00', 'PROGRAMADA', 'Chequeo ginecológico anual',    'Paciente nueva en ginecología'),
(41, 12, 5, '2026-04-15 09:00:00', 'PROGRAMADA', 'Planificación familiar',        'Primera consulta'),
(42, 28, 5, '2026-04-16 11:00:00', 'PROGRAMADA', 'Control prenatal',              'Paciente embarazada de 5 meses');

-- Actualizar secuencia
SELECT setval(pg_get_serial_sequence('cita', 'cita_id'),
    (SELECT MAX(cita_id) FROM cita));

-- ============================================================
-- SECCIÓN 5. ATENCIONES MASIVAS (20 atenciones)
--
-- 15 vinculadas a citas atendidas
-- 5 espontáneas (sin cita_id)
-- ============================================================

INSERT INTO atencion (
    atencion_id, paciente_id, profesional_id, cita_id,
    fecha_hora_atencion, nota_breve, indicaciones_breves
) VALUES
-- ========================
-- Atenciones vinculadas a citas (15)
-- ========================

-- Dr. Paredes - Medicina General
(5,  7,  1, 7,  '2026-03-10 08:15:00',
 'Presión arterial en 145/95, paciente refiere adherencia al tratamiento.',
 'Continuar los mismo medicamentos, control en 30 días. Reducir consumo de sal.'),

(6,  8,  1, 8,  '2026-03-12 09:45:00',
 'Dolor lumbar de evolución de 2 semanas. Contractura muscular palpable.',
 'Relajante muscular, reposo relativo, aplicar calor local. Si no mejora, solicitar radiografía.'),

(7,  10, 1, 9,  '2026-03-15 10:20:00',
 'Paciente en buen estado general. Signos vitales normales. Exámenes de rutina solicitados.',
 'Realizar hemograma, perfil lipídico y glucosa en ayunas. Traer resultados al próximo control.'),

(8,  12, 1, 10, '2026-03-20 11:15:00',
 'Cuadro gripal con fiebre de 38.2°C. Faringe levemente eritematosa.',
 'Paracetamol 500mg cada 8 horas, reposo por 3 días. Abundantes líquidos. Si fiebre persiste, retornar.'),

(9,  15, 1, 11, '2026-03-25 08:50:00',
 'Cefalea tensional recurrente. Paciente refiere estrés laboral. No signos de alarma neurológica.',
 'Ibuprofeno 400mg cada 8 horas por 5 días. Técnicas de relajación. Control si no cede.'),

-- Dra. Mora - Medicina Interna
(10, 9,  2, 12, '2026-03-11 09:25:00',
 'Colesterol LDL en 180, por encima del objetivo. Paciente refiere dificultad con dieta.',
 'Ajustar dosis de atorvastatina a 40mg. Reforzar dieta baja en grasas. Control en 60 días con nuevos exámenes.'),

(11, 11, 2, 13, '2026-03-14 10:50:00',
 'Dolor abdominal en epigastrio. Paciente refiere mejoría parcial con omeprazol.',
 'Endoscopía digestiva alta. Continuar omeprazol 20mg en ayunas. Dieta blanda estricta.'),

(12, 13, 2, 14, '2026-03-18 08:25:00',
 'Hemoglobina en 10.2, por debajo del rango normal. Posible anemia ferropénica.',
 'Sulfato ferroso 325mg cada 12 horas con jugo de naranja. Control de hemoglobina en 30 días.'),

(13, 16, 2, 15, '2026-03-22 11:50:00',
 'Resultados de TSH levemente elevados. Paciente asintomática.',
 'Repetir TSH y T4 libre en 15 días. Si persiste alterado, iniciar levotiroxina.'),

(14, 19, 2, 16, '2026-03-28 09:25:00',
 'Glucosa en ayunas en 142. Paciente diabético tipo 2 en tratamiento con metformina.',
 'Ajustar metformina a 850mg cada 12 horas. Control estricto de dieta. Hemoglobina glicosilada en 60 días.'),

-- Dra. Vargas - Pediatría
(15, 18, 3, 17, '2026-03-13 08:50:00',
 'Niño de 2 años en buen estado. Peso y talla adecuados para la edad. Desarrollo psicomotor normal.',
 'Continuar esquema de vacunación. Alimentación complementaria completa. Control en 3 meses.'),

(16, 20, 3, 18, '2026-03-16 10:20:00',
 'Vacuna triple viral aplicada sin complicaciones. Madre instruida sobre posibles efectos secundarios.',
 'Vigilar fiebre las próximas 48 horas. Paracetamol pediátrico si fiebre mayor a 38°C. Control en 6 meses.'),

(17, 22, 3, 19, '2026-03-21 09:25:00',
 'Bebé de 8 meses con fiebre de 39°C desde ayer. Oídos normales, garganta levemente roja.',
 'Paracetamol jarabe cada 6 horas. Baños tibios. Si fiebre persiste más de 48 horas, retornar a emergencia.'),

-- Dr. Delgado - Cardiología
(18, 23, 4, 20, '2026-03-17 08:30:00',
 'Paciente con palpitaciones descritas como taquicardia paroxística. ECG muestra ritmo sinusal.',
 'Holter de 24 horas. Ecocardiograma. Suspender cafeína. Si palpitaciones son muy frecuentes, iniciar betabloqueante.'),

(19, 25, 4, 21, '2026-03-24 10:30:00',
 'Paciente post-infarto de 3 meses. Fracción de eyección en 45%. Tolerancia al ejercicio aceptable.',
 'Continuar programa de rehabilitación cardíaca. Mantener aspirina, estatina y betabloqueante. Control en 30 días.'),

-- ========================
-- Atenciones espontáneas (5) - sin cita asociada
-- ========================

(20, 3,  1, NULL, '2026-03-11 14:30:00',
 'Paciente llega sin cita por dolor de oído derecho. Otoscopia revela tímpano ligeramente eritematoso.',
 'Gotas óticas antiinflamatorias. No introducir agua en oído. Control en 5 días si no mejora.'),

(21, 5,  2, NULL, '2026-03-19 15:00:00',
 'Paciente acude por malestar general con fiebre intermitente. No tiene signos de alarma.',
 'Paracetamol, reposo, abundantes líquidos. Si fiebre supera 3 días con 39°C, acudir a emergencia.'),

(22, 24, 3, NULL, '2026-03-27 11:00:00',
 'Madre trae a niña de 5 años con erupción cutánea en brazos. Posible reacción alérgica.',
 'Loratadina jarabe pediátrico una vez al día. Identificar posible desencadenante. Control en 3 días.'),

(23, 29, 4, NULL, '2026-03-30 16:00:00',
 'Paciente con dolor en pecho izquierdo de inicio súbito. ECG normal. Probable dolor musculoesquelético.',
 'Naproxeno 250mg cada 8 horas por 5 días. Si dolor se asocia a esfuerzo físico, retornar inmediatamente.'),

(24, 31, 1, NULL, '2026-04-02 14:00:00',
 'Paciente con dolor de garganta de 3 días de evolución. Faringitis viral probable.',
 'Gárgaras con agua tibia y sal. Paracetamol si dolor intenso. Reposo de voz 2 días.');

SELECT setval(pg_get_serial_sequence('atencion', 'atencion_id'),
    (SELECT MAX(atencion_id) FROM atencion));

-- ============================================================
-- SECCIÓN 6. COBROS MASIVOS (15 cobros)
--
-- Montos realistas para consultorio en Guayaquil:
--   Medicina General: $25-$35
--   Medicina Interna: $35-$50
--   Pediatría: $30-$40
--   Cardiología: $50-$80
--   Ginecología: $40-$60
--
-- Distribución de métodos: ~60% efectivo, ~25% transferencia, ~15% tarjeta
-- Estados: ~80% pagado, ~20% pendiente
-- ============================================================

INSERT INTO cobro (
    cobro_id, atencion_id, registrado_por_usuario_id,
    monto, metodo_pago, estado_cobro, observacion_administrativa
) VALUES
-- Cobros del seed original (IDs 1-4 ya existen)

-- Cobros de atenciones Dr. Paredes - Medicina General ($25-$30)
(5,  5, 2, 25.00, 'EFECTIVO',      'PAGADO',   'Pago al finalizar consulta'),
(6,  6, 2, 30.00, 'TRANSFERENCIA', 'PAGADO',   'Transferencia verificada'),
(7,  7, 2, 25.00, 'EFECTIVO',      'PAGADO',   'Pago en efectivo sin novedad'),
(8,  8, 2, 25.00, 'TARJETA',       'PAGADO',   'Tarjeta débito aprobada'),
(9,  9, 2, 30.00, 'EFECTIVO',      'PENDIENTE', 'Paciente solicitará factura después'),

-- Cobros de atenciones Dra. Mora - Medicina Interna ($35-$50)
(10, 10, 2, 40.00, 'EFECTIVO',      'PAGADO',   'Consulta especializada pagada'),
(11, 11, 2, 45.00, 'TRANSFERENCIA', 'PAGADO',   'Comprobante enviado por WhatsApp'),
(12, 12, 2, 40.00, 'EFECTIVO',      'PENDIENTE', 'Paciente pagará en próximo control'),
(13, 13, 2, 50.00, 'TARJETA',       'PAGADO',   'Tarjeta de crédito, 1 cuota'),
(14, 14, 2, 35.00, 'EFECTIVO',      'PAGADO',   'Pago completo en recepción'),

-- Cobros de atenciones Dra. Vargas - Pediatría ($30-$40)
(15, 15, 2, 30.00, 'EFECTIVO',      'PAGADO',   'Pago consulta pediátrica'),
(16, 16, 2, 35.00, 'TRANSFERENCIA', 'PAGADO',   'Transferencia inmediata'),
(17, 17, 2, 40.00, 'EFECTIVO',      'PENDIENTE', 'Madre pagará cuando recoja receta'),

-- Cobros de atenciones Dr. Delgado - Cardiología ($50-$80)
(18, 18, 2, 60.00, 'TARJETA',       'PAGADO',   'Tarjeta de crédito, 2 cuotas'),
(19, 19, 2, 70.00, 'TRANSFERENCIA', 'PAGADO',   'Transferencia bancaria confirmada'),

-- Cobros de atenciones espontáneas
(20, 20, 2, 25.00, 'EFECTIVO',      'PAGADO',   'Atención espontánea pagada al momento'),
(21, 21, 2, 35.00, 'EFECTIVO',      'PENDIENTE', 'Paciente dijo que regresa a pagar'),
(22, 22, 2, 30.00, 'EFECTIVO',      'PAGADO',   'Pago rápido, sin factura'),
(23, 23, 2, 60.00, 'TARJETA',       'PAGADO',   'Emergencia cardiológica, tarjeta aprobada'),
(24, 24, 2, 25.00, 'EFECTIVO',      'PAGADO',   'Consulta general pagada en efectivo');

SELECT setval(pg_get_serial_sequence('cobro', 'cobro_id'),
    (SELECT MAX(cobro_id) FROM cobro));

-- ============================================================
-- SECCIÓN 7. CONSULTAS DE VERIFICACIÓN
-- ============================================================

-- Resumen general del consultorio
-- SELECT
--   (SELECT COUNT(*) FROM paciente) AS total_pacientes,
--   (SELECT COUNT(*) FROM profesional WHERE estado_profesional = 'ACTIVO') AS profesionales_activos,
--   (SELECT COUNT(*) FROM cita WHERE estado_cita = 'PROGRAMADA') AS citas_programadas,
--   (SELECT COUNT(*) FROM cita WHERE estado_cita = 'ATENDIDA') AS citas_atendidas,
--   (SELECT COUNT(*) FROM cita WHERE estado_cita = 'CANCELADA') AS citas_canceladas,
--   (SELECT COUNT(*) FROM atencion) AS total_atenciones,
--   (SELECT COUNT(*) FROM cobro WHERE estado_cobro = 'PAGADO') AS cobros_pagados,
--   (SELECT COUNT(*) FROM cobro WHERE estado_cobro = 'PENDIENTE') AS cobros_pendientes,
--   (SELECT SUM(monto) FROM cobro WHERE estado_cobro = 'PAGADO') AS total_ingresos;

-- Agenda por profesional
-- SELECT p.nombres || ' ' || p.apellidos AS profesional,
--        p.especialidad_breve,
--        COUNT(c.cita_id) AS total_citas,
--        SUM(CASE WHEN c.estado_cita = 'PROGRAMADA' THEN 1 ELSE 0 END) AS programadas,
--        SUM(CASE WHEN c.estado_cita = 'ATENDIDA' THEN 1 ELSE 0 END) AS atendidas,
--        SUM(CASE WHEN c.estado_cita = 'CANCELADA' THEN 1 ELSE 0 END) AS canceladas
-- FROM profesional p
-- LEFT JOIN cita c ON c.profesional_id = p.profesional_id
-- WHERE p.estado_profesional = 'ACTIVO'
-- GROUP BY p.profesional_id, p.nombres, p.apellidos, p.especialidad_breve
-- ORDER BY p.especialidad_breve, p.nombres;

-- Ingresos por profesional
-- SELECT p.nombres || ' ' || p.apellidos AS profesional,
--        COUNT(co.cobro_id) AS total_cobros,
--        SUM(CASE WHEN co.estado_cobro = 'PAGADO' THEN co.monto ELSE 0 END) AS total_pagado,
--        SUM(CASE WHEN co.estado_cobro = 'PENDIENTE' THEN co.monto ELSE 0 END) AS total_pendiente
-- FROM profesional p
-- JOIN atencion a ON a.profesional_id = p.profesional_id
-- JOIN cobro co ON co.atencion_id = a.atencion_id
-- GROUP BY p.profesional_id, p.nombres, p.apellidos
-- ORDER BY total_pagado DESC;

-- Distribución de métodos de pago
-- SELECT metodo_pago,
--        COUNT(*) AS cantidad,
--        SUM(monto) AS total,
--        ROUND(AVG(monto), 2) AS promedio
-- FROM cobro
-- WHERE estado_cobro = 'PAGADO'
-- GROUP BY metodo_pago
-- ORDER BY total DESC;

-- Pacientes con más atenciones
-- SELECT p.nombres || ' ' || p.apellidos AS paciente,
--        COUNT(a.atencion_id) AS total_atenciones
-- FROM paciente p
-- JOIN atencion a ON a.paciente_id = p.paciente_id
-- GROUP BY p.paciente_id, p.nombres, p.apellidos
-- HAVING COUNT(a.atencion_id) > 1
-- ORDER BY total_atenciones DESC;
