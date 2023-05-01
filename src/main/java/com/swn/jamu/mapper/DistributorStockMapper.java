package com.swn.jamu.mapper;

import com.swn.jamu.dto.DistributorStockDTO;
import com.swn.jamu.model.DistributorStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistributorStockMapper {

    @Mapping(target = "id", ignore = true)
    DistributorStock toDistributorStock(DistributorStockDTO distributorStockDTO);

    @Mapping(target = "baseJamuId", source = "baseJamu.id")
    @Mapping(target = "baseJamuName", source = "baseJamu.name")
    @Mapping(target = "baseJamuCode", source = "baseJamu.code")
    DistributorStockDTO toDistributorStockDTO(DistributorStock distributorStock);
}
