package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.IncidenciasDTO;
import com.fct.backfct.domain.models.entity.Incidencias;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class IncidenciasMapper {

    private final ModelMapper mapper = new ModelMapper();


    // metodo para convertir de entidad a dto
    public IncidenciasDTO toDto(Incidencias incidencia) {
        return mapper.map(incidencia, IncidenciasDTO.class);
    }

    // metodo para convertir de dto a entidad
    public Incidencias toEntity(IncidenciasDTO incidencia) {
        return mapper.map(incidencia, Incidencias.class);
    }

    // metodo para convertir una lista de entidades a una lista de dtos
    public List<IncidenciasDTO> toListDtos(List<Incidencias> incidencias) {
        Type listType = new TypeToken<List<IncidenciasDTO>>() {}.getType();
        return mapper.map(incidencias, listType);
    }


    // metodo para convertir una lista de dtos a una lista de entidades
    public List<Incidencias> toListEntities(List<IncidenciasDTO> incidencias) {
        Type listType = new TypeToken<List<Incidencias>>() {}.getType();
        return mapper.map(incidencias, listType);
    }
}
