package com.fct.backfct.domain.services.Cliente;

import com.fct.backfct.domain.converters.ClientesMapper;
import com.fct.backfct.domain.dto.ClientesDTO;
import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.models.dao.IClientesDao;
import com.fct.backfct.domain.models.entity.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesServiceImpl implements IClientesService {

    @Autowired
    private IClientesDao clientesDao;
    @Autowired
    private ClientesMapper clientesMapper;

    @Override
    public List<ClientesDTO> findAll() {

        return clientesMapper.toListDtos(clientesDao.findAll());
    }

    @Override
    public ClientesDTO findById(Long id) {

        return clientesMapper.toDto(clientesDao.findById(id).orElse(null));
    }

    @Override
    public ClientesDTO save(ClientesDTO dto) {

        return clientesMapper.toDto(clientesDao.save(clientesMapper.toEntity(dto)));
    }

    @Override
    public ClientesDTO update(ClientesDTO dto) {

        return clientesMapper.toDto(clientesDao.save(clientesMapper.toEntity(dto)));
    }

    @Override
    public List<ClientesDTO> findByHabitacionId(Long idHabitacion) {

        return clientesMapper.toListDtos(clientesDao.findLas5ClientesDeHabitacion(idHabitacion));
    }

    @Override
    public ClientesDTO delete(ClientesDTO clientesDTO) {
        Clientes clientes = clientesMapper.toEntity(clientesDTO);
        clientes.setActivo(false);
        return clientesMapper.toDto(clientesDao.save(clientes));
    }

    @Override
    public ClientesDTO findByDni(String dni) {

        return clientesMapper.toDto(clientesDao.findBydni(dni));
    }


}
