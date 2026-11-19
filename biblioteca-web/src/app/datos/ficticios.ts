// Datos ficticios para las vistas con ventanas modales.
// No se usa base de datos: todo vive en memoria y se pierde al recargar la página.

export interface Ejemplar {
  codigo: string;
  estado: 'Disponible' | 'Prestado' | 'En reparación';
}

export interface Libro {
  id: number;
  titulo: string;
  isbn: string;
  anioPublicacion: number;
  edicion: string;
  numeroPaginas: number;
  idioma: string;
  editorial: string;
  autores: string[];
  categorias: string[];
  descripcion: string;
  ejemplares: Ejemplar[];
}

export interface Cliente {
  id: number;
  nombreCompleto: string;
  dni: string;
  email: string;
  tipoCliente: string;
  prestamosActivos: number;
}

export interface Prestamo {
  id: number;
  cliente: string;
  ejemplares: { codigo: string; titulo: string }[];
  fechaPrestamo: Date;
  fechaLimite: Date;
  devuelto: boolean;
}

/** Multa ficticia por cada día de retraso, en soles. */
export const MULTA_POR_DIA = 1.5;

/** Fecha relativa a hoy, para que los préstamos siempre se vean "vigentes" o "vencidos". */
function haceDias(dias: number): Date {
  const d = new Date();
  d.setHours(0, 0, 0, 0);
  d.setDate(d.getDate() - dias);
  return d;
}

export const LIBROS: Libro[] = [
  {
    id: 1001, titulo: 'La ciudad y los perros', isbn: '9788420412146', anioPublicacion: 1963,
    edicion: 'Edición conmemorativa', numeroPaginas: 448, idioma: 'Español', editorial: 'Alfaguara',
    autores: ['Mario Vargas Llosa'], categorias: ['Novela', 'Literatura peruana'],
    descripcion: 'Novela ambientada en el Colegio Militar Leoncio Prado de Lima.',
    ejemplares: [
      { codigo: 'EJ-1001', estado: 'Disponible' },
      { codigo: 'EJ-1002', estado: 'Disponible' },
      { codigo: 'EJ-1013', estado: 'Prestado' },
    ],
  },
  {
    id: 1002, titulo: 'Cien años de soledad', isbn: '9780307474728', anioPublicacion: 1967,
    edicion: '1ra edición', numeroPaginas: 471, idioma: 'Español', editorial: 'Planeta',
    autores: ['Gabriel García Márquez'], categorias: ['Novela'],
    descripcion: 'La historia de la familia Buendía a lo largo de siete generaciones en Macondo.',
    ejemplares: [
      { codigo: 'EJ-1003', estado: 'Disponible' },
      { codigo: 'EJ-1014', estado: 'Prestado' },
    ],
  },
  {
    id: 1003, titulo: 'Los heraldos negros', isbn: '9786124169017', anioPublicacion: 1919,
    edicion: '3ra edición', numeroPaginas: 160, idioma: 'Español', editorial: 'Fondo de Cultura Económica',
    autores: ['César Vallejo'], categorias: ['Poesía', 'Literatura peruana'],
    descripcion: 'Primer poemario de César Vallejo.',
    ejemplares: [
      { codigo: 'EJ-1004', estado: 'Disponible' },
      { codigo: 'EJ-1005', estado: 'En reparación' },
    ],
  },
  {
    id: 1004, titulo: 'Clean Code', isbn: '9780132350884', anioPublicacion: 2008,
    edicion: '1ra edición', numeroPaginas: 464, idioma: 'Inglés', editorial: 'Pearson',
    autores: ['Robert C. Martin'], categorias: ['Ingeniería de Software', 'Programación'],
    descripcion: 'Buenas prácticas para escribir código legible y mantenible.',
    ejemplares: [
      { codigo: 'EJ-1006', estado: 'Disponible' },
      { codigo: 'EJ-1007', estado: 'Prestado' },
    ],
  },
  {
    id: 1005, titulo: 'Ingeniería de Software', isbn: '9786073206037', anioPublicacion: 2011,
    edicion: '9na edición', numeroPaginas: 792, idioma: 'Español', editorial: 'Pearson',
    autores: ['Ian Sommerville'], categorias: ['Ingeniería de Software'],
    descripcion: 'Texto de referencia sobre procesos, requisitos, diseño y pruebas de software.',
    ejemplares: [
      { codigo: 'EJ-1008', estado: 'Prestado' },
      { codigo: 'EJ-1009', estado: 'Prestado' },
    ],
  },
  {
    id: 1006, titulo: 'Los ríos profundos', isbn: '9789972402212', anioPublicacion: 1958,
    edicion: '2da edición', numeroPaginas: 288, idioma: 'Español', editorial: 'Editorial Universitaria Arequipa',
    autores: ['José María Arguedas'], categorias: ['Novela', 'Literatura peruana'],
    descripcion: 'Novela sobre el choque entre el mundo andino y el mundo criollo.',
    ejemplares: [{ codigo: 'EJ-1010', estado: 'Disponible' }],
  },
];

