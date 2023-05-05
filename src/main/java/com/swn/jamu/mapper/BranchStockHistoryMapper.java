package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchStockHistoryDTO;
import com.swn.jamu.model.BranchStockHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchStockHistoryMapper {

    @Mapping(target = "id", ignore = true)
    BranchStockHistory toBranchStockHistory(BranchStockHistoryDTO branchStockHistoryDTO);

    @Mapping(target = "branchStockId", source = "branchStock.id")
    @Mapping(target = "baseJamuName", source = "branchStock.baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "branchStock.baseJamu.code")
    BranchStockHistoryDTO toBranchStockHistoryDTO(BranchStockHistory branchStockHistory);
}
