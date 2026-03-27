document.addEventListener("DOMContentLoaded", () => {
  const listaCursos = document.getElementById("listaCursos");
  const listaEstudiantes = document.getElementById("listaEstudiantes");

  // --- 1. Cargar cursos del docente ---
  const docenteId = localStorage.getItem("usuarioId");
  if (!docenteId) {
    alert("No se encontró el docente logueado. Por favor inicia sesión.");
    return;
  }

  fetch(`http://localhost:8080/docentes/${docenteId}/cursos`)
    .then(res => res.json())
    .then(cursos => {
      if (!Array.isArray(cursos)) {
        console.error("Respuesta inesperada:", cursos);
        return;
      }
      cursos.forEach(curso => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
          <td>${curso.nombre}</td>
          <td>${curso.descripcion}</td>
          <td><button class="btn-ver-estudiantes" data-curso="${curso.id}">Ver estudiantes</button></td>
        `;
        listaCursos.appendChild(fila);
      });
    })
    .catch(err => console.error("Error cargando cursos:", err));



  // --- 2. Ver estudiantes de un curso ---
  listaCursos.addEventListener("click", e => {
    if (e.target.classList.contains("btn-ver-estudiantes")) {
      const cursoId = e.target.dataset.curso;
      listaEstudiantes.innerHTML = "";

      fetch(`http://localhost:8080/cursos/${cursoId}/estudiantes`)
        .then(res => res.json())
        .then(estudiantes => {
          estudiantes.forEach(est => {
            const fila = document.createElement("tr");
            fila.innerHTML = `
              <td>${est.nombre} ${est.apellido}</td>
              <td>${est.email}</td>
              <td><input type="number" class="nota1"></td>
              <td><input type="number" class="nota2"></td>
              <td><input type="number" class="nota3"></td>
              <td><input type="number" class="promedio" readonly></td>
              <td><button class="btn-guardar-nota" data-estudiante="${est.id}" data-curso="${cursoId}">Guardar</button></td>
            `;
            listaEstudiantes.appendChild(fila);
          });
        });
    }
  });


    // --- 3. Calcular promedio automáticamente ---
  listaEstudiantes.addEventListener("input", e => {
    const fila = e.target.closest("tr");
    if (fila) {
      const n1 = parseFloat(fila.querySelector(".nota1").value) || 0;
      const n2 = parseFloat(fila.querySelector(".nota2").value) || 0;
      const n3 = parseFloat(fila.querySelector(".nota3").value) || 0;
      const promedio = ((n1 + n2 + n3) / 3).toFixed(2);
      fila.querySelector(".promedio").value = promedio;
    }
  });


  // --- 4. Guardar notas ---
  listaEstudiantes.addEventListener("click", e => {
    if (e.target.classList.contains("btn-guardar-nota")) {
      const fila = e.target.closest("tr");
      const estudianteId = e.target.dataset.estudiante;
      const cursoId = e.target.dataset.curso;

      const nota1 = parseFloat(fila.querySelector(".nota1").value) || 0;
      const nota2 = parseFloat(fila.querySelector(".nota2").value) || 0;
      const nota3 = parseFloat(fila.querySelector(".nota3").value) || 0;

      const notaData = { nota1, nota2, nota3 };

      fetch(`http://localhost:8080/docentes/${docenteId}/cursos/${cursoId}/notas/${estudianteId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(notaData)
      })
      .then(res => {
        if (!res.ok) throw new Error("Error al registrar notas");
        return res.json();
      })
      .then(data => {
        alert(`Notas guardadas para ${data.estudiante.nombre} en curso ${data.curso.nombre}. Promedio: ${data.promedio}`);
      })
      .catch(err => alert(err.message));
    }
  });
});
