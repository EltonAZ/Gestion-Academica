package com.colegio.colegio.entity;

public class LoginRequest {
    private String email;      // Correo electrónico del usuario
    private String password;   // Contraseña del usuario

    // getters y setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
