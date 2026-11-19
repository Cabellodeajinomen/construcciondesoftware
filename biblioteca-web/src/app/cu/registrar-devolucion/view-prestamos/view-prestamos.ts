import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PRESTAMOS, Prestamo } from '../../../datos/ficticios';
import { ModalDevolucion, diasDeRetraso } from '../modal-devolucion/modal-devolucion';

@Component({
  selector: 'app-view-prestamos',
  imports: [RouterLink, DatePipe, ModalDevolucion],
  templateUrl: './view-prestamos.html',
})
export class ViewPrestamos {
  prestamos: Prestamo[] = PRESTAMOS;

  mostrarModal = false;
  prestamoSeleccionado: Prestamo | null = null;
  mensaje = '';

  vencido(p: Prestamo): boolean {
    return !p.devuelto && diasDeRetraso(p.fechaLimite) > 0;
  }

  registrarDevolucion(prestamo: Prestamo) {
    this.prestamoSeleccionado = prestamo;
    this.mostrarModal = true;
    this.mensaje = '';
  }

  /** El modal confirma: se marca el préstamo como devuelto. */
  confirmarDevolucion(multa: number) {
    const p = this.prestamoSeleccionado!;
    this.prestamos = this.prestamos.map((x) => (x.id === p.id ? { ...x, devuelto: true } : x));
    this.mensaje =
      `Préstamo N.° ${p.id} devuelto.` + (multa > 0 ? ` Multa cobrada: S/ ${multa.toFixed(2)}.` : ' Sin multa.');
    console.log('Devolución registrada:', { idPrestamo: p.id, multa });
    this.cerrarModal();
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.prestamoSeleccionado = null;
  }
}