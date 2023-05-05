package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchStockDTO;
import com.swn.jamu.model.BranchStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchStockMapper {

    @Mapping(target = "id", ignore = true)
    BranchStock toBranchStock(BranchStockDTO branchStockDTO);

    @Mapping(target = "baseJamuId", source = "baseJamu.id")
    @Mapping(target = "baseJamuName", source = "baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "baseJamu.code")
    @Mapping(target = "branchId", source = "branch.id")
    BranchStockDTO toBranchStockDTO(BranchStock branchStock);
}
