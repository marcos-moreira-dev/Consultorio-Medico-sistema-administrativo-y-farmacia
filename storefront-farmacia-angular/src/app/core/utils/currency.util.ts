export class CurrencyUtil {
  private static readonly formatter = new Intl.NumberFormat('es-EC', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
  });

  static format(value: number | null | undefined): string {
    const numericValue = typeof value === 'number' && Number.isFinite(value) ? value : 0;

    return CurrencyUtil.formatter.format(numericValue);
  }
}
