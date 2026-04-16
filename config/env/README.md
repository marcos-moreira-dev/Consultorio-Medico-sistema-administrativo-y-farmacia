# Configuración global de variables de entorno

Esta carpeta reúne **plantillas globales** de variables de entorno del proyecto.

## Objetivo

Servir como referencia para ambientes `local`, `demo` y `prod`, sin subir secretos reales al repositorio.

## Qué sí debe vivir aquí

- ejemplos de variables compartidas;
- plantillas orientativas de entorno;
- nombres estables de variables que luego usarán los componentes.

## Qué no debe vivir aquí

- credenciales reales;
- tokens válidos;
- archivos `.env` privados del desarrollador;
- configuraciones improvisadas difíciles de rastrear.

## Regla práctica

Subir solo ejemplos. Los secretos reales se inyectan fuera del repo o se manejan en archivos locales no versionados.
