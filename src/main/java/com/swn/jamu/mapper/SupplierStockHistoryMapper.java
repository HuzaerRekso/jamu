package com.swn.jamu.mapper;

import com.swn.jamu.dto.SupplierStockHistoryDTO;
import com.swn.jamu.model.SupplierStockHistory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierStockHistoryMapper {

    @Mapping(target = "id", ignore = true)
    SupplierStockHistory toSupplierStockHistory(SupplierStockHistoryDTO supplierStockHistoryDTO);

    @Mapping(target = "supplierStockId", source = "supplierStock.id")
    @Mapping(target = "baseJamuName", source = "supplierStock.baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "supplierStock.baseJamu.code")
    SupplierStockHistoryDTO toSupplierStockHistoryDTO(SupplierStockHistory supplierStockHistory);

    @AfterMapping
    default void afterSupplierStockDTO(@MappingTarget SupplierStockHistoryDTO supplierStockHistoryDTO,
                                       SupplierStockHistory supplierStockHistory) {
        supplierStockHistoryDTO.setQty(supplierStockHistory.getQty() / 1000);
        supplierStockHistoryDTO.setPreviousQty(supplierStockHistory.getPreviousQty() / 1000);
    }
}
