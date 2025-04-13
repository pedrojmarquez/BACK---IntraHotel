package com.fct.backfct.domain.converters;


import com.fct.backfct.domain.dto.ClientesDTO;
import com.fct.backfct.domain.models.entity.Clientes;
import net.bytebuddy.description.method.MethodDescription;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class ClientesMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public ClientesDTO toDto(Clientes entity) {
        return mapper.map(entity, ClientesDTO.class);
    }

    //metodo para convertir de dto a entidad
    public Clientes toEntity(ClientesDTO dto) {
        return mapper.map(dto, Clientes.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<ClientesDTO> toListDtos(List<Clientes> clientes) {
        Type listType = new TypeToken<List<ClientesDTO>>() {}.getType();
        return mapper.map(clientes, listType);
    }


}
