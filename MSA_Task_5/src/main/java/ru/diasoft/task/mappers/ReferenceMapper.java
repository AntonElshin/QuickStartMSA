package ru.diasoft.task.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.dto.ReferenceDTO;

@Mapper(componentModel = "spring")
public interface ReferenceMapper {

    ReferenceMapper INSTANCE = Mappers.getMapper(ReferenceMapper.class);

    ReferenceDTO toDto(ReferenceEntity referenceEntity);
    ReferenceEntity fromDto(ReferenceDTO referenceDTO);

}
