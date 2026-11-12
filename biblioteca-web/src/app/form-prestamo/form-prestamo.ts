import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { CLIENTES, DIAS_PRESTAMO, EJEMPLARES_DISPONIBLES, MAX_PRESTAMOS } from '../datos/catalogos';
import { RequestPrestamo } from '../models/requests';
import { ErroresCampo } from '../shared/errores-campo/errores-campo';

interface FilaEjemplar {
  uid: number;
  id: number | null;
}

@Component({
  selector: 'app-form-prestamo',
  imports: [FormsModule, JsonPipe, ErroresCampo],
  templateUrl: './form-prestamo.html',
})
export class FormPrestamo {
  clientes = CLIENTES;
  ejemplares = EJEMPLARES_DISPONIBLES;
  maxPrestamos = MAX_PRESTAMOS;
  diasPrestamo = DIAS_PRESTAMO;
  fechaLimite = this.calcularFechaLimite();
  enviado: RequestPrestamo | null = null;

  private siguienteUid = 1;
  filas: FilaEjemplar[] = [this.nuevaFila()];

  private nuevaFila(): FilaEjemplar {
    return { uid: this.siguienteUid++, id: null };
  }

  private calcularFechaLimite(): string {
    const f = new Date();
    f.setDate(f.getDate() + DIAS_PRESTAMO);
    return f.toLocaleDateString('es-PE');
  }

  /** Agrega una fila de ejemplar sin superar el máximo permitido. */
  agregarFila() {
    if (this.filas.length < this.maxPrestamos) this.filas.push(this.nuevaFila());
  }

  /** Quita una fila, dejando siempre al menos una. */
  quitarFila(fila: FilaEjemplar) {
    if (this.filas.length > 1) this.filas = this.filas.filter((f) => f !== fila);
  }

  /** Indica si el ejemplar de la fila ya fue elegido en otra fila. */
  esDuplicado(fila: FilaEjemplar): boolean {
    return fila.id !== null && this.filas.some((f) => f !== fila && f.id === fila.id);
  }

  hayDuplicados(): boolean {
    return this.filas.some((f) => this.esDuplicado(f));
  }

  /** Restablece el formulario a una sola fila vacía. */
  limpiar(form: NgForm) {
    this.filas = [this.nuevaFila()];
    form.resetForm();
    this.enviado = null;
  }

  /** Valida el formulario, arma el RequestPrestamo y lo imprime en consola. */
  onSubmit(form: NgForm) {
    if (form.invalid || this.hayDuplicados()) {
      form.control.markAllAsTouched();
      this.enviado = null;
      return;
    }
    const request: RequestPrestamo = {
      idCliente: form.value.idCliente,
      items: this.filas.map((f) => ({ idEjemplar: f.id as number })),
    };
    console.log('Data del formulario:', form.value);
    console.log('RequestPrestamo:', request);
    this.enviado = request;
    this.filas = [this.nuevaFila()];
    form.resetForm();
  }
}
