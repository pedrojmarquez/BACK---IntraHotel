package com.fct.backfct.domain.services.Cliente;

import com.fct.backfct.domain.dto.ClientesDTO;

import java.util.List;

public interface IClientesService {

    List<ClientesDTO> findAll();
    ClientesDTO findById(Long id);
    ClientesDTO save(ClientesDTO dto);
    ClientesDTO update(ClientesDTO dto);

}
