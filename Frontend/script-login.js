
/*  ===============================
    Pagina index 
    ===============================   */
// Captura el evento "submit" del formulario de login
document.getElementById("loginForm").addEventListener("submit", e => {
  e.preventDefault();
  const usuario = {
    email: document.getElementById("loginEmail").value,
    password: document.getElementById("loginPassword").value
  };

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
    // Limpiar mensaje de error si el login fue exitoso
    document.getElementById("errorMsg").textContent = "";

    // Guardar sesión en localStorage
    localStorage.setItem("usuarioActivo", data.email);
    localStorage.setItem("usuarioId", data.id);

    // Redirigir según el rol
    if (data.rol === "ADMIN") {
      window.location.href = "admin.html";
    } else if (data.rol === "DOCENTE") {
      window.location.href = "docente.html";
    } else {
      window.location.href = "estudiante.html";
    }
  })
  .catch(err => {
    // Mostrar error en el párrafo
    document.getElementById("errorMsg").textContent = err.message;
  });
});


