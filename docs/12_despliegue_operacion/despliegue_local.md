# Despliegue local

## Propósito

Describir cómo levantar el sistema completo en una máquina local de forma ordenada, suficiente para desarrollo, prueba y demostración controlada.

## Principio general

La V1 debe poder ejecutarse localmente sin infraestructura exagerada, pero con separación lógica clara entre componentes. El despliegue local no debe degradar la arquitectura conceptual del proyecto.

## Componentes a levantar

En escenario local, el sistema se compone de:

- `database-consultorio`;
- `database-farmacia`;
- `backend-consultorio`;
- `backend-farmacia`;
- `desktop-consultorio-javafx`;
- `storefront-farmacia-angular`.

## Orden lógico de arranque

1. Preparar variables de entorno y archivos de configuración.
2. Levantar `database-consultorio`.
3. Levantar `database-farmacia`.
4. Aplicar migraciones y seeds de consultorio.
5. Aplicar migraciones y seeds de farmacia.
6. Levantar backend consultorio.
7. Levantar backend farmacia.
8. Ejecutar cliente desktop consultorio.
9. Ejecutar storefront web farmacia.

## Supuestos razonables de entorno local

- la máquina de desarrollo tiene Java, Node y PostgreSQL instalados o encapsulados según la estrategia elegida;
- cada componente conoce su puerto y URL objetivo;
- cada backend puede conectarse solo a su propia base;
- existen datos demo para probar flujos principales.

## Criterios de despliegue local sano

### Separación por componente
Aunque todo corra en una sola máquina, cada componente debe arrancar con su propia configuración y responsabilidad.

### Reproducibilidad
Los pasos deben poder repetirse sin depender de intuición o ajustes manuales ocultos.

### Diagnóstico rápido
Si un componente falla, debe ser posible identificarlo sin revisar todo el proyecto a ciegas.

## Dependencias mínimas a documentar

- versión de Java para backends y desktop;
- versión de Node y gestor de paquetes para storefront;
- versión de PostgreSQL o estrategia concreta para ambas bases;
- puertos por componente;
- variables críticas de conexión y seguridad para cada backend;
- ubicación de migraciones y seeds por subdominio.

## Errores comunes a prevenir

- arrancar backends sin sus bases disponibles;
- cruzar configuración de consultorio y farmacia;
- ejecutar clientes sin contratos backend listos;
- trabajar con bases sin migraciones actualizadas;
- depender de valores hardcodeados dispersos;
- asumir que una sola base cubre ambos contextos.

## Validación mínima del despliegue local

Se considera correcto cuando:

- ambos backends arrancan sin errores críticos;
- `database-consultorio` responde y contiene estructura válida;
- `database-farmacia` responde y contiene estructura válida;
- el desktop puede operar flujos básicos del consultorio;
- el storefront puede consultar el catálogo visible de farmacia.

## Resultado esperado

El despliegue local debe convertir el proyecto en una solución ejecutable y entendible, con fricción razonable y suficiente disciplina como para servir tanto a estudio como a demostración, respetando además la separación de persistencia entre subdominios.