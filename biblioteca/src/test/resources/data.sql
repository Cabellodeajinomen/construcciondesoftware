-- =====================================================================
-- Datos de ejemplo del modelo Biblioteca (versión 2)
-- Los ids empiezan en 1001 para no chocar con las secuencias (que empiezan en 1).
-- Las tablas de estados y tipo_publicacion usan códigos fijos 1, 2, 3...
-- ON CONFLICT DO NOTHING permite ejecutar este script en cada arranque sin duplicar.
-- ISBN, ISSN, DNI y correos son ficticios.
-- =====================================================================

-- ---------- Catálogos con código numérico ----------
INSERT INTO estado_fisico (id, nombre, descripcion) VALUES
 (1, 'Bueno', 'Sin daños visibles'),
 (2, 'Regular', 'Desgaste por uso, pero completo'),
 (3, 'Dañado', 'Páginas o tapas dañadas'),
 (4, 'En reparación', 'Enviado a encuadernación o restauración')
ON CONFLICT DO NOTHING;

INSERT INTO estado_disponibilidad (id, nombre, descripcion) VALUES
 (1, 'Disponible', 'Puede prestarse'),
 (2, 'Prestado', 'Está en poder de un cliente'),
 (3, 'Reservado', 'Separado para una reserva'),
 (4, 'No disponible', 'Fuera de circulación')
ON CONFLICT DO NOTHING;

INSERT INTO tipo_publicacion (id, nombre, descripcion) VALUES
 (1, 'Libro', 'Obra publicada con ISBN'),
 (2, 'Revista', 'Publicación periódica con ISSN'),
 (3, 'Tesis', 'Trabajo de investigación para obtener un grado')
ON CONFLICT DO NOTHING;

-- ---------- Catálogos generales ----------
INSERT INTO idioma (id, codigo, nombre) VALUES
 (1001, 'es', 'Español'),
 (1002, 'en', 'Inglés'),
 (1003, 'pt', 'Portugués'),
 (1004, 'qu', 'Quechua')
ON CONFLICT DO NOTHING;

INSERT INTO editorial (id, nombre, pais_origen, año_fundacion) VALUES
 (1001, 'Alfaguara', 'España', 1964),
 (1002, 'Planeta', 'España', 1949),
 (1003, 'Pearson', 'Reino Unido', 1844),
 (1004, 'Fondo de Cultura Económica', 'México', 1934),
 (1005, 'Editorial Universitaria Arequipa', 'Perú', 2005)
ON CONFLICT DO NOTHING;

INSERT INTO autor (id, nombres, apellidos, nacionalidad) VALUES
 (1001, 'Mario', 'Vargas Llosa', 'Peruana'),
 (1002, 'Gabriel', 'García Márquez', 'Colombiana'),
 (1003, 'César', 'Vallejo', 'Peruana'),
 (1004, 'Robert C.', 'Martin', 'Estadounidense'),
 (1005, 'Ian', 'Sommerville', 'Británica'),
 (1006, 'José María', 'Arguedas', 'Peruana'),
 (1007, 'Ana Lucía', 'Torres Quispe', 'Peruana'),
 (1008, 'Luis Alberto', 'Mamani Choque', 'Peruana')
ON CONFLICT DO NOTHING;

INSERT INTO categoria (id, nombre, descripcion) VALUES
 (1001, 'Novela', 'Narrativa de ficción'),
 (1002, 'Poesía', 'Obras en verso'),
 (1003, 'Ingeniería de Software', 'Procesos, diseño y calidad de software'),
 (1004, 'Programación', 'Lenguajes y buenas prácticas de código'),
 (1005, 'Literatura peruana', 'Autores y obras del Perú'),
 (1006, 'Investigación', 'Trabajos académicos y científicos')
ON CONFLICT DO NOTHING;

INSERT INTO tipo_cliente (id, nombre, descripcion) VALUES
 (1001, 'Estudiante', 'Alumno de pregrado o posgrado'),
 (1002, 'Docente', 'Profesor de la universidad'),
 (1003, 'Administrativo', 'Personal administrativo'),
 (1004, 'Externo', 'Usuario sin vínculo con la universidad')
ON CONFLICT DO NOTHING;

INSERT INTO ubicacion (id, codigo, sala, estante, nivel, seccion) VALUES
 (1001, 'SG-A1-N1', 'Sala General', 'A1', '1', 'Literatura'),
 (1002, 'SG-A2-N2', 'Sala General', 'A2', '2', 'Literatura peruana'),
 (1003, 'SI-B1-N3', 'Sala de Ingeniería', 'B1', '3', 'Software'),
 (1004, 'HE-C1-N1', 'Hemeroteca', 'C1', '1', 'Revistas'),
 (1005, 'ST-D1-N1', 'Sala de Tesis', 'D1', '1', 'Tesis de pregrado')
