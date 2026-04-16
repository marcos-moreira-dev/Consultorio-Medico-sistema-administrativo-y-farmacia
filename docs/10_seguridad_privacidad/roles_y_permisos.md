# Roles y permisos

## Propósito

Definir los roles principales del sistema y los permisos que necesitan para operar cada superficie del producto con claridad y mínimo privilegio.

## Principio general

Los roles no deben existir solo como nombres decorativos. Deben expresar necesidades operativas reales y limitar el acceso de forma coherente con el subdominio y la sensibilidad de la información.

## Roles principales de la V1

### Rol 1. CONSULTORIO
Representa al usuario interno que opera la superficie privada del consultorio.

**Permisos esperados:**
- buscar pacientes;
- registrar pacientes;
- agendar citas;
- cancelar o reprogramar citas;
- registrar atención;
- registrar indicaciones breves;
- registrar cobro asociado a atención;
- consultar información privada del consultorio.

**Restricciones:**
- no administra catálogo público de farmacia por defecto;
- no debe acceder a funciones administrativas ajenas sin autorización explícita.

## Rol 2. FARMACIA_ADMIN
Representa al usuario interno que mantiene el catálogo y la disponibilidad de la farmacia.

**Permisos esperados:**
- crear productos;
- editar productos;
- publicar y despublicar productos;
- inactivar productos;
- ajustar disponibilidad o stock básico;
- consultar estado operativo de productos.

**Restricciones:**
- no accede a pacientes, citas ni atenciones del consultorio;
- no debe ver datos clínicos ni cobros de consulta.

## Rol 3. VISITANTE_PUBLICO
Representa al actor externo que consulta la parte visible de farmacia.

**Permisos esperados:**
- listar productos visibles;
- buscar productos visibles;
- consultar detalle público;
- revisar disponibilidad publicada.

**Restricciones:**
- no autentica en la superficie pública por defecto;
- no accede a funciones administrativas;
- no ve datos internos ni privados.

## Posibles roles de evolución

### Rol 4. SOPORTE o ADMIN_SISTEMA
No es obligatorio como rol operativo central en la V1, pero puede aparecer en evolución futura para mantenimiento, diagnóstico o administración más transversal.

## Matriz conceptual de permisos

### Consultorio
- CONSULTORIO: sí.
- FARMACIA_ADMIN: no.
- VISITANTE_PUBLICO: no.

### Farmacia administrativa
- CONSULTORIO: no por defecto.
- FARMACIA_ADMIN: sí.
- VISITANTE_PUBLICO: no.

### Farmacia pública
- CONSULTORIO: no la necesita para operar, aunque técnicamente podría verla como usuario normal.
- FARMACIA_ADMIN: sí, en cuanto consumidor de la vista pública.
- VISITANTE_PUBLICO: sí.

## Tipos de permiso por acción

### Lectura privada
Aplica a datos del consultorio o administración de farmacia.

### Escritura operativa
Aplica a creación y actualización de registros del negocio.

### Cambio de estado
Aplica a cancelaciones, reprogramaciones, publicación, inactivación y disponibilidad.

### Publicación
Aplica a decidir si un producto entra o no en la superficie visible.

## Reglas de diseño derivadas

### ROL-001. Un mismo usuario no necesita todos los permisos por defecto
La V1 debe privilegiar mínimo privilegio antes que amplitud total por comodidad.

### ROL-002. La UI puede reflejar permisos, pero no definirlos
Ocultar botones ayuda a la experiencia, pero la autorización real vive en backend.

### ROL-003. Los roles deben mapearse a superficies, no solo a pantallas
El mismo criterio de acceso debe sostenerse en rutas, datos y acciones.

### ROL-004. Los contratos públicos no requieren rol autenticado
La superficie pública de farmacia no depende de roles internos para su consulta básica.

## Resultado esperado

Los roles y permisos deben permitir modelar el acceso del sistema con suficiente claridad para endpoints, pantallas, seguridad backend y futuras pruebas de autorización, evitando tanto el exceso de rigidez como la permisividad difusa.

