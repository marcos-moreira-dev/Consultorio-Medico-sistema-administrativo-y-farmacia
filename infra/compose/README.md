# Infraestructura compartida con Docker Compose

Esta carpeta ya no es solo una intención documental. En esta tanda queda una **stack local de farmacia** pensada para desarrollo y validación integrada.

## Archivo operativo actual

- `docker-compose.local.yml`

## Qué levanta hoy

- `postgres-farmacia`
- `backend-farmacia`
- `storefront-farmacia`

## Cómo se usa desde Windows

Desde la raíz del repo:

```bat
run_all.bat up
```

Comandos útiles:

```bat
run_all.bat logs
run_all.bat ps
run_all.bat down
run_all.bat rebuild
run_all.bat resetdb
```

## Objetivo de esta stack

Servir como validación local razonable de la parte **farmacia** del proyecto sin obligarte a arrancar cada componente por separado.

## Nota importante

La stack actual está orientada a desarrollo local. No debe confundirse con una definición final de demo o producción.
