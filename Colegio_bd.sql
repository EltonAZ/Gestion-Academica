CREATE DATABASE colegio_db;

use colegio_db;
select * From estudiantes;
select * From cursos;
select * From notas;
select * From usuarios;
select * From docentes;
select * From estudiante_curso;
select * From docente_curso;

-- Creamos usuario:
-- Creando Administrador
INSERT INTO usuarios (email, password, rol) VALUES 
('admin@sandaniel.edu', 'admin', 'ADMIN'); 


-- Creamos un estudiante
INSERT INTO estudiantes (id, nombre, apellido, email, password)
VALUES (5, 'Carlos', 'Ramírez', 'nuevo@colegio.com', '1234');

-- Asignar curso
INSERT INTO estudiante_curso (estudiante_id, curso_id) VALUES (5, 1);

DELETE FROM docentes 
WHERE id = 3;

DELETE FROM docente_curso 
WHERE docente_id = 3 AND curso_id= 2;


show tables;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE nota;
TRUNCATE TABLE estudiantes;
TRUNCATE TABLE docentes;
TRUNCATE TABLE cursos;
TRUNCATE TABLE estudiante_curso;
TRUNCATE TABLE docente_curso;
SET FOREIGN_KEY_CHECKS = 1;
