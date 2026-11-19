import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LIBROS, Libro } from '../../../datos/ficticios';
import { ModalDetalleLibro } from '../modal-detalle-libro/modal-detalle-libro';

@Component({
  selector: 'app-view-catalogo',
  imports: [RouterLink, ModalDetalleLibro],
  templateUrl: './view-catalogo.html',
})
export class ViewCatalogo {
  libros: Libro[] = LIBROS;

  mostrarModal = false;
  libroSeleccionado: Libro | null = null;

  disponibles(libro: Libro): number {
    return libro.ejemplares.filter((e) => e.estado === 'Disponible').length;
  }

  verDetalle(libro: Libro) {
    this.libroSeleccionado = libro;
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.libroSeleccionado = null;
  }
}