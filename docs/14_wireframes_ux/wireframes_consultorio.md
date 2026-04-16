# Wireframes consultorio

## Propósito

Describir la estructura conceptual de las pantallas principales del consultorio para guiar una futura implementación coherente con el dominio, la privacidad y la operación real del negocio.

## Principio general

El consultorio debe sentirse como una herramienta interna de trabajo: directa, privada, sobria y orientada a flujos encadenados. Las pantallas deben ayudar a buscar, registrar, atender y cobrar con el menor ruido posible.

## Pantalla 1. Inicio o panel principal

### Objetivo
Dar una entrada clara a la operación diaria del consultorio.

### Elementos sugeridos
- resumen breve del día;
- acceso a agenda;
- acceso rápido a búsqueda de pacientes;
- acceso a nueva atención o nuevo paciente;
- recordatorios operativos simples si hacen sentido.

### Observaciones
No debe convertirse en dashboard recargado. Debe servir más como panel de arranque que como centro de métricas.

## Pantalla 2. Búsqueda y listado de pacientes

### Objetivo
Permitir localizar rápidamente un paciente y continuar hacia su flujo.

### Elementos sugeridos
- barra de búsqueda principal;
- listado con coincidencias;
- datos mínimos visibles para distinguir registros;
- acción para crear paciente nuevo;
- acción para abrir detalle o iniciar atención.

### Observaciones
Esta pantalla es crítica. Debe priorizar velocidad de identificación y baja fricción.

## Pantalla 3. Registro o edición de paciente

### Objetivo
Capturar los datos mínimos requeridos sin sobrecargar el formulario.

### Elementos sugeridos
- nombres y apellidos;
- teléfono;
- cédula o identificador;
- campos opcionales adicionales;
- bloque de observaciones internas no clínicas si aplica;
- acciones claras de guardar o cancelar.

### Observaciones
Debe ser un formulario contenido, no una ficha clínica extensa.

## Pantalla 4. Agenda de citas

### Objetivo
Visualizar y gestionar citas del consultorio.

### Elementos sugeridos
- vista del día o rango corto;
- lista o grilla de citas;
- estados visibles;
- acciones para crear, cancelar o reprogramar;
- acceso rápido al paciente o a la atención.

### Observaciones
La agenda debe comunicar orden temporal con claridad y evitar saturación innecesaria.

## Pantalla 5. Registro de atención

### Objetivo
Permitir al médico registrar una atención simple de forma rápida.

### Elementos sugeridos
- encabezado con identificación del paciente;
- contexto breve de la cita si existe;
- campo de nota o cuerpo breve de atención;
- bloque de indicaciones o receta breve;
- acción de guardar;
- acceso a cobro posterior si corresponde.

### Observaciones
Debe sentirse como flujo corto y funcional, no como expediente hospitalario.

## Pantalla 6. Registro de cobro

### Objetivo
Cerrar administrativamente la atención.

### Elementos sugeridos
- referencia visible a la atención o paciente;
- monto;
- método de pago;
- estado del cobro;
- observación administrativa breve;
- acción de registrar.

### Observaciones
Debe ser clara y rápida, sin ambigüedad entre pago completado y pendiente.

## Relaciones entre pantallas

Los recorridos más naturales deberían ser:

- búsqueda de paciente → agenda o atención;
- paciente nuevo → cita o atención;
- agenda → atención;
- atención → cobro.

## Resultado esperado

Los wireframes del consultorio deben servir como guía para construir una interfaz interna enfocada en operación, privacidad y continuidad de flujo, evitando tanto la pobreza funcional como la complejidad clínica innecesaria.