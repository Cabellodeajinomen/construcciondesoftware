import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CLIENTES_LISTA, Cliente } from '../../../datos/ficticios';
import { MAX_PRESTAMOS } from '../../../datos/catalogos';
import { ModalEditarCliente } from '../modal-editar-cliente/modal-editar-cliente';

@Component({
  selector: 'app-view-clientes',
  imports: [RouterLink, ModalEditarCliente],
  templateUrl: './view-clientes.html',
})
export class ViewClientes {
  clientes: Cliente[] = CLIENTES_LISTA;
  maxPrestamos = MAX_PRESTAMOS;

  mostrarModal = false;
  clienteSeleccionado: Cliente | null = null;
  mensaje = '';

  editarCliente(cliente: Cliente) {
    // Se envía una COPIA: si el usuario cancela, la tabla no cambia.
    this.clienteSeleccionado = { ...cliente };
    this.mostrarModal = true;
    this.mensaje = '';
  }

  /** Recibe el cliente editado desde el modal y reemplaza el de la lista. */
  guardarCliente(editado: Cliente) {
    this.clientes = this.clientes.map((c) => (c.id === editado.id ? editado : c));
    this.mensaje = `Se actualizaron los datos de ${editado.nombreCompleto}.`;
    console.log('Cliente actualizado:', editado);
    this.cerrarModal();
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.clienteSeleccionado = null;
  }
}