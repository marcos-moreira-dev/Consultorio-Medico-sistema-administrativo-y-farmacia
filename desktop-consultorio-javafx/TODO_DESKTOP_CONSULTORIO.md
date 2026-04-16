# TODO específico - desktop-consultorio-javafx

## 1. Verificación local obligatoria

- [ ] copiar `scripts/toolchains-temurin-21.example.xml` a `%USERPROFILE%\.m2\toolchains.xml`;
- [ ] ajustar `jdkHome` a Temurin 21 real;
- [ ] ejecutar `scripts\verify-desktop-consultorio.bat`;
- [ ] ejecutar `scripts\dev-desktop-consultorio.bat`.

## 2. Arranque y shell

- [ ] validar que el bootstrap JavaFX abra ventana real;
- [ ] decidir si la primera pantalla será login o shell con dashboard dummy;
- [ ] reemplazar el shell provisional por navegación real del consultorio.

## 3. Integración con backend

- [ ] alinear definitivamente `BACKEND_BASE_URL` con `backend-consultorio`;
- [ ] probar login real contra `/api/v1/auth/login`;
- [ ] almacenar sesión y token;
- [ ] poblar al menos dashboard, pacientes y citas con datos reales.

## 4. Sistema visual

- [ ] convertir los CSS `TODO` en tokens reales;
- [ ] unificar spacing, tipografía, colores y radios;
- [ ] decidir iconografía definitiva.

## 5. Componentes y vistas

- [ ] revisar qué controladores/vistas/viewmodels están vacíos;
- [ ] priorizar login, shell, dashboard, pacientes, citas y cobros;
- [ ] dejar reportes y auditoría después del flujo base.

## 6. Testing

- [ ] reemplazar tests placeholder por pruebas reales de viewmodels y navegación;
- [ ] añadir smoke test de arranque y sesión si el stack lo permite.
