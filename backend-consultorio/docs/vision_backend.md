# Visión backend

## Propósito

Definir la identidad técnica del backend del consultorio y el papel que cumple dentro del sistema completo.

## Qué tipo de backend es este

`backend-consultorio` es un backend **privado**, **multidoctor**, **administrativo**, **modular** y **orientado a casos de uso del negocio**.

No es una API pública de marketing. No es un backend de uso mixto con farmacia. No es una capa delgada que solo retransmite datos a la base. Debe ser el núcleo de coordinación del subdominio consultorio.

## Función principal del componente

Este backend existe para:

- autenticar usuarios internos;
- autorizar acciones según rol;
- coordinar la gestión de profesionales, pacientes, citas, atenciones y cobros;
- aplicar reglas del dominio;
- exponer contratos REST claros y versionados;
- generar reportes en formatos útiles para operación;
- registrar trazabilidad suficiente;
- proteger la información sensible del consultorio.

## Frontera del componente

### Lo que sí le corresponde
- exponer endpoints privados del consultorio;
- consumir exclusivamente `database-consultorio`;
- aplicar reglas de negocio y validaciones fuertes;
- construir DTOs de request y response;
- manejar seguridad y autorización;
- producir reportes operativos;
- sostener logging y trazabilidad técnica.

### Lo que no le corresponde
- servir funcionalidades públicas de farmacia;
- consumir `database-farmacia`;
- delegar reglas críticas al frontend;
- devolver entidades de persistencia directamente;
- comportarse como motor de procesos distribuidos complejos si no hay necesidad real.

## Naturaleza del dominio que atiende

El backend opera un consultorio con múltiples profesionales y usuarios internos. Eso significa que debe modelar con suficiente fidelidad:

- agenda por profesional;
- asociación entre usuarios internos y roles;
- asociación entre usuarios y profesionales cuando corresponde;
- atenciones con o sin cita previa;
- cobros asociados a atenciones;
- generación de documentos útiles para doctor y operación.

## Decisiones de diseño ya fijadas

### Monolito modular
Se adopta un monolito modular porque es suficientemente clásico, estudiable y robusto para esta V1, sin introducir complejidad innecesaria de microservicios.

### REST puro
La API será REST clásica, con versionado, filtros, paginación y ordenamiento cuando el recurso lo requiera.

### Backend como fuente de verdad
Las reglas operativas importantes deben vivir aquí, no solo en frontend ni en base de datos.

### Seguridad interna explícita
El backend debe distinguir y proteger claramente acciones según rol.

### Reportes integrados al backend
PDF, DOCX y XLSX deben entenderse como responsabilidad real del backend, no como un parche externo.

## Módulos que definen su identidad

Este backend debe pensarse alrededor de estos módulos principales:

- auth
- usuarios
- roles
- profesionales
- pacientes
- citas
- atenciones
- cobros
- reportes

## Usuario objetivo del backend

Este backend no está pensado para consumo abierto por terceros. Su usuario principal es el ecosistema interno del sistema:

- frontend o cliente del consultorio;
- operadores internos;
- profesionales clínicos;
- administración del consultorio.

## Tensión central que debe resolver

El backend debe equilibrar tres cosas al mismo tiempo:

- privacidad fuerte;
- claridad administrativa;
- suficiente riqueza funcional para que se vea como sistema real.

No debe hacerse ni tan pobre que parezca maqueta, ni tan complejo que se vuelva hospitalario o difícil de implementar con coherencia.

## Resultado esperado

La visión del backend debe dejar claro que este componente es el núcleo privado del consultorio, con responsabilidades concretas, arquitectura disciplinada y suficiente realismo como para servir de base profesional y de estudio.