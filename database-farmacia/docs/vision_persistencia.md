# Visión de persistencia

## Propósito

Definir la intención general de la base de datos de farmacia y el papel que cumple dentro del sistema híbrido consultorio + farmacia.

## Papel del componente dentro del sistema

`database-farmacia` es el componente responsable de conservar el estado persistente del subdominio farmacia. Su función no es solo guardar datos, sino sostener integridad, trazabilidad mínima y evolución ordenada del contexto comercial y operativo de la farmacia.

Debe permitir que el backend de farmacia pueda operar con claridad sobre:

- productos;
- catálogo visible;
- disponibilidad operativa;
- estado del producto;
- reservas futuras si la V1.1 las incorpora.

## Principio estructural principal

La persistencia de farmacia es independiente de la persistencia del consultorio.

Esto implica:

- base de datos separada;
- sin claves foráneas cruzadas hacia consultorio;
- sin joins directos entre ambas bases;
- integración futura solo por aplicación o contratos explícitos.

## Qué busca resolver esta persistencia

En una farmacia pequeña, la base debe ayudar a representar con orden:

- qué productos existen;
- cuáles están activos o inactivos;
- cuáles son visibles al público;
- qué disponibilidad operativa tienen;
- cómo se reflejan cambios relevantes del catálogo.

La persistencia debe ser suficientemente clara como para soportar operación realista y suficientemente didáctica como para estudiar diseño relacional.

## Alcance de la base

### Incluye
- producto;
- categoría simple o clasificación básica;
- estado del producto;
- visibilidad pública;
- disponibilidad operativa;
- posible reserva en evolución V1.1;
- estructuras auxiliares necesarias para integridad y trazabilidad.

### No incluye
- pacientes;
- citas;
- atenciones;
- cobros de consultorio;
- historia clínica;
- estructuras comerciales empresariales excesivas para la V1.

## Filosofía de diseño

### Fidelidad operativa
La base debe representar hechos reales de la farmacia, no solo un catálogo bonito en papel.

### Separación de conceptos
Cada tabla debe responder a una responsabilidad clara y evitar mezclar catálogo, disponibilidad, reserva y auditoría sin criterio.

### Evolución controlada
La base debe poder crecer desde una primera versión estudiable hacia un esquema 3FN sólido, y luego hacia migraciones con Flyway sin rehacerse desde cero.

### Utilidad pedagógica
El diseño debe permitir estudiar dependencias funcionales, normalización, claves, relaciones e integridad con sentido real de negocio.

## Tensiones de diseño que esta visión asume

- no modelar la farmacia como simple escaparate sin operación interna;
- no sobrediseñar como si fuera una cadena empresarial compleja;
- mantener separadas publicación y operación interna;
- dejar preparada una evolución razonable hacia reservas sin meterla forzosamente en la V1.0.

## Resultado esperado

La visión de persistencia de farmacia debe servir como brújula para todo el componente: documentación, diagrama conceptual, paso por 1FN, informe de normalización, SQL final, seeds, migraciones y decisiones futuras de mantenimiento.

