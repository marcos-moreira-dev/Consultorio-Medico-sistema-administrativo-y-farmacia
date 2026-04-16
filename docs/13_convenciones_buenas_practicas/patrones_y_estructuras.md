# Patrones y estructuras

## Propósito

Fijar patrones y estructuras recomendadas para que el proyecto mantenga coherencia interna al implementarse por componentes y por capas.

## Principio general

Los patrones no se adoptan para presumir complejidad, sino para ordenar responsabilidades y reducir mezcla de lógica. En este proyecto se priorizan patrones que ayuden a sostener una V1 clara, modular y evolutiva.

## Patrones base recomendados

### 1. Separación por capas
Cada backend debe organizarse en torno a capas equivalentes a:

- entrada o interfaz;
- aplicación o casos de uso;
- dominio;
- infraestructura.

### 2. Casos de uso explícitos
Las operaciones importantes del negocio conviene representarlas como unidades claras, por ejemplo registrar paciente, agendar cita, publicar producto o ajustar disponibilidad.

### 3. DTOs explícitos
La comunicación externa debe pasar por DTOs de request y response, separados de entidades de persistencia.

### 4. Repositorios como abstracción de persistencia
La lógica de negocio no debería depender directamente de detalles de acceso a base de datos.

### 5. Mappers o conversión explícita
La transformación entre entidades, DTOs y vistas de respuesta debe ser visible y controlada.

## Estructuras recomendadas

### Para backends
Estructura orientada por contexto y por capa.

### Para clientes
Separar pantallas, lógica de presentación, modelos de interfaz y servicios de acceso a backend cuando el stack lo permita.

### Para base de datos
Separar migraciones, seeds, documentación y modelos de referencia.

## Qué evitar

- lógica de negocio metida en controladores;
- acceso directo a base de datos desde clientes;
- utilitarios genéricos que terminan concentrando lógica dispersa;
- patrones complejos sin necesidad real para esta V1.

## Resultado esperado

Los patrones y estructuras elegidos deben ayudarte a construir el sistema con más orden y menos improvisación, manteniendo una arquitectura que luego puedas explicar, estudiar y extender con criterio.