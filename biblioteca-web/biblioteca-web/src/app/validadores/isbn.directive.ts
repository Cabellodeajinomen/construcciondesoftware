import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';

/** Comprueba formato y dígito verificador de un ISBN-10 o ISBN-13 (acepta guiones y espacios). */
export function esIsbnValido(valor: string): boolean {
  const s = valor.replace(/[-\s]/g, '').toUpperCase();
  if (/^\d{9}[\dX]$/.test(s)) {
    let suma = 0;
    for (let i = 0; i < 10; i++) suma += (s[i] === 'X' ? 10 : Number(s[i])) * (10 - i);
    return suma % 11 === 0;
  }
  if (/^\d{13}$/.test(s)) {
    let suma = 0;
    for (let i = 0; i < 13; i++) suma += Number(s[i]) * (i % 2 === 0 ? 1 : 3);
    return suma % 10 === 0;
  }
  return false;
}

@Directive({
  selector: '[isbn][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: IsbnValidator, multi: true }],
})
export class IsbnValidator implements Validator {
  /** Devuelve el error "isbn" si el valor no es un ISBN válido. */
  validate(control: AbstractControl): ValidationErrors | null {
    const valor = control.value as string | null;
    if (!valor) return null;
    return esIsbnValido(valor) ? null : { isbn: true };
  }
}
