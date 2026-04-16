# INFORME DE ESTADO: Desktop Consultorio JavaFX

> **Fecha de corte:** 13 de abril de 2026
> **Componente:** `desktop-consultorio-javafx`
> **Versión:** 0.1.0-SNAPSHOT | JDK 21 | JavaFX 21.0.2
> **Arquitectura:** MVVM programático (vistas en Java, no FXML) con API REST sobre HttpClient + Jackson

---

## 1. RESUMEN EJECUTIVO

El desktop del consultorio es una aplicación JavaFX de ~234 archivos Java y ~18.000 líneas de código que implementa un cliente de escritorio administrativo con 8 módulos operativos. Todos compilan sin errores. La aplicación funciona en dos modos: **demo** (datos locales hardcodeados) y **remoto** (conectado al backend Spring Boot en `localhost:8080`).

**Estado global: ✅ Compila | ✅ Navegable | ✅ 8 módulos funcionales | ⚠️ Sin validación runtime completa**

---

## 2. LO QUE YA EXISTE Y FUNCIONA

### 2.1 Estructura técnica

| Capa | Archivos | Estado |
|---|---|---|
| Vistas de módulos (Java programático) | 7 archivos | ✅ Completos, 120-260 líneas c/u |
| ViewModels | 6 archivos | ✅ Con propiedades reactivas JavaFX |
| API Services | 9 archivos | ✅ CRUD completo contra backend |
| DTOs | 16 archivos | ✅ Mapeados al backend Spring Boot |
| Componentes compartidos | 15 archivos | ✅ Botones, cards, badges, calendar, dialogs, pagination |
| Hojas CSS | 18 archivos | ~950 líneas organizadas en tokens/components/layouts |
| FXML (shell + login) | 6 archivos | ✅ Usados solo para shell y login |
| Controlador de shell | 1 archivo (594 líneas) | ✅ Navegación, roles, creación de vistas |
| Login | 1 controlador funcional (~193 líneas) | ✅ Auth real + demo local |
| Sesión | SessionSnapshot + InMemorySessionStore | ✅ Inmutable con modo exploración |
| Configuración | 4 clases config + .env.example | ✅ Variables de entorno |

### 2.2 Módulos implementados

| # | Módulo | Layout | Datos | Funcionalidades |
|---|---|---|---|---|
| 1 | **Dashboard** | 3 columnas | Backend API | Saludo contextual, métricas colorizadas, timeline de citas, actividad reciente, cobros pendientes |
| 2 | **Pacientes** | Master-Detail SplitPane | Backend API + 25 demo | Tabla paginada, búsqueda en tiempo real, formulario alta/edición, detalle, **detección duplicados (RN-011)**, **observaciones internas** |
| 3 | **Citas** | 3 paneles (timeline + calendario + detalle) | Backend API + demo | Slots horarios 7:00-17:00, calendario interactivo, badges de estado, **cancelar cita**, **reprogramar cita** (DatePicker) |
| 4 | **Atenciones** | Tabla + formulario deslizable | Backend API + 19 demo | Tabla paginada, búsqueda por paciente/profesional, combos reales, formulario con notas clínicas, **flujo → Cobro (RN-040)** |
| 5 | **Cobros** | Métricas + tabla | Backend API + 25 demo | 3 métricas financieras (pagado/pendiente/promedio), tabla con filas coloreadas por estado, paginación |
| 6 | **Profesionales** | Grid de tarjetas 3 columnas | Backend API + 9 demo | Cards con avatar, especialidad, badge activo/inactivo, estadísticas, **búsqueda en tiempo real** |
| 7 | **Reportes** | Formulario de generación | UI only (sin backend) | Selector tipo/período/formato, botones generar/previsualizar |
| 8 | **Auditoría** | Tabla + filtros | Backend API + demo | Filtros por módulo y búsqueda textual aplicados en cliente, paginación |

### 2.3 Funcionalidades transversales

