document.addEventListener("DOMContentLoaded", () => {
  const listaCursosEstudiante = document.getElementById("listaCursosEstudiante");
  const detalleNotas = document.getElementById("detalleNotas");

  // --- 1. Obtener el ID del estudiante desde login ---
  const estudianteId = localStorage.getItem("usuarioId");
  if (!estudianteId) {
    alert("No se encontró el estudiante logueado. Por favor inicia sesión.");
    return;
  }

  // Boton Salir
  document.getElementById("estudianteSalir").addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "index.html";
  });

  // --- 2. Cargar cursos del estudiante ---
  fetch(`http://localhost:8080/estudiantes/${estudianteId}/cursos`)
    .then(res => {
      if (!res.ok) throw new Error("Error al cargar cursos");
      return res.json();
    })
    .then(cursos => {
      listaCursosEstudiante.innerHTML = ""; // limpiar tabla
      cursos.forEach(curso => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
          <td>${curso.nombre}</td>
          <td>${curso.descripcion}</td>
          <td><button class="btn-ver-notas" data-curso="${curso.id}">Ver notas</button></td>
        `;
        listaCursosEstudiante.appendChild(fila);
      });
    })
    .catch(err => alert(err.message));

  // --- 3. Ver notas de un curso ---
  listaCursosEstudiante.addEventListener("click", e => {
    if (e.target.classList.contains("btn-ver-notas")) {
      const cursoId = e.target.dataset.curso;
      detalleNotas.innerHTML = ""; // limpiar tabla

      fetch(`http://localhost:8080/estudiantes/${estudianteId}/cursos/${cursoId}/notas`)
        .then(res => {
          if (!res.ok) throw new Error("Error al cargar notas");
          return res.json();
        })
        .then(nota => {
          const fila = document.createElement("tr");
          fila.innerHTML = `
            <td>${nota.nota1}</td>
            <td>${nota.nota2}</td>
            <td>${nota.nota3}</td>
            <td>${nota.promedio}</td>
          `;
          detalleNotas.appendChild(fila);
        })
        .catch(err => alert(err.message));
    }
  });
 
});