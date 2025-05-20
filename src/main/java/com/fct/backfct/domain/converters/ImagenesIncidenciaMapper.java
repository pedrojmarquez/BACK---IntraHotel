package com.fct.backfct.domain.converters;

import com.fct.backfct.domain.dto.ImagenesIncidenciaDTO;
import com.fct.backfct.domain.models.entity.ImagenesIncidencia;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class ImagenesIncidenciaMapper {
    private final ModelMapper mapper = new ModelMapper();

    // metodo para convertir de entidad a dto
    public ImagenesIncidenciaDTO toDto(ImagenesIncidencia entity) {
        return mapper.map(entity, ImagenesIncidenciaDTO.class);
    }

    // metodo para convertir de dto a entidad
    public ImagenesIncidencia toEntity(ImagenesIncidenciaDTO dto) {
        return mapper.map(dto, ImagenesIncidencia.class);
    }

    // metodo para convertir una lista de entidades a una lista de dtos
    public List<ImagenesIncidenciaDTO> toListDtos(List<ImagenesIncidencia> imagenesIncidencias) {
        Type listType = new TypeToken<List<ImagenesIncidenciaDTO>>() {}.getType();
        return mapper.map(imagenesIncidencias, listType);
    }

    // metodo para convertir una lista de dtos a una lista de entidades
    public List<ImagenesIncidencia> toListEntities(List<ImagenesIncidenciaDTO> imagenesIncidenciasDto) {
        Type listType = new TypeToken<List<ImagenesIncidencia>>() {}.getType();
        return mapper.map(imagenesIncidenciasDto, listType);
    }

}
