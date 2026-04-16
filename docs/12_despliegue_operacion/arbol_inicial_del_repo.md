# Árbol inicial del repo

## Propósito

Definir el árbol inicial canónico del repositorio para que el proyecto nazca con una estructura profesional, entendible y reutilizable en futuros sistemas.

## Principio general

El árbol inicial no debe llenarse de basura generada ni de carpetas arbitrarias. Debe separar con claridad:

- documentación general;
- infraestructura compartida;
- configuración global;
- componentes de software;
- bases de datos;
- scripts de apoyo.

## Árbol inicial recomendado

```text
consultorio-medico/
├── README.md
├── .gitignore
├── .gitattributes
├── .editorconfig
├── run_all.bat
│
├── config/
│   ├── env/
│   └── profiles/
│
├── docs/
│   ├── 00_contexto_maestro/
│   ├── 01_dominio_consultorio/
│   ├── 02_dominio_farmacia/
│   ├── 03_arquitectura_general/
│   ├── 04_system_design/
│   ├── 10_seguridad_privacidad/
│   ├── 11_logs_trazabilidad_auditoria/
│   ├── 12_despliegue_operacion/
│   ├── 13_convenciones_buenas_practicas/
│   ├── 14_wireframes_ux/
│   ├── 15_adrs/
│   ├── 16_plantillas/
│   └── 17_checklists/
│
├── infra/
│   ├── compose/
│   ├── docker/
│   └── scripts/
│
├── backend-consultorio/
│   ├── docs/
│   ├── .mvn/
│   │   └── wrapper/
│   ├── scripts/
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
│           ├── java/
│           └── resources/
│
├── backend-farmacia/
│   ├── docs/
│   ├── scripts/
│   ├── src/
│   │   ├── app/
│   │   ├── config/
│   │   ├── common/
│   │   └── assets/
│   └── test/
│
├── database-consultorio/
│   ├── docs/
│   ├── Design V1/
│   │   ├── diagrams/
│   │   └── Modelo lógico NO 3FN/
│   ├── scripts/
│   └── V2/
│       ├── docs/
│       ├── migrations/
│       │   └── flyway/
│       ├── routines/
│       ├── schema/
│       ├── security/
│       ├── seeds/
│       ├── tools/
│       └── views/
│
├── database-farmacia/
│   ├── docs/
│   ├── Design V1/
│   │   ├── diagrams/
│   │   └── Modelo lógico NO 3FN/
│   ├── scripts/
│   └── V2/
│       ├── docs/
│       ├── migrations/
│       │   └── flyway/
│       ├── routines/
│       ├── schema/
│       ├── security/
│       ├── seeds/
│       ├── tools/
│       └── views/
│
├── desktop-consultorio-javafx/
│   ├── docs/
│   ├── .mvn/
│   │   └── wrapper/
│   ├── scripts/
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
│           └── java/
│
└── storefront-farmacia-angular/
    ├── docs/
    ├── scripts/
    ├── public/
    └── src/
        ├── app/
        ├── assets/
        └── styles/
```

## Criterios del árbol

### 1. La raíz coordina
La raíz del repositorio debe contener solo lo transversal.

### 2. Cada componente manda en su propio stack
Cada backend o frontend debe tener su propia estructura interna sin depender de mezclas extrañas en la raíz.

### 3. Infraestructura compartida vive fuera de `src`
Docker, compose y scripts de infraestructura deben vivir en `infra/`.

### 4. Configuración global no reemplaza la configuración local
`config/` coordina perfiles y plantillas, pero no reemplaza `application.yml`, `package.json` o `angular.json`.

### 5. Documentación y código no se mezclan
Cada componente conserva su `docs/` local y el proyecto conserva además `docs/` general.

## Resultado esperado

Este árbol debe servir como esqueleto base del proyecto y como plantilla reutilizable para otros repos similares.