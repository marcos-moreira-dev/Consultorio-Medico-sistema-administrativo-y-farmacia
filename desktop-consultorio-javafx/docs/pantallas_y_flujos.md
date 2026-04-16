# Pantallas y flujos

## Propósito

Definir qué pantallas principales existen en `desktop-consultorio-javafx`, qué hace cada una y cómo fluye el usuario entre ellas dentro del shell del sistema.

## Principio general

La aplicación no debe verse como una colección de vistas inconexas. Cada pantalla debe responder a una necesidad operativa concreta y encajar dentro de un flujo reconocible.

## Pantallas principales del sistema

## 1. Login

### Objetivo
Permitir acceso al sistema autenticado.

### Componentes principales
- panel hero institucional;
- formulario de usuario y contraseña;
- botón principal de acceso;
- mensajes de error si el login falla.

### Flujo
- usuario ingresa credenciales;
- sistema valida;
- si es correcto, abre shell principal;
- si falla, muestra feedback claro sin romper la composición.

## 2. Dashboard

### Objetivo
Mostrar un resumen general del sistema al entrar.

### Componentes principales
- título de bienvenida;
- subtítulo contextual;
- panel hero de métrica principal;
- tarjetas KPI;
- bloque de visualización o gráfico;
- resumen operativo lateral si aplica.

### Flujo
- dashboard carga al entrar al sistema;
- el usuario puede saltar a módulos operativos desde sidebar;
- se permite recarga del dashboard cuando haga falta.

## 3. Pantallas de listado operativo

Estas comparten un patrón muy claro. En el consultorio, al menos deberían existir listados para:

- pacientes;
- profesionales;
- citas;
- atenciones;
- cobros;
- reportes;
- auditoría si forma parte de la app visible.

### Objetivo
Permitir trabajo cotidiano con alta densidad de datos.

### Estructura general
- título del módulo;
- breve texto contextual;
- barra de búsqueda y filtros;
- botón de crear o acción principal;
- tabla central;
- acciones por fila;
- paginación;
- feedback de cantidad de registros.

## 4. Pantallas de detalle

### Objetivo
Mostrar información más amplia de un registro sin romper el contexto general.

### Regla de diseño
En V1, conviene que el detalle no se convierta en una navegación infinita. Puede resolverse como:
- vista secundaria dentro del módulo;
- panel de detalle;
- modal más amplio;
- o transición controlada dentro del contenido central.

## 5. Formularios de alta y edición

### Objetivo
Crear o modificar entidades del sistema.

### Regla principal
En V1, estos formularios deberían resolverse preferentemente mediante modales o paneles muy controlados, para no fragmentar la experiencia.

### Ejemplos
- crear profesional;
- crear paciente;
- crear cita;
- registrar cobro;
- solicitar reporte.

## 6. Pantalla o módulo de reportes

### Objetivo
Permitir solicitar, revisar y descargar reportes generados por backend.

### Componentes principales
- formulario de solicitud;
- selección de tipo de reporte;
- filtros;
- formato;
- historial de solicitudes;
- estados;
- acciones de descarga o consulta.

### Flujo
- usuario define tipo de reporte;
- sistema envía solicitud;
- historial refleja estado;
- si está listo, se habilita descarga.

## 7. Pantalla o módulo de auditoría

### Objetivo
Revisar eventos relevantes y trazabilidad si esa parte queda expuesta en la app.

### Componentes principales
- filtros;
- tabla;
- estado y fecha;
- detalles básicos del evento.

## Flujos principales del usuario

## Flujo A. Acceso al sistema
Login → validación → shell principal → dashboard.

## Flujo B. Trabajo con listado
Entrar a módulo → filtrar o buscar → revisar tabla → ejecutar acción por fila.

## Flujo C. Crear nuevo registro
Entrar a módulo → botón crear → modal o formulario → validar → guardar → actualizar listado.

## Flujo D. Editar registro existente
Entrar a módulo → localizar fila → acción editar → modal → guardar → refrescar contexto.

## Flujo E. Ver detalle
Entrar a módulo → seleccionar registro → acción ver → panel, modal o vista de detalle → volver al contexto.

## Flujo F. Solicitar y descargar reporte
Entrar a reportes → definir tipo y filtros → solicitar → revisar historial → descargar cuando esté disponible.

## Prioridad de patrones

### Patrón dominante
Tabla + filtros + acciones.

### Patrón secundario
Modal de formulario.

### Patrón de entrada
Dashboard.

### Patrón especializado
Reportes y auditoría.

## Qué no debe pasar

- cada módulo con una lógica de flujo distinta sin relación con el resto;
- pantallas donde no queda clara la acción principal;
- navegación innecesariamente profunda;
- formularios gigantescos sin agrupación;
- dashboards sin propósito operativo real.

## Resultado esperado

Las pantallas y flujos del desktop deben dejar una aplicación operativa y estable, donde el usuario pueda entrar, encontrar su módulo, trabajar con tablas y formularios, y completar acciones sin sentir que cada pantalla pertenece a un sistema distinto.