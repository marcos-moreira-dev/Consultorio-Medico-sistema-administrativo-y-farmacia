# Manejo de errores UI

## Propósito

Definir cómo debe reaccionar `desktop-consultorio-javafx` ante errores funcionales, técnicos y de conectividad, diferenciando claramente entre error de formulario, error de backend, estado vacío y fallo de red.

## Principio general

La UI no debe ocultar los errores ni dramatizarlos innecesariamente. Debe explicarlos de forma clara, útil y proporcional al problema, sin destruir el flujo de trabajo del usuario.

## Tipos de error que la UI debe distinguir

## 1. Error de formulario
Sucede cuando un dato ingresado por el usuario no cumple la validación de forma o falta información obligatoria.

### Representación recomendada
- mensaje cercano al campo;
- estado visual del input;
- resumen leve solo si hay muchos errores.

## 2. Error de negocio proveniente del backend
Sucede cuando el request era formalmente válido pero el backend rechaza la operación por una regla del dominio.

### Representación recomendada
- mensaje claro en contexto;
- diálogo o panel contextual si la acción era crítica;
- no esconder el error detrás de un “falló” genérico.

## 3. Error de autenticación o sesión
Sucede cuando la sesión expiró o el acceso ya no es válido.

### Representación recomendada
- mensaje claro;
- posibilidad de volver al login;
- limpieza del estado de sesión si corresponde.

## 4. Error de conectividad
Sucede cuando la app no puede hablar con backend o la red falla.

### Representación recomendada
- mensaje técnico entendible;
- opción de reintentar;
- no simular como si fuera un error del formulario.

## 5. Error interno inesperado
Sucede cuando ocurre una falla no prevista del lado UI o integración.

### Representación recomendada
- mensaje sobrio;
- registro técnico en logs;
- feedback usable para el usuario sin exponer stack trace.

## 6. Estado vacío
No es exactamente un error, pero debe distinguirse claramente de uno.

### Ejemplos
- tabla sin resultados;
- historial de reportes sin solicitudes;
- filtro que no encontró coincidencias.

### Representación recomendada
- mensaje claro;
- ilustración suave opcional;
- posibilidad de limpiar filtros o crear nuevo registro según el caso.

## Dónde mostrar el error según contexto

## Campo específico
Para errores de validación local o muy puntuales.

## Panel contextual del módulo
Para errores de operación que afectan toda la vista actual.

## Modal de confirmación o alerta
Para errores críticos o acciones que requieren atención explícita.

## Toast o feedback breve
Solo para confirmaciones o eventos menores, no para errores de negocio importantes.

## Mensajes de error: tono recomendado

Los mensajes deben ser:

- claros;
- sobrios;
- informativos;
- sin lenguaje técnico innecesario para el usuario final.

### Ejemplo bueno
"No se pudo registrar la cita porque el horario ya está ocupado para ese profesional."

### Ejemplo malo
"Error 422 en endpoint de creación."

## Estado visual de error

### Colores
- rojo puntual;
- no dominar toda la pantalla;
- usar contraste suficiente.

### Superficie
- panel de error ligero;
- borde o icono discreto;
- sin efectos dramáticos.

## Reintentos

La UI debe ofrecer reintento cuando tenga sentido, especialmente en:
- carga inicial fallida;
- consulta de tabla;
- descarga de reporte;
- error temporal de conexión.

No conviene ofrecer “reintentar” en un error de formulario obvio como si fuera un fallo técnico.

## Relación con logs

Toda falla técnica relevante debe poder registrarse en logs o diagnóstico, pero el usuario no debe ver detalles internos innecesarios.

## Casos importantes a contemplar

### Login fallido
Mensaje claro sin revelar más información de la necesaria.

### Token expirado
Aviso claro y retorno al login o flujo de reautenticación.

### Falla al guardar
Debe conservar contexto suficiente para que el usuario no sienta que perdió todo sin explicación.

### Falla al cargar tabla
Mostrar panel de error con opción de reintentar.

### Falla en reporte
Distinguir entre solicitud inválida, generación fallida y archivo no disponible.

## Qué no debe pasar

- alertas genéricas para todo;
- stack trace visible al usuario;
- errores silenciosos;
- mensajes ambiguos como “ocurrió un error” sin contexto;
- confundir estado vacío con fallo técnico;
- reventar el shell completo por una falla local del módulo.

## Resultado esperado

El manejo de errores UI del desktop debe hacer que los problemas se entiendan rápido, se traten con sobriedad y no rompan innecesariamente la experiencia de trabajo del usuario, manteniendo siempre la diferencia entre validación, negocio, conectividad y vacío.

