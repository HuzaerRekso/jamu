package com.swn.jamu.mapper;

import com.swn.jamu.dto.SupplierStockDTO;
import com.swn.jamu.model.SupplierStock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierStockMapper {

    @Mapping(target = "id", ignore = true)
    SupplierStock toSupplierStock(SupplierStockDTO supplierStockDTO);

    @Mapping(target = "baseJamuId", source = "baseJamu.id")
    @Mapping(target = "baseJamuName", source = "baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "baseJamu.code")
    SupplierStockDTO toSupplierStockDTO(SupplierStock supplierStock);

    @AfterMapping
    default void afterSupplierStockDTO(@MappingTarget SupplierStockDTO supplierStockDTO,
                                       SupplierStock supplierStock) {
        supplierStockDTO.setQty(supplierStock.getQty() / 1000);
    }
}
