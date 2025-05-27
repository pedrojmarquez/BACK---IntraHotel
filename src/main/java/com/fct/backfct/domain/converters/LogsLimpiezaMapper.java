package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.LogsLimpiezaDTO;
import com.fct.backfct.domain.models.entity.LogsLimpieza;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class LogsLimpiezaMapper {

    private final ModelMapper mapper = new ModelMapper();

    // metodo para convertir de entidad a dto
    public LogsLimpiezaDTO toDto(LogsLimpieza logsLimpieza) {
        return mapper.map(logsLimpieza, LogsLimpiezaDTO.class);
    }

    // metodo para convertir de dto a entidad
    public LogsLimpieza toEntity(LogsLimpiezaDTO dto) {
        return mapper.map(dto, LogsLimpieza.class);
    }

    // metodo para convertir una lista de entidades a una lista de dtos
    public List<LogsLimpiezaDTO> toListDtos(List<LogsLimpieza> logsLimpieza) {
        Type listType = new TypeToken<List<LogsLimpiezaDTO>>() {}.getType();
        return mapper.map(logsLimpieza, listType);
    }


    // metodo para convertir una lista de dtos a una lista de entidades
    public List<LogsLimpieza> toListEntities(List<LogsLimpiezaDTO> logsLimpieza) {
        Type listType = new TypeToken<List<LogsLimpieza>>() {}.getType();
        return mapper.map(logsLimpieza, listType);
    }
}
