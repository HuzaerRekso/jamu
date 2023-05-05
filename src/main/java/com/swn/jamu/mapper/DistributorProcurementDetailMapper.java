package com.swn.jamu.mapper;

import com.swn.jamu.dto.DistributorProcurementDetailDTO;
import com.swn.jamu.model.DistributorProcurementDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistributorProcurementDetailMapper {

    @Mapping(target = "id", ignore = true)
    DistributorProcurementDetail toDistributorProcurementDetail(DistributorProcurementDetailDTO distributorProcurementDetailDTO);

    DistributorProcurementDetailDTO toDistributorProcurementDetailDTO(DistributorProcurementDetail distributorProcurementDetail);
}
