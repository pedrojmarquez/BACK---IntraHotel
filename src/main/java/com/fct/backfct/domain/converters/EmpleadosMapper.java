package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.EmpleadosDTO;
import com.fct.backfct.security.entity.Empleados;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class EmpleadosMapper {

    private final ModelMapper mapper = new ModelMapper();


    // metodo para convertir de entidad a dto
    public EmpleadosDTO toDto(Empleados entity) {
        return mapper.map(entity, EmpleadosDTO.class);
    }

    // metodo para convertir de dto a entidad
    public Empleados toEntity(EmpleadosDTO dto) {
        return mapper.map(dto, Empleados.class);
    }

    // metodo para convertir una lista de entidades a una lista de dtos
    public List<EmpleadosDTO> toListDtos(List<Empleados> empleados) {
        Type listType = new TypeToken<List<EmpleadosDTO>>() {}.getType();
        return mapper.map(empleados, listType);
    }
}
