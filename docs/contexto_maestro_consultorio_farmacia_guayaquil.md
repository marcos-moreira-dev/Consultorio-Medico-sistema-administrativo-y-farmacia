# Contexto Maestro del Proyecto
## Consultorio + Farmacia Integrada en Guayaquil

## 1. Propósito de este lienzo
Este documento funciona como contexto maestro para alimentar otros chats de IA y generar documentación derivada en Markdown y PDF. No busca ser todavía la documentación final de arquitectura ni de implementación, sino la base fundacional desde la cual se podrán producir documentos más especializados, consistentes y trazables.

La meta es reducir al máximo la improvisación y permitir que la IA trabaje con una comprensión bastante completa del dominio, del producto, del contexto local, de la arquitectura esperada y de las buenas prácticas deseadas.

## 2. Intención general del proyecto
Este proyecto no debe interpretarse como una ocurrencia aislada ni como un simple ejercicio académico. Debe entenderse como una pieza estratégica dentro de una trayectoria profesional más amplia del autor, orientada a consolidar experiencia real en software administrativo, arquitectura de sistemas y documentación técnica reutilizable.

La motivación no es solamente construir un sistema para un posible cliente puntual, sino crear una base seria de producto, dominio y arquitectura que pueda evolucionar, reutilizarse, exhibirse en portafolio y servir como referencia para futuros desarrollos del mismo tipo.

En otras palabras, este proyecto tiene un doble valor. Por un lado, busca resolver un problema operativo plausible en un negocio pequeño de barrio. Por otro, busca convertirse en un activo formativo y profesional del autor, capaz de demostrar criterio técnico, comprensión de dominio, capacidad de modelado y madurez documental.

El proyecto consiste en modelar y diseñar un sistema híbrido para un negocio pequeño de barrio en Guayaquil, Ecuador, donde un mismo local opera como:

- consultorio médico pequeño
- farmacia integrada

La intención inicial no es construir un sistema gigantesco ni clínicamente complejo, sino un producto administrativo y operativo, demostrable, entendible para usuarios no técnicos y escalable por capas internas dentro de una sola V1.

Este sistema también tiene una segunda función estratégica:

- servir como activo reutilizable de portafolio
- servir como práctica seria de software administrativo
- servir como base adaptable para futuros clientes similares
- servir como experiencia profesional presentable ante empleadores

## 3. Contexto personal y estratégico del autor
El autor se encuentra en una etapa vital y profesional particularmente útil para incubar este tipo de proyectos. Todavía está en formación universitaria, pero ya cuenta con suficiente exposición a herramientas, stacks y proyectos previos como para comenzar a traducir práctica técnica en propuestas de software con sentido económico y operativo.

Este contexto es importante porque condiciona la estrategia general: no se busca actuar como una gran software factory ni como una consultora madura, sino como un desarrollador en proceso de consolidación que quiere construir sistemas pequeños, serios, vendibles y mantenibles, mientras fortalece su criterio técnico y comercial.

También influye una realidad práctica: el autor quiere depender menos de la improvisación y de la inspiración del momento. Por eso valora tanto la documentación, la trazabilidad de decisiones, la repetición de stacks conocidos y la creación de contexto abundante para la IA. La documentación aquí no es un adorno académico, sino una herramienta de apalancamiento productivo.

La estrategia principal no es diversificar lenguajes por ansiedad, sino repetir un tipo de producto de software y variar el dominio. La repetición del tipo de producto ayuda a consolidar arquitectura, convenciones, patrones y criterio técnico.

Esta etapa universitaria se entiende, además, como una zona de incubación profesional: un momento especialmente útil para construir portafolio serio, comprensión de dominios de negocio, práctica técnica real y una posible base futura de independencia parcial o total.

## 4. Contexto geográfico y social
El contexto local no es un detalle decorativo, sino una de las variables más importantes del proyecto. Diseñar software para un barrio de Guayaquil no es lo mismo que diseñarlo para una clínica privada grande, una startup de salud o una cadena farmacéutica con personal especializado y procesos estandarizados.

Aquí se asume una realidad mucho más terrenal: negocios pequeños que funcionan con trato directo, cierta informalidad operativa, adopción tecnológica desigual, recursos limitados y decisiones muy influenciadas por la confianza interpersonal, la claridad percibida del valor del sistema y la sencillez de uso.

Por eso el software no debe nacer desde una lógica corporativa pesada, sino desde una lógica de utilidad inmediata, progresiva y visualmente entendible. Debe sentirse como una herramienta que ayuda a ordenar el trabajo, no como una carga adicional o una imposición tecnológica difícil de justificar.

Más concretamente, el proyecto se piensa en el contexto urbano de Guayaquil, particularmente en un barrio tipo Huancavilca Norte:

- zona urbana de perfil socioeconómico medio
- entorno no extremadamente desarrollado pero tampoco marginal
- pequeños negocios que parecen estar a punto de profesionalizarse un poco más
- clientes potenciales con baja o media adopción tecnológica
- posibilidad de cortes eléctricos ocasionales
- necesidad de soluciones simples, prácticas y robustas
- operación más cercana al trato directo de barrio que a procesos corporativos

El sistema debe ser pensado para ser útil en ese contexto real, no para un entorno idealizado de clínica grande o cadena farmacéutica.

## 5. Perfil probable de usuarios
### Usuario principal del consultorio
- médico de mayor edad o mediana edad
- posible resistencia inicial a la tecnología
- baja tolerancia a lenguaje técnico
- necesidad de ver algo tangible y claro
- interés en orden, rapidez y facilidad, no en arquitectura de software

### Usuario secundario del consultorio
- asistente administrativa o persona de apoyo
- registra pacientes, agenda citas, ayuda en cobros o impresiones

### Usuario interno de farmacia
- persona encargada de revisar stock, productos y disponibilidad
- perfil operativo, no técnico

### Usuario público de farmacia
- cliente del barrio que quiere saber si hay disponibilidad de un producto
- consulta rápida desde celular o navegador
- no necesariamente quiere registrarse en la primera versión

## 6. Idea general del producto
El sistema se concibe como dos contextos o frentes diferentes del mismo negocio:

### A. Consultorio
App interna, privada, enfocada en operación administrativa y atención simple.

### B. Farmacia
Interfaz pública o semipública para consulta de disponibilidad, con posibilidad futura de administración interna y más adelante solicitudes o compras más estructuradas.

No deben mezclarse alegremente los datos sensibles del consultorio con la parte pública de farmacia.

## 7. Filosofía de producto
La filosofía de producto no debe partir de la idea de impresionar al usuario con complejidad, sino de convencerlo por claridad. El sistema debe comunicar que fue pensado para alguien que trabaja, atiende personas, cobra, resuelve cosas concretas y no tiene tiempo ni interés en descifrar jerga informática.

Por eso el producto debe construirse con una estética sobria, una lógica operativa clara y una sensación general de estabilidad. Aunque por detrás exista una arquitectura bien planteada, eso no debe sentirse en la superficie como complejidad, sino como orden natural.

Además, este proyecto se inserta dentro de una filosofía más amplia del autor: repetir una clase de software administrativo en varios dominios para consolidar oficio. Eso significa que cada decisión tomada aquí debería, en la medida de lo posible, aportar a una futura biblioteca mental y documental reutilizable.

El sistema debe transmitir:

- orden
- simplicidad
- sobriedad
- crecimiento gradual dentro de una sola V1
- utilidad tangible
- facilidad para usuarios no técnicos

No debe intentar deslumbrar con complejidad innecesaria. La prioridad es que el usuario entienda que esto le sirve para trabajar mejor.

## 8. Alcance conceptual de la V1
En este proyecto, todo lo que se construya, por más amplio que resulte, seguirá considerándose parte de una única V1. No se quiere imponer desde ya una narrativa artificial de V1, V2, V3 como si cada subconjunto implicara una reinvención del producto. La idea es más simple: existe una sola primera gran versión, y dentro de ella pueden convivir capas, módulos, endurecimientos técnicos y mejoras graduales.

Esto significa que incluso elementos que en otros contextos podrían verse como “fases posteriores” seguirán siendo aquí subpartes de una V1 expandida, siempre que mantengan coherencia con el mismo núcleo de producto y no rompan el enfoque general.

El término MVP sigue siendo útil como referencia de núcleo mínimo demostrable, pero no debe confundirse con una fragmentación rígida del proyecto. Aquí el MVP es el corazón de una V1 más amplia.

## 8.1. Núcleo MVP dentro de la V1
### MVP del consultorio
Debe poder mostrar o cubrir al menos:

- registro básico de pacientes
- agenda o citas del día
- atención simple
- indicaciones o receta breve
- cobro de consulta
- búsqueda rápida de pacientes

### MVP de farmacia
Debe poder mostrar o cubrir al menos:

- catálogo público simple
- búsqueda de producto
- disponibilidad actual
- actualización básica de stock desde una parte administrativa
- posibilidad de crecimiento futuro a cuentas, solicitudes o pedidos

## 9. Qué no debe entrar fuerte en la primera versión
Para no sobrediseñar, la primera versión debería evitar, o dejar claramente fuera de alcance:

- historia clínica compleja
- expedientes médicos extensos
- laboratorio, imágenes, especialidades complejas
- pagos online
- delivery completo
- integración con WhatsApp desde el inicio
- facturación electrónica avanzada
- multiusuario complejo si todavía no se necesita
- sistema de cuentas de cliente si no aporta valor real en la V1

