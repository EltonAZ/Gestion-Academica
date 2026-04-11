/*  ===============================
    Pagina index 
    ===============================   */
document.getElementById("loginForm").addEventListener("submit", e => {
  e.preventDefault();
  const usuario = {
    email: document.getElementById("loginEmail").value,
    password: document.getElementById("loginPassword").value
  };

  // Llamar al endpoint general de usuarios
  fetch("http://localhost:8080/usuarios/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(usuario)
  })
  .then(res => {
    if (!res.ok) throw new Error("Credenciales inválidas");
    return res.json();
  })
  .then(data => {
    document.getElementById("errorMsg").textContent = "";

    // Guardar sesión en localStorage
    localStorage.setItem("usuarioActivo", JSON.stringify(data));

    // Redirigir según el rol
    if (data.rol === "ADMIN") {
      window.location.href = "admin.html";
    } else if (data.rol === "DOCENTE") {
      // Aquí hacemos un segundo fetch al login de docentes
      return fetch("http://localhost:8080/docentes/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      })
      .then(res => res.json())
      .then(docente => {
        localStorage.setItem("usuarioActivo", JSON.stringify(docente));
        window.location.href = "docente.html";
      });
    } else if (data.rol === "ESTUDIANTE") {
      // Aquí hacemos un segundo fetch al login de estudiantes
      return fetch("http://localhost:8080/estudiantes/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      })
      .then(res => res.json())
      .then(estudiante => {
        localStorage.setItem("usuarioActivo", JSON.stringify(estudiante));
        window.location.href = "estudiante.html";
      });
    }
  })
  .catch(err => {
    document.getElementById("errorMsg").textContent = err.message;
  });
});
