package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.EstadosIncidenciaDTO;
import com.fct.backfct.domain.models.entity.EstadosIncidencia;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EstadosIncidenciaMapper {

    private final ModelMapper mapper = new ModelMapper();

    // metodo para convertir de entidad a dto
    public EstadosIncidenciaDTO toDto(EstadosIncidencia entity) {
        return mapper.map(entity, EstadosIncidenciaDTO.class);
    }

    // metodo para convertir de dto a entidad
    public EstadosIncidencia toEntity(EstadosIncidenciaDTO dto) {
        return mapper.map(dto, EstadosIncidencia.class);
    }
}
