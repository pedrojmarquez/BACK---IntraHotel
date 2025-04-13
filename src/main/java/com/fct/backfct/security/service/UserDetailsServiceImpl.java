package com.fct.backfct.security.service;


import com.fct.backfct.security.entity.Empleados;
import com.fct.backfct.security.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EmpleadoService empleadoService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleados Empleados = empleadoService.getByEmail(email);
        return UsuarioPrincipal.build(Empleados);
    }
}