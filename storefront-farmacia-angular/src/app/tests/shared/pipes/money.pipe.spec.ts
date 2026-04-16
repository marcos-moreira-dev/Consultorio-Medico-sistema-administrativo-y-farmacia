import { MoneyPipe } from '../../../shared/pipes/money.pipe';

describe('MoneyPipe', () => {
  let pipe: MoneyPipe;

  beforeEach(() => {
    pipe = new MoneyPipe();
  });

  it('debe formatear montos en USD para es-EC', () => {
    const result = pipe.transform(12.5);

    expect(result).toContain('12');
    expect(result).toContain('50');
  });

  it('debe tolerar valores vacíos devolviendo 0', () => {
    expect(pipe.transform(undefined)).toBe(pipe.transform(0));
    expect(pipe.transform(null)).toBe(pipe.transform(0));
  });
});
