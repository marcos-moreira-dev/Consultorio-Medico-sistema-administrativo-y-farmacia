import 'reflect-metadata';

import { join } from 'node:path';

import { DataSource, DataSourceOptions } from 'typeorm';

/**
 * Opciones base de DataSource para TypeORM.
 */
export const dataSourceOptions: DataSourceOptions = {
  type: 'postgres',
  host: process.env.DB_HOST ?? 'localhost',
  port: Number(process.env.DB_PORT ?? 5432),
  username: process.env.DB_USER ?? 'postgres',
  password: process.env.DB_PASSWORD ?? 'postgres',
  database: process.env.DB_NAME ?? 'farmacia_db',
  schema: process.env.DB_SCHEMA ?? 'farmacia',
  synchronize: (process.env.DB_SYNC ?? 'false') === 'true',
  logging: (process.env.DB_LOGGING ?? 'false') === 'true',
  entities: [join(__dirname, '..', 'modules', '**', 'entities', '*.entity.{ts,js}')],
  migrations: [join(__dirname, 'migrations', '*.{ts,js}')],
};

const appDataSource = new DataSource(dataSourceOptions);

export default appDataSource;
