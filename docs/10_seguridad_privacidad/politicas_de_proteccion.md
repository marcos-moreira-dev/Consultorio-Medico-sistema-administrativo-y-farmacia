# Políticas de protección

## Propósito

Fijar principios prácticos de protección aplicables al diseño y construcción del sistema, de forma que seguridad y privacidad no dependan solo de buenas intenciones.

## Principio rector

Proteger en este proyecto significa diseñar para reducir exposición, limitar accesos, separar superficies y evitar filtraciones accidentales de información sensible o interna.

## Política 1. Mínimo privilegio
Cada usuario, rol o superficie debe acceder solo a lo necesario para cumplir su función.

### Implicaciones
- los usuarios del consultorio no reciben permisos administrativos de farmacia por defecto;
- los usuarios de farmacia no acceden al consultorio;
- la capa pública no ve estructuras internas.

## Política 2. Separación explícita de superficies
Toda funcionalidad debe pertenecer claramente a una superficie privada, administrativa o pública.

### Implicaciones
- endpoints separados según sensibilidad;
- DTOs separados según tipo de consumidor;
- clientes separados según función principal.

## Política 3. Protección desde backend
La validación de acceso y el control real de permisos deben consolidarse en backend.

### Implicaciones
- ocultar botones en UI no es suficiente;
- la autorización debe verificarse en la ruta o caso de uso correspondiente;
- los errores de acceso deben devolverse de forma uniforme.

## Política 4. Minimización de datos sensibles
Solo se registra y procesa la información sensible necesaria para la V1.

### Implicaciones
- no inflar el consultorio con historia clínica innecesaria;
- no publicar datos privados por conveniencia;
- no registrar más de lo útil para la operación y el aprendizaje del proyecto.

## Política 5. Contratos ajustados a la sensibilidad
No todos los consumidores merecen el mismo nivel de detalle.

### Implicaciones
- los contratos públicos de farmacia deben ser sobrios;
- los contratos privados del consultorio pueden manejar mayor detalle, pero con acceso controlado;
- los DTOs administrativos no deben filtrarse a la capa pública.

## Política 6. Trazabilidad responsable
Las acciones relevantes deben dejar rastro suficiente, pero sin convertir logs o auditorías en una nueva fuente de exposición indebida.

### Implicaciones
- registrar hechos útiles;
- evitar imprimir datos sensibles innecesarios en logs;
- mantener equilibrio entre diagnóstico y privacidad.

## Política 7. Persistencia no expuesta
La base de datos no debe ser utilizada como interfaz directa por clientes ni por integraciones improvisadas.

### Implicaciones
- desktop y web consumen backends;
- la lógica de acceso se centraliza en servicios y contratos.

## Política 8. Evolución sin degradar protección
Las nuevas capacidades de V1.1, como reservas o mayor trazabilidad de agenda, no deben romper la separación de superficies ni aumentar exposición sin justificación.

## Señales de incumplimiento de política

Hay problemas de protección cuando:

- una respuesta pública incluye campos internos no justificados;
- un usuario autenticado puede ver más de lo que necesita;
- una UI evita mostrar algo, pero el backend igual lo entrega;
- los logs contienen información sensible excesiva;
- consultorio y farmacia comparten contratos o modelos sin criterio.

## Resultado esperado

Estas políticas deben servir como criterio práctico al modelar la base de datos, diseñar endpoints, construir DTOs, definir permisos, escribir logs y decidir qué información aparece en cada interfaz del sistema.