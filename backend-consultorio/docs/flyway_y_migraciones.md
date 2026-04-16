# Flyway y migraciones del backend-consultorio

## Decisión ya cerrada

En este backend **Flyway ya no es decorativo**. La postura adoptada es:

- `database-consultorio` sigue siendo la fuente didáctica y de modelado;
- `backend-consultorio` pasa a tener una **fuente operativa propia** para arranque local;
- esa fuente operativa se materializa en `src/main/resources/db/migration/`.

## Migraciones sembradas en esta etapa

### `V1__schema_consultorio.sql`

Contiene:

- schema `consultorio`;
- tablas base del dominio;
- constraints principales;
- índices operativos mínimos.

### `V2__seed_demo_consultorio.sql`

Contiene:

- roles demo;
- usuarios demo;
- profesionales demo;
- pacientes demo;
- citas demo;
- atenciones demo;
- cobros demo.

## Regla práctica

Los **scripts de migración del backend** no reemplazan la documentación de `database-consultorio`.
La relación correcta es esta:

- `database-consultorio` explica, justifica y enseña el modelo;
- `backend-consultorio` permite **arrancar y evolucionar** el sistema de forma ejecutable.

## Qué debe hacer la siguiente persona o IA

1. correr Flyway contra PostgreSQL real;
2. revisar que el schema final coincida con lo que Hibernate valida;
3. verificar que los seeds demo no choquen con nuevos tests o fixtures;
4. si se cambia el modelo en `database-consultorio`, reflejar el cambio también en Flyway;
5. evitar que la BD documental y la operativa diverjan silenciosamente.