ON CONFLICT DO NOTHING;

INSERT INTO parametro (id, codigo, valor, tipo_dato, descripcion) VALUES
 (1001, 'MAX_PRESTAMOS', '3', 'ENTERO', 'Máximo de ejemplares por préstamo'),
 (1002, 'DIAS_PRESTAMO', '7', 'ENTERO', 'Días que dura un préstamo'),
 (1003, 'MULTA_DIARIA', '1.50', 'DECIMAL', 'Multa en soles por cada día de retraso'),
 (1004, 'DIAS_RESERVA', '2', 'ENTERO', 'Días que se guarda una reserva'),
 (1005, 'MAX_RESERVAS', '2', 'ENTERO', 'Máximo de reservas activas por cliente')
ON CONFLICT DO NOTHING;

-- ---------- Publicaciones (tabla padre) ----------
INSERT INTO publicacion (id, titulo, año_publicacion, descripcion, id_tipo_publicacion, id_idioma, id_editorial) VALUES
 (1001, 'La ciudad y los perros', 1963, 'Novela ambientada en el Colegio Militar Leoncio Prado', 1, 1001, 1001),
 (1002, 'Cien años de soledad', 1967, 'Historia de la familia Buendía en Macondo', 1, 1001, 1001),
 (1003, 'Los heraldos negros', 1918, 'Primer poemario de César Vallejo', 1, 1001, 1002),
 (1004, 'Clean Code', 2008, 'Buenas prácticas para escribir código limpio', 1, 1002, 1003),
 (1005, 'Software Engineering', 2015, 'Texto clásico de ingeniería de software', 1, 1002, 1003),
 (1006, 'Los ríos profundos', 1958, 'Novela sobre el mundo andino', 1, 1001, 1004),
 (1007, 'Revista de Ingeniería y Tecnología Sur', 2025, 'Artículos de ingeniería del sur del Perú', 2, 1001, 1005),
 (1008, 'Software Practice Review', 2024, 'Revista de prácticas de desarrollo de software', 2, 1002, 1003),
 (1009, 'Sistema web para la gestión de préstamos de una biblioteca universitaria', 2025, 'Tesis de pregrado', 3, 1001, NULL),
 (1010, 'Aplicación móvil para el registro de asistencia con códigos QR', 2024, 'Tesis de pregrado', 3, 1001, NULL)
ON CONFLICT DO NOTHING;

-- ---------- Subtipos (PK = FK a publicacion) ----------
INSERT INTO libro (id_publicacion, isbn, edicion, numero_paginas) VALUES
 (1001, '978-0-0000-1001-1', 'Edición conmemorativa', 456),
 (1002, '978-0-0000-1002-2', 'Edición RAE', 792),
 (1003, '978-0-0000-1003-3', 'Primera edición', 120),
 (1004, '978-0-0000-1004-4', '1ra edición', 464),
 (1005, '978-0-0000-1005-5', '10ma edición', 816),
 (1006, '978-0-0000-1006-6', '2da edición', 288)
ON CONFLICT DO NOTHING;

INSERT INTO revista (id_publicacion, issn, volumen, numero, fecha_publicacion) VALUES
 (1007, '0000-1007', 'Vol. 5', 'N.° 2', '2025-06-15'),
 (1008, '0000-1008', 'Vol. 12', 'N.° 4', '2024-11-30')
ON CONFLICT DO NOTHING;

INSERT INTO tesis (id_publicacion, grado_academico, institucion, asesor) VALUES
 (1009, 'Ingeniero de Software', 'Universidad La Salle', 'Mg. Carlos Rivera Paredes'),
 (1010, 'Ingeniero de Software', 'Universidad La Salle', 'Dr. Jorge Salas Huamán')
ON CONFLICT DO NOTHING;

-- ---------- Tablas relacionales (clave primaria compuesta) ----------
-- un libro en varias categorías: Clean Code (1004) está en Ingeniería de Software y Programación
INSERT INTO publicacion_categoria (id_publicacion, id_categoria) VALUES
 (1001, 1001), (1001, 1005),
 (1002, 1001),
 (1003, 1002), (1003, 1005),
 (1004, 1003), (1004, 1004),
 (1005, 1003),
 (1006, 1001), (1006, 1005),
 (1007, 1006),
 (1008, 1003), (1008, 1006),
 (1009, 1003), (1009, 1006),
 (1010, 1004), (1010, 1006)
ON CONFLICT DO NOTHING;

INSERT INTO publicacion_autor (id_publicacion, id_autor) VALUES
 (1001, 1001),
 (1002, 1002),
 (1003, 1003),
 (1004, 1004),
 (1005, 1005),
 (1006, 1006),
 (1009, 1007),
 (1010, 1007), (1010, 1008)
ON CONFLICT DO NOTHING;