## 10. Justificación técnica del enfoque dual
### Consultorio en JavaFX
JavaFX se considera adecuado para el consultorio porque:

- da sensación de herramienta interna y seria
- puede funcionar como aplicación administrativa directa
- facilita un flujo controlado y sobrio
- sirve bien para prototipos demostrables
- encaja con la experiencia previa del autor
- permite consolidar un stack repetible en desktop

### Farmacia con Angular
Angular se considera adecuado para la farmacia porque:

- permite exponer catálogo y disponibilidad de forma web
- encaja con un storefront o vitrina consultable
- facilita una futura administración web
- sirve para un acceso rápido desde navegador o celular
- separa bien lo público de lo estrictamente interno del consultorio

## 11. Stack tecnológico base imaginado
### Lenguaje y runtime principal
- Java Eclipse Temurin 21

### Backend
- Spring Boot 4

### Frontend web
- Angular estable actual

### Desktop
- JavaFX

### Base de datos
- PostgreSQL

### Contenedores y entorno
- Docker y Docker Compose, si la fase lo justifica

### Landing page
- Astro no aplica por ahora para este producto específico

## 12. Decisiones técnicas implícitas del autor
El autor prefiere:

- arquitectura clara y explicable
- documentación previa fuerte
- contexto abundante para la IA
- partición documental por componente
- repetir stacks conocidos en vez de dispersarse demasiado
- usar IA para acelerar implementación, pero apoyándose en contexto sólido
- construir software que luego pueda vender o reutilizar

## 13. Problema práctico que resuelve este enfoque documental
Este proyecto asume explícitamente una realidad de trabajo contemporánea: la IA puede acelerar muchísimo la implementación, pero solo produce resultados buenos y consistentes cuando recibe contexto de calidad. Sin contexto, la IA improvisa, mezcla criterios, pierde coherencia entre componentes y obliga al autor a corregir demasiado.

Por eso la documentación aquí cumple una función central. No se escribe para “verse profesional”, sino para construir una especie de sistema nervioso del proyecto. Cada archivo, cada definición y cada convención ayudan a que otras sesiones de IA, otros chats y futuras implementaciones partan de una base compartida, más precisa y menos caótica.

El valor estratégico de esta decisión es enorme: aunque el autor no disponga siempre de la mejor IA de generación de código, podrá seguir apalancándose en una documentación suficientemente rica como para producir buenos resultados con menor dependencia del contexto conversacional inmediato.

Por eso el autor busca generar una base documental grande, detallada y modular, para luego alimentar otros chats con contexto preciso y obtener documentos derivados o código de mejor calidad.

La lógica general es:

1. construir contexto maestro
2. ramificar la documentación por componente
3. usar esa documentación como base para otros chats
4. obtener markdowns, PDFs, diseños, casos de uso, contratos, arquitectura y código con mayor consistencia

## 14. Estrategia documental deseada
La estrategia documental de este proyecto debe entenderse como una estrategia de partición inteligente del conocimiento. En lugar de meter todo en uno o dos archivos gigantes difíciles de reutilizar, se busca dividir la información por capas, contextos y componentes, de manera que cada documento tenga una responsabilidad clara y pueda ser reutilizado, ampliado o entregado a otra IA sin contaminarlo con ruido innecesario.

Este enfoque también tiene una ventaja cognitiva para el autor: le permite pensar el proyecto como un conjunto de piezas encajables, no como una masa difusa de ideas. Esa claridad favorece el modelado, la arquitectura, la programación asistida por IA y la futura venta o mantenimiento del producto.

La documentación no debe quedar en un solo archivo enorme. Se quiere una ramificación fuerte por áreas.

### Grandes bloques documentales esperados
- contexto maestro del proyecto
- dominio del consultorio
- dominio de farmacia
- arquitectura general
- system design
- base de datos
- backend consultorio
- backend farmacia
- desktop JavaFX consultorio
- storefront Angular farmacia
- seguridad
- privacidad y límites de datos
- trazabilidad y auditoría
- logging y observabilidad
- convenciones de nombres
- convenciones de carpetas y repositorios
- manejo de errores y excepciones
- seeds y datos demo
- despliegue y operación
- UX y wireframes
- ADRs
- plantillas de casos de uso
- checklist de calidad

## 15. Enfoque de dominio
El autor no busca volverse médico. Lo que busca es comprender suficientemente el dominio operativo para construir software útil.

Debe entender:

- flujo de atención
- flujo de agenda
- flujo de cobro
- flujo de productos de farmacia
- disponibilidad y stock
- qué cosas son administrativas
- qué cosas son sensibles
- qué cosas no debe decidir el software

La lógica central es:

- el médico decide lo médico
- el sistema organiza lo operativo e informático

## 16. Límite entre lo sensible y lo público
La frontera entre consultorio y farmacia es crítica.

### Debe quedar claramente privado
- datos de pacientes
- citas
- atenciones
- notas o indicaciones internas
- cobros vinculados al consultorio

### Puede tener superficie pública o semipública
- catálogo de farmacia
- disponibilidad actual de productos
- nombre del producto
- precio si así se decide
- fecha u hora de actualización

La arquitectura debe reflejar esa frontera desde el diseño.

## 17. Hipótesis de crecimiento dentro de una sola V1
Aunque aquí se describan bloques de avance progresivo, todos deben entenderse como partes internas de una misma V1 extensa. No se está proponiendo una secuencia estricta de versiones comerciales distintas, sino un modo de ordenar mentalmente el crecimiento del proyecto sin perder coherencia documental ni arquitectónica.

La utilidad de esta división no está en etiquetar “versiones nuevas”, sino en facilitar planificación, priorización y conversación técnica. Cada bloque representa una profundización del mismo producto inicial.

### Núcleo fundacional de la V1
- contexto maestro
- investigación de dominio
- wireframes
- decisiones iniciales de arquitectura
- glosario, actores y visión del sistema

### Capa operativa inicial de la V1
- consultorio básico funcional
- pacientes
- agenda
- atención simple
- cobro

### Capa pública y comercial básica de la V1
- farmacia pública consultable
- catálogo
- búsqueda
- stock visible

### Capa administrativa ampliada de la V1
- administración de farmacia
- login interno
- CRUD de productos
- ajuste de stock

### Capa de endurecimiento técnico de la V1
- logs
- trazabilidad
- auditoría
- backups
- despliegue reproducible
- checklists operativos

### Capa de expansión opcional todavía dentro de la V1
- cuentas de cliente
- solicitudes internas
- pedidos
- domicilios
- integraciones futuras

## 18. Criterios UX prioritarios
Debido al tipo de usuario objetivo, el producto debe priorizar:

- tipografía legible
- botones claros y grandes
- pocos elementos por pantalla
- lenguaje no técnico
- flujos lineales
- navegación evidente
- acciones importantes muy visibles
- confirmaciones simples
- evitar ruido visual

## 19. Riesgos que deben tenerse presentes
El proyecto debe ser leído también desde la prudencia. No basta con que una idea sea atractiva o técnicamente posible; debe ser sostenible para una persona que todavía está estudiando, que quiere usar IA de forma intensiva pero no ilimitada, y que necesita equilibrar aprendizaje, portafolio, viabilidad comercial y mantenimiento futuro.

Por eso aquí el riesgo no es solo técnico. También existe riesgo de dispersión mental, de exceso documental sin utilidad práctica, de construir demasiado antes de validar, o de terminar con una arquitectura elegante en papel pero desalineada con el nivel real del negocio objetivo.

### Riesgos de producto
- sobrediseñar demasiado pronto
- mezclar farmacia y consultorio sin límites claros
- construir más de lo que el cliente necesita
- meter cuentas o compras antes de tiempo

### Riesgos técnicos
- exponer endpoints sensibles
- no separar bien contextos
- logs pobres o sin trazabilidad
- arquitectura demasiado grande para un MVP
- baja calidad documental que vuelva inconsistente a la IA

### Riesgos operativos
- cortes eléctricos
- soporte informal o desordenado
- cambios de alcance no controlados
- dependencia excesiva del autor para soporte si no se define bien el mantenimiento

## 20. Principios de arquitectura deseados
Los principios de arquitectura no son adornos teóricos. En este proyecto cumplen la función de impedir que el sistema crezca de forma caótica, difícil de mantener o demasiado dependiente de la memoria del autor. Cada principio actúa como una regla de diseño que ayuda a decidir cómo dividir responsabilidades, cómo nombrar, cómo documentar, cómo escalar y cómo evitar que la implementación termine contradiciendo la intención original del producto.

En este contexto, los principios deben leerse de forma práctica. No se trata de perseguir una pureza académica extrema, sino de construir un software suficientemente ordenado como para que:

- pueda evolucionar sin romperse con facilidad
- pueda explicarse a otra IA o a otra persona
- pueda estudiarse después con claridad
- pueda adaptarse a clientes similares
- pueda subirse a GitHub con una estructura profesional

El proyecto debe tender a:

- separación de responsabilidades
- módulos claros
- nombres consistentes
- contratos definidos
- errores uniformes
- trazabilidad mínima útil
- seeds reproducibles
- facilidad de onboarding
- crecimiento gradual sin rehacer todo
- estructura limpia y mantenible de repositorio
- scaffolding coherente entre componentes
- facilidad de arranque y exploración del proyecto para estudio y reutilización

### 20.0.1. Separación de responsabilidades
Este principio significa que cada parte del sistema debe tener una responsabilidad principal relativamente clara. No conviene que una misma clase, módulo o carpeta mezcle lógica de negocio, acceso a datos, presentación visual, validación de entrada, manejo de errores y detalles de infraestructura al mismo tiempo.

