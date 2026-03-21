document.addEventListener("DOMContentLoaded", function() {
    const tabla = document.getElementById("tablaNotasEstudiante").querySelector("tbody");

    // Simulación: notas cargadas
  const notas = [
    { curso: "Matemáticas", nota: 15 },
    { curso: "Historia", nota: 18 },
    { curso: "Programación", nota: 20 }
  ];

  notas.forEach(registro => {
    const fila = tabla.insertRow();
    fila.innerHTML = `<td>${registro.curso}</td><td>${registro.nota}</td>`;
  })
});