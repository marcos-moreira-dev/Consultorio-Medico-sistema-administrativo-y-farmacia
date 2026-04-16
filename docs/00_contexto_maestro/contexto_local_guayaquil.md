# Contexto local Guayaquil

## Propósito

Este documento fija las condiciones del entorno local que influyen en el diseño del producto. No se trata de describir toda la ciudad, sino de establecer un marco realista para un negocio pequeño de consultorio y farmacia en Guayaquil, de forma que el software no se diseñe como si operara en un contexto abstracto o idealizado.

## Tipo de entorno asumido

Se asume un entorno urbano de barrio o zona comercial pequeña, con atención directa, operación relativamente simple y un equipo reducido. La relación con el cliente o paciente es cercana, práctica y muchas veces apoyada en hábitos informales.

En este contexto, el software debe adaptarse a una operación que probablemente convive con:

- agendas manejadas de forma semiinformal;
- consulta rápida de disponibilidad de productos;
- baja tolerancia a pantallas confusas o lentas;
- dependencia de pocas personas clave para operar el negocio;
- necesidad de resolver rápido sin procesos corporativos complejos.

## Perfil probable de usuarios

### Personal de consultorio
Pueden ser personas con experiencia operativa, pero no necesariamente con alta alfabetización técnica. Necesitan registrar, buscar y confirmar información sin fricción.

### Personal de farmacia
Necesita consultar productos, disponibilidad y actualizar información básica de forma clara. La prioridad es rapidez operativa y legibilidad.

### Pacientes o clientes finales
No se debe asumir paciencia para navegar interfaces complejas. La capa pública debe ser simple, orientada a búsqueda y consulta.

## Restricciones realistas del contexto

### Infraestructura tecnológica desigual
No se debe asumir equipamiento sofisticado, redes impecables ni procesos completamente digitalizados. El sistema debe sentirse robusto incluso en entornos modestos.

### Necesidad de claridad visual
El diseño debe priorizar contraste, legibilidad, densidad razonable de información y recorridos simples. La sofisticación visual no puede sacrificar entendimiento.

### Operación centrada en tiempo y atención
En un negocio pequeño, cada clic extra molesta más que en una gran organización. Las pantallas deben ayudar a resolver tareas concretas, no a impresionar.

### Sensibilidad de los datos
Aunque el negocio sea pequeño, sigue existiendo una diferencia fuerte entre datos de salud y datos comerciales. El contexto local no justifica relajar esta separación.

## Implicaciones para el producto

### Implicación 1: UX sobria
El producto debe comunicar orden, seriedad y facilidad de uso. Menos adornos y más claridad funcional.

### Implicación 2: dominio realista, no académico
Los procesos deben modelarse como suceden en un negocio pequeño real, con excepciones plausibles, decisiones rápidas y estados administrativos útiles.

### Implicación 3: exposición pública controlada
La farmacia puede tener visibilidad pública o semipública para catálogo y disponibilidad, pero la parte clínica debe permanecer protegida.

### Implicación 4: crecimiento gradual
El sistema debe poder crecer por capas sin exigir una transformación total del negocio ni del software.

## Hipótesis operativas de trabajo

Para efectos del proyecto se asumen las siguientes hipótesis:

- el consultorio atiende un volumen manejable de pacientes;
- la farmacia maneja un catálogo acotado o mediano;
- el equipo operativo es pequeño;
- el valor del software está en ordenar y sostener la operación, no en automatizar una gran empresa;
- la solución debe ser demostrable en local y entendible por un cliente real.

## Resultado esperado

El producto debe sentirse compatible con el entorno local de Guayaquil y con la lógica de un negocio pequeño de salud y venta de medicamentos, evitando decisiones de diseño que solo tendrían sentido en contextos corporativos, hospitalarios o tecnológicamente más sofisticados.