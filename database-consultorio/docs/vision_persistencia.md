# Visión de persistencia

## Propósito

Definir la intención general de la base de datos del consultorio ahora que el componente se asume **multidoctor** y con gestión interna de usuarios y roles.

## Papel del componente dentro del sistema

`database-consultorio` conserva el estado persistente del subdominio consultorio. Debe sostener integridad, trazabilidad mínima y evolución ordenada de un consultorio pequeño pero realista donde existen:

- pacientes;
- profesionales;
- usuarios internos y roles;
- agenda de citas;
- atención simple;
- indicaciones breves dentro de la atención;
- cobros asociados a atención;
- reportes derivados desde backend.

## Principio estructural principal

La persistencia del consultorio es independiente de la persistencia de farmacia.

Esto implica:

- base de datos separada;
- sin claves foráneas cruzadas hacia farmacia;
- sin joins directos entre ambas bases;
- integración futura solo por aplicación o contratos explícitos.

## Qué busca resolver esta persistencia

Esta base debe responder con claridad:

- quién es el paciente;
- qué profesional atiende;
- qué usuario interno opera el sistema;
- cuándo fue citada una persona;
- cuándo fue atendida;
- qué indicaciones breves quedaron registradas;
- si la atención generó cobro y quién lo registró.

## Alcance de la base

### Incluye
- identidad operativa del paciente;
- identidad operativa del profesional;
- usuarios internos del sistema;
- roles internos básicos;
- citas con estados coherentes;
- atenciones vinculadas a paciente y profesional, con referencia opcional a cita;
- cobros asociados a atención;
- estructuras auxiliares necesarias para integridad y trazabilidad.

### No incluye
- catálogo de productos;
- stock o disponibilidad de farmacia;
- reservas de farmacia;
- historia clínica extensa fuera del alcance de la V1;
- estructuras hospitalarias complejas.

## Filosofía de diseño

### Fidelidad operativa
La base debe representar un consultorio pequeño realista con varios profesionales, no un mono-doctor implícito ni un hospital grande.

### Separación de conceptos
Cada tabla debe responder a una responsabilidad clara: identidad, agenda, atención, cobro o seguridad interna.

### Evolución controlada
La base debe poder crecer desde una primera versión estudiable hacia una 3FN sólida, y luego hacia migraciones con Flyway sin rehacerse desde cero.

### Utilidad pedagógica
El diseño debe permitir estudiar dependencias funcionales, normalización, claves, relaciones, roles y trazabilidad con sentido real de negocio.

## Tensiones de diseño que esta visión asume

- no registrar demasiada clínica innecesaria en la V1;
- no empobrecer tanto el modelo que luego se vuelva frágil;
- modelar multidoctor sin caer en complejidad hospitalaria;
- mantener privacidad y simplicidad al mismo tiempo;
- sostener trazabilidad útil de usuarios, profesionales y cobros.

## Resultado esperado

La visión de persistencia del consultorio debe servir como brújula para todo el componente: documentación, diagrama conceptual, paso por 1FN, informe de normalización, SQL final, seeds, migraciones y decisiones futuras de mantenimiento, ahora ya bajo un modelo multidoctor explícito.

