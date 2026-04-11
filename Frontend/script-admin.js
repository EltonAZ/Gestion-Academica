/*  ===============================
    Pagina Administrador 
    ===============================   */
document.addEventListener("DOMContentLoaded", function() {
  // Al cargar la página, listar datos existentes
  cargarEstudiantes();
  cargarCursos();
  cargarDocentes();

//Sidebar activar o resaltar
document.querySelectorAll('.sidebar ul li a').forEach(link => {
  link.addEventListener('click', function() {
    document.querySelectorAll('.sidebar ul li a').forEach(el => el.classList.remove('active'));
    this.classList.add('active');
  });
});

  // --- Registrar Estudiante ---
  document.getElementById("formEstudiante").addEventListener("submit", e => {
    e.preventDefault();
    const selectedOptions = Array.from(document.querySelectorAll("#cursosEstudiante input[type=checkbox]:checked"));
    const cursosSeleccionados = selectedOptions.map(opt => ({ id: parseInt(opt.value) }));

    const estudiante = {
      nombre: document.getElementById("nombreEstudiante").value,
      apellido: document.getElementById("apellidoEstudiante").value,
      email: document.getElementById("correoEstudiante").value,
      password: document.getElementById("passwordEstudiante").value,
      cursos: cursosSeleccionados
    };

    fetch("http://localhost:8080/estudiantes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(estudiante)
    })
    .then(res => res.json())
    .then(data => {
      console.log("Estudiante registrado:", data);
      cargarEstudiantes();
    })
    .catch(err => console.error("Error registrando estudiante:", err));

    e.target.reset();
  });

  // --- Control del Modal Estudiante ---
  btnAgregarEstudiante.onclick = () => modalEstudiante.style.display = "block";
  cerrarModal.onclick = () => modalEstudiante.style.display = "none";

  // --- Control del Modal Curso ---
  btnAgregarCurso.onclick = () => modalCurso.style.display = "block";
  cerrarModalCurso.onclick = () => modalCurso.style.display = "none";

  // --- Control del Modal Docente ---
  btnAgregarDocente.onclick = () => modalDocente.style.display = "block";
  cerrarModalDocente.onclick = () => modalDocente.style.display = "none";

  // --- Listener global para cerrar modales al hacer clic fuera ---
  window.addEventListener("click", (event) => {
    if (event.target === modalEstudiante) {
      modalEstudiante.style.display = "none";
    }
    if (event.target === modalCurso) {
      modalCurso.style.display = "none";
    }
    if (event.target === modalDocente) {
      modalDocente.style.display = "none";
    }
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
    })
    .then(res => res.json())
    .then(data => {
      console.log("Curso registrado:", data);
      cargarCursos();
    })
    .catch(err => console.error("Error registrando curso:", err));

    e.target.reset();
  });

  // --- Registrar Docente ---
  document.getElementById("formDocente").addEventListener("submit", e => {
  e.preventDefault();
  const selectedOptions = Array.from(document.querySelectorAll("#cursosDocente input[type=checkbox]:checked"));
  const cursosSeleccionados = selectedOptions.map(opt => ({ id: parseInt(opt.value) }));

  const docente = {
    nombre: document.getElementById("nombreDocente").value,
    apellido: document.getElementById("apellidoDocente").value,
    email: document.getElementById("correoDocente").value,
    password: document.getElementById("passwordDocente").value,
    cursos: cursosSeleccionados
  };

  console.log("JSON enviado docente:", JSON.stringify(docente));

  fetch("http://localhost:8080/docentes", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(docente)
  })
  .then(res => {
    if (!res.ok) {
      throw new Error("Error HTTP: " + res.status);
    }
    return res.json();
  })
  .then(data => {
    console.log("Docente registrado:", data);
    cargarDocentes();
  })
  .catch(err => console.error("Error registrando docente:", err));

  e.target.reset();
});
});

