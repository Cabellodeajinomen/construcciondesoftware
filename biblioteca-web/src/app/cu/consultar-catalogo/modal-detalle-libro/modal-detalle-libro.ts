import { Component, HostListener, input, output } from '@angular/core';
import { Libro } from '../../../datos/ficticios';

/** Modal de solo lectura: muestra la ficha completa de un libro y el estado de sus ejemplares. */
@Component({
  selector: 'app-modal-detalle-libro',
  templateUrl: './modal-detalle-libro.html',
})
export class ModalDetalleLibro {
  /** Libro que envía el padre (ViewCatalogo). */
  libro = input.required<Libro>();

  /** Evento que avisa al padre que debe cerrar el modal. */
  cerrar = output<void>();

  /** Cerrar con la tecla Escape. */
  @HostListener('document:keydown.escape')
  alPresionarEscape() {
    this.cerrar.emit();
  }

  /** Cerrar al hacer clic en el fondo oscuro (no dentro del cuadro). */
  clicFondo(evento: MouseEvent) {
    if (evento.target === evento.currentTarget) this.cerrar.emit();
  }

  claseEstado(estado: string): string {
    if (estado === 'Disponible') return 'estado-ok';
    if (estado === 'Prestado') return 'estado-alerta';
    return 'estado-neutro';
  }
}