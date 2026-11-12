export interface RequestCliente {
  nombreCompleto: string;
  dni: string;
  email: string;
  idTipoCliente: number;
}

export interface RequestLibro {
  titulo: string;
  anioPublicacion: number;
  descripcion: string;
  isbn: string;
  edicion: string;
  numeroPaginas: number;
  idIdioma: number;
  idEditorial: number;
  idAutores: number[];
  idCategorias: number[];
}

export interface RequestPrestamoItem {
  idEjemplar: number;
}

export interface RequestPrestamo {
  idCliente: number;
  items: RequestPrestamoItem[];
}
