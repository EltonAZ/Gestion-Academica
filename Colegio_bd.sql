CREATE DATABASE colegio_db;

use colegio_db;
select * From estudiantes;
select * From cursos;
select * From notas;
select * From usuarios;
select * From docentes;
select * From estudiante_curso;
select * From docente_curso;

INSERT INTO usuario (email, password, rol) VALUES 
('admin@colegio.com', '123', 'admin'),
('docente@colegio.com', '123', 'docente'),
('juan.perez@colegio.com', '123', 'estudiante');

INSERT INTO usuarios (email, password, rol) VALUES 
('elton@colegio.com', '1234', 'ESTUDIANTE');

DELETE FROM docentes 
WHERE id = 3;

DELETE FROM docente_curso 
WHERE id = 6 AND id= 3;


show tables;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE nota;
TRUNCATE TABLE estudiantes;
TRUNCATE TABLE docentes;
TRUNCATE TABLE cursos;
TRUNCATE TABLE estudiante_curso;
TRUNCATE TABLE docente_curso;
SET FOREIGN_KEY_CHECKS = 1;
