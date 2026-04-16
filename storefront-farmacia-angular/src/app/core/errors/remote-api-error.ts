import { AppError } from './app-error';

export class RemoteApiError extends AppError {
  constructor(
    message: string,
    code = 'REMOTE_API_ERROR',
    correlationId?: string,
    public readonly details?: unknown,
  ) {
    super(message, code, correlationId);
    this.name = 'RemoteApiError';
  }
}
