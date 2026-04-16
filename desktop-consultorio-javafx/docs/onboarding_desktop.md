# Onboarding desktop

## Propósito

Explicar cómo debe incorporarse alguien a `desktop-consultorio-javafx`, qué necesita entender primero, qué piezas debe reconocer rápido y cómo empezar a trabajar sin perderse en el proyecto.

## Estado del componente en esta etapa

Este desktop ya no está solo en fase conceptual. En el repo actual ya existe:

- `pom.xml` con una base JavaFX + Toolchains;
- bootstrap inicial de la app;
- `.env.example` y scripts de verificación/arranque;
- FXML sembrado por módulos;
- árbol de paquetes amplio;
- TODO específico para continuar el componente.

Eso no significa que el desktop esté terminado. Significa que el onboarding ya debe tratarlo como **componente parcialmente poblado**, no como cascarón puro.

## Ruta de lectura sugerida

1. `README.md`
2. `docs/vision_desktop.md`
3. `docs/arquitectura_desktop.md`
4. `docs/estructura_de_paquetes.md`
5. `TODO_DESKTOP_CONSULTORIO.md`

## Ruta de arranque sugerida

1. copiar `scripts/toolchains-temurin-21.example.xml` a `%USERPROFILE%\.m2\toolchains.xml`;
2. ajustar `jdkHome` a tu Temurin 21 real;
3. revisar `.env.example`;
4. ejecutar `scripts\verify-desktop-consultorio.bat`;
5. ejecutar `scripts\dev-desktop-consultorio.bat`.

## Qué debe entender primero una persona nueva

### 1. Esto es una app de escritorio real
No es una web disfrazada. Tiene shell, navegación interna, modales, tablas y estados de sesión propios.

### 2. Esta app representa al consultorio interno
No pertenece a farmacia y no debe mezclarse con sus superficies ni contratos.

### 3. El backend asociado es `backend-consultorio`
El desktop consume la API privada del consultorio. No toca la base de datos directamente.

### 4. El shell todavía está en transición
Ya existe bootstrap y estructura, pero el shell definitivo, login real y navegación completa todavía deben endurecerse.

## Qué revisar primero en código

- `app/AppLauncher.java`
- `app/DesktopConsultorioApplication.java`
- `config/AppConfig.java`
- `config/EnvConfig.java`
- recursos en `src/main/resources/fxml/`
- `TODO_DESKTOP_CONSULTORIO.md`

## Regla práctica

Antes de poblar módulos enteros, cerrar primero:

- arranque JavaFX real;
- sesión/login;
- shell principal;
- integración base con backend;
- tokens visuales mínimos.