En este proyecto, aplicar separación de responsabilidades ayuda mucho porque existen varios frentes distintos: dominio del consultorio, dominio de farmacia, backend, desktop JavaFX, storefront Angular, base de datos, scripts y documentación. Si todo se mezcla, la IA tenderá a producir código confuso y el mantenimiento se volverá frágil.

Como sugerencia práctica, cada componente debe poder responder con claridad a la pregunta: “¿para qué existe esta pieza?”

### 20.0.2. Módulos claros
Un módulo claro es una parte del sistema con límites entendibles, una intención reconocible y poco ruido interno. No tiene que ser perfecto ni enorme, pero sí suficientemente identificable.

Aquí esto aplica, por ejemplo, al distinguir consultorio de farmacia, frontend público de administración privada, backend de consultorio de backend de farmacia si finalmente se separan, y también al distinguir documentación de dominio, arquitectura, UX, seguridad o despliegue.

La modularidad clara ayuda a que el proyecto sea más reusable y evita que todo quede como una sola masa de código o documentos.

### 20.0.3. Nombres consistentes
Nombrar bien no es un lujo. Es parte de la arquitectura cognitiva del proyecto. Si las entidades, carpetas, endpoints, DTOs, servicios, scripts o documentos se nombran de forma inconsistente, luego todo el ecosistema se vuelve difícil de seguir, especialmente cuando hay IA de por medio.

En este contexto, conviene que los nombres reflejen el dominio real y no solo términos genéricos. Por ejemplo, distinguir claramente paciente, cita, atención, producto, disponibilidad, stock, consulta pública, administración de farmacia, etc.

### 20.0.4. Contratos definidos
Un contrato es una expectativa explícita entre dos partes del sistema. Puede ser un contrato API, un DTO, una interfaz, una convención de respuesta o incluso un acuerdo documental sobre qué contiene un archivo.

Este principio es importante porque reduce ambigüedad. Si Angular espera una forma de respuesta y Spring Boot devuelve otra, o si JavaFX consume datos de manera distinta a lo documentado, el sistema se vuelve inconsistente.

En este proyecto, los contratos definidos deben existir tanto a nivel técnico como documental.

### 20.0.5. Errores uniformes
No basta con capturar errores. Hay que tratarlos de manera coherente. Un error uniforme significa que el sistema responde con estructuras, mensajes y criterios previsibles cuando algo falla.

Esto aplica especialmente a backend, pero también a JavaFX y Angular. La idea es que el usuario no técnico reciba mensajes entendibles y que el desarrollador pueda rastrear la causa técnica con logs o códigos apropiados.

### 20.0.6. Trazabilidad mínima útil
La trazabilidad no significa registrar absolutamente todo. Significa poder seguir acciones o fallos importantes con suficiente contexto. Por ejemplo, saber quién intentó actualizar stock, cuándo ocurrió un cambio relevante o qué operación falló.

En este proyecto conviene una trazabilidad mínima, pragmática y útil, sobre todo porque el sistema apunta a ser mantenible y quizás comercializable. Sin trazabilidad, el soporte se vuelve mucho más difícil.

### 20.0.7. Seeds reproducibles
Los seeds son datos iniciales o de demostración que permiten poblar el sistema de forma rápida y consistente. Son muy valiosos para demos, pruebas, portafolio y desarrollo con IA.

Aquí aplican especialmente para productos de farmacia, datos de muestra, usuarios de prueba, estados básicos y cualquier información necesaria para mostrar el sistema vivo sin llenarlo todo manualmente.

### 20.0.8. Facilidad de onboarding
El onboarding es la capacidad del proyecto de explicarse y ponerse en marcha sin depender por completo de la memoria del autor. Implica documentación clara, scripts útiles, estructura legible y una ruta de entrada comprensible.

En este contexto, el onboarding no solo sirve para personas. También sirve para nuevos chats de IA que necesiten entender el proyecto rápidamente.

### 20.0.9. Crecimiento gradual sin rehacer todo
Este principio significa diseñar la V1 de forma que pueda expandirse sin botar la base cada vez que aparece una nueva necesidad. No implica sobrediseñar, sino dejar espacio razonable para crecer.

Aquí aplica muy bien porque el proyecto quiere comenzar simple, pero con posibilidad de incorporar luego administración de farmacia más fuerte, cuentas, pedidos, trazabilidad endurecida, etc., sin destruir la coherencia original.

### 20.0.10. Estructura limpia y mantenible de repositorio
La arquitectura de un proyecto no vive solo dentro del código fuente. También vive en la forma en que se ordenan documentos, scripts, assets, infraestructura, bases de datos y componentes.

En este proyecto, una estructura limpia es clave porque se quiere estudiar, reutilizar, mostrar en GitHub y alimentar con IA. Un repositorio caótico reduce mucho el valor del trabajo, aunque el código funcione.

### 20.0.11. Scaffolding coherente entre componentes
Scaffolding significa la estructura base repetible con la que se construye cada componente. Ayuda a que backend, desktop y frontend no parezcan proyectos creados por tres personas distintas sin acuerdo alguno.

Aquí el objetivo no es forzar igualdad artificial, sino una filosofía compartida de orden: README por componente, tests ubicables, recursos bien puestos, scripts claros y carpetas con sentido.

### 20.0.12. Facilidad de arranque y exploración del proyecto
El proyecto debe invitar a ser explorado. Debe ser razonablemente fácil arrancarlo, revisar sus partes, ubicar documentación, entender qué corre dónde y probar algo sin fricción excesiva.

Esto es valioso para estudio, para portafolio y para productividad asistida por IA. Un proyecto difícil de explorar tiende a degradarse más rápido.

## 20.1. Higiene estructural, onboarding y scaffolding del proyecto
Además de la arquitectura lógica del sistema, este proyecto debe cuidar explícitamente la arquitectura del repositorio y de sus componentes. No basta con que el código funcione; también debe quedar organizado de forma limpia, navegable, profesional y preparada tanto para estudio como para futura publicación en GitHub.

La limpieza estructural aquí tiene varios propósitos simultáneos:

- facilitar el onboarding del propio autor cuando vuelva al proyecto después de semanas o meses
- facilitar que otra IA entienda mejor dónde está cada cosa
- reducir archivos mal ubicados, temporales o ambiguos
- permitir una presentación profesional del repositorio
- favorecer la reutilización futura de componentes, patrones y convenciones

El proyecto debe pensarse como algo que, una vez estabilizado, pueda subirse a GitHub con buena apariencia, documentación clara, estructura coherente, capturas del producto y una identidad visual mínima pero profesional.

### 20.1.1. Estructura externa del repositorio
Debe existir una preocupación explícita por la estructura fuera de `src/`. Es decir, no solo importa el árbol de código fuente, sino también todo lo que vive alrededor del código y que hace al proyecto reproducible, mantenible y entendible.

Esto incluye, según corresponda:

- `docs/` para documentación ramificada
- `scripts/` para automatizaciones y tareas operativas
- `infra/` para Docker, Compose y recursos de infraestructura
- `assets/` o carpetas equivalentes para material visual general del repositorio
- `database/` para esquema, migraciones, seeds y utilidades relacionadas
- carpetas específicas por componente, cada una con su propia organización interna clara

La meta es evitar repositorios caóticos donde los archivos auxiliares queden mezclados arbitrariamente con el código.

### 20.1.2. Onboarding del proyecto
El proyecto debe contemplar onboarding desde el principio. Aquí onboarding no significa solo un README bonito, sino una estrategia de entrada ordenada al proyecto.

Debe existir documentación que responda preguntas como:

- qué es este proyecto
- qué componentes tiene
- cómo se arranca localmente
- qué se necesita instalar
- qué versión de cada tecnología se usa
- dónde están los scripts importantes
- cómo se ejecuta todo de forma global o por componente
- qué parte es demo, qué parte es productiva y qué parte es de apoyo

El onboarding debe servir tanto para el autor futuro como para terceros y para chats de IA.

### 20.1.3. Scaffolding coherente
El scaffolding de cada componente debe ser deliberado y consistente. No se quiere un conjunto de carpetas creadas al azar, sino una plantilla de organización reproducible.

Cada componente importante del sistema debería nacer ya con cierta estructura base coherente, por ejemplo:

- carpeta de código principal
- carpeta de recursos o estáticos cuando aplique
- carpeta de tests
- README local del componente
- scripts específicos si realmente aportan valor
- archivos de configuración claramente identificados

La idea es que backend, desktop y frontend compartan una filosofía común de orden, aunque cada stack tenga sus convenciones propias.

### 20.1.4. Scripts y automatización de arranque
Debe haber una preocupación explícita por los scripts operativos. El proyecto debería tender a una experiencia donde exista un punto de entrada claro para arrancar, preparar o revisar el sistema.

A nivel conceptual, se desea:

- un script global para ayudar a levantar o preparar el conjunto del proyecto cuando sea razonable
- scripts locales por componente solo cuando aporten claridad real
- nombres de scripts evidentes
- automatización de tareas repetitivas
- reducción de fricción al iniciar el proyecto

En entorno Windows, puede tener sentido que exista un `.bat` global de arranque o coordinación, siempre que no sustituya una documentación clara y que no genere dependencia innecesaria de un único sistema operativo sin justificación.

### 20.1.5. Limpieza del repositorio al terminar
Aunque durante el desarrollo puedan existir archivos temporales, notas o artefactos transitorios, el estado objetivo del proyecto debe ser limpio. Antes de considerarlo una base seria o publicable, el repositorio debe tender a:

