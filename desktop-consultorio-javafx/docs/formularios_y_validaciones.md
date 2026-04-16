# Formularios y validaciones

## Propósito

Definir cómo deben construirse los formularios de `desktop-consultorio-javafx`, cómo se agrupan los campos y cómo se representa la validación en la UI para que el usuario entienda con claridad qué debe corregir sin romper el flujo de trabajo.

## Principio general

El desktop del consultorio no debe tener formularios improvisados, desalineados o con validaciones confusas. Los formularios deben sentirse:

- claros;
- sobrios;
- rápidos de completar;
- visualmente consistentes;
- coherentes con la estructura del backend.

## Rol de los formularios en la app

En este sistema, los formularios aparecen sobre todo en:

- login;
- alta de registros;
- edición de registros;
- filtros avanzados;
- solicitud de reportes;
- confirmaciones con datos estructurados cuando haga falta.

## Contenedores JavaFX recomendados

### Formularios simples
`VBox`

### Formularios con alineación semántica de pares etiqueta-valor
`GridPane`

### Formularios dentro de modal
`VBox` o `BorderPane` con body organizado mediante `VBox` o `GridPane`.

## Estructura recomendada de un formulario

### 1. Título o encabezado
Debe indicar claramente qué se está creando o editando.

### 2. Sección o subtítulo opcional
Útil cuando hay varios grupos de campos.

### 3. Cuerpo de campos
Agrupado por sentido de negocio.

### 4. Zona de feedback
Mensajes de error o confirmación contextual.

### 5. Footer de acciones
Botón principal y secundario, con jerarquía clara.

## Reglas visuales de campos

### Etiqueta
- clara;
- cercana al campo;
- sin abreviaturas confusas.

### Campo
- altura uniforme;
- borde suave;
- radio moderado;
- placeholder útil pero no imprescindible.

### Espaciado vertical
Entre `10px` y `14px`.

### Espaciado horizontal en formularios de varias columnas
Entre `14px` y `20px`.

## Tipos de controles comunes

- `TextField`
- `PasswordField`
- `ComboBox`
- `DatePicker`
- `TextArea` si se necesitara en flujos más largos
- `Button`
- `Label`
- `CheckBox` solo si realmente aporta

## Agrupación de campos

Conviene agrupar por bloques semánticos.

### Ejemplos
- datos personales;
- datos de contacto;
- información administrativa;
- criterios del reporte;
- información de agenda.

No conviene mezclar todo en una lista interminable de inputs sin estructura.

## Validación en UI

## Tipos de validación visibles

### 1. Validación inmediata básica
Para errores obvios de forma:
- requerido vacío;
- formato incorrecto;
- longitud mínima o máxima.

### 2. Validación al enviar
Para revisión completa del formulario antes de solicitar persistencia.

### 3. Validación proveniente de backend
Para reglas de negocio que la UI no puede decidir sola.

## Regla de oro
La UI puede ayudar a validar, pero el backend sigue siendo la fuente de verdad.

## Cómo debe verse un error de formulario

### Forma preferida
- mensaje breve bajo el campo o muy cercano;
- borde o estado visual claro del campo;
- evitar mensajes gigantes que destruyan la composición.

### Qué debe evitarse
- solo pintar el borde en rojo sin texto;
- mostrar error lejísimos del campo;
- usar alertas globales para cada error pequeño.

## Botones de formulario

### Botón primario
Guardar, crear, enviar o confirmar.

### Botón secundario
Cancelar, cerrar o limpiar cuando haga sentido.

### Regla de jerarquía
Debe quedar clarísimo cuál es la acción principal.

## Formularios en modal

En V1, muchos formularios conviene resolverlos dentro de un modal.

### Reglas específicas
- no hacer modales demasiado estrechos;
- no meter formularios eternos en modales pequeños;
- mantener footer visible con acciones claras;
- permitir cerrar con `Esc` cuando no comprometa pérdida silenciosa de datos.

## Formularios de filtros

Los filtros también son formularios, pero de baja fricción.

### Deben ser
- compactos;
- legibles;
- rápidos;
- sin exceso de campos cuando una búsqueda simple basta.

## Ejemplos funcionales esperados

### Login
- usuario;
- contraseña;
- feedback de error claro;
- botón principal.

### Crear o editar registro
- datos agrupados;
- validación local básica;
- respuesta clara ante error de backend.

### Solicitar reporte
- tipo de reporte;
- formato;
- filtros;
- feedback de envío.

## Qué no debe pasar

- formularios desalineados;
- etiquetas ambiguas;
- validaciones solo al final sin pista previa;
- mensajes de error agresivos o poco claros;
- botones iguales compitiendo visualmente;
- una experiencia donde el usuario no sepa si guardó o no guardó.

## Resultado esperado

Los formularios y validaciones del desktop deben permitir capturar y editar datos con claridad, manteniendo una UI sobria y operativa, donde los errores se entienden rápido y la estructura visual ayuda al usuario en lugar de estorbarlo.

