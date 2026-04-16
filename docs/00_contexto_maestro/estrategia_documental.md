# Estrategia documental

## Propósito

Este documento define cómo se organiza, produce y mantiene la documentación del proyecto. Su objetivo no es llenar carpetas por formalidad, sino convertir la documentación en una herramienta real de diseño, coherencia, trazabilidad y aprendizaje.

## Principio rector

En este proyecto, la documentación forma parte del sistema de trabajo. No se escribe al final como adorno, sino que guía decisiones de dominio, arquitectura, base de datos, backend, frontend y despliegue.

Por tanto, documentar aquí significa:

- pensar antes de implementar;
- reducir contradicciones entre componentes;
- conservar el razonamiento técnico;
- permitir iteraciones con IA sin perder contexto;
- convertir el proyecto en material serio de estudio y portafolio.

## Niveles documentales del repositorio

### 1. Documentación maestra en `docs/`
La carpeta `docs/` contiene la visión transversal del proyecto. Aquí viven el contexto maestro, el dominio de alto nivel, la arquitectura general, seguridad, trazabilidad, despliegue, UX, ADRs, plantillas y checklists.

Su función es responder preguntas del tipo:

- qué es el producto;
- qué alcance tiene;
- qué principios lo rigen;
- cómo se divide el sistema;
- qué límites y responsabilidades existen;
- cómo debe pensarse el proyecto de forma global.

### 2. Documentación por componente
Cada gran componente técnico tendrá su propia carpeta `docs` local, por ejemplo en base de datos, backends o frontends. Esa documentación aterriza el diseño transversal en decisiones más cercanas a implementación.

Su función es responder preguntas del tipo:

- cómo se implementa este componente;
- qué contratos expone;
- qué reglas locales aplica;
- qué decisiones técnicas lo afectan;
- qué relación guarda con otros componentes.

### 3. Carpeta `llenados`
La carpeta `llenados` representa una versión trabajada o iterada del documento base. Su utilidad principal es conservar avance concreto sin destruir el andamiaje documental original.

En términos prácticos:

- el archivo base marca el tema o el contenedor;
- `llenados` guarda la versión desarrollada que se va produciendo;
- sirve como historial operativo mientras el proyecto toma forma.

## Regla de jerarquía documental

Cuando dos documentos parezcan superponerse, se debe usar la siguiente jerarquía:

1. contexto maestro;
2. documento temático global;
3. documento técnico local del componente;
4. versión concreta en `llenados`.

Esto no significa que el documento más alto siempre tenga más detalle. Significa que el nivel superior define el marco y el inferior lo aterriza.

## Criterios de redacción

### Claridad antes que adorno
Los documentos deben escribirse para orientar decisiones, no para sonar rebuscados.

### Consistencia terminológica
Si se fija un término, una frontera o una convención, debe mantenerse en todo el repositorio.

### Modularidad documental
Cada documento debe tener un foco claro. Se evita mezclar demasiados temas en un solo archivo.

### Profesionalismo práctico
La documentación debe ser suficientemente formal como para mostrar oficio, pero suficientemente concreta como para servir durante la implementación.

## Qué debe pasar al llenar documentos

Cuando se complete un documento, deben respetarse estas reglas:

- no contradecir el contexto maestro;
- no introducir complejidad incompatible con la V1;
- no duplicar innecesariamente contenido ya fijado en otro nivel;
- explicitar decisiones que afecten estructura, reglas o contratos;
- escribir con orientación a uso real del proyecto.

## Relación con el trabajo asistido por IA

Este proyecto se construirá parcialmente con apoyo de IA. Por eso la documentación debe servir también como memoria estructurada del sistema. Una buena estrategia documental reduce improvisación, mantiene continuidad entre sesiones y permite generar contenido técnico con menos riesgo de incoherencia.

## Resultado esperado

La estrategia documental debe permitir que el repositorio crezca de forma ordenada, que cada componente se entienda dentro de un marco mayor y que el proyecto pueda explicarse, evolucionarse y defenderse como una construcción profesional y no como una colección de archivos sueltos.