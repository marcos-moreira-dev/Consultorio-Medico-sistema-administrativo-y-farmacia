# Auth y sesiones

## Propósito

Aterrizar el funcionamiento de autenticación y sesión técnica del `backend-farmacia`, separándolo del documento general de seguridad para dejar claro cómo entra un usuario administrativo al sistema, qué devuelve el login y cómo se valida una sesión.

## Principio general

El backend de farmacia tiene una superficie pública sin login y una superficie administrativa privada. Por eso, el flujo de autenticación no gobierna todo el backend, pero sí es absolutamente central para la parte admin.

## Modelo de autenticación adoptado

Se adopta un esquema clásico basado en:

- credenciales administrativas internas;
- autenticación contra usuario admin válido;
- emisión de JWT;
- validación del token en cada request protegida.

## Qué se autentica

Se autentican **usuarios administrativos internos**, no visitantes públicos, no clientes finales y no consumidores del catálogo público.

Eso significa que la identidad técnica de acceso es `ADMIN_FARMACIA` o un usuario equivalente de la superficie administrativa.

## Flujo básico de autenticación

### Paso 1. Login admin
El cliente administrativo envía credenciales válidas.

### Paso 2. Verificación
El backend verifica:
- existencia del usuario admin;
- estado del usuario;
- validez de credenciales;
- consistencia del rol admin;
- cualquier otra condición técnica mínima.

### Paso 3. Emisión de token
Si todo es correcto, el backend emite un JWT.

### Paso 4. Uso del token
El cliente administrativo envía ese token en requests posteriores a rutas protegidas.

### Paso 5. Validación por request
El backend valida el token y reconstruye el contexto del usuario autenticado para aplicar autorización.

## Qué debe devolver el login

La respuesta de login debe ser útil, pero no excesiva.

Como mínimo debería devolver:

- token de acceso;
- tipo de token;
- datos básicos del usuario admin autenticado;
- rol principal.

No conviene devolver en login información masiva o irrelevante del sistema.

## Sesión técnica

La sesión en este backend debe entenderse como **sesión stateless soportada por JWT**.

Eso implica:

- el backend no mantiene una sesión servidor clásica para cada cliente;
- la validez se apoya en el token y su verificación;
- el contexto de autorización se reconstruye por request.

## Refresh token

Para esta V1, la postura recomendada es **conservadora**:

- puede empezar sin refresh token para mantener el sistema más simple;
- si luego se incorpora, debe documentarse de forma explícita y no improvisarse a medias.

Como este proyecto busca una base clásica y muy estudiable, una V1 con access token bien documentado puede ser suficiente.

## Relación con la superficie pública

La autenticación admin no debe contaminar la experiencia pública del catálogo.

Eso significa:
- el catálogo público no exige token;
- los controladores públicos deben permanecer limpios;
- la parte admin sí debe quedar claramente protegida por guards o equivalente.

## Qué rutas quedan fuera del requisito de token

En principio, deberían quedar libres:

- login admin;
- endpoints públicos de catálogo;
- quizá algún endpoint técnico de salud si decides crearlo;
- documentación Swagger solo si deliberadamente quieres dejarla visible en desarrollo, nunca por descuido en una demo o entorno serio.

## Criterios para JWT

### El token debe ser suficiente para resolver identidad
No conviene hacerlo gigante ni cargarlo de datos redundantes.

### Debe existir expiración clara
No conviene token eterno.

### Debe usarse una clave bien gestionada
No hardcodeada sin control en el proyecto.

### Debe integrarse con Nest de forma natural
No como middleware improvisado sin disciplina.

## Logout

Dado que el backend es stateless, el logout se entiende principalmente como:

- eliminación del token del lado cliente;
- o invalidación deliberada si luego decides implementar estrategia adicional.

Para V1, una estrategia simple y explícita es suficiente.

## Qué no debe pasar

- mezclar login admin con el catálogo público;
- devolver información innecesaria en la respuesta de auth;
- esconder reglas de sesión solo en frontend;
- dejar tokens sin expiración clara;
- improvisar seguridad alrededor de JWT sin modelo definido.

## Resultado esperado

El documento de auth y sesiones debe dejar listo un flujo clásico, privado y profesional para la superficie administrativa de `backend-farmacia`, suficientemente claro como para implementarse sin ambigüedad y suficientemente simple como para estudiarse bien después.

