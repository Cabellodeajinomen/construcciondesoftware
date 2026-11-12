import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { AUTORES, CATEGORIAS, EDITORIALES, IDIOMAS } from '../datos/catalogos';
import { RequestLibro } from '../models/requests';
import { ErroresCampo } from '../shared/errores-campo/errores-campo';
import { IsbnValidator } from '../validadores/isbn.directive';

@Component({
  selector: 'app-form-libro',
  imports: [FormsModule, JsonPipe, ErroresCampo, IsbnValidator],
  templateUrl: './form-libro.html',
})
export class FormLibro {
  idiomas = IDIOMAS;
  editoriales = EDITORIALES;
  autores = AUTORES;
  categorias = CATEGORIAS;
  anioActual = new Date().getFullYear();
  enviado: RequestLibro | null = null;

  /** Valida el formulario, arma el RequestLibro y lo imprime en consola. */
  onSubmit(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      this.enviado = null;
      return;
    }
    const v = form.value;
    const request: RequestLibro = {
      titulo: v.titulo.trim(),
      anioPublicacion: v.anioPublicacion,
      descripcion: (v.descripcion ?? '').trim(),
      isbn: v.isbn.replace(/[-\s]/g, '').toUpperCase(),
      edicion: (v.edicion ?? '').trim(),
      numeroPaginas: v.numeroPaginas,
      idIdioma: v.idIdioma,
      idEditorial: v.idEditorial,
      idAutores: v.idAutores,
      idCategorias: v.idCategorias,
    };
    console.log('Data del formulario:', form.value);
    console.log('RequestLibro:', request);
    this.enviado = request;
    form.resetForm();
  }
}