- no arrastrar basura innecesaria
- no incluir archivos temporales sin propósito
- no mezclar documentación final con borradores caóticos
- respetar `.gitignore`
- presentar una estructura clara para lectura humana

El objetivo es que el proyecto, al mirarse desde fuera, comunique orden y madurez.

### 20.1.6. Preparación para GitHub y portafolio
El proyecto debe diseñarse también como pieza de exhibición técnica. Esto implica que, cuando alcance un estado suficientemente estable, debería poder presentarse en GitHub con:

- README profesional
- introducción clara del producto
- logo o identidad visual mínima del proyecto
- capturas de pantalla del sistema funcionando
- explicación breve del problema que resuelve
- stack tecnológico
- instrucciones de arranque
- organización interna del repositorio

No se busca marketing vacío, sino una presentación lo bastante buena como para que un cliente potencial, un reclutador o el propio autor en el futuro entiendan rápidamente el valor del proyecto.

### 20.1.7. Assets y recursos estáticos
Debe quedar explicitado que los recursos visuales y estáticos también forman parte de la arquitectura práctica del proyecto.

Dependiendo del componente, debe existir una convención clara para recursos como:

- imágenes
- íconos
- logos
- tipografías
- audio si en algún caso se usa
- video si existe material de demo
- archivos de muestra o demo

La ubicación exacta dependerá del stack. Por ejemplo, Angular y JavaFX no organizan assets exactamente igual. Lo importante es que cada componente tenga una convención clara y que esta esté documentada.

No se trata de inventar carpetas innecesarias porque sí, sino de dejar preparado el proyecto para incorporar recursos sin desorden.

### 20.1.8. Documentación dentro del código y fuera del código
El proyecto debe servir también como material de estudio. Por eso no basta con documentación externa en Markdown; también debe haber una expectativa razonable de documentación dentro del propio código.

Eso implica:

- comentarios cuando realmente ayuden a entender decisiones no obvias
- clases, módulos o componentes con responsabilidad clara
- nombres suficientemente expresivos
- documentación técnica externa donde el comentario en código no sea suficiente
- equilibrio entre código autoexplicativo y comentarios útiles

La meta no es saturar el proyecto con comentarios redundantes, sino dejarlo en un estado donde el autor pueda estudiar después la lógica, la arquitectura y las decisiones implementadas.

### 20.1.9. Proyecto pensado para estudiar y reutilizar
Este proyecto no se concibe solo como algo para entregar o mostrar, sino como un objeto de estudio personal. Debe quedar lo bastante claro y bien estructurado como para que el autor pueda volver a él, recorrerlo, aprender de él y reutilizar partes en futuros sistemas.

Eso exige una combinación de:

- estructura limpia
- documentación fuerte
- comentarios útiles
- assets organizados
- scripts claros
- convención consistente entre componentes

## 21. Buenas prácticas y áreas de ingeniería que deben quedar explícitas
Este proyecto no debe limitarse a hablar solo de pantallas, tablas o módulos de negocio. Debe dejar mencionado, aunque sea inicialmente de forma superficial, el conjunto amplio de preocupaciones de ingeniería necesarias para construir software administrativo serio, mantenible y reutilizable.

La idea no es desarrollar cada tema a profundidad dentro de este mismo lienzo, sino dejarlo explícito como mapa de cobertura obligatoria para la documentación derivada. Así se evita olvidar componentes importantes del diseño, de la arquitectura o de la operación.

En este contexto, estas áreas funcionan como una lista de control intelectual. Cada una responde a una pregunta distinta del proyecto:

- producto: ¿qué se quiere resolver realmente?
- dominio: ¿cómo funciona el negocio?
- arquitectura: ¿cómo se divide el sistema?
- persistencia: ¿cómo se guardan los datos?
- backend: ¿cómo se exponen capacidades?
- desktop/web: ¿cómo interactúa el usuario?
- seguridad: ¿qué debe protegerse?
- errores: ¿qué pasa cuando algo falla?
- trazabilidad: ¿cómo seguimos lo que pasó?
- testing: ¿cómo confiamos en que funciona?
- operación: ¿cómo se arranca, mantiene y recupera?

### 21.1. Producto, alcance y modelado del problema
Esta área existe para evitar que el proyecto arranque directamente en código sin tener una definición razonable del problema. Aquí se fija qué producto se quiere construir, para quién, con qué límites y bajo qué supuestos.

En este proyecto aplica para dejar claro que el objetivo no es una clínica compleja, sino un consultorio pequeño con farmacia integrada, pensado para usuarios no técnicos y para una V1 amplia pero controlada.

Debe existir documentación sobre:

- visión del producto
- alcance funcional y no funcional
- objetivos del sistema
- supuestos y restricciones
- actores y tipos de usuario
- casos de uso
- historias de usuario si se decide utilizarlas
- reglas de negocio
- glosario del dominio
- eventos del dominio
- flujos principales y alternos
- criterios de aceptación
- límites explícitos de la V1

### 21.2. Arquitectura y system design
Esta área se encarga de traducir la intención del producto en una estructura técnica entendible. System design, en este contexto, no significa diseñar una mega plataforma distribuida, sino decidir con claridad qué componentes existen, cómo se relacionan y dónde están sus fronteras.

Aquí es especialmente importante porque el proyecto mezcla desktop, web, backend, base de datos, dominio privado y superficie pública.

Debe existir documentación sobre:

- arquitectura general del sistema
- separación por componentes y responsabilidades
- límites entre consultorio, farmacia, backend, desktop y web
- decisiones de modularidad
- capas de la aplicación
- contratos entre capas
- estrategias de integración entre componentes
- diagramas de contexto, contenedores y componentes si se estima necesario
- decisiones de escalabilidad gradual
- estrategia para evitar acoplamiento excesivo
- ADRs (registros de decisiones arquitectónicas)

### 21.3. Diseño del dominio y modelado interno
Esta área busca que la estructura del software represente con cierta fidelidad la lógica del negocio, sin obligarte a convertirte en médico. Aquí se modelan las entidades importantes, sus relaciones, sus estados y sus reglas.

En este proyecto, esto sirve para distinguir claramente pacientes, citas, atenciones, productos, stock, cobros y fronteras entre farmacia y consultorio.

Debe existir documentación sobre:

- entidades principales
- agregados o agrupaciones lógicas si aplican
- objetos de valor si resultan útiles
- estados y transiciones relevantes
- invariantes del dominio
- reglas de consistencia
- separación entre lógica de dominio y detalles técnicos
- modelado de catálogos, agenda, pacientes, productos, stock, consultas y cobros

### 21.4. Base de datos y persistencia
Esta área define cómo se almacenan los datos de manera consistente y trazable. No se trata solo de dibujar tablas, sino de tomar decisiones sobre integridad, nombres, restricciones, relaciones y evolución.

En este proyecto es importante porque la persistencia debe acompañar un dominio moderadamente claro, con separación de datos sensibles y públicos.

Debe existir documentación sobre:

- modelo relacional inicial
- tablas y relaciones
- claves primarias y foráneas
- restricciones
- índices
- decisiones de normalización
- estrategia de migraciones
- seeds de datos demo
- auditoría de cambios si se requiere
- decisiones de nombres de tablas y columnas
- estrategia de backups y restauración

### 21.5. Backend y contratos de servicio
Esta área define cómo el sistema expone sus capacidades de forma organizada. Incluye endpoints, validaciones, formatos de respuesta, autenticación y separación entre aplicación, dominio e infraestructura.

Aquí aplica tanto para el backend del consultorio como para el de farmacia, ya sea que se mantengan separados o compartan parte de la base.

Debe existir documentación sobre:

- módulos del backend
- endpoints y contratos API
- DTOs
- validaciones de entrada y salida
- formato estándar de respuestas
- manejo de errores HTTP
- autenticación y autorización
- servicios de aplicación
- repositorios o puertos de acceso a datos
- separación entre dominio, aplicación e infraestructura
- versionado de API si aplica
- paginación, filtros y búsqueda cuando tengan sentido

### 21.6. Desktop JavaFX del consultorio
Esta área existe para tratar al desktop como un componente serio y no como simple interfaz improvisada. Aquí se define cómo se estructura la aplicación interna del consultorio, cómo se navega, cómo se valida y cómo se comunica con el resto del sistema.

En este proyecto importa mucho porque JavaFX es parte de tu identidad técnica repetible y también porque el consultorio apunta a un usuario que valora claridad, sobriedad y flujo directo.

Debe existir documentación sobre:

- arquitectura interna del cliente desktop
- navegación entre pantallas
- manejo del estado de UI
- validaciones de formularios
- componentes reutilizables
- manejo de errores de cara al usuario
- diseño visual sobrio y consistente
- estructura de paquetes del proyecto desktop
- comunicación con backend o servicios
- estrategia de pruebas de UI o de lógica asociada

### 21.7. Storefront Angular de farmacia
Esta área trata al frontend web como una superficie pública o semipública con sus propias necesidades. No es solo una pantalla bonita; es una interfaz con catálogo, disponibilidad, administración potencial y consumo de servicios.

En este proyecto, Angular sirve para separar la parte pública de farmacia del mundo privado del consultorio.

Debe existir documentación sobre:

- arquitectura interna del frontend web
- componentes, páginas y rutas
- manejo de estado si se adopta
- consumo de endpoints públicos y privados
- formularios y validaciones
- separación entre catálogo público y administración privada
- experiencia móvil básica
- estrategia visual para catálogo, búsqueda y disponibilidad
- manejo de errores del lado cliente

