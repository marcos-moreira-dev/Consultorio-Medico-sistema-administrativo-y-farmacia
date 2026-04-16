-- ============================================================
-- database-farmacia / Seed backend extension
-- Archivo: 03_seed_backend_extension.sql
-- Objetivo:
--   Cargar datos mínimos operativos para backend de farmacia.
--
-- Credencial demo:
--   username: admin.farmacia
--   password: Admin123*
--
-- IMPORTANTE:
--   Cambiar esta contraseña inmediatamente fuera de entorno demo.
-- ============================================================

SET search_path TO farmacia;

-- ============================================================
-- SECCIÓN 1. USUARIO ADMIN DEMO
-- ============================================================

INSERT INTO usuario_admin (
    usuario_admin_id,
    username,
    password_hash,
    email,
    estado,
    rol_codigo
) VALUES (
    1,
    'admin.farmacia',
    '$2b$10$49gLr/bOurpiX1MJLe3wZOvAby8K8cCV.K6M5AYFjWyKuQtySYN.y',
    'admin@farmacia.local',
    'ACTIVO',
    'ADMIN_FARMACIA'
)
ON CONFLICT (username)
DO UPDATE SET
    password_hash = EXCLUDED.password_hash,
    email = EXCLUDED.email,
    estado = EXCLUDED.estado,
    rol_codigo = EXCLUDED.rol_codigo,
    fecha_actualizacion = CURRENT_TIMESTAMP;

SELECT setval(
    pg_get_serial_sequence('usuario_admin', 'usuario_admin_id'),
    COALESCE((SELECT MAX(usuario_admin_id) FROM usuario_admin), 1),
    true
);

-- ============================================================
-- SECCIÓN 2. CONSULTAS DE VERIFICACIÓN OPCIONALES
-- ============================================================

-- SELECT usuario_admin_id, username, email, estado, rol_codigo
-- FROM usuario_admin
-- ORDER BY usuario_admin_id;
