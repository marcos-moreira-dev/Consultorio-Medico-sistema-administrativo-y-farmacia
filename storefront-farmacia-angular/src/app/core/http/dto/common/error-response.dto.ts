export interface ErrorResponseDto {
  ok: false;
  error?: {
    code?: string;
    message?: string;
    details?: unknown;
  };
  correlationId?: string;
  timestamp?: string;
  message?: string;
}
