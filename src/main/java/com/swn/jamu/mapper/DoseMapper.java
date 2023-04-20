package com.swn.jamu.mapper;

import com.swn.jamu.dto.DoseDTO;
import com.swn.jamu.model.Dose;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoseMapper {

    @Mapping(target = "id", ignore = true)
    Dose toDose(DoseDTO doseDTO);

    DoseDTO toDoseDTO(Dose dose);

    @Mapping(target = "id", ignore = true)
    List<Dose> toDoses(List<DoseDTO> doseDTOS);
}
