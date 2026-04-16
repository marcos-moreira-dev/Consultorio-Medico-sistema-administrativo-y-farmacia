# HANDOFF_RETOQUE_FINAL_GLOBAL

## Propósito

Este documento sirve como **handoff global para la siguiente IA o siguiente ronda**. La idea es que el próximo trabajo ya no parta desde cero ni vuelva a abrir alcance por costumbre.

## Estado práctico del proyecto

### Farmacia

- `backend-farmacia`: base fuerte y bastante madura, con foco en catálogo, admin y media.
- `storefront-farmacia-angular`: frontend público con home, catálogo, detalle y navegación ya sembrados.
- `database-farmacia`: fuente documental y SQL fuerte del subdominio.

### Consultorio

- `backend-consultorio`: backend bastante fuerte, con seguridad, roles, módulos y Flyway ya sembrados.
- `desktop-consultorio-javafx`: cliente desktop amplio, con shell, login, módulos principales y transición demo/remoto.
- `database-consultorio`: base documental y SQL fuerte del subdominio.

## Qué NO debería hacer la siguiente IA

- No rediseñar arquitectura por gusto.
- No abrir nuevos subdominios ni módulos raros.
- No romper los nombres ya estables de roles, rutas y componentes.
- No inflar visualmente el proyecto solo para que “parezca más terminado”.
- No borrar la documentación viva del repo para reemplazarla con parches más cortos pero menos útiles.

## Qué sí conviene hacer después

### 1. Validación local real

Primero, correr lo ya sembrado fuera del sandbox:

- `run_all.bat up` para farmacia.
- `run_all.bat doctor` para validar compose y estado del stack local de farmacia.
- `run_consultorio_all.bat doctor` y `run_consultorio_all.bat up` para consultorio.
- `npm install`, `ng build` y `npm test` en `storefront-farmacia-angular`.
- `mvn test`, `mvn verify` y `mvn javafx:run` donde aplique en consultorio.

### 2. Corregir solo errores reales

Después de la validación local, corregir:

- errores de compilación;
- errores de runtime;
- errores de integración;
- problemas de puertos, dependencias o variables de entorno.

No meter funcionalidad nueva hasta cerrar lo que explote de verdad.

### 3. Cerrar recursos finales

Una vez estable el runtime:

- meter imágenes reales de farmacia;
- revisar logos, iconos y recursos visuales finales;
- ajustar pequeños detalles de spacing, responsive o UX;
- decidir si el desktop necesita recursos gráficos definitivos o si seguirá sobrio/minimalista.

### 4. Despliegue y empaquetado

La siguiente gran capa ya no debería ser “más arquitectura”, sino:

- despliegue local repetible;
- empaquetado razonable;
- scripts más robustos;
- validación final para demo o cliente.

## Documentos que la siguiente IA debería leer primero

1. `README.md` raíz
2. `docs/GLOSARIO_PROYECTO.md`
3. `MANUAL_USUARIO_FARMACIA.md`
4. `MANUAL_USUARIO_CONSULTORIO.md`
5. `TODO_REPO_PENDIENTE.md`
6. Handoffs y TODOs específicos de cada componente

## Observación importante

Ya existe bastante código. La siguiente IA no debería tratar esto como repositorio vacío ni como simple scaffolding. La prioridad correcta ya es:

**validar, endurecer, rematar y desplegar**,
no volver a inventar medio sistema.
