# Reportes - backend-consultorio

## Postura adoptada

El módulo de reportes existe y es parte real del backend, pero en una versión deliberadamente sobria.

La meta actual no es construir un motor documental complejo, sino dejar:

- contratos claros;
- obtención de datos consistente;
- selección de generador por tipo;
- salida homogénea y estudiable.

## Alcance de esta etapa

- endpoint versionado para generar reportes;
- servicio de aplicación que resuelve datos y generador;
- generadores simples para PDF, DOCX y XLSX;
- templates HTML básicos sin placeholders rotos.

## Qué no debe asumirse todavía

- no es un motor de plantillas rico;
- no es una capa final de diseño documental;
- no debe venderse como sistema de reporting avanzado.

## Criterio práctico

El módulo ya es suficientemente real para estudio y V1. Si crece después, el crecimiento debe priorizar robustez y mantenibilidad antes que complejidad visual.
