# Superficies públicas vs privadas

## Propósito

Distinguir con claridad las superficies del sistema según su nivel de exposición, su tipo de consumidor y la sensibilidad de la información que manejan.

## Principio general

No todo lo que existe en el sistema debe ser accesible por todos. La seguridad del proyecto depende en gran parte de definir bien qué superficie es pública, cuál es privada y cuál es mixta.

## Superficie privada del consultorio

### Naturaleza
Es la superficie más sensible del sistema.

### Incluye
- pacientes;
- citas;
- atención simple;
- indicaciones breves;
- cobros de consulta;
- búsquedas internas del consultorio.

### Consumidores
- usuarios autenticados del consultorio.

### Reglas
- requiere autenticación;
- requiere autorización;
- no expone contratos públicos;
- no debe mezclar datos con la capa pública de farmacia.

## Superficie administrativa de farmacia

### Naturaleza
Es una superficie interna, no pública.

### Incluye
- alta y edición de productos;
- publicación y despublicación;
- inactivación;
- ajuste de disponibilidad o stock básico;
- futura gestión de reservas en V1.1.

### Consumidores
- usuarios autenticados con rol de farmacia administrativa.

### Reglas
- requiere autenticación;
- requiere autorización;
- no debe exponerse a visitantes externos.

## Superficie pública o semipública de farmacia

### Naturaleza
Es la parte visible del sistema para personas externas.

### Incluye
- listado de productos visibles;
- búsqueda simple;
- detalle público autorizado;
- disponibilidad publicada.

### Consumidores
- visitantes externos;
- clientes potenciales;
- cualquier consumidor público permitido por la aplicación.

### Reglas
- no requiere acceso a datos privados;
- solo muestra información publicable;
- no debe revelar datos administrativos internos ni nada del consultorio.

## Superficie persistente interna

### Naturaleza
Es la superficie de almacenamiento del sistema.

### Incluye
- base de datos y estructuras persistentes.

### Consumidores legítimos
- backends autorizados del sistema.

### Reglas
- no debe ser accedida directamente por clientes;
- no debe actuar como interfaz de integración pública.

## Clasificación práctica de exposición

### Pública
- catálogo visible de farmacia;
- búsqueda pública;
- detalle público del producto.

### Privada
- todo el consultorio;
- administración interna de farmacia;
- estructuras persistentes;
- trazas con información sensible.

### Mixta o controlada
- backend farmacia, porque puede tener endpoints administrativos y endpoints públicos separados;
- componentes que técnicamente sirven a dos superficies, pero con contratos diferenciados.

## Riesgos a evitar

- usar el mismo contrato para público e interno cuando la sensibilidad es distinta;
- exponer DTOs administrativos en la capa pública;
- reutilizar conceptos del consultorio en pantallas de farmacia;
- permitir que la conveniencia técnica rompa la separación de superficies.

## Resultado esperado

Esta clasificación debe servir como criterio para rutas, DTOs, permisos, pantallas, logs y decisiones de despliegue, garantizando que la separación pública/privada sea visible y real, no solo discursiva.

