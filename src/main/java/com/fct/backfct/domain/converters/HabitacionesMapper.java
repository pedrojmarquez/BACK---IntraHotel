package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.models.entity.Habitaciones;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class HabitacionesMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public HabitacionesDTO toDto(Habitaciones entity) {
        return mapper.map(entity, HabitacionesDTO.class);
    }

    //metodo para convertir de dto a entidad
    public Habitaciones toEntity(HabitacionesDTO dto) {
        return mapper.map(dto, Habitaciones.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<HabitacionesDTO> toListDtos(List<Habitaciones> habitaciones) {
        Type listType = new TypeToken<List<HabitacionesDTO>>() {}.getType();
        return mapper.map(habitaciones, listType);
    }
}
