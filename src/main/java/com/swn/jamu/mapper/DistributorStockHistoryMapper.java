package com.swn.jamu.mapper;

import com.swn.jamu.dto.DistributorStockHistoryDTO;
import com.swn.jamu.model.DistributorStockHistory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DistributorStockHistoryMapper {

    @Mapping(target = "id", ignore = true)
    DistributorStockHistory toDistributorStockHistory(DistributorStockHistoryDTO distributorStockHistoryDTO);

    @Mapping(target = "distributorStockId", source = "distributorStock.id")
    @Mapping(target = "baseJamuName", source = "distributorStock.baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "distributorStock.baseJamu.code")
    DistributorStockHistoryDTO toDistributorStockHistoryDTO(DistributorStockHistory distributorStockHistory);

    @AfterMapping
    default void afterToDistributorStockHistoryDTO(@MappingTarget DistributorStockHistoryDTO distributorStockHistoryDTO,
                                                   DistributorStockHistory distributorStockHistory) {
        distributorStockHistoryDTO.setQty(distributorStockHistory.getQty() / 1000);
        distributorStockHistoryDTO.setPreviousQty(distributorStockHistory.getPreviousQty() / 1000);
    }
}
