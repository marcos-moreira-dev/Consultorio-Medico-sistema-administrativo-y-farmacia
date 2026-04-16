# database-farmacia

Base de datos PostgreSQL del subdominio farmacia.

## Rol dentro del sistema

Este componente sostiene la persistencia comercial y operativa de farmacia. Debe permanecer separado del consultorio y reflejar claramente la distinción entre existencia interna de producto, visibilidad pública y disponibilidad operativa.

## Qué debe representar

- productos;
- categorías si se formalizan como entidad;
- publicación y disponibilidad pública;
- media o referencias asociadas cuando aplique;
- una posible evolución futura hacia reservas.

## Qué no debe mezclar

- pacientes;
- citas;
- atenciones;
- cobros del consultorio;
- joins físicos con `database-consultorio`.

## Estructura conceptual del componente

- `docs/`: visión de persistencia, reglas, integridad, rendimiento y onboarding;
- `Design V1/`: material conceptual y esquemas previos;
- `V2/`: versiones más formales del diseño y del SQL;
- `scripts/`: utilidades auxiliares.

## Documentos que conviene leer primero

- `docs/vision_persistencia.md`
- `docs/modelo_relacional.md`
- `docs/reglas_de_persistencia.md`
- `docs/restricciones_integridad.md`
- `docs/onboarding_database.md`

## Regla práctica

La base de farmacia debe pensarse como una persistencia comercial sobria y progresiva. No conviene sobrediseñarla como si ya fuera una cadena grande o un e-commerce complejo.
