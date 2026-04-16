# Testing frontend

## Propósito

Definir qué debe probarse en `storefront-farmacia-angular`, con qué criterio y con qué nivel de profundidad razonable para una V1 pública, comercial y orientada a estudio.

## Principio general

El testing del storefront no debe ser decorativo ni limitarse a comprobar que “la página abre”. Debe ayudar a verificar que el frontend realmente sostiene:

- navegación pública coherente;
- catálogo usable;
- cards bien renderizadas;
- integración razonable con backend;
- filtros y búsqueda;
- manejo correcto de loading, vacío y error;
- consistencia de componentes reutilizables.

## Base técnica adoptada en esta tanda

El onboarding deja configurado el runner base con **Karma + Jasmine**, alineado con la semilla de Angular `20.3.x` escogida para este proyecto.

### Archivos clave
- `karma.conf.js`
- `tsconfig.spec.json`
- target `test` en `angular.json`

### Comando base
```bash
npm run test
```

## Qué se busca con el testing en esta V1

### 1. Proteger flujos esenciales
Especialmente home, catálogo, detalle y búsqueda.

### 2. Evitar regresiones obvias
Que cambiar el hero o el grid no rompa navegación, filtros o render de producto.

### 3. Dar confianza a la arquitectura UI
Que layout, páginas y componentes colaboren como fueron diseñados.

### 4. Servir como estudio
Que el proyecto también enseñe qué se prueba y por qué se prueba en un frontend Angular público y comercial.

## Niveles de prueba recomendados

## 1. Unit tests

### Objetivo
Probar piezas aisladas que realmente contengan lógica útil.

### Cuándo sí aporta
- transformación de datos públicos;
- composición de filtros;
- mapeo de responses;
- utilidades de paginación;
- lógica de estado UI no trivial.

### Cuándo no aporta tanto
No hace falta llenar el proyecto de tests vacíos sobre markup sin comportamiento relevante.

## 2. Integration tests

### Objetivo
Verificar que varias piezas colaboren bien entre sí.

### Ejemplos importantes
- página de catálogo + servicio de API + grid;
- filtros + paginación + estado visible;
- detalle de producto + carga de datos;
- hero + layout + navegación básica.

## 3. UI / interaction tests

### Objetivo
Comprobar que la interacción visible del usuario se comporte como se espera.

### Ejemplos importantes
- navegar desde home a catálogo;
- usar buscador;
- aplicar filtro de categoría;
- entrar al detalle;
- ver empty state;
- ver error state;
- volver al catálogo.

## Áreas que conviene probar sí o sí

## Home
- render correcto del hero;
- visibilidad del CTA principal;
- presencia del branding;
- navegación inicial hacia catálogo.

## Catálogo
- carga correcta de productos;
- búsqueda funcional;
- filtros visibles y operativos;
- paginación coherente;
- estado vacío claro;
- error de carga bien representado.

## Cards de producto
- render de imagen;
- uso de placeholder cuando falta imagen;
- nombre visible;
- badge de disponibilidad;
- CTA funcional.

## Detalle de producto
- carga correcta por ruta;
- render de imagen principal;
- información pública visible;
- estado de error si el producto no existe.

## Navegación
- header estable;
- rutas principales funcionando;
- links de cards hacia detalle;
- retorno razonable a exploración.

## Qué no debe pasar

- pruebas que dependen del orden casual de ejecución;
- solo snapshotting sin valor funcional;
- cero pruebas sobre filtros y búsqueda;
- cero pruebas sobre estados vacíos y errores;
- catálogo sin cobertura mínima de render y navegación.

## Resultado esperado

La estrategia de testing del storefront debe dejar una aplicación Angular razonablemente confiable, donde la experiencia pública esencial esté cubierta lo suficiente como para sostener evolución sin degradar el sitio.