// --- Funciones de carga ---
function cargarEstudiantes() {
  fetch("http://localhost:8080/estudiantes")
    .then(res => res.json())
    .then(data => {
      console.log("Estudiantes:", data);
      const tbody = document.querySelector("#tablaEstudiantes tbody");
      tbody.innerHTML = "";
      data.forEach(est => {
        const cursos = est.cursos ? est.cursos.map(c => c.nombre).join(", ") : "-";
        const fila = tbody.insertRow();
        fila.innerHTML = `
          <td>${est.nombre}</td>
          <td>${est.apellido}</td>
          <td>${est.email}</td>
          <td>${cursos}</td>
        `;
      });
    })
    .catch(err => console.error("Error cargando estudiantes:", err));
}



function cargarDocentes() {
  fetch("http://localhost:8080/docentes")
    .then(res => res.json())
    .then(data => {
      console.log("Docentes:", data);
      const tbody = document.querySelector("#tablaDocentes tbody");
      tbody.innerHTML = "";
      data.forEach(doc => {
        const cursos = doc.cursos ? doc.cursos.map(c => c.nombre).join(", ") : "-";
        const fila = tbody.insertRow();
        fila.innerHTML = `
          <td>${doc.nombre}</td>
          <td>${doc.apellido}</td>
          <td>${doc.email}</td>
          <td>${cursos}</td>
        `;
      });
    })
    .catch(err => console.error("Error cargando docentes:", err));
}


function cargarCursos() {
  fetch("http://localhost:8080/cursos")
    .then(res => res.json())
    .then(data => {
      console.log("Cursos:", data);
      const tbody = document.querySelector("#tablaCursos tbody");
      tbody.innerHTML = "";

      const contenedorDocente = document.querySelector("#cursosDocente");
      const contenedorEstudiante = document.querySelector("#cursosEstudiante");
      contenedorDocente.innerHTML = "";
      contenedorEstudiante.innerHTML = "";

      data.forEach(curso => {
        const fila = tbody.insertRow();
        fila.innerHTML = `<td>${curso.nombre}</td><td>${curso.descripcion}</td>`;

        // === Checkbox para docentes ===
        const divDocente = document.createElement("div");
        divDocente.classList.add("opcion");

        const labelDocente = document.createElement("label");
        labelDocente.textContent = curso.nombre;
        labelDocente.setAttribute("for", "docente-" + curso.id);

        const checkboxDocente = document.createElement("input");
        checkboxDocente.type = "checkbox";
        checkboxDocente.value = curso.id;
        checkboxDocente.id = "docente-" + curso.id;

        // Primero el nombre, luego el checkbox
        divDocente.appendChild(labelDocente);
        divDocente.appendChild(checkboxDocente);
        contenedorDocente.appendChild(divDocente);

        // === Checkbox para estudiantes ===
        const divEstudiante = document.createElement("div");
        divEstudiante.classList.add("opcion");

        const labelEstudiante = document.createElement("label");
        labelEstudiante.textContent = curso.nombre;
        labelEstudiante.setAttribute("for", "estudiante-" + curso.id);

        const checkboxEstudiante = document.createElement("input");
        checkboxEstudiante.type = "checkbox";
        checkboxEstudiante.value = curso.id;
        checkboxEstudiante.id = "estudiante-" + curso.id;

        divEstudiante.appendChild(labelEstudiante);
        divEstudiante.appendChild(checkboxEstudiante);
        contenedorEstudiante.appendChild(divEstudiante);

      });
    })
    .catch(err => console.error("Error cargando cursos:", err));


}


// SCRIPT PARA EL BOTON SALIR
// Al cargar el panel, validar si hay sesión
if (!localStorage.getItem("usuarioActivo")) {
  window.location.href = "index.html"; // si no hay sesión, vuelve al index
}

// Botón salir
document.getElementById('btnSalir').addEventListener('click', function() {
  localStorage.removeItem('usuarioActivo'); // borra la sesión
  window.location.href = "index.html";      // redirige al index
});










