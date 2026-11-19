import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-inicio',
  imports: [RouterLink],
  templateUrl: './inicio.html',
})
export class Inicio {
  formularios = [
    { ruta: '/catalogo', titulo: 'Catálogo de libros', texto: 'Consulte la ficha de cada libro y el estado de sus ejemplares.' },
    { ruta: '/clientes', titulo: 'Clientes', texto: 'Revise y edite los datos de estudiantes, docentes y externos.' },
    { ruta: '/prestamos', titulo: 'Préstamos', texto: 'Registre devoluciones y calcule multas por retraso.' },
  ];
}