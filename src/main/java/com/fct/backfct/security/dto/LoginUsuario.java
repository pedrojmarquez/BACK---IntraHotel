package com.fct.backfct.security.dto;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {
    @NotBlank
    private String email;
    @NotBlank
    private String clave;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}