package com.swn.jamu.mapper;

import com.swn.jamu.dto.DoseDTO;
import com.swn.jamu.dto.JamuDTO;
import com.swn.jamu.model.Dose;
import com.swn.jamu.model.Jamu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JamuMapper {

    @Mapping(target = "id", ignore = true)
    Jamu toJamu(JamuDTO jamuDTO);

    JamuDTO toJamuDTO(Jamu jamu);

    @AfterMapping
    default void afterDoseToDoseDTO(@MappingTarget DoseDTO doseDTO, Dose dose) {
        if (doseDTO.getBaseJamuId() == null && dose.getBaseJamu() != null) {
            doseDTO.setBaseJamuId(dose.getBaseJamu().getId());
            doseDTO.setBaseJamuCode(dose.getBaseJamu().getCode());
            doseDTO.setBaseJamuName(dose.getBaseJamu().getName());
        }
        if (doseDTO.getJamuId() == null && dose.getJamu() != null) {
            doseDTO.setJamuId(dose.getJamu().getId());
        }
    }
}
