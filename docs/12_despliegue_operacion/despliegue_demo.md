# Despliegue demo

## Propósito

Definir una forma estable y comprensible de preparar una demostración funcional del sistema, de modo que el proyecto pueda mostrarse como producto serio y no como una colección improvisada de pantallas sueltas.

## Principio general

Una demo útil no debe intentar mostrarlo todo. Debe mostrar lo correcto, en el orden correcto y con un entorno suficientemente limpio como para transmitir control del producto.

## Objetivo de la demo

La demo debe permitir mostrar, como mínimo:

- la operación privada del consultorio;
- la capa pública o semipública de farmacia;
- la separación entre ambas superficies;
- la existencia de persistencia real y separada;
- el valor de la arquitectura modular del proyecto.

## Escenario recomendado de demo

### Parte 1. Consultorio
Mostrar un flujo breve y coherente:

1. buscar o crear paciente;
2. agendar cita o registrar atención directa;
3. registrar atención;
4. registrar cobro.

### Parte 2. Farmacia pública
Mostrar un flujo visible y simple:

1. abrir catálogo;
2. buscar producto;
3. ver detalle y disponibilidad.

### Parte 3. Farmacia administrativa
Mostrar mantenimiento breve:

1. crear o editar producto;
2. publicar o inactivar;
3. reflejar el cambio en la superficie pública.

## Preparación previa de la demo

Antes de demostrar, debe verificarse:

- `database-consultorio` lista y consistente;
- `database-farmacia` lista y consistente;
- migraciones de ambos subdominios aplicadas;
- seeds funcionales cargados en ambas bases;
- usuarios demo creados;
- catálogo visible coherente;
- datos del consultorio suficientes para flujo breve;
- puertos y configuraciones correctos.

## Qué conviene no improvisar en demo

- credenciales definidas en el último minuto;
- datos aleatorios inconsistentes;
- arranque manual caótico de componentes;
- rutas o endpoints no verificados;
- flujos demasiado largos o poco claros;
- cruces improvisados entre consultorio y farmacia a nivel de base de datos.

## Criterios de una buena demo

### Claridad
Debe entenderse qué parte pertenece al consultorio y cuál a farmacia.

### Brevedad
Se muestran flujos útiles, no todo el sistema.

### Coherencia
Los datos usados deben tener sentido entre sí dentro de cada subdominio.

### Control
Debe percibirse que el sistema responde a una estructura real, no a una simulación frágil.

## Riesgos de demo a evitar

- intentar mostrar más de lo que la V1 realmente sostiene;
- mezclar pantallas privadas y públicas sin explicación;
- depender de cambios manuales directos en bases de datos durante la presentación;
- usar datos sensibles reales.

## Resultado esperado

El despliegue de demo debe permitir presentar el proyecto como una aplicación administrativa modular, coherente y razonablemente madura, destacando su utilidad, su separación de superficies y su separación real de persistencia.