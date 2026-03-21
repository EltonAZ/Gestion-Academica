/*  ===============================
    Pagina Administrador 
    ===============================   */
document.addEventListener("DOMContentLoaded", function() {
  // --- CRUD Estudiantes ---
  const formEstudiante = document.getElementById("formEstudiante");
  formEstudiante.addEventListener("submit", function(e) {
    e.preventDefault();

    const nombre = document.getElementById("nombreEst").value;
    const apellido = document.getElementById("apellidoEst").value;
    const correo = document.getElementById("correoEst").value;
    
    console.log("Estudiante registrado:", nombre, apellido, correo);

    const tabla = document.getElementById("tablaEstudiantes").querySelector("tbody");
    const fila = tabla.insertRow();
    fila.innerHTML = `<td>${nombre}</td><td>${apellido}</td><td>${correo}</td>`;

    e.target.reset();
  });

  // --- CRUD Cursos ---
  const formCurso = document.getElementById("formCurso");
  formCurso.addEventListener("submit", function(e) {
    e.preventDefault();

    const nombreCurso = document.getElementById("nombreCurso").value;
    const codigoCurso = document.getElementById("codigoCurso").value;

    const tabla = document.getElementById("tablaCursos").querySelector("tbody");
    const fila = tabla.insertRow();
    fila.innerHTML = `<td>${nombreCurso}</td><td>${codigoCurso}</td>`;

    e.target.reset();
  });
});

