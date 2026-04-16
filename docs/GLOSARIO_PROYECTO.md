# GLOSARIO_PROYECTO

Este glosario existe para que el vocabulario del repositorio no quede como una “jerga flotando”.

No todos los términos de aquí son palabras académicas estrictas. Algunas se usan de forma práctica dentro del proyecto para describir decisiones de diseño, etapas de trabajo o piezas de arquitectura.

## Términos generales del repo

### Backend
Parte del sistema que concentra reglas de negocio, seguridad, persistencia, validaciones y exposición de endpoints.

### Frontend
Parte del sistema que representa la interfaz con la que interactúa el usuario. En farmacia, el frontend actual es el `storefront-farmacia-angular`.

### Storefront
Frontend público orientado a mostrar un catálogo o una vitrina digital. En este proyecto no significa “panel administrativo”, sino la cara pública de farmacia.

### Superficie pública
Conjunto de rutas, pantallas y datos que pueden exponerse a visitantes sin privilegios administrativos.

### Superficie administrativa
Conjunto de rutas, pantallas y operaciones reservadas a personal autenticado o a operadores internos.

### Subdominio
Parte del problema de negocio separada por responsabilidad. Aquí, `consultorio` y `farmacia` se tratan como subdominios distintos.

### Dominio
Área de negocio que el software intenta modelar. En este proyecto el dominio general es la operación híbrida de consultorio y farmacia.

### Contexto
Información necesaria para entender una pieza del proyecto antes de modificarla. Puede ser documental, arquitectónica, operativa o de negocio.

### Contexto maestro
Documento grande que intenta fijar la visión general del proyecto para que el resto de documentos y decisiones no nazcan desalineados.

### Contrato
Forma esperada de comunicación entre dos capas o componentes. Por ejemplo, el contrato entre backend y frontend suele expresarse mediante rutas, parámetros, DTOs y respuestas JSON.

### DTO (Data Transfer Object)
Objeto de transferencia de datos. Se usa para describir la forma de entrada o salida de datos entre capas, sin mezclarlo directamente con entidades de persistencia.

### Mapper
Pieza que transforma un tipo de dato en otro. Por ejemplo, de entidad a DTO, o de respuesta HTTP a modelo de UI.

### Adapter
Capa que adapta estructuras externas a la forma interna que conviene usar en el proyecto.

### Facade
Objeto o servicio que simplifica el acceso a una parte más compleja del sistema. En frontend suele agrupar consumo de API, estado y transformación para que el componente quede más limpio.

### Store
Pieza encargada de mantener estado de UI o estado derivado del flujo de interacción.

### State / estado
Información viva de una pantalla o flujo. Ejemplo: cargando, error, resultados, filtros actuales, detalle cargado.

### Nullability / nulabilidad
Tema de diseño relacionado con qué campos pueden venir ausentes, nulos o vacíos. Es importante porque afecta contratos, validaciones y UI.

## Términos de implementación y arquitectura

### Scaffolding
Estructura inicial del proyecto antes de que toda la lógica esté implementada. Incluye carpetas, archivos base, convenciones y esqueletos útiles.

### Shell de la app
Esqueleto funcional de la aplicación. Suele incluir layout principal, navegación, router y la zona donde se inyectan las páginas.

### Layout
Estructura visual base de una pantalla o de una familia de pantallas. Por ejemplo, navbar, footer, contenedor principal y áreas comunes.

### Placeholder
Elemento temporal que existe para reservar espacio o intención, pero que todavía no representa implementación final.

### Hardening
Pasada de limpieza y robustecimiento. No implica expandir alcance, sino endurecer el proyecto para que falle menos y sea más confiable.

### Orquestación
Coordinación de varios componentes para que arranquen y trabajen juntos. Por ejemplo, levantar PostgreSQL, backend y frontend con un mismo comando.

### Healthcheck
Mecanismo para verificar si un servicio está vivo y respondiendo como se espera.

### Traceability / trazabilidad
Capacidad de seguir qué pasó, en qué orden pasó y dónde falló. En práctica incluye logs, identificadores de petición, contexto de navegación y mensajes útiles de error.

### Handoff
Entrega de contexto entre una etapa y otra, o entre una IA y otra, o entre una persona y otra. Un documento de handoff intenta evitar que el siguiente actor arranque a ciegas.

### Onboarding
Conjunto de archivos y decisiones que permiten entender, instalar, arrancar y modificar el proyecto sin improvisar desde cero.

### Bootstrap / bootstrapping
Proceso de arranque inicial de una aplicación o de una parte del sistema. También se usa para hablar de la fase donde se siembra la base mínima para que el proyecto empiece a funcionar.

### Workspace
Estructura general de un proyecto o monorepo en una herramienta determinada. En Angular, por ejemplo, es el espacio definido por `angular.json` y los archivos asociados.

### Dockerizar
Preparar un componente para correr dentro de un contenedor Docker con configuración repetible.

## Términos de UI y frontend

### Slot
Espacio reservado dentro de un componente o layout para insertar contenido. A veces se usa de forma general para hablar de “huecos previstos” donde luego irá un elemento, incluso si la tecnología concreta no usa slots formales.

### Hero
Bloque principal de entrada en una página, normalmente en la parte superior, usado para presentar la propuesta principal del sitio o de la sección.

### Card
Componente visual que agrupa información relacionada dentro de un bloque acotado.

### Chip
Etiqueta pequeña y compacta, normalmente usada para representar filtros, categorías, estados o acciones rápidas.

### Empty state
Estado visual mostrado cuando no hay resultados, no hay datos o no hay contenido relevante para enseñar.

### Skeleton
Representación visual temporal de carga que imita la estructura del contenido mientras llegan los datos reales.

### Responsive
Capacidad de la interfaz para adaptarse con criterio a distintos tamaños de pantalla.

### Breadcrumb
Ruta de navegación que muestra dónde está el usuario dentro de la jerarquía del sitio y le permite retroceder con contexto.

### Polish
Pulido fino. No significa reestructurar la app, sino mejorar detalles visuales, microcopys, espaciados, transiciones y consistencia.

## Términos de proceso y estrategia

### MVP (Minimum Viable Product)
Versión mínima viable de un producto que ya permite validar utilidad, flujo y propuesta, aunque todavía no esté completa.

### Borrador fuerte
Expresión informal usada en este proyecto para hablar de una base que todavía no está terminada, pero sí está lo bastante estructurada como para estudiarla, ejecutarla y rematarla.

### Escrito en piedra
Decisión que se considera estable dentro del repositorio y que no debería moverse sin una razón fuerte.

### Decisión abierta
Tema que todavía no está cerrado y que puede requerir validación posterior o trabajo adicional.

### Remate
Ajuste final o toque de cierre que se hace sobre una base ya bastante armada.

### Tramo final
Fase donde la estructura gruesa ya existe y el trabajo se concentra en validación real, pulido y correcciones finas.

## Cómo usar este glosario

Conviene leer este archivo cuando aparezca una palabra que suene técnica, extraña o demasiado “de IA”.

Si el proyecto sigue creciendo, este glosario también debe crecer. La idea no es volverlo enciclopédico, sino mantenerlo útil para estudiar el repositorio sin depender de recordar una conversación previa.