### 21.8. Seguridad, privacidad y límites de acceso
Esta área responde a una pregunta crítica: qué se puede ver, qué no, quién puede hacer qué y cómo se evita exponer información sensible. En un sistema que mezcla consultorio y farmacia, esto no es opcional.

Aquí la seguridad debe ser suficientemente seria, aunque el sistema empiece pequeño. La privacidad y la minimización de exposición deben estar presentes desde el diseño.

Debe existir documentación sobre:

- autenticación
- autorización por roles
- separación entre datos sensibles y datos públicos
- protección de endpoints
- gestión de sesiones si aplica
- políticas de acceso interno
- sanitización y validación
- protección contra errores comunes de exposición de datos
- privacidad por diseño
- minimización de datos sensibles en superficies no necesarias

### 21.9. Manejo de errores y excepciones
Esta área define cómo piensa el sistema cuando algo sale mal. No todos los errores son iguales: unos pertenecen al dominio, otros a la aplicación y otros a la infraestructura.

En este proyecto conviene distinguirlos porque eso te ayuda a mostrar mensajes adecuados al usuario, registrar la información técnica necesaria y diseñar un sistema más estudiable y mantenible.

Debe existir documentación sobre:

- errores de dominio
- errores de aplicación
- errores de infraestructura
- catálogo de códigos de error
- estrategia de mensajes al usuario
- estrategia de mensajes técnicos para logs
- mapeo consistente entre excepciones y respuestas del sistema
- política de fallos recuperables y no recuperables

### 21.10. Logs, trazabilidad, auditoría y observabilidad
Esta área existe para que el sistema deje huellas útiles de lo que pasa. Los logs ayudan a diagnosticar, la trazabilidad ayuda a seguir acciones y la auditoría ayuda a registrar cambios sensibles o relevantes.

En este proyecto esto importa bastante porque quieres un software que pueda mantenerse, venderse y estudiarse después, no solo una demo visual.

Debe existir documentación sobre:

- logging estructurado o al menos consistente
- niveles de log
- eventos importantes a registrar
- correlation id o identificador de seguimiento si se considera útil
- trazabilidad de acciones relevantes
- auditoría de cambios sensibles
- monitoreo mínimo
- diagnóstico de fallos
- estrategia para soporte y mantenimiento

### 21.11. Testing y calidad
Esta área responde a cómo validar que el sistema funciona razonablemente bien y no se rompe con facilidad. Testing aquí no debe verse como lujo corporativo, sino como una forma de consolidar aprendizaje, evitar regresiones tontas y sostener calidad.

En este proyecto conviene pensar pruebas de manera pragmática: lo suficiente para validar reglas, flujos y contratos clave, sin exigir una maquinaria absurda desde el primer día.

Debe existir documentación sobre:

- estrategia de pruebas unitarias
- pruebas de integración
- pruebas de contratos si se justifica
- pruebas de repositorio o persistencia
- pruebas de servicios
- pruebas básicas de UI cuando sea razonable
- casos de prueba críticos
- criterios mínimos de calidad antes de considerar algo estable
- estrategia de datos de prueba

### 21.12. Patrones de diseño y estructuras de datos
Esta área sirve para dejar explícitas las soluciones recurrentes que ayudan a mantener orden conceptual. Los patrones no deben usarse como decoración, sino cuando realmente aclaran responsabilidades o reducen complejidad.

En este proyecto esto aplica a backend, JavaFX, Angular y también a decisiones internas sobre colecciones, mapas, listas, estados o colas, cuando aporten claridad a la implementación.

Debe existir documentación sobre:

- patrones arquitectónicos adoptados
- patrones de diseño aplicables en backend, desktop y frontend
- criterios para no abusar de patrones innecesarios
- estructuras de datos útiles para catálogos, agenda, búsquedas, filtros, colas o estados
- decisiones sobre colecciones, mapas, listas, sets o estructuras especializadas cuando aporten claridad
- separación entre decisiones elegantes y decisiones pragmáticas

### 21.13. Convenciones y coherencia del código
Esta área existe para que el proyecto no se convierta en una colección de estilos mezclados. Las convenciones ayudan a que el código, la documentación, los endpoints y las carpetas hablen un idioma común.

Como quieres estudiar el proyecto y alimentarlo a otras IAs, esta coherencia vale muchísimo.

Debe existir documentación sobre:

- convenciones de nombres
- convenciones de paquetes y carpetas
- convenciones de endpoints
- convenciones para entidades, DTOs, servicios y componentes
- estilo de commits si se define
- estructura del repositorio
- reglas de documentación mínima en código y en Markdown
- políticas de versionado

### 21.14. DevOps, entornos y operación
Esta área cubre el mundo que existe después del código: cómo se configura el proyecto, cómo se levanta, cómo se despliega, cómo se respalda y cómo se recupera si algo falla.

En este proyecto tiene especial sentido por el interés en scripts, onboarding, arranque limpio, `.bat` global si aplica y preparación para contextos reales con posibles cortes de energía.

Debe existir documentación sobre:

- perfiles de entorno
- variables de entorno
- uso de Docker y Docker Compose
- scripts de arranque
- configuración reproducible
- despliegue local
- despliegue demostrativo o productivo si llega a existir
- estrategia de respaldo
- estrategia ante cortes de energía o caídas del sistema
- recuperación operativa mínima

### 21.15. UX, diseño visual y comunicación con usuario no técnico
Esta área recuerda que el sistema no será usado por programadores, sino por personas que quieren resolver tareas concretas. Por eso la UX debe ser funcional, sobria y clara.

Aquí aplica mucho a la figura del médico mayor y al uso de lenguaje no técnico, botones visibles, flujos directos y superficies limpias.

Debe existir documentación sobre:

- lineamientos visuales
- wireframes
- principios de usabilidad
- mensajes claros para usuario final
- confirmaciones y alertas
- lenguaje no técnico
- reducción de fricción operativa
- accesibilidad básica
- consistencia entre desktop y web cuando corresponda

### 21.16. Mantenimiento, soporte y evolución
Esta área define cómo el producto seguirá vivo después de una primera entrega o primera demo seria. Incluye cómo se controlan cambios, cómo se entiende el soporte y cómo se decide la evolución dentro de la misma V1.

En este proyecto es importante porque la intención futura incluye posible mantenimiento mensual y reutilización con otros clientes.

Debe existir documentación sobre:

- estrategia de mantenimiento mensual si se comercializa
- límites del soporte
- procedimiento para cambios de alcance
- registro de incidencias o tickets si más adelante se desea
- estrategia de evolución del producto sin romper la V1
- criterios para decidir qué entra y qué no entra

### 21.17. Documentación derivada obligatoria
La documentación derivada deberá incluir con fuerza:

- system design
- arquitectura por capas o modular
- reglas de negocio explícitas
- casos de uso
- convenciones de nombres
- estructura de carpetas
- contratos API
- DTOs
- validaciones
- manejo profesional de errores
- excepciones de dominio
- excepciones de infraestructura
- logs
- auditoría
- trazabilidad
- seguridad
- privacidad por diseño
- despliegue
- backups
- seeds
- patrones de diseño útiles
- estructuras de datos útiles
- testing
- checklist de calidad

## 21.18. Sugerencia de markdowns por componente y por área
A continuación se propone un mapa más explícito de markdowns que convendría generar después de este lienzo. No todos tienen que escribirse de una sola vez, pero sí funcionan como lista guía de documentación derivada.

Esta sección debe leerse como una guía de producción documental por capas y no como el árbol final exhaustivo. La estructura canónica más completa del proyecto queda fijada más adelante en la sección 26.1, donde se presenta el árbol oficial de documentación.

La lógica recomendada no es producirlos en orden arbitrario, sino por capas. Primero se documenta el contexto y el dominio; luego la arquitectura y la persistencia; después los componentes concretos; y finalmente las áreas transversales como errores, seguridad, trazabilidad, testing, operación y checklists.

Dicho de otra forma, antes de documentar cómo luce una pantalla o cómo responde un endpoint, conviene dejar claro qué problema se resuelve, qué actores existen, qué reglas manda el negocio y cómo se divide el sistema.

### A. Contexto maestro y visión general
#### Paso 0.0. Semilla global del proyecto
**`docs/00_contexto_maestro/README.md`**  
Documento semilla del proyecto. Resume visión, contexto, objetivos, alcance y estrategia general. Sirve como texto base para alimentar otros chats.

#### Paso 0.1. Propósito del producto
**`docs/00_contexto_maestro/vision_producto.md`**  
Explica qué problema resuelve el sistema, para quién existe y cuál es su propuesta de valor.

#### Paso 0.2. Frontera de la V1
**`docs/00_contexto_maestro/alcance_v1.md`**  
Define qué entra y qué no entra en la V1 amplia. Ayuda a no sobrediseñar.

### B. Dominio del consultorio
#### Paso 1.0. Comprender al usuario y el flujo
**`docs/01_dominio_consultorio/actores_y_flujos.md`**  
Describe usuarios, tareas, flujo de atención, agenda, cobro y búsqueda de pacientes.

#### Paso 1.1. Formalizar reglas operativas
**`docs/01_dominio_consultorio/reglas_de_negocio.md`**  
Recoge reglas del consultorio: validaciones, restricciones, secuencia lógica y decisiones operativas.

