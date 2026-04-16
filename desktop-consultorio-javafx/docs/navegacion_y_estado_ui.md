# Navegación y estado UI

## Propósito

Definir cómo navega el usuario dentro de `desktop-consultorio-javafx`, qué significa el estado de la interfaz y cómo debe mantenerse coherencia visual y funcional al cambiar de módulo, abrir modales o recargar información.

## Principio general

La navegación del desktop debe sentirse estable y predecible. El usuario no debe sentir que cada módulo es una aplicación distinta.

La UI debe apoyarse en tres ideas:

- shell persistente;
- módulo activo claro;
- estado visible bien controlado.

## Modelo de navegación adoptado

Se adopta una navegación **interna dentro de un shell fijo**.

Eso significa:

- el login vive como pantalla separada del shell;
- al autenticarse, el usuario entra al shell principal;
- dentro del shell, la zona central cambia según el módulo activo;
- sidebar y barra superior permanecen estables.

## Etapas principales de navegación

## 1. Estado no autenticado

### Pantalla visible
Login.

### Elementos visibles
- card de acceso;
- mensaje institucional;
- branding mínimo.

### No debe verse
- módulos internos;
- sidebar;
- dashboard;
- acciones del sistema autenticado.

## 2. Estado autenticado

### Pantalla visible
Shell principal del sistema.

### Elementos visibles
- `MenuBar`;
- barra superior contextual;
- sidebar;
- módulo activo en zona central.

## 3. Navegación entre módulos

### Mecanismo principal
Selección desde el sidebar.

### Reglas
- solo un módulo se considera activo visualmente a la vez;
- el botón activo del sidebar debe destacarse claramente;
- la zona central debe reemplazar contenido sin romper el shell;
- el título de pantalla debe actualizarse con coherencia.

## Estado UI que conviene modelar

## Estado de sesión
Debe contemplar:
- usuario autenticado;
- rol activo;
- token o contexto interno;
- etiqueta visible de sesión si aplica.

## Estado de navegación
Debe contemplar:
- módulo activo;
- vista o subvista activa si aplica;
- historial mínimo de navegación interna si luego se justifica.

## Estado de pantalla
Cada módulo puede necesitar:
- loading;
- datos cargados;
- error;
- vacío;
- modal abierto o cerrado;
- filtros visibles.

## Estado de selección
En módulos tabulares puede existir:
- fila seleccionada;
- registro activo;
- página actual;
- filtros aplicados.

## Reglas de navegación visual

### Sidebar
La navegación principal vive ahí.

### Barra superior
Debe reflejar que el usuario está dentro del sistema y no perdido en una vista flotante.

### Módulo activo
Debe ser muy claro qué pantalla está abierta.

### Modales
No deben romper la navegación del shell; deben apilarse sobre la vista activa.

## Comportamiento recomendado por tipo de pantalla

## Dashboard
Debe ser el módulo de entrada luego del login, salvo que una decisión explícita diga otra cosa.

## Módulos tabulares
Deben permitir ir de:
- listado;
- filtro;
- acción;
- modal;
- volver al contexto sin perder consistencia.

## Reportes
Puede requerir estado adicional de solicitud, historial y descarga.

## Recarga y persistencia de contexto

### Recomendación
Cuando el usuario cambia entre módulos, conviene decidir con claridad qué estado se conserva y qué estado se reinicia.

### Postura razonable para V1
- conservar módulo activo mientras la sesión siga viva;
- no prometer persistencia sofisticada de todos los filtros si eso complica la implementación demasiado;
- sí permitir recarga manual clara cuando haga falta.

## Estado de loading

Cada módulo debe poder mostrar:
- carga inicial;
- recarga puntual;
- operación en curso;
- overlay de espera en acciones sensibles.

## Estado de error

La UI debe poder distinguir:
- error de formulario;
- error de backend;
- error de conectividad;
- ausencia de resultados.

## Estado vacío

Las tablas y dashboards deben tener empty states claros cuando no hay datos.

## Qué no debe pasar

- navegación que reconstruye todo el shell una y otra vez;
- pérdida caótica del módulo activo;
- sidebar sin indicación clara de selección;
- modales que se sienten como pantallas aparte desconectadas;
- cambios de módulo que no actualizan correctamente contexto visual.

## Resultado esperado

La navegación y estado UI del desktop deben hacer que el sistema se sienta estable, reconocible y coherente, con un shell fijo, módulos claramente seleccionados y una UI capaz de reflejar con claridad sesión, carga, error, vacío y operaciones en curso.