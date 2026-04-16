# Auth y sesiones

## Propósito

Aterrizar el funcionamiento de autenticación y sesión técnica del `backend-consultorio`, separándolo del documento general de seguridad para dejar claro cómo se entra al sistema, qué devuelve el login y cómo se valida una sesión.

## Principio general

El backend del consultorio es completamente privado. Por eso, el flujo de autenticación no es opcional ni accesorio. Toda operación útil comienza con una identidad interna válida.

## Modelo de autenticación adoptado

Se adopta un esquema clásico basado en:

- credenciales internas;
- autenticación contra usuario activo del sistema;
- emisión de JWT;
- validación del token en cada request protegida.

## Qué se autentica

Se autentican **usuarios internos**, no pacientes, no clientes públicos y no profesionales como entidad aislada.

Eso significa que la identidad técnica de acceso es `usuario`, aunque un usuario pueda estar asociado a un `profesional`.

## Flujo básico de autenticación

### Paso 1. Login
El cliente del consultorio envía credenciales válidas.

### Paso 2. Verificación
El backend verifica:
- existencia del usuario;
- estado del usuario;
- validez de credenciales;
- consistencia del rol;
- cualquier otra condición técnica mínima.

### Paso 3. Emisión de token
Si todo es correcto, el backend emite un JWT.

### Paso 4. Uso del token
El cliente envía ese token en requests posteriores a rutas protegidas.

### Paso 5. Validación por request
El backend valida el token y reconstruye el contexto del usuario autenticado para aplicar autorización.

## Qué debe devolver el login

La respuesta de login debe ser útil, pero no excesiva.

Como mínimo debería devolver:

- token de acceso;
- tipo de token;
- datos básicos del usuario autenticado;
- rol principal;
- referencia a profesional cuando aplique.

No conviene devolver en login información masiva o irrelevante del sistema.

## Sesión técnica

La sesión en este backend debe entenderse como **sesión stateless soportada por JWT**.

Eso implica:

- el backend no mantiene una sesión servidor clásica para cada cliente;
- la validez se apoya en el token y su verificación;
- el contexto de autorización se reconstruye por request.

## Refresh token

Para esta V1, mi postura recomendada es **conservadora**:

- puedes empezar sin refresh token si quieres mantener el sistema más simple;
- si decides usarlo, debe documentarse con claridad y no meterse a medias.

Como este proyecto busca una base clásica y muy estudiable, una V1 con access token bien documentado puede ser suficiente.

## Criterios de validación del usuario

Al autenticar, debe verificarse al menos:

- que el usuario exista;
- que el usuario esté activo;
- que la contraseña coincida con el hash almacenado;
- que el rol asociado siga siendo válido;
- que cualquier asociación opcional a profesional no rompa el contexto esperado.

## Relación con roles

El proceso de login no solo autentica identidad. También debe preparar el contexto mínimo de autorización.

Eso significa que el token o el contexto derivado del token debe permitir conocer:

- `usuario_id`
- `rol`
- `profesional_id` si aplica

## Qué rutas quedan fuera del requisito de token

En principio, solo deberían quedar libres:

- login;
- quizá algún endpoint técnico de salud o ping si decides crearlo;
- documentación Swagger solo si deliberadamente quieres dejarla visible en desarrollo, nunca por descuido.

## Criterios para JWT

### El token debe ser suficiente para resolver identidad
No conviene hacerlo gigante ni cargarlo de datos redundantes.

### Debe existir expiración clara
No conviene token eterno.

### Debe usarse una clave bien gestionada
No hardcodeada sin control en el proyecto.

### Debe integrarse con Spring Security
No como filtro aislado inventado sin disciplina.

## Logout

Dado que el backend es stateless, el logout se entiende principalmente como:

- eliminación del token del lado cliente;
- o invalidación deliberada si luego decides implementar estrategia adicional.

Para V1, una estrategia simple y explícita es suficiente.

## Qué no debe pasar

- mezclar login con gestión completa de usuarios;
- devolver información innecesaria en la respuesta de auth;
- esconder reglas de sesión solo en frontend;
- dejar tokens sin expiración clara;
- improvisar seguridad alrededor de Spring Security sin modelo definido.

## Resultado esperado

El documento de auth y sesiones debe dejar listo un flujo clásico, privado y profesional para el backend-consultorio, suficientemente claro como para implementarse sin ambigüedad y suficientemente simple como para estudiarse bien después.