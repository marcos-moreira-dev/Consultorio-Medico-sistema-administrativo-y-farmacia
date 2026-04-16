# Diccionario de datos

## Propósito

Definir el significado funcional de las tablas y columnas principales esperadas en la base de datos de farmacia, de forma que el modelo sea legible tanto para implementación como para estudio posterior.

## Alcance

Este diccionario corresponde únicamente a `database-farmacia`. No describe estructuras del consultorio ni mezcla conceptos de otros componentes.

## Tablas núcleo esperadas

### `categoria`
Representa una clasificación simple o comercial básica para los productos.

**Columnas esperadas de alto nivel:**
- identificador principal;
- nombre_categoria;
- descripcion_breve, si aplica;
- timestamps mínimos de control.

### `producto`
Representa el elemento comercial administrado por la farmacia.

**Columnas esperadas de alto nivel:**
- identificador principal;
- categoria_id, si se modela categoría separada;
- nombre_producto;
- presentacion;
- descripcion_breve;
- precio_visible, si se decide mostrarlo;
- estado_producto;
- es_publicable o bandera equivalente;
- estado_disponibilidad o referencia de stock básico;
- fecha_creacion;
- fecha_actualizacion.

### `reserva`
Representa el apartado formal de unidades de un producto. Esta tabla es candidata natural para V1.1.

**Columnas esperadas de alto nivel:**
- identificador principal;
- producto_id;
- cantidad_reservada;
- estado_reserva;
- referencia_operativa;
- fecha_creacion;
- fecha_actualizacion.

## Posibles tablas auxiliares

Según el nivel de formalización del esquema final, podrían aparecer además:

### Tabla de auditoría o rastro estructurado
Para cambios relevantes del subdominio.

### Tabla de usuario o referencia de actor
Si se decide persistir responsable de ciertas acciones a nivel de BD.

## Criterios de lectura del diccionario

### 1. Este documento describe intención funcional, no todavía tipos SQL definitivos
Los tipos concretos y restricciones detalladas se consolidarán en el esquema relacional final.

### 2. El diccionario debe alinearse con el dominio
Si una columna existe, debe tener justificación dentro de la farmacia.

### 3. No se deben introducir columnas por costumbre vacía
Cada atributo debe tener valor operativo o pedagógico claro.

## Qué debe evitarse

- columnas que mezclen datos del consultorio;
- atributos sin diferencia clara entre estado interno y publicación;
- nombres ambiguos sin significado funcional claro;
- acumulación de campos empresariales avanzados fuera del alcance de la V1.

## Resultado esperado

Este diccionario debe funcionar como un puente entre dominio y SQL: suficientemente concreto para orientar el esquema y suficientemente claro para que luego puedas estudiar por qué existe cada parte del modelo.

