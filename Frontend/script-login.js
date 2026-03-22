
/*  ===============================
    Pagina index 
    ===============================   */
// Captura el evento "submit" del formulario de login
document.getElementById("loginForm").addEventListener("submit", function(e) {
  e.preventDefault(); // Evita que la página se recargue al enviar el formulario

  // Obtiene los valores ingresados por el usuario
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value; 

  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({email: email, password: password})
  })
  .then(response => {
    if (!response.ok) {
      throw new Error("Credenciales invalidas");
    }
    return response.json();
  })
  .then(data => {
    //Redirigir segun el rol
    if (data.rol === "admin") {
      window.location.href = "admin.html";
    } else if (data.rol === "docente") {
      window.location.href = "docente.html";
    }else if (data.rol === "estudiante") {
      window.location.href = "estudiante.html";
    }
  })
  .catch(error => {
    document.getElementById("errorMsg").textContent = "Correo o contraseña incorrectos";
    console.error(error);
  }); 
});
