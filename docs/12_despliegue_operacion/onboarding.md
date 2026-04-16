# Onboarding

## Propósito

Guiar a una persona nueva para entender y arrancar el proyecto con el menor nivel posible de confusión, dependencia informal y prueba-error innecesario.

## Principio general

Un buen onboarding reduce tiempo muerto, evita errores repetidos y convierte el proyecto en algo más serio y compartible. Incluso si este sistema nace como proyecto personal de estudio, debe poder ser comprendido por alguien más o por tu versión futura sin depender de memoria frágil.

## Qué debe lograr el onboarding

Al terminar este recorrido, una persona debería poder:

- entender qué es el proyecto;
- identificar sus componentes principales;
- saber en qué orden revisar la documentación;
- preparar el entorno local;
- levantar el sistema;
- ejecutar una demo básica.

## Ruta sugerida de entrada

### Paso 1. Entender el contexto maestro
Leer primero:

- `docs/00_contexto_maestro`;
- `docs/01_dominio_consultorio`;
- `docs/02_dominio_farmacia`.

### Paso 2. Entender arquitectura y system design
Leer después:

- `docs/03_arquitectura_general`;
- `docs/04_system_design`.

### Paso 3. Entender operación técnica
Leer luego:

- `docs/10_seguridad_privacidad`;
- `docs/11_logs_trazabilidad_auditoria`;
- `docs/12_despliegue_operacion`.

### Paso 4. Preparar entorno
Instalar o verificar dependencias y configuración mínima.

### Paso 5. Levantar componentes
Arrancar base, backends y clientes en el orden definido.

## Información mínima que debe conocer quien entra

- propósito del proyecto;
- diferencia entre consultorio y farmacia;
- superficies privada, administrativa y pública;
- componentes principales;
- stack tecnológico usado;
- orden lógico de arranque;
- ubicación de la documentación relevante.

## Errores de onboarding que conviene evitar

- empezar por el código sin entender el modelo documental;
- mezclar backends o configuraciones;
- saltarse migraciones y seeds;
- intentar abrir clientes antes de que los servicios estén listos;
- asumir que una carpeta técnica explica por sí sola el sistema completo.

## Resultado esperado

El onboarding debe convertir el proyecto en una base de trabajo legible y retomable, útil tanto para una persona nueva como para tu yo futuro cuando necesite regresar al sistema después de semanas o meses.

