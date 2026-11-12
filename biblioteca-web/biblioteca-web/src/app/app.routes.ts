import { Routes } from '@angular/router';
import { Inicio } from './inicio/inicio';
import { FormCliente } from './form-cliente/form-cliente';
import { FormLibro } from './form-libro/form-libro';
import { FormPrestamo } from './form-prestamo/form-prestamo';
import { NoEncontrado } from './no-encontrado/no-encontrado';

export const routes: Routes = [
  { path: '', component: Inicio, title: 'Biblioteca ULS' },
  { path: 'inicio', component: Inicio, title: 'Biblioteca ULS' },
  { path: 'cliente/nuevo', component: FormCliente, title: 'Registrar cliente' },
  { path: 'libro/nuevo', component: FormLibro, title: 'Registrar libro' },
  { path: 'prestamo/nuevo', component: FormPrestamo, title: 'Registrar préstamo' },
  { path: '**', component: NoEncontrado, title: 'Recurso no encontrado' },
];
