# Riesgos y tradeoffs

## Propósito

Documentar los principales riesgos técnicos y los compromisos de diseño asumidos en el system design del proyecto, de modo que la arquitectura no parezca arbitraria ni ingenua.

## Principio general

Toda decisión técnica útil resuelve algo y sacrifica algo. Este documento explicita esos intercambios para que el sistema pueda defenderse con criterio y evolucionar sin autoengaño.

## Riesgo 1. Mezcla entre consultorio y farmacia

### Descripción
Al ser un proyecto híbrido, existe la tentación de mezclar datos, vocabulario o responsabilidades por comodidad de implementación.

### Consecuencia
- contaminación de modelos;
- riesgo de privacidad;
- APIs confusas;
- dificultad para mantener fronteras funcionales.

### Respuesta de diseño
Separación explícita de contextos, componentes, contratos y superficies.

## Riesgo 2. Sobrediseño para una V1

### Descripción
El proyecto puede caer en complejidad excesiva si intenta parecer una clínica grande o un ERP farmacéutico completo.

### Consecuencia
- pérdida de foco;
- implementación lenta;
- documentación inflada;
- menor valor pedagógico práctico.

### Respuesta de diseño
Modularidad pragmática, V1 amplia pero controlada, y V1.1 reservada para una evolución funcional concreta.

## Riesgo 3. Simplificación excesiva

### Descripción
El riesgo opuesto es hacer un dominio demasiado pobre, incapaz de representar operación real.

### Consecuencia
- bases de datos frágiles;
- reglas ingenuas;
- pantallas irreales;
- menor valor de portafolio.

### Respuesta de diseño
Modelado con fidelidad operativa suficiente, incluyendo estados, excepciones razonables y separación seria entre superficies.

## Riesgo 4. Exposición indebida de datos sensibles

### Descripción
La parte privada del consultorio podría quedar mal protegida si la separación depende solo de disciplina manual.

### Consecuencia
- vulneración conceptual de privacidad;
- mala práctica de diseño;
- pérdida de coherencia del proyecto.

### Respuesta de diseño
Protección por estructura: contratos separados, clientes separados, rutas separadas y control de acceso explícito.

## Riesgo 5. Dependencia excesiva de la UI

### Descripción
Si el frontend o el desktop asumen demasiada lógica crítica, el sistema pierde consistencia y se vuelve más difícil de mantener.

### Consecuencia
- validaciones duplicadas o divergentes;
- reglas repartidas;
- mayor fragilidad evolutiva.

### Respuesta de diseño
Mantener la lógica crítica y la validación fuerte en backend, dejando a los clientes como guías del flujo y capa de presentación.

## Riesgo 6. Demasiada infraestructura para una demo local

### Descripción
Intentar desplegar desde el inicio una topología compleja puede entorpecer aprendizaje y operatividad.

### Consecuencia
- mayor fricción técnica;
- más tiempo en infraestructura que en dominio;
- dificultad para demostrar el proyecto de forma sencilla.

### Respuesta de diseño
Separación lógica fuerte con despliegue físico inicial razonable y local.

## Tradeoff 1. Dos backends separados vs un solo backend unificado

### Opción elegida
Se favorece separación por contexto funcional.

### Lo que se gana
- claridad mental;
- menor mezcla de responsabilidades;
- mejor defensa arquitectónica;
- mayor coherencia entre superficies.

### Lo que se sacrifica
- algo más de estructura;
- necesidad de mantener contratos distintos;
- mayor disciplina documental.

## Tradeoff 2. Uniformidad de contratos vs simplicidad rápida

### Opción elegida
Se favorecen DTOs explícitos, respuestas uniformes y errores documentables.

### Lo que se gana
- profesionalismo;
- estabilidad;
- mejor aprendizaje;
- menor acoplamiento accidental.

### Lo que se sacrifica
- más trabajo inicial;
- más esfuerzo documental.

## Tradeoff 3. Despliegue simple vs sofisticación distribuida

### Opción elegida
Se prioriza despliegue lógico claro con ejecución local razonable.

### Lo que se gana
- facilidad de estudio;
- menor fricción;
- demostración más manejable.

### Lo que se sacrifica
- menor realismo de infraestructura avanzada;
- menos exposición temprana a operación distribuida compleja.

## Tradeoff 4. Fidelidad operativa vs complejidad clínica o empresarial

### Opción elegida
Se busca realismo administrativo suficiente, no completitud total del dominio salud ni del retail farmacéutico.

### Lo que se gana
- foco útil;
- mejor equilibrio entre estudio e implementación;
- portafolio más defendible.

### Lo que se sacrifica
- cobertura de casos avanzados;
- profundidad clínica o logística extensa.

## Resultado esperado

Este documento debe permitir justificar por qué el sistema está diseñado como está, qué riesgos se intentan controlar y qué concesiones se aceptan para que la V1 siga siendo seria, viable y pedagógicamente valiosa.

