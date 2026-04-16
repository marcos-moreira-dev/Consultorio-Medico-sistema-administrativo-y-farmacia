# Entornos y configuración

## Propósito

Definir cómo se organizan los entornos del proyecto y qué criterios deben seguirse para manejar configuración, variables y diferencias entre ejecución local, demo y futura evolución.

## Principio general

La configuración debe estar separada del código tanto como sea razonable. Un sistema administrativo serio no debe depender de valores hardcodeados dispersos para conectarse, arrancar o comportarse correctamente.

## Entornos considerados en la V1

### Entorno local de desarrollo
Usado para programar, probar y depurar el sistema por componentes.

### Entorno local de demo
Usado para presentar el proyecto en una configuración más controlada y estable que el desarrollo diario.

No hace falta asumir, por ahora, una infraestructura compleja de producción. Pero sí conviene dejar la estructura mental lista para diferenciar comportamientos y configuraciones.

## Tipos de configuración esperados

### Configuración de database consultorio
- host;
- puerto;
- nombre de base;
- usuario;
- credenciales;
- parámetros de conexión.

### Configuración de database farmacia
- host;
- puerto;
- nombre de base;
- usuario;
- credenciales;
- parámetros de conexión.

### Configuración de backend consultorio
- puerto;
- perfil activo;
- URL de conexión a `database-consultorio`;
- secretos o claves según necesidad;
- niveles de log;
- parámetros funcionales o flags de entorno.

### Configuración de backend farmacia
- puerto;
- perfil activo;
- URL de conexión a `database-farmacia`;
- secretos o claves según necesidad;
- niveles de log;
- parámetros funcionales o flags de entorno.

### Configuración del storefront
- URL del backend farmacia;
- modo de ejecución;
- variables públicas necesarias para la UI.

### Configuración del desktop
- URL del backend consultorio;
- opciones de arranque;
- modo local o de demo si aplica.

## Criterios de diseño para la configuración

### CFG-001. Lo sensible no se hardcodea
Secretos, credenciales y valores de entorno no deben quedar regados en código fuente sin control.

### CFG-002. Cada componente conoce solo lo que necesita
No se debe compartir configuración innecesaria entre consultorio y farmacia.

### CFG-003. Los perfiles deben servir un propósito claro
Separar entornos solo tiene sentido si cambia comportamiento, integración o seguridad de manera comprensible.

### CFG-004. La configuración debe documentarse
No basta con que funcione en la máquina del autor.

### CFG-005. Las bases deben diferenciarse explícitamente
No debe quedar ambigüedad sobre qué backend usa qué base de datos.

## Variables críticas mínimas

Como mínimo, debe quedar previsto documentar:

- conexión a `database-consultorio`;
- conexión a `database-farmacia`;
- puertos de backend consultorio y backend farmacia;
- URL del backend que consume cada cliente;
- perfil activo;
- ubicación o forma de carga de secretos básicos.

## Riesgos a evitar

- mezclar configuración de consultorio con la de farmacia;
- usar puertos cambiantes sin documentación;
- dejar credenciales visibles en código o repositorio sin criterio;
- depender de ajustes manuales no registrados;
- configurar ambos backends como si compartieran una misma base.

## Resultado esperado

Los entornos y la configuración deben permitir que el sistema sea reproducible, ordenado y adaptable a distintos escenarios locales sin convertir el proyecto en un caos de valores ocultos o frágiles, manteniendo además clara la separación entre las dos bases de datos.