package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.ServiciosDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiciosMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public ServiciosDTO toDto(com.fct.backfct.domain.models.entity.Servicios entity) {
        return mapper.map(entity, ServiciosDTO.class);
    }

    //metodo para convertir de dto a entidad
    public com.fct.backfct.domain.models.entity.Servicios toEntity(ServiciosDTO dto) {
        return mapper.map(dto, com.fct.backfct.domain.models.entity.Servicios.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public java.util.List<ServiciosDTO> toListDtos(java.util.List<com.fct.backfct.domain.models.entity.Servicios> servicios) {
        return servicios.stream().map(this::toDto).toList();
    }
}
