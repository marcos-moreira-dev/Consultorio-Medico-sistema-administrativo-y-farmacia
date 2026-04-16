import { parseOptionalQueryBoolean } from '../../../../src/app/common/utils/parse-query-boolean.util';

describe('parseOptionalQueryBoolean', () => {
  it('debe convertir valores booleanos directos', () => {
    expect(parseOptionalQueryBoolean(true)).toBe(true);
    expect(parseOptionalQueryBoolean(false)).toBe(false);
  });

  it('debe convertir strings típicos a booleano', () => {
    expect(parseOptionalQueryBoolean('true')).toBe(true);
    expect(parseOptionalQueryBoolean('false')).toBe(false);
    expect(parseOptionalQueryBoolean('1')).toBe(true);
    expect(parseOptionalQueryBoolean('0')).toBe(false);
    expect(parseOptionalQueryBoolean(' TRUE ')).toBe(true);
    expect(parseOptionalQueryBoolean(' FALSE ')).toBe(false);
  });

  it('debe convertir números 1 y 0 a booleano', () => {
    expect(parseOptionalQueryBoolean(1)).toBe(true);
    expect(parseOptionalQueryBoolean(0)).toBe(false);
  });

  it('debe devolver undefined para ausencias o string vacío', () => {
    expect(parseOptionalQueryBoolean(undefined)).toBeUndefined();
    expect(parseOptionalQueryBoolean(null)).toBeUndefined();
    expect(parseOptionalQueryBoolean('')).toBeUndefined();
    expect(parseOptionalQueryBoolean('   ')).toBeUndefined();
  });

  it('debe devolver el valor original cuando no es interpretable', () => {
    expect(parseOptionalQueryBoolean('talvez')).toBe('talvez');
    expect(parseOptionalQueryBoolean(7)).toBe(7);
    expect(parseOptionalQueryBoolean({})).toEqual({});
  });
});