#### Paso 1.2. Convertir operación en interacciones documentadas
**`docs/01_dominio_consultorio/casos_de_uso.md`**  
Presenta casos de uso del consultorio en formato ordenado y reutilizable.

#### Paso 1.3. Fijar el vocabulario del dominio
**`docs/01_dominio_consultorio/glosario.md`**  
Define términos del dominio para mantener coherencia en código y documentación.

### C. Dominio de farmacia
#### Paso 2.0. Comprender catálogo, disponibilidad y administración
**`docs/02_dominio_farmacia/actores_y_flujos.md`**  
Explica catálogo, stock, consulta pública, administración interna y futuras extensiones.

#### Paso 2.1. Formalizar reglas de stock y visibilidad
**`docs/02_dominio_farmacia/reglas_de_negocio.md`**  
Reglas de disponibilidad, actualización de stock, publicación de productos y límites de la parte pública.

#### Paso 2.2. Convertir operación en interacciones documentadas
**`docs/02_dominio_farmacia/casos_de_uso.md`**  
Casos de uso para consulta pública, administración y evolución futura.

#### Paso 2.3. Fijar el vocabulario del subdominio farmacia
**`docs/02_dominio_farmacia/glosario.md`**  
Vocabulario propio de farmacia y catálogo.

### D. Arquitectura general y system design
#### Paso 3.0. Dibujar la estructura gruesa del sistema
**`docs/03_arquitectura_general/arquitectura_general.md`**  
Explica la arquitectura global, los componentes y sus límites.

#### Paso 3.1. Expresar el sistema a nivel de contexto y contenedores
**`docs/04_system_design/contexto_y_contenedores.md`**  
Describe el sistema a nivel de contexto y contenedores. Muy útil para diagramas simples.

#### Paso 3.2. Detallar piezas e interacción entre piezas
**`docs/04_system_design/componentes_y_contratos.md`**  
Detalla los componentes principales y cómo interactúan.

#### Paso 3.3. Registrar decisiones arquitectónicas
**`docs/15_adrs/adrs.md`**  
Registro de decisiones arquitectónicas importantes y su justificación.

### E. Base de datos
#### Paso 4.0. Traducir el dominio a persistencia
**`docs/05_base_datos/modelo_relacional.md`**  
Explica entidades, tablas, relaciones y decisiones de modelado.

#### Paso 4.1. Documentar criterios de integridad y consistencia
**`docs/05_base_datos/reglas_de_persistencia.md`**  
Describe restricciones, normalización, índices y criterios de persistencia.

#### Paso 4.2. Preparar evolución y datos de prueba
**`docs/05_base_datos/migraciones_y_seeds.md`**  
Documenta cómo evoluciona el esquema y cómo se cargan datos iniciales.

### F. Backend consultorio
#### Paso 5.0. Definir la arquitectura del backend interno
**`docs/06_backend_consultorio/arquitectura_backend.md`**  
Describe módulos, capas y organización interna del backend del consultorio.

#### Paso 5.1. Exponer contratos API del consultorio
**`docs/06_backend_consultorio/contratos_api.md`**  
Lista endpoints, request/response, DTOs y reglas de uso.

#### Paso 5.2. Formalizar validaciones y fallos del consultorio
**`docs/06_backend_consultorio/errores_y_validaciones.md`**  
Define validaciones, excepciones, respuestas de error y mensajes.

### G. Backend farmacia
#### Paso 6.0. Definir la arquitectura del backend de catálogo y administración
**`docs/07_backend_farmacia/arquitectura_backend.md`**  
Arquitectura específica del backend orientado a catálogo y administración de farmacia.

#### Paso 6.1. Exponer contratos públicos y privados
**`docs/07_backend_farmacia/contratos_api.md`**  
Endpoints públicos y privados, DTOs y convenciones de respuesta.

#### Paso 6.2. Formalizar validaciones y fallos de farmacia
**`docs/07_backend_farmacia/errores_y_validaciones.md`**  
Criterios de error, validaciones y políticas de fallos.

### H. Desktop JavaFX del consultorio
#### Paso 7.0. Definir la arquitectura interna del cliente desktop
**`docs/08_javafx_consultorio/arquitectura_desktop.md`**  
Explica paquetes, capas internas, navegación y estructura general del cliente JavaFX.

#### Paso 7.1. Documentar pantallas y rutas de trabajo
**`docs/08_javafx_consultorio/pantallas_y_flujos.md`**  
Describe pantallas, rutas de navegación y comportamiento esperado.

#### Paso 7.2. Fijar convenciones visuales y de interacción
**`docs/08_javafx_consultorio/convenciones_ui.md`**  
Lineamientos visuales, componentes reutilizables, mensajes y UX local.

### I. Angular farmacia
#### Paso 8.0. Definir la arquitectura interna del frontend web
**`docs/09_angular_farmacia/arquitectura_frontend.md`**  
Explica módulos, rutas, componentes y servicios del storefront.

#### Paso 8.1. Documentar páginas, paneles y composición visual
**`docs/09_angular_farmacia/paginas_y_componentes.md`**  
Lista páginas públicas, paneles administrativos y composición general.

#### Paso 8.2. Fijar reglas de experiencia visual y formularios
**`docs/09_angular_farmacia/convenciones_ui.md`**  
Reglas de diseño visual, formularios, estados de error y experiencia móvil básica.

### J. Seguridad, trazabilidad y observabilidad
#### Paso 9.0. Definir acceso y fronteras de privacidad
**`docs/10_seguridad_privacidad/modelo_de_acceso.md`**  
Define roles, permisos, fronteras entre datos públicos y privados.

#### Paso 9.1. Documentar tratamiento de datos sensibles
**`docs/10_seguridad_privacidad/privacidad_y_datos_sensibles.md`**  
Explica qué datos requieren mayor protección y por qué.

#### Paso 9.2. Definir estrategia de logs y monitoreo mínimo
**`docs/11_logs_trazabilidad_auditoria/logging_y_observabilidad.md`**  
Define estrategia de logs, niveles, eventos y monitoreo mínimo.

#### Paso 9.3. Definir trazabilidad y auditoría
**`docs/11_logs_trazabilidad_auditoria/trazabilidad_y_auditoria.md`**  
Detalla qué acciones deben quedar registradas y con qué propósito.

### K. Operación, despliegue y soporte
#### Paso 10.0. Preparar onboarding general
**`docs/12_despliegue_operacion/onboarding.md`**  
Guía de arranque del proyecto, dependencias, versiones y scripts.

#### Paso 10.1. Definir entornos y configuración
**`docs/12_despliegue_operacion/entornos_y_configuracion.md`**  
Describe variables de entorno, perfiles y configuración reproducible.

#### Paso 10.2. Documentar scripts y arranque coordinado
**`docs/12_despliegue_operacion/scripts_y_arranque.md`**  
Documenta el `.bat` global, scripts locales y automatizaciones.

#### Paso 10.3. Definir respaldos y recuperación
**`docs/12_despliegue_operacion/backups_y_recuperacion.md`**  
Explica políticas de respaldo y recuperación básica.

### L. Convenciones y calidad
#### Paso 11.0. Fijar convenciones de nombres
**`docs/13_convenciones_buenas_practicas/convenciones_de_nombres.md`**  
Reglas de nombres para carpetas, clases, entidades, endpoints y documentos.

#### Paso 11.1. Fijar la estructura del repositorio
**`docs/13_convenciones_buenas_practicas/estructura_repositorio.md`**  
Describe la organización del repo y el porqué de cada carpeta principal.

#### Paso 11.2. Formalizar el marco de errores
**`docs/13_convenciones_buenas_practicas/manejo_de_errores.md`**  
Expone el marco conceptual de errores de dominio, aplicación e infraestructura.

#### Paso 11.3. Fijar patrones y estructuras recurrentes
**`docs/13_convenciones_buenas_practicas/patrones_y_estructuras.md`**  
Resume patrones de diseño y estructuras de datos adoptadas o recomendadas.

#### Paso 11.4. Fijar estrategia de testing y calidad
**`docs/13_convenciones_buenas_practicas/testing_y_calidad.md`**  
Define tipos de pruebas, umbrales mínimos y estrategia general de calidad.

### M. UX y material visual
#### Paso 12.0. Definir wireframes del consultorio
**`docs/14_wireframes_ux/wireframes_consultorio.md`**  
Wireframes o descripción de pantallas clave del consultorio.

#### Paso 12.1. Definir wireframes de farmacia
**`docs/14_wireframes_ux/wireframes_farmacia.md`**  
Wireframes o descripción de catálogo y panel de farmacia.

#### Paso 12.2. Fijar criterios transversales de UX
**`docs/14_wireframes_ux/criterios_ux.md`**  
Principios de claridad, accesibilidad y usabilidad en este contexto.

### N. Plantillas y checklists
#### Paso 13.0. Crear plantilla de casos de uso
**`docs/16_plantillas/plantilla_caso_de_uso.md`**  
Plantilla estándar para documentar casos de uso.

#### Paso 13.1. Crear plantilla de ADRs
**`docs/16_plantillas/plantilla_adr.md`**  
Plantilla para registrar decisiones arquitectónicas.

#### Paso 13.2. Crear checklist de ejecución local
**`docs/17_checklists/checklist_lanzamiento_local.md`**  
Lista de control para considerar el proyecto listo para correr localmente.

#### Paso 13.3. Crear checklist de publicación en GitHub
**`docs/17_checklists/checklist_publicacion_github.md`**  
Lista de control para dejar el repositorio limpio, presentable y publicable.

