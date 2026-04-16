# Matriz local, demo y producción

## Propósito

Resumir de forma comparativa cómo se entienden los entornos `local`, `demo` y `prod` dentro de este proyecto, componente por componente.

## Matriz

| Componente | Local | Demo | Producción |
|---|---|---|---|
| backend-consultorio | desarrollo y estudio, logs verbosos, configuración flexible | configuración estable para mostrar funcionalidades, datos demo | configuración estricta, secretos externos, datos reales |
| backend-farmacia | desarrollo y pruebas del catálogo público/admin | demostración controlada de catálogo y administración | operación real con configuración más segura |
| desktop-consultorio-javafx | consumo de backend local o de prueba | consumo de backend demo para mostrar el producto | consumo de backend real con endpoints definitivos |
| storefront-farmacia-angular | desarrollo UI y consumo de API pública local | storefront demostrable con branding y datos demo | storefront real apuntando a backend productivo |
| database-consultorio | base de estudio o pruebas locales | base demo preparada para exhibición | base real con controles estrictos |
| database-farmacia | base de estudio o pruebas locales | base demo pública/controlada | base real con operación y seguridad completas |
| Docker / Compose | opcional o parcial | altamente útil para demo reproducible | posible, pero con configuración más cuidada |

## Interpretación práctica

### `local`
Sirve para:
- estudiar;
- programar;
- probar;
- depurar.

### `demo`
Sirve para:
- mostrar el proyecto;
- enseñar funcionalidad;
- probar flujos con una versión más limpia y controlada.

### `prod`
Sirve para:
- operar de verdad;
- cuidar datos reales;
- aplicar configuración estricta;
- evitar improvisación.

## Regla mental útil

- `local` = libertad para construir y aprender
- `demo` = control para mostrar
- `prod` = disciplina para operar

## Resultado esperado

Esta matriz debe servir como resumen ejecutivo técnico para no confundir entornos y para recordar que cada componente del ecosistema vive esos tres contextos de forma distinta.