-- ---------- Clientes ----------
INSERT INTO cliente (id, nombre_completo, dni, email, id_tipo_cliente) VALUES
 (1001, 'Lucía Fernández Paredes', '70000001', 'lucia.fernandez@correo.pe', 1001),
 (1002, 'María Fernanda Quispe Ramos', '70000002', 'maria.quispe@correo.pe', 1001),
 (1003, 'Carlos Rivera Paredes', '40000003', 'carlos.rivera@correo.pe', 1002),
 (1004, 'Rosa Elena Huamán Ccama', '40000004', 'rosa.huaman@correo.pe', 1003),
 (1005, 'Pedro Pablo Condori Apaza', '70000005', 'pedro.condori@correo.pe', 1004)
ON CONFLICT DO NOTHING;

-- ---------- Ejemplares (estado físico y disponibilidad numéricos + nota) ----------
INSERT INTO ejemplar (id, id_publicacion, codigo_barras, id_ubicacion, id_estado_fisico, id_estado_disponibilidad, nota) VALUES
 (1001, 1001, 'EJ-1001', 1001, 1, 1, NULL),
 (1002, 1001, 'EJ-1002', 1001, 2, 1, 'Tapa con desgaste leve'),
 (1003, 1002, 'EJ-1003', 1001, 1, 1, NULL),
 (1004, 1003, 'EJ-1004', 1002, 1, 1, NULL),
 (1005, 1004, 'EJ-1005', 1003, 3, 4, 'Páginas 45 a 60 dañadas en la última devolución'),
 (1006, 1004, 'EJ-1006', 1003, 1, 1, NULL),
 (1007, 1005, 'EJ-1007', 1003, 1, 2, NULL),
 (1008, 1004, 'EJ-1008', 1003, 1, 3, 'Separado para reserva'),
 (1009, 1006, 'EJ-1009', 1002, 2, 2, NULL),
 (1010, 1003, 'EJ-1010', 1002, 1, 2, NULL),
 (1011, 1007, 'EJ-1011', 1004, 1, 1, NULL),
 (1012, 1008, 'EJ-1012', 1004, 1, 1, NULL),
 (1013, 1009, 'EJ-1013', 1005, 1, 1, 'Solo consulta en sala'),
 (1014, 1010, 'EJ-1014', 1005, 4, 4, 'En encuadernación')
ON CONFLICT DO NOTHING;

-- ---------- Reservas (estado: 1 Pendiente, 2 Asignada, 3 Atendida, 4 Cancelada, 5 Vencida) ----------
INSERT INTO reserva (id, id_cliente, id_publicacion, id_ejemplar, fecha_reserva, fecha_vencimiento, estado, fecha_atencion) VALUES
 (1001, 1004, 1004, 1008, '2026-10-12 09:30:00', '2026-10-14 09:30:00', 2, NULL),
 (1002, 1001, 1002, NULL, '2026-10-13 16:00:00', '2026-10-15 16:00:00', 1, NULL),
 (1003, 1005, 1006, NULL, '2026-09-20 10:00:00', '2026-09-22 10:00:00', 5, NULL),
 (1004, 1002, 1005, 1007, '2026-09-28 11:15:00', '2026-09-30 11:15:00', 3, '2026-10-01 10:00:00')
ON CONFLICT DO NOTHING;

-- ---------- Préstamos (cabecera) e ítems (detalle) ----------
INSERT INTO prestamo (id, id_cliente, fecha_prestamo, fecha_limite) VALUES
 (1001, 1001, '2026-09-01 10:00:00', '2026-09-08'),
 (1002, 1002, '2026-10-01 10:00:00', '2026-10-08'),
 (1003, 1003, '2026-10-10 15:30:00', '2026-10-17')
ON CONFLICT DO NOTHING;

INSERT INTO prestamo_item (id, id_prestamo, id_ejemplar, devuelto) VALUES
 (1001, 1001, 1003, TRUE),
 (1002, 1001, 1005, TRUE),
 (1003, 1002, 1007, FALSE),
 (1004, 1002, 1009, FALSE),
 (1005, 1003, 1010, FALSE)
ON CONFLICT DO NOTHING;

-- ---------- Devoluciones (cabecera) e ítems (detalle) ----------
-- préstamo 1001 vencía el 08/09 y se devolvió el 10/09: 2 días x S/ 1.50 por ejemplar
INSERT INTO devolucion (id, id_prestamo, fecha_devolucion, total_multa) VALUES
 (1001, 1001, '2026-09-10 12:00:00', 6.00)
ON CONFLICT DO NOTHING;

INSERT INTO devolucion_item (id, id_devolucion, id_prestamo_item, danado, dias_retraso, multa) VALUES
 (1001, 1001, 1001, FALSE, 2, 3.00),
 (1002, 1001, 1002, TRUE, 2, 3.00)
ON CONFLICT DO NOTHING;
