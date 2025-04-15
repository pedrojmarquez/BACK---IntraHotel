package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.EstadosHabitacionDTO;
import com.fct.backfct.domain.models.entity.EstadosHabitacion;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class EstadosHabitacionMapper {
    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public EstadosHabitacionDTO toDto(EstadosHabitacion entity) {
        return mapper.map(entity, EstadosHabitacionDTO.class);
    }

    //metodo para convertir de dto a entidad
    public EstadosHabitacion toEntity(EstadosHabitacionDTO dto) {
        return mapper.map(dto, EstadosHabitacion.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<EstadosHabitacionDTO> toListDtos(List<EstadosHabitacion> estadosHabitacion) {
        Type listType = new TypeToken<List<EstadosHabitacionDTO>>() {}.getType();
        return mapper.map(estadosHabitacion, listType);
    }

}
