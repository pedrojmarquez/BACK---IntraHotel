package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.models.entity.Facturas;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class FacturasMapper {

    private final ModelMapper mapper = new ModelMapper();

    // metodo para convertir de entidad a dto
    public Facturas toEntity(FacturasDTO entity) {
        return mapper.map(entity, Facturas.class);
    }

    // metodo para convertir de dto a entidad
    public FacturasDTO toDto(Facturas entity) {
        return mapper.map(entity, FacturasDTO.class);
    }


    // metodo para convertir una lista de entidades a una lista de dtos
    public List<Facturas> toListDtos(List<Facturas> facturas) {
        Type listType = new TypeToken<List<Facturas>>() {}.getType();
        return mapper.map(facturas, listType);
    }
}
