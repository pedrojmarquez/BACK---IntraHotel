package com.fct.backfct.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {
    private Long idEmpleado;
    private String nombre;
    private String email;
    private String clave;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(Long idEmpleado, String nombre, String email, String clave, Collection<? extends GrantedAuthority> authorities) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(Empleados empleados){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(empleados.getRol().getNombreRol().equals("coordinador-tecnico") ? new SimpleGrantedAuthority("coordinador-tecnico") : new SimpleGrantedAuthority("tecnico"));
        return new UsuarioPrincipal(empleados.getId(), empleados.getNombre(), empleados.getEmail(), empleados.getClave(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setId(Long id) {
        this.idEmpleado = id;
    }
}
