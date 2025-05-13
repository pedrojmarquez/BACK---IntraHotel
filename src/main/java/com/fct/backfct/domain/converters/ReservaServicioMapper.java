package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.ReservaServicioDTO;
import com.fct.backfct.domain.models.entity.ReservasServicios;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class ReservaServicioMapper {

    private final ModelMapper mapper = new ModelMapper();


    //metodo para convertir de entidad a dto
    public ReservaServicioDTO toEntity(ReservasServicios entity) {
        return mapper.map(entity, ReservaServicioDTO.class);
    }

    //metodo para convertir de dto a entidad
    public ReservasServicios toDto(ReservaServicioDTO entity) {
        return mapper.map(entity, ReservasServicios.class);
    }

    //metodo para convertir una lista de entidades a una lista de dtos
    public List<ReservaServicioDTO> toListDtos(List<ReservasServicios> reservasServicios) {
        Type listType = new TypeToken<List<ReservaServicioDTO>>() {}.getType();
        return mapper.map(reservasServicios, listType);
    }

    //metodo para convertir una lista de dtos a una lista de entidades
    public List<ReservasServicios> toListEntities(List<ReservaServicioDTO> reservasServiciosDto) {
        Type listType = new TypeToken<List<ReservasServicios>>() {}.getType();
        return mapper.map(reservasServiciosDto, listType);
    }
}
