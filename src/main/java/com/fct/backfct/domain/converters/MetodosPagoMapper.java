package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.models.entity.MetodosPago;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class MetodosPagoMapper {

    private final ModelMapper mapper = new ModelMapper();

    // metodo para convertir de entidad a dto
    public MetodosPagoDTO toDto(MetodosPago entity) {
        return mapper.map(entity, MetodosPagoDTO.class);
    }

    // metodo para convertir de dto a entidad
    public MetodosPago toEntity(MetodosPagoDTO dto) {
        return mapper.map(dto, MetodosPago.class);
    }

    // metodo para convertir una lista de entidades a una lista de dtos
    public List<MetodosPagoDTO> toListDtos(List<MetodosPago> metodosPago) {
        Type listType = new TypeToken<List<MetodosPagoDTO>>() {}.getType();
        return mapper.map(metodosPago, listType);
    }
}
