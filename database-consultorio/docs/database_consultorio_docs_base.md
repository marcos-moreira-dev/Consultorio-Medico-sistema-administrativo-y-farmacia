# Base de trabajo: database-consultorio/docs

## Lo que queda fijado para este componente

Este componente ya no se va a documentar como una base “genérica” de apoyo, sino como una **base de datos propia del subdominio consultorio**, separada físicamente de farmacia. Por tanto, toda la documentación de `database-consultorio/docs` debe asumir estas reglas:

- pertenece solo al subdominio consultorio;
- no comparte tablas con farmacia;
- no tiene claves foráneas cruzadas hacia farmacia;
- si en el futuro necesita relacionarse con farmacia, eso será por aplicación/API y no por JOIN directo entre bases.

## Ruta de construcción que vamos a seguir

La documentación y el estudio de esta base deben construirse en este orden mental:

### 1. Dominio aterrizado del consultorio
Primero se define con claridad qué entidades reales existen en el consultorio, qué atributos tienen, qué relaciones guardan y qué reglas de persistencia les afectan.

### 2. Modelo conceptual
Aquí se piensa el diagrama: entidades, atributos, relaciones y cardinalidades. Todavía no es SQL, pero ya no es “idea vaga”. Aquí se decide qué existe realmente en el negocio.

### 3. Esquema en 1FN
Luego se construye una primera versión tabular que ya sea estudiable y operativa, aunque todavía no sea la versión final normalizada.

### 4. Informe de normalización
Después se redacta un markdown que explique cómo se pasa de esa versión inicial hacia una estructura mejor, justificando dependencias funcionales, separación de conceptos y camino hacia 3FN.

### 5. SQL final en 3FN
Aquí ya se define el esquema relacional final del consultorio en PostgreSQL, con claves, restricciones, relaciones e integridad.

### 6. Seeds en 3FN
Por último, se crean datos de ejemplo coherentes con el dominio y útiles para estudiar, probar y demostrar el sistema.

## Lo que espero documentar dentro de `database-consultorio/docs`

Este bloque deberá explicar, como mínimo:

- visión de persistencia del consultorio;
- modelo relacional del subdominio;
- reglas de persistencia;
- restricciones de integridad;
- estrategia de claves y relaciones;
- diccionario de datos;
- migraciones y seeds;
- índices y rendimiento;
- auditoría y trazabilidad a nivel BD;
- backup y recuperación;
- convenciones de nombres.

## Criterio pedagógico

Como tú quieres estudiar luego el SQL, el resultado no debe quedarse en puro formalismo. Debe permitir:

- entender por qué existe cada tabla;
- entender por qué una relación se modela de cierta manera;
- ver cómo se pasa de una idea de negocio a una estructura relacional;
- leer SQL con comentarios suficientemente útiles para PostgreSQL.

## Regla importante de alcance

Esta base debe quedarse en el consultorio. Por eso, aquí sí entrarían cosas como:

- paciente;
- cita;
- atención;
- cobro;
- quizá indicación o receta breve, si se modela aparte.

Y aquí no deben entrar cosas como:

- producto;
- stock;
- disponibilidad de farmacia;
- reservas de farmacia.

## Resultado esperado

Cuando terminemos este componente, deberías tener no solo markdowns profesionales del bloque `database-consultorio/docs`, sino también una ruta de estudio completa para entender cómo nace y se formaliza una base de datos realista del consultorio, desde el modelo conceptual hasta el SQL final y sus seeds.