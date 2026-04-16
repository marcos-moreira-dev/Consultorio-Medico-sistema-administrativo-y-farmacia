# Scripts y arranque

## Propósito

Documentar la lógica de arranque del sistema y el papel que deben cumplir los scripts o comandos de soporte para reducir fricción operativa.

## Principio general

Los scripts no deben existir solo para automatizar por automatizar. Deben ayudar a volver repetible lo importante: levantar componentes, preparar entorno, ejecutar migraciones y facilitar una demo o prueba sin pasos ocultos.

## Objetivos de los scripts

### 1. Reducir repetición manual
Evitar secuencias largas de comandos propensas a error.

### 2. Hacer visible el orden correcto
Los scripts deben reforzar el flujo lógico de arranque del sistema.

### 3. Facilitar desarrollo y demo
No todos los scripts tienen el mismo propósito. Algunos apoyan desarrollo y otros una ejecución más controlada para presentación.

## Tipos de script esperables

### Scripts de preparación
Para verificar dependencias, preparar variables, inicializar estructura o ayudar con configuración local.

### Scripts de database consultorio
Para levantar `database-consultorio` si la estrategia lo permite, aplicar migraciones, cargar seeds o reinicializar entorno de prueba del consultorio.

### Scripts de database farmacia
Para levantar `database-farmacia` si la estrategia lo permite, aplicar migraciones, cargar seeds o reinicializar entorno de prueba de farmacia.

### Scripts de backends
Para arrancar backend consultorio y backend farmacia con sus perfiles adecuados.

### Scripts de clientes
Para lanzar el storefront y facilitar la ejecución del desktop según el stack concreto adoptado.

### Scripts de demo
Para preparar un entorno más limpio y repetible antes de mostrar el sistema.

## Orden lógico de arranque que los scripts deben respetar

1. configuración validada;
2. `database-consultorio` disponible;
3. `database-farmacia` disponible;
4. migraciones aplicadas por subdominio;
5. seeds listas por subdominio si corresponden;
6. backends levantados;
7. clientes iniciados.

## Reglas de diseño para scripts

### SCR-001. Un script debe tener propósito claro
No conviene crear scripts ambiguos o demasiado mágicos.

### SCR-002. El nombre del script debe insinuar su función
Debe ser fácil distinguir si prepara, arranca, resetea o demuestra.

### SCR-003. El script no debe ocultar dependencias críticas
Si necesita variables, servicios o herramientas externas, debe estar documentado.

### SCR-004. El sistema no debe depender de comandos solo conocidos por el autor
La lógica operativa debe quedar legible y transferible.

### SCR-005. Los scripts deben respetar separación por subdominio
No conviene mezclar migraciones, reset o seeds de consultorio y farmacia en un solo flujo opaco sin diferenciar responsabilidad.

## Riesgos a evitar

- scripts que cambian demasiado comportamiento sin explicarlo;
- scripts que funcionan solo en una máquina o shell específica sin advertencia;
- arranques que mezclan consultorio y farmacia sin orden;
- comandos destructivos mal diferenciados de los normales;
- scripts que hacen parecer que ambos backends usan una sola base.

## Resultado esperado

La estrategia de scripts y arranque debe convertir el sistema en algo más repetible, más claro y menos dependiente de memoria manual, sirviendo tanto a la práctica diaria como a las demostraciones controladas, sin romper la separación entre las dos bases de datos.