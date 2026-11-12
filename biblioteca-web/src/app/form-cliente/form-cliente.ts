import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { TIPOS_CLIENTE } from '../datos/catalogos';
import { RequestCliente } from '../models/requests';
import { ErroresCampo } from '../shared/errores-campo/errores-campo';

@Component({
  selector: 'app-form-cliente',
  imports: [FormsModule, JsonPipe, ErroresCampo],
  templateUrl: './form-cliente.html',
})
export class FormCliente {
  tiposCliente = TIPOS_CLIENTE;
  enviado: RequestCliente | null = null;

  /** Valida el formulario, arma el RequestCliente y lo imprime en consola. */
  onSubmit(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      this.enviado = null;
      return;
    }
    const v = form.value;
    const request: RequestCliente = {
      nombreCompleto: v.nombreCompleto.trim(),
      dni: v.dni,
      email: v.email.trim().toLowerCase(),
      idTipoCliente: v.idTipoCliente,
    };
    console.log('Data del formulario:', form.value);
    console.log('RequestCliente:', request);
    this.enviado = request;
    form.resetForm();
  }
}
