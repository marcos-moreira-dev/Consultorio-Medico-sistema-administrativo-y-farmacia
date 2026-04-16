# Modelo de acceso

## Propósito

Definir cómo se controla el acceso al sistema y qué principios rigen la autenticación y la autorización en la V1.

## Principio general

El acceso al sistema debe diseñarse según la sensibilidad de la superficie y no según comodidad de implementación. No todas las partes del producto requieren el mismo nivel de protección, pero todas deben tener reglas explícitas.

## Superficies de acceso del sistema

### Superficie privada de consultorio
Incluye pacientes, citas, atenciones, indicaciones y cobros.

**Requisito:**
Debe requerir autenticación y autorización explícita.

## Superficie administrativa de farmacia
Incluye creación, edición, publicación, inactivación y ajuste de disponibilidad de productos.

**Requisito:**
Debe requerir autenticación y autorización explícita.

## Superficie pública o semipública de farmacia
Incluye catálogo visible, búsqueda y detalle público de producto.

**Requisito:**
Puede consultarse sin autenticación, salvo que el diseño final defina una modalidad semipública restringida.

## Modelo de autenticación

Para la V1, se asume un esquema de autenticación razonable, suficiente y compatible con backend administrativo moderno.

### Principios
- autenticación obligatoria para superficies privadas e internas;
- sesiones o tokens gestionados por backend;
- credenciales nunca expuestas en clientes más allá del flujo necesario;
- control de acceso basado en rol y permisos.

### Criterio práctico de la V1
La solución debe ser lo bastante seria para representar una aplicación administrativa profesional, pero no sobrediseñada como plataforma empresarial compleja.

## Modelo de autorización

La autorización se define por combinación de:

- superficie funcional;
- rol del usuario;
- tipo de acción.

### Tipos de acción principales
- consultar;
- crear;
- actualizar;
- cambiar estado;
- administrar publicación;
- acceder a datos sensibles.

## Reglas generales de acceso

### ACC-001. Ninguna superficie privada debe exponerse sin autenticación
Aplica especialmente al consultorio y a toda administración interna.

### ACC-002. La autorización debe evaluarse en backend
La UI puede ocultar opciones por usabilidad, pero la decisión real de acceso debe consolidarse del lado servidor.

### ACC-003. Los contratos públicos y privados deben separarse
No se debe reutilizar por comodidad un endpoint privado como si fuera público.

### ACC-004. El principio de mínimo privilegio guía el diseño
Cada usuario debe ver y operar solo lo que necesita para su función.

### ACC-005. La capa pública de farmacia solo accede a información publicable
No debe conocer datos internos administrativos innecesarios.

## Usuarios considerados en la V1

### Usuario de consultorio
Accede a flujos privados del consultorio.

### Usuario administrativo de farmacia
Accede a mantenimiento y gestión del catálogo.

### Visitante externo
Accede únicamente al catálogo visible.

## Reglas de implementación derivadas

- rutas privadas protegidas;
- permisos por rol en backends;
- DTOs distintos para consumo público y privado;
- clientes desacoplados de la base de datos;
- errores de acceso tratados de forma uniforme.

## Evolución esperable

La V1.1 puede endurecer o ampliar reglas de acceso si aparecen nuevas capacidades como reservas en farmacia o trazabilidad más fina de agenda, pero sin alterar la lógica base: separación de superficies, autenticación en lo privado y exposición mínima en lo público.

## Resultado esperado

El modelo de acceso debe dejar claro quién entra, a qué entra y bajo qué condiciones, sirviendo como base para roles, permisos, contratos, endpoints y comportamiento de interfaz.

