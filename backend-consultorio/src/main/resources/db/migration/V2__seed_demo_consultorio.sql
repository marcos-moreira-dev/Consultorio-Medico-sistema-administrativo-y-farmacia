SET search_path TO consultorio;

INSERT INTO rol (rol_id, nombre_rol, descripcion_breve) VALUES
(1, 'ADMIN_CONSULTORIO', 'Administrador interno del consultorio'),
(2, 'OPERADOR_CONSULTORIO', 'Usuario de recepción y operación administrativa'),
(3, 'PROFESIONAL_CONSULTORIO', 'Profesional clínico del consultorio')
ON CONFLICT (rol_id) DO NOTHING;

INSERT INTO usuario (usuario_id, rol_id, username, password_hash, estado_usuario) VALUES
(1, 1, 'admin.consultorio', '$2y$10$TC5z0lrMAvxlR3Rf8sYTuOEIQtg.rJfGgjlYujKAPjhc.PWaEEbFS', 'ACTIVO'),
(2, 2, 'recepcion.ana', '$2y$10$TC5z0lrMAvxlR3Rf8sYTuOEIQtg.rJfGgjlYujKAPjhc.PWaEEbFS', 'ACTIVO'),
(3, 3, 'dr.paredes', '$2y$10$TC5z0lrMAvxlR3Rf8sYTuOEIQtg.rJfGgjlYujKAPjhc.PWaEEbFS', 'ACTIVO'),
(4, 3, 'dra.mora', '$2y$10$TC5z0lrMAvxlR3Rf8sYTuOEIQtg.rJfGgjlYujKAPjhc.PWaEEbFS', 'ACTIVO')
ON CONFLICT (usuario_id) DO NOTHING;

INSERT INTO profesional (profesional_id, usuario_id, nombres, apellidos, especialidad_breve, registro_profesional, estado_profesional) VALUES
(1, 3, 'Jorge Luis', 'Paredes Molina', 'Medicina general', 'REG-001', 'ACTIVO'),
(2, 4, 'Elena Sofía', 'Mora Cedeño', 'Medicina interna', 'REG-002', 'ACTIVO')
ON CONFLICT (profesional_id) DO NOTHING;

INSERT INTO paciente (paciente_id, nombres, apellidos, telefono, cedula, fecha_nacimiento, direccion_basica) VALUES
(1, 'Carlos Alberto', 'Mendoza Vera', '0991001001', '0912345678', '1990-04-12', 'Guayaquil, sur'),
(2, 'María Fernanda', 'Solís Paredes', '0991001002', '0923456789', '1988-09-03', 'Guayaquil, centro'),
(3, 'José Luis', 'Villacrés Cedeño', '0991001003', NULL, '1979-01-19', 'Guayaquil, norte'),
(4, 'Ana Lucía', 'Peñafiel Torres', '0991001004', '0934567890', '1995-06-28', 'Guayaquil, norte')
ON CONFLICT (paciente_id) DO NOTHING;

INSERT INTO cita (cita_id, paciente_id, profesional_id, fecha_hora_inicio, estado_cita, motivo_breve, observacion_operativa) VALUES
(1, 1, 1, '2026-04-01 09:00:00', 'ATENDIDA', 'Control general', 'Paciente llegó puntual'),
(2, 2, 1, '2026-04-01 10:00:00', 'ATENDIDA', 'Dolor de garganta', 'Se registró atención completa'),
(3, 4, 2, '2026-04-02 08:30:00', 'ATENDIDA', 'Malestar estomacal', 'Atención realizada el mismo día')
ON CONFLICT (cita_id) DO NOTHING;

INSERT INTO atencion (atencion_id, paciente_id, profesional_id, cita_id, fecha_hora_atencion, nota_breve, indicaciones_breves) VALUES
(1, 1, 1, 1, '2026-04-01 09:10:00', 'Paciente refiere cansancio general y molestias leves.', 'Reposo, hidratación y control en una semana.'),
(2, 2, 1, 2, '2026-04-01 10:12:00', 'Molestia en garganta con irritación moderada.', 'Evitar bebidas frías y regresar si persiste fiebre.'),
(3, 4, 2, 3, '2026-04-02 08:45:00', 'Consulta por malestar estomacal con evolución corta.', 'Dieta blanda e hidratación controlada.')
ON CONFLICT (atencion_id) DO NOTHING;

