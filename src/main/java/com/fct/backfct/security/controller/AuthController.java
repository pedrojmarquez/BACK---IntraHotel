package com.fct.backfct.security.controller;



import com.fct.backfct.security.dto.JwtDto;
import com.fct.backfct.security.dto.LoginUsuario;
import com.fct.backfct.security.dto.NuevoUsuario;
import com.fct.backfct.security.entity.Empleados;
import com.fct.backfct.security.jwt.JwtProvider;
import com.fct.backfct.security.service.RolService;
import com.fct.backfct.security.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    RolService rolService;



    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity("Campos erróneos o email inválido", HttpStatus.BAD_REQUEST);
        if (empleadoService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity("El email ya existe", HttpStatus.BAD_REQUEST);
        Empleados empleado = new Empleados(nuevoUsuario.getDni(),nuevoUsuario.getNombre(),
                nuevoUsuario.getApellidos(),nuevoUsuario.getEmail(),
                nuevoUsuario.getTelefono(),rolService.getById(nuevoUsuario.getIdRol()),passwordEncoder.encode(nuevoUsuario.getClave())
                ,nuevoUsuario.getFechaContratacion(),nuevoUsuario.getActivo());

        empleadoService.save(empleado);
        return new ResponseEntity("Usuario creado correctamente", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity("Campos erróneos", HttpStatus.BAD_REQUEST);
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginUsuario.getEmail(), loginUsuario.getClave()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtDto jwtDto = new JwtDto(jwt);
            return new ResponseEntity(jwtDto, HttpStatus.OK);
        }catch (BadCredentialsException | UsernameNotFoundException ex){
            return new ResponseEntity("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}