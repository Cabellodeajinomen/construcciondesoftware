import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-inicio',
  imports: [RouterLink],
  templateUrl: './inicio.html',
})
export class Inicio {
  formularios = [
    { ruta: '/cliente/nuevo', titulo: 'Registrar cliente', texto: 'Alta de estudiantes, docentes, administrativos y externos con DNI y correo.' },
    { ruta: '/libro/nuevo', titulo: 'Registrar libro', texto: 'Alta de un libro con ISBN, idioma, editorial, autores y categorías.' },
    { ruta: '/prestamo/nuevo', titulo: 'Registrar préstamo', texto: 'Préstamo de uno o más ejemplares disponibles a un cliente.' },
  ];
}
