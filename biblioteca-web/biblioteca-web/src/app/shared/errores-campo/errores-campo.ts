import { Component, Input } from '@angular/core';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-errores-campo',
  template: `
    @if (mensaje) {
      <p class="error" role="alert">{{ mensaje }}</p>
    }
  `,
})
export class ErroresCampo {
  @Input() control: NgModel | null | undefined;
  @Input() mensajes: Record<string, string> = {};

  /** Mensaje del primer error del control que tenga texto definido; null si no corresponde mostrarlo. */
  get mensaje(): string | null {
    const c = this.control;
    if (!c || !c.invalid || !(c.touched || c.dirty) || !c.errors) return null;
    const clave = Object.keys(this.mensajes).find((k) => k in c.errors!);
    return clave ? this.mensajes[clave] : 'Valor no válido';
  }
}
