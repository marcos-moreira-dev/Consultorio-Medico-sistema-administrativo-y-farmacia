export interface ApiResponseDto<TData> {
  ok: boolean;
  data: TData;
  message?: string;
  correlationId?: string;
  timestamp?: string;
}
