/*  ===============================
    Pagina Administrador 
    ===============================   */
document.addEventListener("DOMContentLoaded", function() {
  // Al cargar la página, listar datos existentes
  cargarEstudiantes();
  cargarCursos();
  cargarDocentes();
  // --- Registrar Estudiante ---
  document.getElementById("formEstudiante").addEventListener("submit", e => {
    e.preventDefault();
    const estudiante = {
      nombre: document.getElementById("nombreEstudiante").value,
      apellido: document.getElementById("apellidoEstudiante").value,
      email: document.getElementById("correoEstudiante").value
    };
    fetch("http://localhost:8080/estudiantes", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(estudiante)
    }).then(() => cargarEstudiantes());
    e.target.reset();
  });

  // --- Registrar Curso ---
  document.getElementById("formCurso").addEventListener("submit", e => {
    e.preventDefault();
    const curso = {
      nombre: document.getElementById("nombreCurso").value,
      descripcion: document.getElementById("descripcionCurso").value
    };
    fetch("http://localhost:8080/cursos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(curso)
    }).then(() => cargarCursos());
    e.target.reset();
  });

  // --- Registrar Docente ---
  document.getElementById("formDocente").addEventListener("submit", e => {
    e.preventDefault();
    const docente = {
      nombre: document.getElementById("nombreDocente").value,
      apellido: document.getElementById("apellidoDocente").value,
      email: document.getElementById("correoDocente").value
    };
    fetch("http://localhost:8080/docentes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(docente)
    }).then(() => cargarDocentes());
    e.target.reset();
  });
});

// --- Funciones de carga ---
function cargarEstudiantes() {
  fetch("http://localhost:8080/estudiantes")
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#tablaEstudiantes tbody");
      tbody.innerHTML = "";
      data.forEach(est => {
        const fila = tbody.insertRow();
        fila.innerHTML = `<td>${est.nombre}</td><td>${est.apellido}</td><td>${est.email}</td>`;
      });
    });
}

function cargarCursos() {
  fetch("http://localhost:8080/cursos")
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#tablaCursos tbody");
      tbody.innerHTML = "";
      data.forEach(curso => {
        const fila = tbody.insertRow();
        fila.innerHTML = `<td>${curso.nombre}</td><td>${curso.descripcion}</td>`;
      });
    });
}

function cargarDocentes() {
  fetch("http://localhost:8080/docentes")
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#tablaDocentes tbody");
      tbody.innerHTML = "";
      data.forEach(doc => {
        const fila = tbody.insertRow();
        fila.innerHTML = `
          <td>${doc.nombre}</td>
          <td>${doc.apellido}</td>
          <td>${doc.email}</td>
          <td class="cursoAsignado">${doc.curso ? doc.curso.nombre : "-"}</td>
          <td><button class="btnAsignar">Asignar Curso</button></td>
        `;
        fila.querySelector(".btnAsignar").addEventListener("click", () => asignarCurso(doc.id, fila));
      });
    });
}

// --- Asignar curso a docente ---
function asignarCurso(docenteId, filaDocente) {
  fetch("http://localhost:8080/cursos")
    .then(res => res.json())
    .then(cursos => {
      if (cursos.length === 0) {
        alert("No hay cursos registrados para asignar.");
        return;
      }

      const select = document.createElement("select");
      cursos.forEach(curso => {
        const option = document.createElement("option");
        option.value = curso.id;
        option.textContent = curso.nombre;
        select.appendChild(option);
      });

      const accionesCell = filaDocente.querySelector("td:last-child");
      accionesCell.innerHTML = "";
      accionesCell.appendChild(select);

      const btnConfirmar = document.createElement("button");
      btnConfirmar.textContent = "Confirmar";
      accionesCell.appendChild(btnConfirmar);

      btnConfirmar.addEventListener("click", () => {
        const cursoId = select.value;
        fetch(`http://localhost:8080/docentes/${docenteId}/curso/${cursoId}`, { method: "PUT" })
          .then(res => res.json())
          .then(data => {
            filaDocente.querySelector(".cursoAsignado").textContent = data.curso.nombre;
            accionesCell.innerHTML = `<button class="btnAsignar">Asignar Curso</button>`;
            accionesCell.querySelector(".btnAsignar").addEventListener("click", () => asignarCurso(docenteId, filaDocente));
          });
      });
    });
}





