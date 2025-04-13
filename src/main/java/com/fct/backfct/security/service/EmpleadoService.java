package com.fct.backfct.security.service;


import com.fct.backfct.domain.models.dao.IEmpleadosDao;
import com.fct.backfct.security.entity.Empleados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpleadoService {


    @Autowired
    IEmpleadosDao empleadosDao;

    public Empleados getByEmail(String email){
        return empleadosDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }


    public boolean existsByEmail(String email){
        return empleadosDao.existsByEmail(email);
    }

    public void save(Empleados Empleados){
        empleadosDao.save(Empleados);
    }





}