## 21.19. Secuencia recomendada de producción documental
Si se busca una secuencia concreta para pedirle trabajo a otra IA, una ruta razonable sería esta:

1. `00_contexto_maestro/README.md`
2. `00_contexto_maestro/vision_producto.md`
3. `00_contexto_maestro/alcance_v1.md`
4. `01_dominio_consultorio/*`
5. `02_dominio_farmacia/*`
6. `03_arquitectura_general/*`
7. `04_system_design/*`
8. `15_adrs/adrs.md`
9. `05_base_datos/*`
10. `13_convenciones_buenas_practicas/estructura_repositorio.md`
11. `13_convenciones_buenas_practicas/convenciones_de_nombres.md`
12. `13_convenciones_buenas_practicas/manejo_de_errores.md`
13. `06_backend_consultorio/*`
14. `07_backend_farmacia/*`
15. `08_javafx_consultorio/*`
16. `09_angular_farmacia/*`
17. `10_seguridad_privacidad/*`
18. `11_logs_trazabilidad_auditoria/*`
19. `12_despliegue_operacion/*`
20. `13_convenciones_buenas_practicas/testing_y_calidad.md`
21. `14_wireframes_ux/*`
22. `16_plantillas/*`
23. `17_checklists/*`

Esta secuencia no es obligatoria, pero sí ayuda a que la documentación nazca en un orden más natural: primero contexto, luego dominio, luego arquitectura, luego persistencia y convenciones, y solo después detalles por componente.

## 22. Idea de reutilización futura
Aunque este producto nace inspirado por un caso concreto de consultorio con farmacia en un barrio de Guayaquil, la intención es que la solución o parte de ella pueda reutilizarse para:

- otro consultorio similar
- farmacia sola
- pequeño centro de atención
- negocio mixto similar
- portafolio profesional
- evidencia de experiencia ante empleadores

## 23. Idea de organización de carpetas del proyecto
Esta sección resume la organización conceptual gruesa del proyecto. La idea central es distinguir entre documentación verdaderamente transversal y documentación especializada por producto de software.

A nivel de componentes principales, la organización conceptual del proyecto sería:

- `docs/` para el contexto global y las decisiones transversales
- `database/` para la base de datos y su documentación específica
- `backend-consultorio/` para el backend del consultorio y su documentación ## 24. Idea de organización documental dentro de docs
La carpeta `docs/` de la raíz debe contener solo documentación transversal, fundacional y de coordinación general. No debe convertirse en un espejo completo de todos los componentes, porque eso duplica demasiado y vuelve confuso el proyecto.

Por eso, dentro de `docs/` conviene dejar únicamente áreas como estas:

- `00_contexto_maestro/`
- `01_dominio_consultorio/`
- `02_dominio_farmacia/`
- `03_arquitectura_general/`
- `04_system_design/`
- `10_seguridad_privacidad/`
- `11_logs_trazabilidad_auditoria/`
- `12_despliegue_operacion/`
- `13_convenciones_buenas_practicas/`
- `14_wireframes_ux/`
- `15_adrs/`
- `16_plantillas/`
- `17_checklists/`

En cambio, todo lo que ya sea diseño técnico detallado de base de datos, backend, JavaFX o Angular debería vivir principalmente dentro de la carpeta `docs/` del componente correspondiente.

## 24.1. Cómo leer este documento: principios, decisiones y sugerencias
- 12_despliegue_operacion/
- 13_convenciones_buenas_practicas/
- 14_wireframes_ux/
- 15_adrs/
- 16_plantillas/
- 17_checklists/

En esta propuesta, la carpeta `15_adrs/` existe para centralizar decisiones arquitectónicas relevantes, aunque parte de su contenido pueda relacionarse directamente con system design. La separación se mantiene porque ayuda a consultar decisiones clave sin mezclarlas con el resto de la explicación estructural del sistema.

## 24.1. Cómo leer este documento: principios, decisiones y sugerencias
Para que este lienzo se use bien en otros chats de IA, conviene distinguir tres niveles de afirmación que aparecen a lo largo del documento.

### A. Principios
Los principios son reglas guía relativamente estables. No describen todavía una implementación exacta, pero sí marcan cómo debería pensarse y evaluarse el proyecto. Por ejemplo: separación de responsabilidades, modularidad clara, trazabilidad mínima útil, estructura limpia del repositorio o crecimiento gradual dentro de una sola V1.

Los principios sirven para mantener coherencia cuando luego otra IA proponga carpetas, clases, endpoints, pantallas o documentos.

### B. Decisiones
Las decisiones son elecciones concretas ya asumidas en este proyecto, al menos a nivel de intención actual. Por ejemplo: usar JavaFX para el consultorio, Angular para la farmacia, PostgreSQL para persistencia, Temurin 21 como base Java, una sola V1 amplia, y una estrategia documental fuertemente ramificada.

Las decisiones pueden evolucionar si el proyecto cambia, pero deben tratarse como el punto de partida oficial mientras no exista una ADR o documento posterior que las modifique.

### C. Sugerencias
Las sugerencias son propuestas razonables que ayudan a ordenar o enriquecer el proyecto, pero que todavía no deben leerse como obligación rígida. Por ejemplo: crear ciertos scripts, incorporar ciertos checklists, añadir cierta convención visual o separar aún más algunos documentos.

Las sugerencias sirven para guiar a la IA sin convertir cada idea en una restricción absoluta demasiado temprano.

### D. Cómo aplicar esta distinción en la práctica
Cuando otra IA genere documentación o código a partir de este lienzo, debería:

- respetar los principios como reglas guía
- asumir las decisiones como base vigente
- tratar las sugerencias como opciones bien fundamentadas, pero ajustables

Esta separación ayuda a reducir contradicciones y evita que el proyecto se vuelva ambiguo o excesivamente rígido.

## 25. Tesis práctica de este enfoque
La tesis central de este proyecto es que, para el tipo de carrera y estrategia que persigue el autor, el valor no está únicamente en escribir código, sino en construir una capacidad reproducible de crear software administrativo con ayuda de IA sobre una base documental muy sólida.

Eso implica que la documentación no compite contra la implementación, sino que la potencia. Cada decisión contextualizada hoy puede reducir errores mañana, mejorar prompts, facilitar código más coherente, y permitir que otros chats o herramientas trabajen con mucha menos ambigüedad.

Este enfoque también convierte al proyecto en una especie de laboratorio profesional personal: un espacio donde el autor puede consolidar dominio, arquitectura, lenguaje técnico, estilo documental y criterio de producto, sin depender de que un cliente real ya haya comprado formalmente el sistema.

La fuerza del proyecto no estará solo en el código, sino en la combinación de:

- contexto maestro sólido
- documentación ramificada por componente
- stack repetible
- dominio suficientemente entendido
- arquitectura razonable
- apoyo intensivo de IA sobre una base documental fuerte

## 26. Qué debe salir después de este lienzo
Este lienzo debe servir para generar luego:

- README maestro
- documento de dominio del consultorio
- documento de dominio de farmacia
- catálogo de actores
- catálogo de casos de uso
- reglas de negocio
- glosario del dominio
- arquitectura general
- system design
- modelo de datos
- contratos API
- manejo de errores
- estrategia de logs y auditoría
- estrategia de seguridad y privacidad
- documento JavaFX
- documento Angular
- documento de despliegue
- plantillas de PDF y Markdown

## 26.1. Árbol oficial de documentación del proyecto
A continuación se propone un árbol oficial centrado únicamente en documentación y archivos README. Esta estructura debe tratarse como el mapa canónico del proyecto para otras IAs y para futuras iteraciones del propio autor.

