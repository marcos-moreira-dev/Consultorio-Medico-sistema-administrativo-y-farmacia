# Checklist lanzamiento local

## Propósito

Verificar si el sistema está en condiciones de levantarse localmente de forma ordenada, funcional y demostrable, sin depender de improvisaciones de último minuto.

## Entorno y dependencias

- [ ] Las versiones necesarias de Java, Node y PostgreSQL están claras y disponibles.
- [ ] Las variables de entorno mínimas están definidas.
- [ ] Los puertos de cada componente están documentados.
- [ ] No hay dependencias críticas pendientes de instalar sin registrar.

## Database consultorio

- [ ] `database-consultorio` arranca correctamente.
- [ ] Sus migraciones se aplican sin errores.
- [ ] Sus seeds necesarios para prueba o demo están cargados.
- [ ] Su estructura es compatible con la versión actual del sistema.

## Database farmacia

- [ ] `database-farmacia` arranca correctamente.
- [ ] Sus migraciones se aplican sin errores.
- [ ] Sus seeds necesarios para prueba o demo están cargados.
- [ ] Su estructura es compatible con la versión actual del sistema.

## Backends

- [ ] El backend consultorio arranca correctamente.
- [ ] El backend farmacia arranca correctamente.
- [ ] Cada backend puede conectarse a su propia base de datos.
- [ ] No hay errores críticos de configuración al iniciar.

## Clientes

- [ ] El desktop consultorio puede conectarse al backend correspondiente.
- [ ] El storefront farmacia puede consultar el backend correspondiente.
- [ ] La capa pública muestra datos visibles coherentes.

## Validación funcional mínima

- [ ] Se puede registrar o consultar un paciente.
- [ ] Se puede crear una cita válida.
- [ ] Se puede registrar una atención.
- [ ] Se puede registrar un cobro.
- [ ] Se puede consultar el catálogo de farmacia.
- [ ] Se puede publicar o inactivar un producto desde la capa administrativa.

## Diagnóstico y soporte

- [ ] Los logs permiten identificar fallos relevantes.
- [ ] Los errores de arranque son legibles y no totalmente opacos.
- [ ] Existe una forma clara de reiniciar componentes si algo falla.

## Verificación de separación arquitectónica

- [ ] El arranque no asume una sola base compartida.
- [ ] La configuración de consultorio y farmacia no está cruzada.
- [ ] Ningún cliente intenta acceder directamente a una base de datos.

## Preparación final

- [ ] El arranque puede repetirse sin pasos secretos.
- [ ] El entorno local no depende de hacks de último minuto.
- [ ] Hay una ruta razonable para volver a un estado estable si algo se rompe.

## Resultado esperado

Si esta lista se cumple, el sistema debería poder ejecutarse localmente con suficiente confianza para desarrollo, prueba y demostración controlada, respetando además la separación entre las dos bases de datos.