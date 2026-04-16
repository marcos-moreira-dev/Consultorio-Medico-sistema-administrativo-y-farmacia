# consultorio-medico

Proyecto documental y de scaffolding para un sistema híbrido compuesto por un **consultorio privado** y una **farmacia con superficie pública**. La base de trabajo se apoya en documentación abundante para poder diseñar, scaffoldear e implementar con criterio usando herramientas tradicionales e IA.

## Qué incluye el repositorio

- `backend-consultorio`: API privada del consultorio, orientada a operación interna, seguridad, reglas de negocio y reportes.
- `desktop-consultorio-javafx`: cliente de escritorio JavaFX del consultorio.
- `database-consultorio`: base de datos PostgreSQL del subdominio consultorio.
- `backend-farmacia`: backend NestJS de farmacia, con superficie administrativa y pública.
- `storefront-farmacia-angular`: storefront Angular para catálogo público de farmacia.
- `database-farmacia`: base de datos PostgreSQL del subdominio farmacia.
- `config/`: ejemplos de variables y perfiles globales.
- `infra/`: material de infraestructura compartida y scripts de soporte.
- `docs/`: contexto maestro del proyecto, del dominio y de la estrategia documental.
- `docs/GLOSARIO_PROYECTO.md`: glosario vivo de términos usados en la documentación, el código y los handoffs.
- `MANUAL_USUARIO_FARMACIA.md`: manual funcional orientado al uso visible de la farmacia.
- `MANUAL_USUARIO_CONSULTORIO.md`: manual funcional orientado al uso visible del consultorio.
- `HANDOFF_RETOQUE_FINAL_GLOBAL.md`: handoff global para la siguiente ronda de remate, validación y despliegue.

## En qué estado está el proyecto

El repositorio sigue siendo **muy documental**, pero ya no debe leerse como un conjunto vacío de scaffolding. Hoy conviven dos capas:

- una capa documental fuerte, que fija arquitectura, contratos, convenciones y decisiones de diseño;
- y una capa de código ya bastante sembrada, sobre todo en `backend-farmacia`, `storefront-farmacia-angular`, `backend-consultorio` y `desktop-consultorio-javafx`.

La prioridad correcta ya no es “inventar estructura”, sino **validar localmente, corregir inconsistencias reales, rematar UX y preparar despliegue**.

## Regla de lectura sugerida

Para entender el proyecto sin perderse, conviene recorrerlo en este orden:

1. `docs/contexto_maestro_consultorio_farmacia_guayaquil.md`
2. `docs/GLOSARIO_PROYECTO.md`
3. `MANUAL_USUARIO_FARMACIA.md` y `MANUAL_USUARIO_CONSULTORIO.md`
4. `README.md` de cada componente
5. `docs/` propios del componente que se va a trabajar
6. archivos de configuración reales del componente (`pom.xml`, `package.json`, `angular.json`, `.env.example`, etc.)
7. estructura de carpetas y scaffolding

## Filosofía de trabajo

Este proyecto busca equilibrio entre:

- documentación suficiente para no improvisar;
- arquitectura entendible y enseñable;
- utilidad práctica en un contexto local de Guayaquil;
- crecimiento gradual dentro de una sola V1 amplia;
- uso de IA con contexto fuerte, en vez de generación ciega.

## Arranque rápido de la stack local de farmacia

Si quieres validar la parte farmacia de forma integrada en Windows con Docker Desktop:

```bat
run_all.bat up
```

El launcher ahora levanta la stack en segundo plano, espera healthchecks, resume qué sí arrancó y abre Swagger y storefront cuando el arranque fue sano.

URLs esperadas:

- backend público: `http://localhost:3001/api/v1/catalogo`
- swagger: `http://localhost:3001/docs`
- storefront: `http://localhost:4200`

Otros comandos útiles:

```bat
run_all.bat doctor
run_all.bat logs
run_all.bat ps
run_all.bat down
run_all.bat rebuild
run_all.bat resetdb
```

Para consultorio ya existe un launcher raíz separado que abre backend y desktop en ventanas distintas:

```bat
run_consultorio_all.bat up
```

## Qué conviene hacer después de esta tanda

1. validar localmente Docker, backend y frontend;
2. corregir cualquier explosión real que aparezca al correrlos fuera del sandbox;
3. cerrar decisiones abiertas usando `TODO_REPO_PENDIENTE.md`;
4. recién después seguir agregando funcionalidad.

## Nota sobre esta corrección documental

Esta versión del `README.md` existe para convertir la raíz del proyecto en una puerta de entrada real, no en una carpeta muda. Debe servir tanto para ti como para cualquier otra IA o persona que reciba el repositorio.


## Diagnóstico rápido del stack de farmacia

El launcher `run_all.bat` mantiene un único reporte reutilizable en `.diagnostics\farmacia-last-run.log`.
Ese archivo se sobreescribe en cada ejecución relevante (`up`, `rebuild`, `doctor`, `report` o fallos de arranque) para evitar llenar el repo con basura y dejar un rastro útil de diagnóstico.
