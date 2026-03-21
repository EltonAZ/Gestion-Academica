
/*  ===============================
    Pagina index 
    ===============================   */
// Captura el evento "submit" del formulario de login
document.getElementById("loginForm").addEventListener("submit", function(e) {
  e.preventDefault(); // Evita que la página se recargue al enviar el formulario

  // Obtiene los valores ingresados por el usuario
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value; 

  // Simulación de roles (esto luego se conectará al backend)
  if(email === "admin@elitec.edu" && password === "1234") {
    window.location.href = "admin.html";
  } else if(email === "docente@elitec.edu" && password === "1234") {
    window.location.href = "docente.html";
  } else if(email === "estudiante@elitec.edu" && password === "1234") {
    window.location.href = "estudiante.html";
  } else {
      // Si las credenciales no coinciden, muestra un mensaje de error
      document.getElementById("errorMsg").textContent = "Credenciales inválidas";
  }
});
