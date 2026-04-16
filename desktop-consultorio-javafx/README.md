# desktop-consultorio-javafx

Cliente de escritorio JavaFX del subdominio consultorio.

## Rol dentro del sistema

Este componente representa la estación de trabajo interna del consultorio. Debe sentirse como una aplicación administrativa seria, sobria y estable, no como una maqueta visual aislada.

## Responsabilidades principales

- autenticación visual del usuario;
- shell principal con navegación estable;
- dashboards, tablas, formularios y modales del sistema;
- consumo de `backend-consultorio`;
- manejo visible de estados, errores y sesión;
- soporte a módulos operativos del consultorio.

## Estado actual

Ya no debe leerse como puro scaffolding vacío. El componente tiene:

- documentación amplia;
- árbol de paquetes grande y coherente;
- recursos FXML y estilos sembrados;
- `pom.xml` y scripts de onboarding más serios;
- bootstrap JavaFX mínimo para dejar el componente arrancable.

Todavía falta bastante implementación real en controladores, viewmodels, estilos y pruebas, pero el onboarding ya quedó mucho más útil para empezar a trabajarlo.

## Documentos que conviene leer primero

- `docs/vision_desktop.md`
- `docs/arquitectura_desktop.md`
- `docs/pantallas_y_flujos.md`
- `docs/navegacion_y_estado_ui.md`
- `docs/layout_espacial_y_shell.md`
- `docs/testing_desktop.md`
- `docs/onboarding_desktop.md`
- `TODO_DESKTOP_CONSULTORIO.md`

## Regla práctica

La interfaz debe priorizar flujo operativo, claridad y estabilidad. El brillo visual nunca debe romper la sensación de herramienta de trabajo.
