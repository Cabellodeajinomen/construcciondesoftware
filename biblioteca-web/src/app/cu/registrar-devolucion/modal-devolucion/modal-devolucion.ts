import { Component, HostListener, computed, input, output } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MULTA_POR_DIA, Prestamo } from '../../../datos/ficticios';

/** Días transcurridos desde la fecha límite hasta hoy (0 si aún no vence). */
export function diasDeRetraso(fechaLimite: Date): number {
  const hoy = new Date();
  hoy.setHours(0, 0, 0, 0);
  const diferencia = hoy.getTime() - fechaLimite.getTime();
  return Math.max(0, Math.floor(diferencia / (1000 * 60 * 60 * 24)));
}

/** Modal de confirmación: resume el préstamo y calcula la multa antes de registrar la devolución. */
@Component({
  selector: 'app-modal-devolucion',
  imports: [DatePipe],
  templateUrl: './modal-devolucion.html',
})
export class ModalDevolucion {
  prestamo = input.required<Prestamo>();

  /** Envía al padre el monto de la multa al confirmar. */
  confirmar = output<number>();
  cerrar = output<void>();

  multaPorDia = MULTA_POR_DIA;

  /** computed(): se recalcula solo si cambia el préstamo recibido. */
  diasRetraso = computed(() => diasDeRetraso(this.prestamo().fechaLimite));
  multa = computed(() => this.diasRetraso() * MULTA_POR_DIA);

  @HostListener('document:keydown.escape')
  alPresionarEscape() {
    this.cerrar.emit();
  }

  clicFondo(evento: MouseEvent) {
    if (evento.target === evento.currentTarget) this.cerrar.emit();
  }
}