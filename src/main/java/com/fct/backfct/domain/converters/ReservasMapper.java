package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.models.entity.Reservas;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class ReservasMapper {

    private final ModelMapper mapper = new ModelMapper();


    //metodo para convertir de entidad a dto
    public ReservasDTO toDto(Reservas entity) {
        return mapper.map(entity, ReservasDTO.class);
    }

    //metodo para convertir de dto a entidad
    public Reservas toEntity(ReservasDTO dto) {
        return mapper.map(dto, Reservas.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<ReservasDTO> toListDtos(List<Reservas> reservas) {
        Type listType = new TypeToken<List<ReservasDTO>>() {}.getType();
        return mapper.map(reservas, listType);
    }


}
