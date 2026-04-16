# database-consultorio

Base de datos PostgreSQL del subdominio consultorio.

## Rol dentro del sistema

Este componente modela la persistencia propia del consultorio y debe mantenerse físicamente separado de farmacia. Su documentación busca explicar no solo el SQL final, sino también el camino conceptual y de normalización que lleva hasta él.

## Qué debe representar

- pacientes;
- profesionales;
- usuarios internos y relaciones operativas cuando aplique;
- citas;
- atenciones;
- cobros;
- trazabilidad y restricciones razonables del consultorio.

## Qué no debe mezclar

- productos de farmacia;
- stock;
- catálogo público;
- reservas o pedidos de farmacia;
- claves foráneas cruzadas hacia `database-farmacia`.

## Estructura conceptual del componente

- `docs/`: visión, reglas de persistencia, integridad, rendimiento y onboarding;
- `Design V1/`: material previo de diseño y transición conceptual;
- `V2/`: esquema más maduro, migraciones, rutinas y SQL 3FN;
- `scripts/`: utilidades auxiliares del componente.

## Documentos que conviene leer primero

- `docs/vision_persistencia.md`
- `docs/modelo_relacional.md`
- `docs/reglas_de_persistencia.md`
- `docs/restricciones_integridad.md`
- `docs/onboarding_database.md`

## Propósito pedagógico

Este componente no solo existe para almacenar datos. También sirve para estudiar cómo se traduce un dominio de negocio a una base relacional realista en PostgreSQL.

## Regla práctica

Antes de tocar el SQL final, conviene entender el recorrido completo: dominio, modelo conceptual, 1FN, informe de normalización y versión 3FN.
