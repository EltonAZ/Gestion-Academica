document.addEventListener("DOMContentLoaded", function() {
    const formNota = document.getElementById("formNota");

    formNota.addEventListener("submit", function(e) {
        e.preventDefault();

        const estudiante = document.getElementById("nombreEstudiante").value;
        const curso = document.getElementById("nombreCurso").value;
        const nota = document.getElementById("nota").value;

        const tabla = document.getElementById("tablaNotas").querySelector("tbody");
        const fila = tabla.insertRow();
        fila.innerHTML = `<td>${estudiante}</td><td>${curso}</td><td>${nota}</td>`;

        e.target.reset();
    });
});

