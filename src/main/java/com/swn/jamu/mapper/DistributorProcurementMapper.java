package com.swn.jamu.mapper;

import com.swn.jamu.dto.DistributorProcurementDTO;
import com.swn.jamu.dto.DistributorProcurementDetailDTO;
import com.swn.jamu.model.BaseJamu;
import com.swn.jamu.model.DistributorProcurement;
import com.swn.jamu.model.DistributorProcurementDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DistributorProcurementMapper {

    @Mapping(target = "id", ignore = true)
    DistributorProcurement toDistributorProcurement(DistributorProcurementDTO distributorProcurementDTO);

    DistributorProcurementDTO toDistributorProcurementDTO(DistributorProcurement distributorProcurement);

    @AfterMapping
    default void afterDistributorProcurementDetailToDistributorProcurementDetailDTO(@MappingTarget DistributorProcurementDetailDTO distributorProcurementDetailDTO,
                                                                          DistributorProcurementDetail distributorProcurementDetail) {
        if (distributorProcurementDetail.getBaseJamu() != null) {
            BaseJamu jamu = distributorProcurementDetail.getBaseJamu();
            distributorProcurementDetailDTO.setBaseJamuId(jamu.getId());
            distributorProcurementDetailDTO.setBaseJamuName(jamu.getName());
            distributorProcurementDetailDTO.setBaseJamuCode(jamu.getCode());
        }
    }
}
