# Checklist calidad V1

## Propósito

Verificar si la V1 del proyecto realmente alcanzó un nivel suficiente de solidez funcional, técnica y documental para considerarse una base seria de estudio, demo y portafolio.

## Checklist funcional

- [ ] El flujo básico del consultorio permite buscar o registrar pacientes.
- [ ] El flujo de agenda permite crear citas válidas.
- [ ] El sistema evita solapamientos de cita para el mismo profesional.
- [ ] El flujo de atención simple funciona sin exigir complejidad clínica innecesaria.
- [ ] El flujo de cobro exige existencia de atención previa.
- [ ] La farmacia pública permite buscar y consultar productos visibles.
- [ ] La farmacia administrativa permite crear, editar, publicar, despublicar o inactivar productos.
- [ ] La disponibilidad publicada de productos es coherente con el estado operativo.

## Checklist de dominio y consistencia

- [ ] Consultorio y farmacia están separados conceptualmente y en implementación.
- [ ] Las reglas críticas del dominio están expresadas en backend y no solo en UI.
- [ ] Los estados del sistema son claros y no contradictorios.
- [ ] Los casos alternos importantes están contemplados al menos a nivel razonable.

## Checklist técnico

- [ ] Los componentes principales arrancan sin errores críticos.
- [ ] `database-consultorio` tiene migraciones aplicables y estructura coherente.
- [ ] `database-farmacia` tiene migraciones aplicables y estructura coherente.
- [ ] Los contratos API usan DTOs explícitos.
- [ ] Las respuestas y errores siguen una convención consistente.
- [ ] La configuración del sistema no depende excesivamente de hardcodes ocultos.

## Checklist de arquitectura y persistencia

- [ ] `backend-consultorio` usa solo `database-consultorio`.
- [ ] `backend-farmacia` usa solo `database-farmacia`.
- [ ] No existen atajos relacionales entre consultorio y farmacia.
- [ ] Cualquier integración futura entre subdominios se piensa por aplicación o contrato, no por acceso directo a tablas ajenas.

## Checklist de seguridad y privacidad

- [ ] La superficie privada del consultorio no se expone públicamente.
- [ ] La superficie pública de farmacia solo muestra datos autorizados.
- [ ] Los roles y permisos mínimos están definidos con claridad.
- [ ] No se filtran datos sensibles innecesarios en respuestas o logs.

## Checklist de observabilidad y operación

- [ ] Existen logs útiles para entender operaciones relevantes.
- [ ] Los errores importantes son diagnosticables.
- [ ] El sistema puede levantarse localmente con pasos razonables.
- [ ] Existen seeds o datos demo funcionales por subdominio.

## Checklist documental

- [ ] El contexto maestro está completo y coherente.
- [ ] Los dominios de consultorio y farmacia están documentados.
- [ ] Arquitectura y system design reflejan el sistema real con dos bases separadas.
- [ ] Seguridad, despliegue y buenas prácticas tienen lineamientos claros.
- [ ] Los documentos no se contradicen entre sí de forma evidente.

## Cierre

- [ ] La V1 se siente como un sistema administrativo pequeño pero realista.
- [ ] El proyecto ya puede mostrarse como pieza seria de aprendizaje y portafolio.
- [ ] La base quedó lista para evolucionar a V1.1 sin rehacer todo desde cero.

## Resultado esperado

Si la mayoría de esta lista se cumple con honestidad, la V1 ya no es solo un experimento. Se convierte en una base defendible, útil y suficientemente madura para estudio profundo y presentación profesional.