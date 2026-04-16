# Correlación y diagnóstico

## Propósito

Definir cómo seguir una operación a través de componentes, capas y logs, de forma que los problemas del sistema puedan investigarse con menos fricción y mayor coherencia.

## Principio general

Diagnosticar bien no depende solo de tener muchos logs. Depende de poder relacionar eventos y mensajes que pertenecen a la misma operación.

En este proyecto, la correlación debe permitir reconstruir un flujo con suficiente claridad, por ejemplo:

- un registro de paciente;
- una cita reprogramada;
- una atención que termina en cobro;
- una publicación de producto;
- una actualización de disponibilidad.

## Identificador de correlación

Se fija como práctica oficial el uso de un identificador de correlación por operación o solicitud relevante.

### Función
Permitir agrupar logs, errores y eventos relacionados con una misma interacción o flujo.

### Uso esperado
- se genera o propaga al inicio de la operación;
- acompaña el recorrido por backend;
- puede incluirse en respuestas o errores cuando sea útil;
- debe aparecer en logs importantes y eventos relevantes.

## Casos donde la correlación es especialmente útil

### Consultorio
- alta de paciente con advertencia de duplicado;
- agenda con reprogramación;
- atención más cobro;
- errores de validación en flujos privados.

### Farmacia
- alta y publicación de producto;
- ajuste de disponibilidad;
- inactivación;
- reservas en V1.1.

### Transversal
- acceso denegado;
- error inesperado;
- incidencia de integración;
- fallo de migración o arranque.

## Estrategia de diagnóstico

### Paso 1. Identificar el flujo afectado
¿Se trata de consultorio, farmacia administrativa, farmacia pública o un problema transversal?

### Paso 2. Ubicar el identificador de correlación
Permite enlazar la solicitud, los eventos y los mensajes relevantes.

### Paso 3. Revisar nivel del fallo
Determinar si es:
- validación;
- regla de negocio;
- autorización;
- infraestructura;
- error inesperado.

### Paso 4. Revisar entidad o caso de uso principal
Paciente, cita, atención, cobro, producto, disponibilidad, reserva, etcétera.

### Paso 5. Confirmar impacto y estado persistido
Verificar si el fallo dejó el sistema sin cambios, con cambios parciales o con operación completada.

## Reglas de diseño derivadas

### COR-001. Todo error relevante debe ser diagnosticable
No basta con devolver “falló”. Debe existir contexto suficiente en backend para entender qué ocurrió.

### COR-002. La correlación no debe filtrar datos sensibles
El identificador sirve para relacionar eventos, no para exponer contenido privado.

### COR-003. Los mensajes de log deben ayudar a reconstruir el recorrido
Cada log importante debe indicar componente, operación y resultado principal.

### COR-004. El diagnóstico debe distinguir entre error esperado y fallo inesperado
No es lo mismo una validación rechazada que una excepción interna.

## Beneficios esperados

- menor tiempo de depuración;
- mejor comprensión del sistema;
- mayor capacidad de explicar incidentes;
- mejor soporte para evolución hacia V1.1.

## Resultado esperado

El sistema debe poder investigarse con método: seguir una operación, unir sus piezas, entender dónde falló y reducir la dependencia de intuición o memoria manual al depurar.