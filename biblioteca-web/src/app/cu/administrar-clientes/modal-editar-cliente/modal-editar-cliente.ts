import { Component, HostListener, input, output } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { TIPOS_CLIENTE } from '../../../datos/catalogos';
import { Cliente } from '../../../datos/ficticios';
import { ErroresCampo } from '../../../shared/errores-campo/errores-campo';

/** Modal con formulario basado en plantilla para editar los datos de un cliente. */
@Component({
  selector: 'app-modal-editar-cliente',
  imports: [FormsModule, ErroresCampo],
  templateUrl: './modal-editar-cliente.html',
})
export class ModalEditarCliente {
  /** Copia del cliente que envía el padre (ViewClientes). */
  cliente = input.required<Cliente>();

  /** Devuelve al padre el cliente con los cambios. */
  guardar = output<Cliente>();

  /** Avisa al padre que se canceló la edición. */
  cerrar = output<void>();

  tiposCliente = TIPOS_CLIENTE;

  onGuardar(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }
    this.guardar.emit({
      ...this.cliente(),
      nombreCompleto: form.value.nombreCompleto.trim(),
      dni: form.value.dni,
      email: form.value.email.trim().toLowerCase(),
      tipoCliente: form.value.tipoCliente,
    });
  }

  @HostListener('document:keydown.escape')
  alPresionarEscape() {
    this.cerrar.emit();
  }

  clicFondo(evento: MouseEvent) {
    if (evento.target === evento.currentTarget) this.cerrar.emit();
  }
}