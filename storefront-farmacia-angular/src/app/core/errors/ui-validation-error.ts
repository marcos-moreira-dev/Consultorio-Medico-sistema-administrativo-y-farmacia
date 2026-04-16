import { AppError } from './app-error';

export class UiValidationError extends AppError {
  constructor(message: string, code = 'UI_VALIDATION_ERROR') {
    super(message, code);
    this.name = 'UiValidationError';
  }
}