```text
consultorio-medico/
├── README.md
├── docs/
│   ├── 00_contexto_maestro/
│   │   ├── README.md
│   │   ├── vision_producto.md
│   │   ├── alcance_v1.md
│   │   ├── estrategia_documental.md
│   │   └── contexto_local_guayaquil.md
│   ├── 01_dominio_consultorio/
│   │   ├── README.md
│   │   ├── actores_y_flujos.md
│   │   ├── reglas_de_negocio.md
│   │   ├── casos_de_uso.md
│   │   ├── glosario.md
│   │   ├── eventos_del_dominio.md
│   │   ├── datos_minimos_requeridos.md
│   │   └── criterios_de_aceptacion.md
│   ├── 02_dominio_farmacia/
│   │   ├── README.md
│   │   ├── actores_y_flujos.md
│   │   ├── reglas_de_negocio.md
│   │   ├── casos_de_uso.md
│   │   ├── glosario.md
│   │   ├── eventos_del_dominio.md
│   │   ├── datos_minimos_requeridos.md
│   │   └── criterios_de_aceptacion.md
│   ├── 03_arquitectura_general/
│   │   ├── README.md
│   │   ├── arquitectura_general.md
│   │   ├── mapa_de_componentes.md
│   │   ├── fronteras_y_responsabilidades.md
│   │   ├── integracion_entre_componentes.md
│   │   └── decisiones_de_modularidad.md
│   ├── 04_system_design/
│   │   ├── README.md
│   │   ├── contexto_y_contenedores.md
│   │   ├── componentes_y_contratos.md
│   │   ├── vistas_de_despliegue_logico.md
│   │   ├── flujo_de_datos_principal.md
│   │   └── riesgos_y_tradeoffs.md
│   ├── 10_seguridad_privacidad/
│   │   ├── README.md
│   │   ├── modelo_de_acceso.md
│   │   ├── roles_y_permisos.md
│   │   ├── privacidad_y_datos_sensibles.md
│   │   ├── superficies_publicas_vs_privadas.md
│   │   └── politicas_de_proteccion.md
│   ├── 11_logs_trazabilidad_auditoria/
│   │   ├── README.md
│   │   ├── logging_y_observabilidad.md
│   │   ├── trazabilidad_y_auditoria.md
│   │   ├── catalogo_de_eventos.md
│   │   └── correlacion_y_diagnostico.md
│   ├── 12_despliegue_operacion/
│   │   ├── README.md
│   │   ├── onboarding.md
│   │   ├── entornos_y_configuracion.md
│   │   ├── scripts_y_arranque.md
│   │   ├── despliegue_local.md
│   │   ├── despliegue_demo.md
│   │   ├── backup_y_recuperacion.md
│   │   └── operacion_en_contexto_local.md
│   ├── 13_convenciones_buenas_practicas/
│   │   ├── README.md
│   │   ├── convenciones_de_nombres.md
│   │   ├── estructura_repositorio.md
│   │   ├── manejo_de_errores.md
│   │   ├── patrones_y_estructuras.md
│   │   ├── testing_y_calidad.md
│   │   ├── comentarios_y_documentacion_en_codigo.md
│   │   └── criterios_de_revision.md
│   ├── 14_wireframes_ux/
│   │   ├── README.md
│   │   ├── wireframes_consultorio.md
│   │   ├── wireframes_farmacia.md
│   │   ├── criterios_ux.md
│   │   ├── lineamientos_visuales.md
│   │   └── microcopys_y_mensajes.md
│   ├── 15_adrs/
│   │   ├── README.md
│   │   ├── adrs.md
│   │   └── plantilla_adr.md
│   ├── 16_plantillas/
│   │   ├── README.md
│   │   ├── plantilla_caso_de_uso.md
│   │   ├── plantilla_regla_de_negocio.md
│   │   ├── plantilla_contrato_api.md
│   │   └── plantilla_componente.md
│   └── 17_checklists/
│       ├── README.md
│       ├── checklist_lanzamiento_local.md
│       ├── checklist_publicacion_github.md
│       ├── checklist_calidad_v1.md
│       └── checklist_pre_entrega.md
├── database/
│   ├── README.md
│   └── docs/
│       ├── README.md
│       ├── vision_persistencia.md
│       ├── modelo_relacional.md
│       ├── diccionario_de_datos.md
│       ├── reglas_de_persistencia.md
│       ├── restricciones_integridad.md
│       ├── indices_y_rendimiento.md
│       ├── estrategia_de_claves_y_relaciones.md
│       ├── migraciones_y_seeds.md
│       ├── auditoria_y_trazabilidad_bd.md
│       ├── backup_y_recuperacion.md
│       └── convenciones_de_nombres_bd.md
├── backend-consultorio/
│   ├── README.md
│   └── docs/
│       ├── README.md
│       ├── vision_backend.md
│       ├── arquitectura_backend.md
│       ├── estructura_de_paquetes.md
│       ├── modulos_y_responsabilidades.md
│       ├── contratos_api.md
│       ├── dto_y_modelos.md
│       ├── servicios_y_casos_de_uso.md
│       ├── reglas_de_validacion.md
│       ├── errores_y_validaciones.md
│       ├── seguridad_y_autorizacion.md
│       ├── logging_y_trazabilidad.md
│       ├── testing_backend.md
│       ├── convenciones_backend.md
│       └── onboarding_backend.md
├── backend-farmacia/
│   ├── README.md
│   └── docs/
│       ├── README.md
│       ├── vision_backend.md
│       ├── arquitectura_backend.md
│       ├── estructura_de_paquetes.md
│       ├── modulos_y_responsabilidades.md
│       ├── contratos_api.md
│       ├── dto_y_modelos.md
│       ├── servicios_y_casos_de_uso.md
│       ├── reglas_de_validacion.md
│       ├── errores_y_validaciones.md
│       ├── seguridad_y_autorizacion.md
│       ├── logging_y_trazabilidad.md
│       ├── testing_backend.md
│       ├── convenciones_backend.md
│       └── onboarding_backend.md
├── desktop-consultorio-javafx/
│   ├── README.md
│   └── docs/
│       ├── README.md
│       ├── vision_desktop.md
│       ├── arquitectura_desktop.md
│       ├── estructura_de_paquetes.md
│       ├── pantallas_y_flujos.md
│       ├── navegacion_y_estado_ui.md
│       ├── componentes_reutilizables.md
│       ├── formularios_y_validaciones.md
│       ├── manejo_de_errores_ui.md
│       ├── convenciones_ui.md
│       ├── recursos_y_assets.md
│       ├── testing_desktop.md
│       ├── accesibilidad_y_usabilidad.md
│       └── onboarding_desktop.md
└── storefront-farmacia-angular/
    ├── README.md
    └── docs/
        ├── README.md
        ├── vision_frontend.md
        ├── arquitectura_frontend.md
        ├── estructura_de_carpetas.md
        ├── paginas_y_componentes.md
        ├── rutas_y_navegacion.md
        ├── consumo_de_api.md
        ├── formularios_y_validaciones.md
        ├── manejo_de_estado.md
        ├── manejo_de_errores_ui.md
        ├── convenciones_ui.md
        ├── assets_y_recursos.md
        ├── testing_frontend.md
        └── onboarding_frontend.md
```

## 26.2. Cómo interpretar este árbol Cómo interpretar este árbol
Este árbol se concentra únicamente en documentación y archivos README, porque en esta etapa lo importante es fijar el esqueleto documental con el que otra IA podrá trabajar con máxima consistencia.

Por un lado, existe una carpeta `docs/` global en la raíz, que funciona como fuente canónica y transversal del proyecto. Ahí vive el contexto maestro, el dominio, la arquitectura general, los principios, la seguridad, la trazabilidad, el onboarding y las plantillas.

Por otro lado, cada componente importante también tiene su propia carpeta `docs/`, más pequeña y localizada, para que el componente pueda diseñarse y explicarse con un nivel mucho más específico. Esto permite que otra IA tome solo el componente relevante y aún así tenga una guía suficientemente detallada.

La regla sugerida es esta:

- `docs/` raíz = documentación transversal, fundacional y de referencia general
- `docs/` por componente = documentación específica para diseñar ese producto de software desde cero con alta consistencia

## 26.3. Sugerencia de consistencia entre documentación global y local
Para no generar contradicciones, conviene que los documentos locales de cada componente hereden y desarrollen la documentación global correspondiente, sin competir con ella.

Por ejemplo:

- `docs/05_base_datos/modelo_relacional.md` define el diseño conceptual de persistencia del proyecto
- `database/docs/modelo_relacional.md` puede desarrollarlo con mayor nivel de detalle operativo

- `docs/06_backend_consultorio/arquitectura_backend.md` define la idea general del backend de consultorio
- `backend-consultorio/docs/arquitectura_backend.md` puede ser una versión más cercana a la implementación concreta del componente

- `docs/08_javafx_consultorio/pantallas_y_flujos.md` define pantallas y comportamiento esperado
- `desktop-consultorio-javafx/docs/pantallas_y_flujos.md` puede traducirlo al diseño real del cliente desktop

- `docs/09_angular_farmacia/arquitectura_fro## 26.2. Cómo interpretar este árbol
Este árbol se concentra únicamente en documentación y archivos README, porque en esta etapa lo importante es fijar el esqueleto documental con el que otra IA podrá trabajar con máxima consistencia.

La idea central es que la carpeta `docs/` de la raíz ya no cargue documentación altamente especializada de base de datos, backend o frontend. Esa documentación vive mejor dentro del componente correspondiente.

Por eso la regla sugerida queda así:

- `docs/` raíz = documentación transversal, fundacional y de referencia general
- `database/docs/` = diseño técnico detallado de persistencia
- `backend-*/docs/` = diseño técnico detallado de backends
- `desktop-*/docs/` = diseño técnico detallado del cliente desktop
- `storefront-*/docs/` = diseño técnico detallado del frontend web

## 26.3. Sugerencia de consistencia entre documentación global y localalle como para diseñar ese producto de software casi desde cero

En particular, para base de datos, backend, JavaFX y Angular, la IA debería tratar cada carpeta `docs/` local como un mini paquete de diseño técnico del componente, no como una simple copia reducida de la documentación global.

## 27. Resumen final
Este documento no debe leerse como una simple descripción de un software posible, sino como la semilla estratégica de una línea de trabajo más amplia. Resume una forma de pensar productos administrativos pequeños, adaptables y bien documentados, pensados para contextos urbanos reales de Guayaquil y para usuarios que valoran más la claridad práctica que la sofisticación técnica visible.

También condensa una apuesta metodológica: usar la documentación como palanca para trabajar mejor con IA, para modular el conocimiento del proyecto y para sostener una línea de desarrollo profesional más estable, reutilizable y presentable.

En conjunto, el proyecto debe entenderse como un sistema híbrido pequeño, serio y escalable dentro de una sola V1 amplia, pensado para un contexto real de barrio en Guayaquil, con usuarios no técnicos, baja tolerancia al lenguaje informático y necesidad de ver valor tangible.

El objetivo no es solo construir software para un posible cliente, sino construir una base profesional de producto, arquitectura y documentación que permita:

- acelerar trabajo con IA
- generar documentos derivados
- producir código con más consistencia
- vender o adaptar la solución a futuros clientes
- fortalecer portafolio y experiencia laboral real

Este documento debe usarse como semilla fundacional para todos los demás documentos del proyecto.

