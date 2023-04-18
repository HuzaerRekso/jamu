package com.swn.jamu.mapper;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.model.BaseJamu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BaseJamuMapper {

    @Mapping(target = "id", ignore = true)
    BaseJamu toBaseJamu(BaseJamuDTO baseJamuDTO);

    BaseJamuDTO toBaseJamuDTO(BaseJamu baseJamu);
}
