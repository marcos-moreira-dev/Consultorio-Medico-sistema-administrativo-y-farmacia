# Flujo de datos principal

## Propósito

Explicar cómo circula la información en los flujos más importantes del sistema, desde la entrada del usuario hasta la persistencia y la respuesta mostrada en la interfaz.

## Principio general

El flujo de datos del sistema debe ser claro, controlado y coherente con las fronteras del proyecto. Los clientes capturan entrada y muestran resultados, los backends aplican reglas y cada base de datos conserva el estado persistente de su propio subdominio.

## Flujo principal 1. Consultorio: paciente a atención y cobro

### Descripción general
Es el flujo más sensible del proyecto porque maneja datos privados y representa la parte central del consultorio.

### Secuencia lógica
1. Un usuario interno opera el cliente desktop del consultorio.
2. Busca un paciente existente o registra uno nuevo.
3. El cliente envía la solicitud al backend consultorio.
4. El backend valida la entrada, ejecuta el caso de uso y persiste o consulta información en `database-consultorio`.
5. El backend responde con DTOs apropiados.
6. El desktop muestra el resultado y permite continuar hacia cita, atención o cobro.
7. Cuando se registra la atención, el backend guarda el evento operativo y actualiza estados asociados si corresponde.
8. Cuando se registra el cobro, el backend valida que exista una atención previa y persiste el pago en `database-consultorio`.

### Datos que circulan
- datos mínimos de paciente;
- datos de cita;
- cuerpo breve de atención;
- indicaciones breves;
- monto, método y estado de cobro.

### Restricciones clave
- no exponer estos datos fuera de la superficie privada;
- no permitir que la UI sea la fuente final de validación;
- no registrar cobros sin atención.

## Flujo principal 2. Farmacia: catálogo público y disponibilidad

### Descripción general
Es el flujo más visible externamente del proyecto y debe equilibrar sencillez pública con control administrativo.

### Secuencia lógica
1. Un visitante accede al storefront de farmacia.
2. Realiza búsqueda o navegación del catálogo.
3. El storefront consulta el backend farmacia mediante contratos públicos.
4. El backend filtra únicamente productos visibles y publicables.
5. `database-farmacia` devuelve la información persistida.
6. El backend construye DTOs públicos.
7. El storefront muestra listado, detalle y disponibilidad controlada.

### Datos que circulan
- nombre del producto;
- presentación;
- categoría;
- disponibilidad pública;
- detalle visible autorizado;
- precio visible, si se decide mostrarlo.

### Restricciones clave
- no exponer datos internos del consultorio;
- no exponer información administrativa innecesaria de farmacia;
- no mostrar como disponible un producto agotado.

## Flujo principal 3. Farmacia administrativa: mantenimiento de catálogo

### Descripción general
Es el flujo interno que sostiene la calidad y coherencia del catálogo público.

### Secuencia lógica
1. Un usuario interno accede a la superficie administrativa de farmacia.
2. Crea, edita, publica, inactiva o ajusta disponibilidad de un producto.
3. La solicitud llega al backend farmacia.
4. El backend aplica reglas de publicación y consistencia.
5. `database-farmacia` persiste el estado actualizado.
6. El backend responde con confirmación o error uniforme.
7. La capa pública refleja posteriormente el nuevo estado cuando corresponda.

## Flujo de integración futura entre consultorio y farmacia

### Descripción general
Si en una evolución futura ambos subdominios deben intercambiar información, esa comunicación no debe ocurrir por acceso directo a tablas de la otra base.

### Regla oficial
Toda integración futura entre consultorio y farmacia debe pasar por contratos de aplicación, referencias externas controladas o coordinación explícita entre backends.

## Flujo evolutivo V1.1

### Reprogramación de cita
Introduce cambios de estado y trazabilidad más fina en el consultorio.

### Reserva de producto
Introduce una nueva tensión de datos en farmacia: disponibilidad interna, unidades reservadas y visibilidad pública.

## Reglas comunes del flujo de datos

- los clientes nunca hablan directamente con una base de datos;
- toda validación crítica se consolida en backend;
- cada base de datos actúa como estado persistente de su propio subdominio;
- la respuesta hacia el cliente debe pasar por DTOs y contratos definidos;
- los errores deben tratarse de forma uniforme.

## Resultado esperado

El flujo de datos principal debe dejar claro cómo se mueve la información en el sistema y por qué la arquitectura elegida protege coherencia, privacidad, separación entre subdominios y capacidad de evolución.