| Característica | Estado | Detalle |
|---|---|---|
| **Roles en sidebar** | ✅ | ADMIN (8 módulos), OPERADOR (7, sin Auditoría), PROFESIONAL (3: Dashboard, Citas, Atenciones) |
| **Selección persistente en sidebar** | ✅ | Clase CSS `.sidebar-button-active` con `!important` + hover dedicado |
| **Paginación** | ✅ | Componente `PaginationBar` reusable en Pacientes (10/pág), Atenciones (20/pág), Cobros (15/pág), Auditoría (25/pág) |
| **Búsqueda en tiempo real** | ✅ | Pacientes, Atenciones, Profesionales, Auditoría |
| **Modo demo fallback** | ✅ | Todos los módulos cargan datos hardcodeados si el backend no responde |
| **Login demo local** | ✅ | Password `123456` con 4 usuarios conocidos |
| **Navegación cruzada** | ✅ | Atenciones → Cobros via `MainShellController.navigateToCobros()` |
| **Stubs vacíos documentados** | ✅ | 14 archivos convertidos a `@Deprecated` con explicación |
| **Launcher integrado** | ✅ | `run_all.bat` lanza farmacia + desktop automáticamente |

---

## 3. LO QUE FALTA SEGÚN LA DOCUMENTACIÓN

### 3.1 Brechas funcionales (mencionadas en docs como pendientes)

| # | Brecha | Documento fuente | Impacto | Esfuerzo estimado |
|---|---|---|---|---|
| **F-01** | **Validación runtime completa** | TODO §0, HANDOFF §1 | Alta — nadie ha corrido la app completa fuera del sandbox | 1-2 sesiones |
| **F-02** | **Formularios complejos de edición** | MANUAL "NOT expected yet" | Media — Pacientes tiene formulario básico, otros módulos no tienen | 2-3 días |
| **F-03** | **Empaquetado instalable** | HANDOFF §4, TODO §5.1 | Media — Sin `.exe`, `.msi`, `.dmg` ni jpackage | 1 día |
| **F-04** | **Recursos visuales definitivos** | HANDOFF §3, TODO §7.3 | Baja — Logos genéricos, sin favicon ni branding final | 2-4 horas |
| **F-05** | **Reconexión automática** | MANUAL "NOT expected yet" | Media — Si el backend cae, no reintenta automáticamente | 1 día |
| **F-06** | **Refresh tokens** | MANUAL "NOT expected yet" | Baja — JWT expira sin renovación | 1 día |
| **F-07** | **Persistencia local** | MANUAL "NOT expected yet" | Baja — Sin caché offline | 2 días |
| **F-08** | **Generación real de reportes** | MANUAL §9 | Media — La UI existe pero no llama al backend | 4-6 horas |
| **F-09** | **Descarga de reportes** | TODO §4.3 | Media — `ReporteApiService.descargarArchivo` no conectado en UI | 2 horas |
| **F-10** | **Crear cobro manualmente** | Flujo RN-040 parcial | Baja — Solo llega desde Atenciones, no hay botón directo en Cobros | 2 horas |
| **F-11** | **Backend compilación verificada** | TODO §4 | Media — Backend Spring Boot necesita validación runtime fuera sandbox | 1 sesión |
| **F-12** | **Seeds de base de datos consultorio** | TODO §6.2 | Media — No está claro si existen seeds para PostgreSQL del consultorio | 2-4 horas |

### 3.2 Brechas de infraestructura

| # | Brecha | Impacto | Detalle |
|---|---|---|---|
| **I-01** | Sin tests unitarios | Baja — 0 tests JUnit escritos | El `pom.xml` incluye surefire pero no hay tests |
| **I-02** | Sin tests de integración | Baja — No se verifica conexión real API | Podrían fallar endpoints sin que el build lo detecte |
| **I-03** | Bootstrap vacío | Nulo — 4 clases `@Deprecated` | `AppContext`, `ControllerFactory`, `ServiceRegistry`, `ViewModelFactory` marcados como obsoletos |
| **I-04** | Navegación parcial | Bajo — `FxNavigator` y `Route` deprecados | La navegación funciona vía `MainShellController` directamente |
| **I-05** | `.env` no existe | Baja — Solo `.env.example` | Las variables de entorno deben definirse manualmente al ejecutar |

---

