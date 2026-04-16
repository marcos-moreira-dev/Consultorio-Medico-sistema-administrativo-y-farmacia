# Informe de normalización de V0/1FN a 3FN de farmacia

## Propósito

Este documento explica cómo se transforma la primera versión relacional de estudio del subdominio farmacia hacia un modelo más limpio y estable en tercera forma normal (3FN).

No se trata solo de “arreglar tablas”. Se trata de entender qué problemas aparecen en un diseño inicial, por qué aparecen y cómo se resuelven mediante separación de dependencias, eliminación de redundancias y definición más clara de entidades.

## Alcance

Este informe corresponde exclusivamente a `database-farmacia`.

No analiza consultorio, pacientes, citas, atenciones ni ninguna otra base del sistema. Todo lo que se describe aquí pertenece al contexto comercial y operativo de farmacia.

## Punto de partida

La versión inicial en 1FN fue construida con intención pedagógica. Ya cumple una estructura tabular válida y usa valores atómicos, pero todavía arrastra redundancias y dependencias que no conviene conservar en el diseño final.

Las tablas consideradas en ese punto de partida son:

- `categoria_1fn`
- `producto_1fn`
- `reserva_1fn`

## Qué sí cumple la versión 1FN

La versión inicial ya cumple 1FN porque:

- cada tabla tiene filas identificables;
- los atributos contienen valores atómicos;
- no hay grupos repetidos dentro de una misma columna;
- ya existe una noción básica de relación entre categoría, producto y reserva.

Eso significa que el modelo ya es un buen punto de partida para estudiar, pero no significa que sea el modelo correcto para sostener la V1 final.

## Problemas principales detectados en el diseño inicial

### 1. Redundancia de datos de categoría dentro de producto

En la versión 1FN, datos como:

- `nombre_categoria`
- `descripcion_categoria`

aparecen no solo en `categoria_1fn`, sino también repetidos en `producto_1fn`.

#### Consecuencias

- si cambia el nombre o la descripción de la categoría, habría que actualizar múltiples filas de producto;
- si unas filas se actualizan y otras no, queda inconsistencia;
- se incrementa almacenamiento redundante sin necesidad;
- el catálogo se vuelve más frágil frente a errores de mantenimiento.

### 2. Redundancia de datos de producto y categoría dentro de reserva

En `reserva_1fn` se repiten datos como:

- `nombre_categoria`
- `presentacion`
- `estado_producto`
- `estado_disponibilidad`

aunque esos datos realmente pertenecen al producto o al contexto de categoría.

#### Consecuencias

- la reserva deja de representar solo el hecho del apartado;
- aparecen dependencias innecesarias entre entidades conceptualmente distintas;
- si cambia la presentación o disponibilidad del producto, la reserva puede quedar desalineada.

### 3. Uso de claves compuestas con intención didáctica

En `producto_1fn` la clave primaria es:

- `(categoria_id, nombre_producto)`

Y en `reserva_1fn` la clave primaria es:

- `(categoria_id, nombre_producto, fecha_hora_reserva)`

#### Consecuencias

- el modelo se vuelve más incómodo para ciertas referencias;
- aparecen dependencias parciales en atributos que dependen solo de parte de la PK;
- la evolución del esquema y del backend se vuelve menos cómoda.

### 4. Mezcla entre identidad de producto y estado operativo replicado

En la versión inicial, atributos como estado del producto y disponibilidad se arrastran dentro de la reserva, cuando la reserva debería centrarse en su propio hecho operativo.

#### Consecuencias

- se confunden responsabilidades entre tablas;
- cuesta ver con claridad qué dato pertenece al producto y cuál al hecho de reservar.

## Análisis hacia segunda forma normal (2FN)

## Recordatorio conceptual

Una tabla está en 2FN si:

- ya está en 1FN;
- todos los atributos no clave dependen de la clave completa;
- no existen dependencias parciales respecto de una clave compuesta.

## Dónde aparecen dependencias parciales en la versión inicial

### Caso de `producto_1fn`

La clave primaria es:

- `(categoria_id, nombre_producto)`

Pero atributos como:

- `nombre_categoria`
- `descripcion_categoria`

no dependen de toda la clave compuesta. En realidad dependen solo de `categoria_id`.

Eso es una dependencia parcial clara.

### Caso de `reserva_1fn`

La clave primaria es:

- `(categoria_id, nombre_producto, fecha_hora_reserva)`

Pero varios atributos como:

- `nombre_categoria`
- `presentacion`
- `estado_producto`
- `estado_disponibilidad`

no dependen de toda la clave compuesta. Dependen del producto o incluso de la categoría, no del hecho completo de la reserva.

Eso vuelve evidente que la reserva está cargando información que no le pertenece como entidad propia.

## Transformaciones necesarias para llegar a 2FN

### Transformación 1. Mantener a categoría como fuente única de sus datos propios

Toda la información descriptiva de la categoría debe vivir en `categoria`.

Por tanto, deben eliminarse de `producto` los campos repetidos de categoría.

### Transformación 2. Mantener al producto como fuente única de sus datos propios

Toda la información descriptiva y operativa del producto debe vivir en `producto`.

Por tanto, deben eliminarse de `reserva` los campos repetidos del producto y de la categoría.

### Transformación 3. Evaluar PK sustitutas más limpias

