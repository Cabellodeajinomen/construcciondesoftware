// Catálogos de ejemplo tomados de data.sql (se reemplazan por llamadas a la API cuando se conecte el backend).
export const MAX_PRESTAMOS = 3;
export const DIAS_PRESTAMO = 7;

export const TIPOS_CLIENTE = [
  { id: 1001, nombre: 'Estudiante' },
  { id: 1002, nombre: 'Docente' },
  { id: 1003, nombre: 'Administrativo' },
  { id: 1004, nombre: 'Externo' },
];

export const IDIOMAS = [
  { id: 1001, nombre: 'Español' },
  { id: 1002, nombre: 'Inglés' },
  { id: 1003, nombre: 'Portugués' },
  { id: 1004, nombre: 'Quechua' },
];

export const EDITORIALES = [
  { id: 1001, nombre: 'Alfaguara' },
  { id: 1002, nombre: 'Planeta' },
  { id: 1003, nombre: 'Pearson' },
  { id: 1004, nombre: 'Fondo de Cultura Económica' },
  { id: 1005, nombre: 'Editorial Universitaria Arequipa' },
];

export const AUTORES = [
  { id: 1001, nombre: 'Mario Vargas Llosa' },
  { id: 1002, nombre: 'Gabriel García Márquez' },
  { id: 1003, nombre: 'César Vallejo' },
  { id: 1004, nombre: 'Robert C. Martin' },
  { id: 1005, nombre: 'Ian Sommerville' },
  { id: 1006, nombre: 'José María Arguedas' },
  { id: 1007, nombre: 'Ana Lucía Torres Quispe' },
  { id: 1008, nombre: 'Luis Alberto Mamani Choque' },
];

export const CATEGORIAS = [
  { id: 1001, nombre: 'Novela' },
  { id: 1002, nombre: 'Poesía' },
  { id: 1003, nombre: 'Ingeniería de Software' },
  { id: 1004, nombre: 'Programación' },
  { id: 1005, nombre: 'Literatura peruana' },
  { id: 1006, nombre: 'Investigación' },
];

export const CLIENTES = [
  { id: 1001, nombre: 'Lucía Fernández Paredes', dni: '70000001' },
  { id: 1002, nombre: 'María Fernanda Quispe Ramos', dni: '70000002' },
  { id: 1003, nombre: 'Carlos Rivera Paredes', dni: '40000003' },
  { id: 1004, nombre: 'Rosa Elena Huamán Ccama', dni: '40000004' },
  { id: 1005, nombre: 'Pedro Pablo Condori Apaza', dni: '70000005' },
];

// Ejemplares con estado de disponibilidad "Disponible"
export const EJEMPLARES_DISPONIBLES = [
  { id: 1001, codigo: 'EJ-1001', titulo: 'La ciudad y los perros' },
  { id: 1002, codigo: 'EJ-1002', titulo: 'La ciudad y los perros' },
  { id: 1003, codigo: 'EJ-1003', titulo: 'Cien años de soledad' },
  { id: 1004, codigo: 'EJ-1004', titulo: 'Los heraldos negros' },
  { id: 1006, codigo: 'EJ-1006', titulo: 'Clean Code' },
  { id: 1011, codigo: 'EJ-1011', titulo: 'Revista de Ingeniería y Tecnología Sur' },
  { id: 1012, codigo: 'EJ-1012', titulo: 'Software Practice Review' },
];
