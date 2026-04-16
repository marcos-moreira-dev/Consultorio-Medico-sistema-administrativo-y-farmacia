/**
 * Configuración centralizada del backend.
 */
export default () => {
  const publicBaseUrl = process.env.PUBLIC_BASE_URL ?? 'http://localhost:3001';

  return {
    app: {
      env: process.env.APP_ENV ?? 'local',
      port: Number(process.env.APP_PORT ?? 3001),
      apiPrefix: process.env.API_PREFIX ?? 'api/v1',
      swaggerPath: process.env.SWAGGER_PATH ?? 'docs',
      publicBaseUrl,
      corsAllowedOrigins: process.env.CORS_ALLOWED_ORIGINS ?? '',
    },
    jwt: {
      secret: process.env.JWT_SECRET ?? 'CAMBIAR_EN_ENTORNO_REAL',
      expiresIn: process.env.JWT_EXPIRES_IN ?? '1d',
    },
    db: {
      host: process.env.DB_HOST ?? 'localhost',
      port: Number(process.env.DB_PORT ?? 5432),
      name: process.env.DB_NAME ?? 'farmacia_db',
      user: process.env.DB_USER ?? 'postgres',
      password: process.env.DB_PASSWORD ?? 'postgres',
      schema: process.env.DB_SCHEMA ?? 'farmacia',
      sync: (process.env.DB_SYNC ?? 'false') === 'true',
      logging: (process.env.DB_LOGGING ?? 'false') === 'true',
    },
    storage: {
      root: process.env.STORAGE_ROOT ?? './storage',
      mediaBaseUrl: process.env.MEDIA_BASE_URL ?? `${publicBaseUrl}/media`,
      maxFileSizeMb: Number(process.env.MAX_FILE_SIZE_MB ?? 5),
    },
  };
};
