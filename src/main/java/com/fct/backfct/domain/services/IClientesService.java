package com.fct.backfct.domain.services;

import com.fct.backfct.domain.dto.ClientesDTO;
import com.fct.backfct.domain.models.entity.Clientes;

import java.util.List;

public interface IClientesService {

    List<ClientesDTO> findAll();
    ClientesDTO findById(Long id);
    ClientesDTO save(ClientesDTO dto);
    ClientesDTO update(ClientesDTO dto);

}
