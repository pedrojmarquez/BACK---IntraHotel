package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.models.entity.EstadosReserva;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class EstadosReservasMapper {
    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public EstadosReservaDTO toDto(EstadosReserva entity) {
        return mapper.map(entity, EstadosReservaDTO.class);
    }

    //metodo para convertir de dto a entidad
    public EstadosReserva toEntity(EstadosReservaDTO dto) {
        return mapper.map(dto, EstadosReserva.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<EstadosReservaDTO> toListDtos(List<EstadosReserva> estadosReservas) {
        Type listType = new TypeToken<List<EstadosReservaDTO>>() {}.getType();
        return mapper.map(estadosReservas, listType);
    }
}
