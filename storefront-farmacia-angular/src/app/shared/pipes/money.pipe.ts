import { Pipe, PipeTransform } from '@angular/core';

import { CurrencyUtil } from '../../core/utils/currency.util';

@Pipe({
  name: 'money',
  standalone: true,
})
export class MoneyPipe implements PipeTransform {
  transform(value: number | null | undefined): string {
    return CurrencyUtil.format(value);
  }
}