Aunque la PK compuesta sirve para estudio, para el diseño final conviene usar claves primarias propias:

- `categoria_id`
- `producto_id`
- `reserva_id`

Esto elimina gran parte de la incomodidad asociada a dependencias parciales y simplifica referencias futuras.

## Resultado esperado al alcanzar 2FN

Al llegar a 2FN, el modelo ya debería:

- eliminar repeticiones innecesarias de categoría dentro de producto;
- eliminar repeticiones innecesarias de producto dentro de reserva;
- separar mejor cada entidad según su responsabilidad;
- dejar de depender de PK compuestas como eje del diseño final.

## Análisis hacia tercera forma normal (3FN)

## Recordatorio conceptual

Una tabla está en 3FN si:

- ya está en 2FN;
- no existen dependencias transitivas entre atributos no clave.

Es decir, un atributo no clave no debe depender de otro atributo no clave, sino de la clave de la entidad.

## Dónde conviene vigilar dependencias transitivas

### 1. Estados y catálogos cerrados

Atributos como:

- `estado_producto`
- `estado_disponibilidad`
- `estado_reserva`

pueden resolverse de dos formas en V1:

- como `CHECK` simples;
- o como tablas catálogo si se quiere mayor formalidad.

Para una V1 pequeña, mantenerlos como `CHECK` suele ser suficiente y evita sobrediseño. Pero hay que asegurarse de que no generen duplicidad conceptual ni semántica confusa dentro de las tablas.

### 2. Publicación y disponibilidad

Hay que cuidar que publicación y disponibilidad no se mezclen como si fueran el mismo atributo.

- la publicación responde a si el producto debe ser visible;
- la disponibilidad responde a si el producto puede ofrecerse operativamente.

No son exactamente la misma cosa y, por tanto, el diseño final debe tratarlos con claridad semántica.

### 3. Separación clara entre producto y reserva

El producto representa el objeto comercial del catálogo.

La reserva representa un hecho operativo posterior asociado a ese producto.

Si atributos del producto dependen semánticamente de la reserva o viceversa más allá de la FK, entonces aún hay mezcla conceptual.

## Transformaciones necesarias para llegar a 3FN

### Transformación 4. Consolidar cada entidad con su propio identificador

El modelo final debe usar tablas del estilo:

- `categoria`
- `producto`
- `reserva` si la evolución la incorpora

cada una con su PK propia.

### Transformación 5. Mantener solo atributos que dependan de la entidad correcta

#### `categoria`
Debe guardar solo identidad y descripción de la clasificación.

#### `producto`
Debe guardar solo información propia del catálogo: nombre, presentación, descripción, estado, publicación, disponibilidad y precio visible si aplica.

#### `reserva`
Debe guardar solo el hecho del apartado: referencia al producto, cantidad, estado y referencia operativa mínima.

### Transformación 6. Mantener la reserva como entidad opcional y limpia

Esto permite soportar:

- productos sin reservas;
- una evolución V1.1 sin reestructurar todo el modelo.

### Transformación 7. No introducir entidades innecesarias todavía

El paso a 3FN no significa crear tablas por crear.

Por ejemplo, en esta V1 no hace falta separar todo en catálogos si un `CHECK` ya resuelve el problema con claridad suficiente.

## Modelo conceptual de llegada a 3FN

El diseño final esperado conceptualmente sería:

### `categoria`
Entidad de clasificación simple del catálogo.

### `producto`
Entidad eje de farmacia, con FK a `categoria` si se decide mantenerla separada.

### `reserva`
Entidad operativa opcional, con FK a `producto`.

## Beneficios del paso a 3FN

### 1. Menor redundancia
Los datos de categoría ya no se repiten en producto y los del producto ya no se repiten en reserva.

### 2. Menor riesgo de inconsistencias
Actualizar nombre de categoría, presentación o estado del producto no obliga a tocar múltiples tablas redundantes.

### 3. Relaciones más claras
Cada entidad representa mejor su responsabilidad.

### 4. SQL final más defendible
El esquema queda más profesional para backend, ORM, migraciones y estudio formal.

### 5. Mejor base para seeds y pruebas
El modelo final facilita cargar datos demo coherentes sin arrastrar duplicación artificial.

## Resumen del recorrido de normalización

### En 1FN
Ya hay atomicidad y tablas válidas, pero con redundancia y dependencias parciales.

### Para 2FN
Se eliminan dependencias parciales, sobre todo las derivadas de repetir datos de categoría en producto y de repetir datos de producto/categoría en reserva.

### Para 3FN
Se eliminan dependencias transitivas y se consolida que cada tabla guarde solo atributos propios de su entidad.

## Decisiones finales que este informe recomienda

- usar PK sustitutas estables para `producto` y `reserva`;
- conservar `categoria` como fuente única de clasificación si se decide modelarla aparte;
- mantener `producto` como entidad eje de farmacia;
- mantener `reserva` dependiente de `producto` y no mezclarla con información del catálogo;
- resolver estados simples con `CHECK` en la V1, salvo que una necesidad real justifique tablas catálogo;
- no mezclar ninguna estructura de consultorio en esta base.

## Resultado esperado

Después de este proceso, el siguiente paso natural es construir el SQL de la versión final en 3FN de farmacia, con una estructura más limpia, menos redundancia y mejor alineación con el backend, las migraciones y el estudio formal de PostgreSQL.