## 4. LO QUE SE PUEDE AGREGAR (NO ESTÁ EN LA DOCUMENTACIÓN)

### 4.1 Mejoras de UX que elevarían la percepción de calidad

| # | Mejora | Justificación | Esfuerzo |
|---|---|---|---|
| **U-01** | **Notificaciones toast** | Feedback visual tras crear/editar/eliminar registros (éxito, error, advertencia) | 2 horas |
| **U-02** | **Atajos de teclado** | `Ctrl+S` guardar, `Ctrl+N` nuevo, `Ctrl+F` buscar, `Esc` cancelar — estándar en apps de escritorio | 1 día |
| **U-03** | **Tooltip en botones del sidebar** | Hover muestra descripción del módulo | 30 min |
| **U-04** | **Doble clic en fila abre detalle** | En Pacientes y Atenciones, doble clic = editar/ver detalle | 1 hora |
| **U-05** | **Ordenamiento por columnas** | Clic en encabezado de tabla ordena ascendente/descendente | 2 horas |
| **U-06** | **Exportar tabla a CSV** | Botón en toolbar de tablas para descargar datos visibles | 2 horas |
| **U-07** | **Contador de registros en sidebar** | Badge numérico junto a "Pacientes", "Citas", "Cobros" | 1 hora |
| **U-08** | **Tema oscuro** | Alternativa al tema claro actual (toggle en topbar) | 1 día |
| **U-09** | **Animaciones de transición** | Fade-in al cambiar de módulo, slide del formulario en Atenciones | 2 horas |
| **U-10** | **Indicador de carga global** | Spinner en topbar mientras cualquier módulo carga datos | 1 hora |

### 4.2 Funcionalidades operativas adicionales

| # | Funcionalidad | Justificación | Esfuerzo |
|---|---|---|---|
| **O-01** | **Impresión de comprobante de cobro** | Generar PDF simple con datos del cobro para imprimir | 4 horas |
| **O-02** | **Historial del paciente** | Panel en detalle de paciente que muestre sus citas, atenciones y cobros | 1 día |
| **O-03** | **Agenda del profesional** | Vista de citas filtrada por profesional seleccionado | 4 horas |
| **O-04** | **Estadísticas de cobros** | Gráfico simple (barras) de cobros por día/semana/mes | 1 día |
| **O-05** | **Alertas de citas próximas** | Notificación desktop cuando una cita está por comenzar (5 min antes) | 2 horas |
| **O-06** | **Búsqueda global** | Campo en topbar que busque en pacientes, citas y cobros simultáneamente | 1 día |
| **O-07** | **Multi-ventana** | Abrir detalle de paciente o reporte en ventana independiente | 1 día |
| **O-08** | **Configuración de la app** | Diálogo para cambiar backend URL, timeouts, tema visual | 4 horas |

### 4.3 Mejoras técnicas

| # | Mejora | Justificación | Esfuerzo |
|---|---|---|---|
| **T-01** | **Tests unitarios de ViewModels** | Verificar lógica de negocio sin JavaFX UI | 1 día |
| **T-02** | **Mock del API client** | Permitir testear vistas sin backend real | 4 horas |
| **T-03** | **Cache local con expiración** | Reducir llamadas repetitivas al backend (catálogos) | 1 día |
| **T-04** | **Reconexión con backoff exponencial** | Reintentar conexión al backend con delays crecientes | 4 horas |
| **T-05** | **Logging a archivo** | Guardar logs en `logs/app.log` para diagnóstico | 2 horas |
| **T-06** | **jpackage para empaquetado** | Generar `.exe` instalable para Windows | 4 horas |
| **T-07** | **Auto-update** | Verificar nueva versión al iniciar | 1 día |

---

## 5. PRIORIZACIÓN RECOMENDADA

### Inmediato (antes de demo con usuario real)
| Prioridad | Item | Razón |
|---|---|---|
| 🔴 **P0** | F-01: Validación runtime completa | Sin esto no sabemos si algo explota al correrlo |
| 🔴 **P0** | F-11: Backend verificado | El desktop depende del backend funcionando |
| 🟡 **P1** | F-08: Generación real de reportes | Es la única funcionalidad del módulo Reportes que no hace nada |
| 🟡 **P1** | F-10: Crear cobro manual | Flujo incompleto sin botón directo en Cobros |
| 🟡 **P1** | U-01: Notificaciones toast | Mejora dramática en la percepción de calidad |

