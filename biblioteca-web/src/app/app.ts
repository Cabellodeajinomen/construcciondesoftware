import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
})
export class App {
  /** Opciones del menú principal (una por caso de uso). */
  opcionesMenu = [
    { ruta: '/inicio', texto: 'Inicio' },
    { ruta: '/catalogo', texto: 'Catálogo' },
    { ruta: '/clientes', texto: 'Clientes' },
    { ruta: '/prestamos', texto: 'Préstamos' },
  ];

  /** En móvil el menú empieza cerrado; el botón ☰ lo abre y cierra. */
  menuAbierto = false;

  toggleMenu(): void {
    this.menuAbierto = !this.menuAbierto;
  }

  /** Al elegir una opción se cierra el menú para que no tape la página. */
  cerrarMenu(): void {
    this.menuAbierto = false;
  }
}