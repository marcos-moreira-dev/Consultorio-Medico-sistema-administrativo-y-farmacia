# Estrategia de configuración global

## Propósito

Definir cómo se distribuye la configuración en el proyecto para evitar mezclar configuración global, configuración de componente y secretos sensibles.

## Principio general

La configuración del proyecto debe repartirse por niveles:

- raíz del repositorio;
- configuración global compartida;
- configuración específica de cada componente;
- secretos fuera del repo.

## Nivel 1. Raíz del repositorio

La raíz coordina el proyecto y contiene:

- `.gitignore`
- `.gitattributes`
- `.editorconfig`
- `run_all.bat`
- `README.md`

Aquí no deben vivir configuraciones internas de negocio de cada componente.

## Nivel 2. `config/`

La carpeta `config/` debe servir para plantillas y referencias globales del proyecto.

### Subcarpetas sugeridas
- `config/env/`
- `config/profiles/`

### Contenido razonable
- plantillas globales de variables;
- referencias de perfiles `local`, `demo`, `prod`;
- notas o convenciones compartidas.

## Nivel 3. Configuración por componente

Cada componente conserva su configuración propia.

### `backend-consultorio`
- `application.yml`
- `application-dev.yml`
- `application-prod.yml`
- `.env.example` si se requiere

### `backend-farmacia`
- `.env.example`
- archivos de configuración del stack Nest/Node

### `desktop-consultorio-javafx`
- configuración de proyecto Maven;
- posible archivo de propiedades o equivalente si luego se necesita;
- endpoint base configurable si aplica.

### `storefront-farmacia-angular`
- `.env.example` o estrategia equivalente del frontend;
- configuración de build;
- configuración de entornos del frontend.

### Bases de datos
- scripts y configuraciones de study/demo claramente separados de producción;
- seeds y migraciones documentadas.

## Nivel 4. Secretos

Los secretos reales no deben subirse al repositorio.

### Ejemplos
- contraseñas reales;
- tokens reales;
- URIs sensibles;
- claves privadas.

Lo que sí debe existir son plantillas:
- `.env.example`
- perfiles de ejemplo;
- comentarios explicativos.

## Relación con `local`, `demo` y `prod`

La estrategia global debe permitir que cada componente se conecte a esos contextos sin pelear con los demás.

### Ejemplo mental
- raíz coordina;
- `config/` documenta;
- componente implementa;
- secreto real se inyecta fuera del repo.

## Qué evitar

- meter toda la configuración del proyecto en la raíz;
- duplicar la misma variable en cinco lugares sin criterio;
- subir secretos reales;
- confundir configuración de entorno con documentación.

## Resultado esperado

La estrategia de configuración global debe dejar claro quién coordina, quién implementa y dónde vive cada cosa, para que el proyecto pueda crecer sin convertirse en una mezcla confusa de archivos repetidos y secretos expuestos.