export const CLIENTES_LISTA: Cliente[] = [
  { id: 1001, nombreCompleto: 'Lucía Fernández Paredes', dni: '70000001', email: 'lfernandez@ulasalle.edu.pe', tipoCliente: 'Estudiante', prestamosActivos: 1 },
  { id: 1002, nombreCompleto: 'María Fernanda Quispe Ramos', dni: '70000002', email: 'mquispe@ulasalle.edu.pe', tipoCliente: 'Estudiante', prestamosActivos: 2 },
  { id: 1003, nombreCompleto: 'Carlos Rivera Paredes', dni: '40000003', email: 'crivera@ulasalle.edu.pe', tipoCliente: 'Docente', prestamosActivos: 1 },
  { id: 1004, nombreCompleto: 'Rosa Elena Huamán Ccama', dni: '40000004', email: 'rhuaman@ulasalle.edu.pe', tipoCliente: 'Administrativo', prestamosActivos: 0 },
  { id: 1005, nombreCompleto: 'Pedro Pablo Condori Apaza', dni: '70000005', email: 'pcondori@gmail.com', tipoCliente: 'Externo', prestamosActivos: 1 },
];

export const PRESTAMOS: Prestamo[] = [
  {
    id: 5001, cliente: 'Lucía Fernández Paredes',
    ejemplares: [{ codigo: 'EJ-1013', titulo: 'La ciudad y los perros' }],
    fechaPrestamo: haceDias(3), fechaLimite: haceDias(-4), devuelto: false,
  },
  {
    id: 5002, cliente: 'María Fernanda Quispe Ramos',
    ejemplares: [
      { codigo: 'EJ-1014', titulo: 'Cien años de soledad' },
      { codigo: 'EJ-1008', titulo: 'Ingeniería de Software' },
    ],
    fechaPrestamo: haceDias(12), fechaLimite: haceDias(5), devuelto: false,
  },
  {
    id: 5003, cliente: 'Carlos Rivera Paredes',
    ejemplares: [{ codigo: 'EJ-1007', titulo: 'Clean Code' }],
    fechaPrestamo: haceDias(6), fechaLimite: haceDias(-1), devuelto: false,
  },
  {
    id: 5004, cliente: 'Pedro Pablo Condori Apaza',
    ejemplares: [{ codigo: 'EJ-1009', titulo: 'Ingeniería de Software' }],
    fechaPrestamo: haceDias(9), fechaLimite: haceDias(2), devuelto: false,
  },
  {
    id: 5005, cliente: 'Rosa Elena Huamán Ccama',
    ejemplares: [{ codigo: 'EJ-1004', titulo: 'Los heraldos negros' }],
    fechaPrestamo: haceDias(20), fechaLimite: haceDias(13), devuelto: true,
  },
];