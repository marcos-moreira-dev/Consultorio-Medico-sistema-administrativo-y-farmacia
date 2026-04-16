# MANUAL_USUARIO_CONSULTORIO

## Propósito de este documento

Este documento funciona como **manual de uso orientado al operador o validador funcional** de la interfaz del consultorio. Está pensado para una persona que quiere probar el sistema como herramienta administrativa, no para una persona que quiere leer arquitectura o clases Java.

La referencia principal de este manual es la combinación de:

- documentación de `desktop-consultorio-javafx`;
- documentación de `backend-consultorio`;
- pantallas, rutas, módulos y servicios ya sembrados en el cliente desktop.

## Qué es el consultorio dentro del proyecto

El consultorio, en esta versión, se presenta como una **aplicación de escritorio administrativa**. Debe sentirse como estación de trabajo interna, no como página web publicitaria.

La experiencia esperada se organiza alrededor de:

- login;
- shell principal;
- dashboard;
- pacientes;
- citas;
- profesionales;
- atenciones;
- cobros;
- reportes;
- auditoría.

## Idea central de uso

La app no gira alrededor de una sola pantalla, sino de un flujo administrativo. El usuario entra, se autentica, ve un resumen inicial y luego navega a módulos operativos según su rol.

## Qué roles existen en la interfaz

En la lógica actual del proyecto, la UI reconoce al menos estos roles:

- `ADMIN_CONSULTORIO`
- `OPERADOR_CONSULTORIO`
- `PROFESIONAL_CONSULTORIO`

El menú lateral y algunos accesos pueden variar según el rol. Eso significa que **no todos los usuarios deberían ver exactamente lo mismo**.

## Flujo base que debería poder probarse

### Caso 1. Abrir la aplicación y entrar al login

Al abrir el desktop, el usuario debería poder:

- ver una pantalla de acceso;
- ingresar credenciales;
- entrar a la aplicación si las credenciales son válidas;
- ver un mensaje comprensible si la autenticación falla o si el backend no responde.

### Caso 2. Entrar al shell principal

Después del login, el usuario debería poder:

- ver la estructura principal del sistema;
- reconocer el módulo activo;
- navegar desde una barra lateral o navegación principal;
- cerrar sesión.

### Caso 3. Ver el dashboard

El dashboard debería servir para orientarse rápidamente. El usuario debería poder:

- ver métricas o resúmenes operativos;
- entender el estado general del consultorio;
- usar el dashboard como punto de partida para entrar a módulos específicos.

### Caso 4. Consultar pacientes

En pacientes, la interfaz debería permitir por lo menos:

- listar registros;
- buscar o filtrar de forma simple;
- reconocer los campos principales del paciente.

Si existen formularios visibles, todavía conviene tratarlos como trabajo en progreso hasta validarlos completamente en local.

### Caso 5. Consultar agenda o citas

En citas, la interfaz debería permitir por lo menos:

- ver listado o agenda básica;
- reconocer fecha, hora, paciente, profesional y estado;
- filtrar o buscar de forma simple.

### Caso 6. Consultar profesionales

En profesionales, el usuario debería poder:

- ver listado de profesionales;
- reconocer nombre, especialidad o datos visibles equivalentes;
- filtrar o buscar de forma simple.

### Caso 7. Consultar atenciones

En atenciones, el usuario debería poder:

- ver el historial o lista de atenciones;
- reconocer fecha, paciente, profesional y nota breve;
- filtrar o buscar en lo básico.

### Caso 8. Consultar cobros

En cobros, el usuario debería poder:

- ver lista de cobros;
- reconocer monto, estado, método y referencia operativa;
- filtrar o buscar de forma simple.

### Caso 9. Generar reportes

En reportes, la interfaz debería permitir:

- elegir un tipo o formato de reporte;
- lanzar la generación;
- guardar el archivo localmente si la operación fue exitosa.

En esta etapa, eso ya es una señal fuerte de utilidad aunque el módulo todavía no esté completamente refinado.

### Caso 10. Consultar auditoría

En auditoría, la interfaz debería permitir al menos:

- listar eventos auditados;
- buscar o filtrar de forma simple;
- ver usuario, tipo de evento, módulo y fecha.

Este módulo conviene validarlo especialmente con un perfil administrativo.

## Casos de uso concretos por tipo de usuario

### Caso de uso A. Administrador

1. Iniciar sesión.
2. Revisar dashboard.
3. Entrar a pacientes.
4. Entrar a citas.
5. Revisar cobros.
6. Generar un reporte.
7. Revisar auditoría.

Resultado esperado: la aplicación permite recorrer las áreas críticas del consultorio sin que el menú, los módulos o los permisos resulten contradictorios.

### Caso de uso B. Operador

1. Iniciar sesión.
2. Revisar dashboard.
3. Consultar pacientes.
4. Consultar citas.
5. Consultar atenciones.
6. Consultar cobros.
7. Generar reportes si el rol lo permite.

Resultado esperado: el operador ve lo necesario para la operación diaria, pero no módulos que deberían quedar restringidos.

### Caso de uso C. Profesional

1. Iniciar sesión.
2. Entrar al dashboard.
3. Consultar agenda o citas.
4. Consultar atenciones.
5. Generar reportes permitidos si aplica.

Resultado esperado: el profesional no ve una interfaz inflada con tareas administrativas que no le corresponden.

## Diferencia importante entre modo demo y modo remoto

La aplicación puede convivir con dos modos:

- **modo demo**, útil para revisar pantallas, navegación y comportamiento visual;
- **modo remoto**, útil para validar integración con el backend real.

Para revisión funcional seria del producto, lo ideal es priorizar el modo remoto. El modo demo sigue siendo útil para enseñar el flujo general cuando el backend no está levantado.

## Qué señales indican que el desktop ya está funcionando razonablemente bien

- el login es comprensible;
- la sesión entra y sale de forma coherente;
- el shell no se siente improvisado;
- la navegación cambia según rol;
- dashboard, pacientes, citas, profesionales, atenciones y cobros abren sin romper la app;
- reportes permite una generación temprana;
- auditoría es navegable al menos para admin;
- los errores remotos o de conectividad no destruyen toda la experiencia.

## Qué no conviene exigirle todavía a esta versión

No conviene evaluarla como si ya fuera un producto final completamente cerrado en:

- formularios complejos de alta/edición;
- validaciones visuales finas en todos los módulos;
- empaquetado instalable final para cliente;
- branding final y recursos visuales definitivos;
- manejo completo de reconexión, refresh token o persistencia local avanzada.

## Checklist corto para revisión manual

- [ ] El login abre y se entiende.
- [ ] Entrar y salir de sesión funciona.
- [ ] El shell principal carga bien.
- [ ] El menú visible cambia según rol.
- [ ] Dashboard abre y muestra información.
- [ ] Pacientes abre y lista información.
- [ ] Citas abre y lista información.
- [ ] Profesionales abre y lista información.
- [ ] Atenciones abre y lista información.
- [ ] Cobros abre y lista información.
- [ ] Reportes permite una generación temprana.
- [ ] Auditoría abre correctamente con rol admin.

## Criterio final

El consultorio debe sentirse como una **herramienta de trabajo administrativo sobria, estable y entendible**. Si una persona puede iniciar sesión, orientarse, navegar por módulos, entender qué ve y completar un recorrido razonable sin perderse, entonces la interfaz ya está cumpliendo bien su promesa principal en esta etapa.
