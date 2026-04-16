# MANUAL_USUARIO_FARMACIA

## Propósito de este documento

Este documento sirve como **manual de uso orientado a cliente o validador funcional** de la parte visible de farmacia. No explica clases, funciones ni detalles internos del código. Explica **qué debería poder hacer una persona al usar la interfaz pública** y qué cosas no conviene evaluar todavía como si fueran producto final cerrado.

La referencia principal de este manual es la combinación de:

- documentación de `storefront-farmacia-angular`;
- documentación de `backend-farmacia`;
- rutas, componentes y flujos realmente sembrados en el storefront.

## Qué es la farmacia dentro del proyecto

La farmacia, en esta versión, se presenta como una **vitrina pública web**. Su objetivo no es administrar inventario desde la interfaz pública, sino permitir que un visitante:

- descubra la marca;
- explore el catálogo;
- busque productos;
- filtre por categoría;
- abra el detalle de un producto;
- vea disponibilidad pública;
- y vuelva a navegar sin perder demasiado contexto.

## Qué interfaz debería existir

Al abrir el storefront, la experiencia esperada es esta:

1. **Home** como punto de entrada.
2. **Catálogo** como lista navegable de productos públicos.
3. **Detalle de producto** para ver información más específica.
4. **Navbar pública** con búsqueda y navegación principal.
5. **Footer** y estructura pública consistente.
6. **Página 404** para rutas inválidas.

## Flujo principal que debería poder probarse

### Caso 1. Entrar a la página principal

Al abrir la farmacia, el usuario debería poder:

- ver una portada sobria con identidad de farmacia;
- entender que existe un catálogo público;
- usar un botón o enlace para empezar a explorar;
- ver categorías destacadas o accesos rápidos si están disponibles.

### Caso 2. Entrar al catálogo y recorrer productos

Desde el catálogo, el usuario debería poder:

- ver una grilla o lista pública de productos;
- leer nombre del producto;
- ver categoría cuando aplique;
- ver disponibilidad pública;
- abrir el detalle de un producto concreto.

### Caso 3. Buscar un producto por texto

La búsqueda debería permitir algo simple y entendible:

- escribir un término en la barra de búsqueda;
- ejecutar la búsqueda desde la navbar o desde el catálogo;
- ver resultados filtrados por ese término;
- limpiar la búsqueda si ya no se quiere usar.

### Caso 4. Filtrar por categoría

El usuario debería poder:

- entrar al catálogo con una categoría concreta;
- reconocer qué filtro está activo;
- quitar ese filtro sin recargar manualmente toda la aplicación;
- volver a explorar otros productos.

### Caso 5. Ordenar o cambiar tamaño de página

Si la pantalla lo muestra, el usuario debería poder:

- cambiar orden del listado;
- cambiar cantidad visible por página;
- moverse entre páginas sin perder el filtro activo.

### Caso 6. Ver un detalle de producto

En el detalle del producto, el usuario debería poder:

- ver el nombre del producto;
- ver categoría y disponibilidad;
- ver imagen si existe recurso asociado;
- ver una descripción o información pública suficiente;
- volver al catálogo;
- seguir explorando productos relacionados si esa sección aparece.

## Casos de uso concretos para probar como usuario

### Caso de uso A. “Solo quiero mirar qué venden”

1. Entrar a la home.
2. Ir al catálogo.
3. Avanzar por varias páginas.
4. Abrir dos o tres productos.
5. Volver al catálogo sin perder demasiado el hilo.

Resultado esperado: la navegación se siente clara y no rompe el contexto de exploración.

### Caso de uso B. “Busco un producto específico”

1. Escribir un término en la búsqueda.
2. Entrar al catálogo filtrado.
3. Revisar los resultados.
4. Abrir el detalle del más relevante.
5. Volver al catálogo filtrado.

Resultado esperado: la búsqueda se refleja en la interfaz y el retorno no lleva a una página completamente distinta o vacía.

### Caso de uso C. “Quiero ver solo una categoría”

1. Entrar a una categoría desde la home o desde un card.
2. Confirmar que la categoría está activa.
3. Quitar el filtro desde la propia UI.
4. Seguir explorando el resto del catálogo.

Resultado esperado: el filtro es visible y reversible.

## Señales de que la versión está funcionando razonablemente bien

La versión se puede considerar funcionalmente razonable si se cumple lo siguiente:

- la home no se siente muda;
- la navbar sirve realmente para navegar y buscar;
- el catálogo responde a búsqueda, filtros y paginación;
- el detalle de producto abre correctamente;
- la disponibilidad pública se entiende;
- el retorno al catálogo conserva contexto útil;
- si algo falla, la interfaz muestra estado vacío o error sin romperse por completo.

## Cosas que no conviene exigirle todavía a esta versión

Esta versión **no debe evaluarse todavía** como si fuera una tienda online completa con:

- carrito de compras;
- pagos;
- login de clientes;
- historial de pedidos;
- recomendaciones complejas por IA;
- gestión administrativa desde la parte pública.

Tampoco conviene exigir branding final, porque las imágenes y recursos visuales definitivos todavía pueden estar pendientes.

## Checklist corto para revisión manual

- [ ] Home abre y se entiende.
- [ ] Navbar navega y permite buscar.
- [ ] Catálogo lista productos públicos.
- [ ] Los filtros visibles pueden quitarse.
- [ ] La paginación no rompe el contexto.
- [ ] El detalle abre correctamente.
- [ ] El usuario puede volver al catálogo sin perderse.
- [ ] Estados vacíos o errores no destruyen la experiencia.

## Criterio final

La farmacia debe sentirse como una **vitrina pública clara, sobria y útil**. Si el usuario entiende rápidamente cómo entrar, buscar, filtrar, abrir un producto y volver a explorar, entonces la interfaz ya está cumpliendo bien su propósito principal en esta etapa.