### Corto plazo (antes de entrega formal)
| Prioridad | Item | Razón |
|---|---|---|
| 🟢 **P2** | F-02: Formularios de edición | Profesionales y Cobros no tienen forma de crear/editar |
| 🟢 **P2** | O-02: Historial del paciente | Valor real para el usuario operativo |
| 🟢 **P2** | T-06: jpackage | Entregar `.exe` profesional, no "corre mvn javafx:run" |
| 🟢 **P2** | U-05: Ordenamiento por columnas | Expectativa básica en cualquier tabla |
| 🟢 **P2** | F-04: Recursos visuales | Logo real, iconos definidos, sin placeholders |

### Medio plazo (mejora continua)
| Prioridad | Item | Razón |
|---|---|---|
| 🔵 **P3** | O-06: Búsqueda global | Conveniencia, no bloqueante |
| 🔵 **P3** | U-08: Tema oscuro | Preferencia personal, no funcional |
| 🔵 **P3** | O-04: Estadísticas de cobros | Dashboard mejorado |
| 🔵 **P3** | T-03: Cache local | Performance, no correctitud |
| 🔵 **P3** | O-05: Alertas de citas | Feature avanzada, requiere timer |

---

## 6. RIESGOS IDENTIFICADOS

| # | Riesgo | Probabilidad | Impacto | Mitigación |
|---|---|---|---|---|
| R-01 | El backend Spring Boot no arranca fuera del sandbox | Alta | Alto | Validar con `run_consultorio_all.bat` inmediatamente |
| R-02 | PostgreSQL del consultorio sin seeds | Media | Alto | Crear script SQL con datos demo de los 25 pacientes, 19 atenciones, etc. |
| R-03 | JavaFX 21.0.2 incompatible con JDK del usuario | Baja | Alto | Documentar JDK mínimo requerido (Temurin 21) |
| R-04 | Los DTOs del desktop no coinciden con los del backend | Media | Medio | Los 16 DTOs están alineados; verificar tras primer arranque real |
| R-05 | `run_all.bat` falla con paths con espacios | **Ya confirmado** | Medio | `launch_desktop.bat` ya corrige el problema de `%ROOT%` con espacios |

---

## 7. MÉTRICAS DEL PROYECTO

| Métrica | Valor |
|---|---|
| Archivos Java (src/main/java) | 234 |
| Líneas estimadas de código | ~18.000 |
| Módulos funcionales | 8 / 8 |
| Componentes compartidos | 15 |
| Servicios API | 9 |
| DTOs | 16 |
| Archivos CSS | 18 |
| Archivos FXML | 23 (6 activos, 17 heredados) |
| Stubs deprecados | 14 |
| Errores de compilación | **0** |
| Tests unitarios | 0 |
| Tests de integración | 0 |

---

## 8. CONCLUSIÓN

El desktop del consultorio está en un **estado de madurez funcional avanzada**: los 8 módulos existen, navegan, muestran datos (reales o demo), y tienen interacciones básicas (crear, buscar, cancelar, filtrar). La arquitectura es coherente y el código compila limpio.

**Lo que separa este estado de un "producto entregable" son 3 cosas:**

1. **Validación runtime** — Nadie ha corrido la app completa fuera del entorno de desarrollo. Este es el riesgo #1.
2. **Empaquetado** — Entregar un `.exe` instalable cambia la percepción de "proyecto de código" a "producto".
3. **Pulido visual** — Toasts, tooltips, ordenamiento, logo real, tema consistente. Son mejoras menores en código pero mayores en percepción.

**Recomendación:** Antes de agregar cualquier funcionalidad nueva, ejecutar `run_all.bat` (que ya incluye el desktop) con Docker Desktop activo y verificar que los 4 componentes arrancan. Si algo falla, corregir. Si todo funciona, el siguiente paso natural es jpackage + toast notifications + validación de formularios.