INSERT INTO cobro (cobro_id, atencion_id, registrado_por_usuario_id, monto, metodo_pago, estado_cobro, observacion_administrativa) VALUES
(1, 1, 2, 20.00, 'EFECTIVO', 'PAGADO', 'Pago realizado al finalizar la consulta'),
(2, 2, 2, 25.00, 'TRANSFERENCIA', 'PAGADO', 'Comprobante validado en recepción'),
(3, 3, 2, 22.00, 'TARJETA', 'PAGADO', 'Pago aprobado sin novedades')
ON CONFLICT (cobro_id) DO NOTHING;

-- ============================================================
-- SEED AUDITORÍA: Eventos operativos demo
-- ============================================================

INSERT INTO audit_log (evento_id, usuario_id, username, tipo_evento, modulo, descripcion, entidad_id, entidad_tipo, fecha_hora) VALUES
(1, 1, 'admin.consultorio', 'LOGIN', 'AUTH', 'Inicio de sesión exitoso', NULL, NULL, '2026-04-01 07:55:00'),
(2, 1, 'admin.consultorio', 'CREAR_PACIENTE', 'PACIENTES', 'Paciente registrado: Carlos Mendoza', 1, 'paciente', '2026-04-01 08:00:00'),
(3, 2, 'recepcion.ana', 'CREAR_CITA', 'CITAS', 'Cita programada para Carlos Mendoza con Dr. Paredes', 1, 'cita', '2026-04-01 08:05:00'),
(4, 3, 'dr.paredes', 'CREAR_ATENCION', 'ATENCIONES', 'Atención registrada: Control general', 1, 'atencion', '2026-04-01 09:10:00'),
(5, 2, 'recepcion.ana', 'REGISTRAR_COBRO', 'COBROS', 'Cobro registrado: $20.00 Efectivo', 1, 'cobro', '2026-04-01 09:20:00'),
(6, 3, 'dr.paredes', 'CREAR_ATENCION', 'ATENCIONES', 'Atención registrada: Dolor de garganta', 2, 'atencion', '2026-04-01 10:12:00'),
(7, 2, 'recepcion.ana', 'REGISTRAR_COBRO', 'COBROS', 'Cobro registrado: $25.00 Transferencia', 2, 'cobro', '2026-04-01 10:20:00'),
(8, 2, 'recepcion.ana', 'CANCELAR_CITA', 'CITAS', 'Cita cancelada: José Villacrés', 3, 'cita', '2026-04-01 11:25:00'),
(9, 4, 'dra.mora', 'LOGIN', 'AUTH', 'Inicio de sesión exitoso', NULL, NULL, '2026-04-02 07:50:00'),
(10, 4, 'dra.mora', 'CREAR_ATENCION', 'ATENCIONES', 'Atención registrada: Malestar estomacal', 3, 'atencion', '2026-04-02 08:45:00'),
(11, 2, 'recepcion.ana', 'REGISTRAR_COBRO', 'COBROS', 'Cobro registrado: $22.00 Tarjeta', 3, 'cobro', '2026-04-02 09:00:00'),
(12, 1, 'admin.consultorio', 'ACTUALIZAR_PACIENTE', 'PACIENTES', 'Datos actualizados: María Solís', 2, 'paciente', '2026-04-02 10:15:00'),
(13, 2, 'recepcion.ana', 'CREAR_CITA', 'CITAS', 'Cita programada: Ana Peñafiel con Dra. Mora', 3, 'cita', '2026-04-02 10:30:00'),
(14, 1, 'admin.consultorio', 'CREAR_PROFESIONAL', 'PROFESIONALES', 'Profesional registrado: Dra. Vargas', 3, 'profesional', '2026-04-02 14:00:00'),
(15, 3, 'dr.paredes', 'REPROGRAMAR_CITA', 'CITAS', 'Cita reprogramada de 09:00 a 10:00', 4, 'cita', '2026-04-02 15:30:00')
ON CONFLICT (evento_id) DO NOTHING;
