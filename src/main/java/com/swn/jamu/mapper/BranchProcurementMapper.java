package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchProcurementDTO;
import com.swn.jamu.dto.BranchProcurementDetailDTO;
import com.swn.jamu.model.BaseJamu;
import com.swn.jamu.model.BranchProcurement;
import com.swn.jamu.model.BranchProcurementDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchProcurementMapper {

    @Mapping(target = "id", ignore = true)
    BranchProcurement toBranchProcurement(BranchProcurementDTO branchProcurementDTO);

    @Mapping(target = "branchName", source = "branch.name")
    BranchProcurementDTO toBranchProcurementDTO(BranchProcurement branchProcurement);

    @AfterMapping
    default void afterBranchProcurementDetailToBranchProcurementDetailDTO(@MappingTarget BranchProcurementDetailDTO branchProcurementDetailDTO,
                                                                          BranchProcurementDetail branchProcurementDetail) {
        if (branchProcurementDetail.getBaseJamu() != null) {
            BaseJamu jamu = branchProcurementDetail.getBaseJamu();
            branchProcurementDetailDTO.setBaseJamuId(jamu.getId());
            branchProcurementDetailDTO.setBaseJamuName(jamu.getName());
            branchProcurementDetailDTO.setBaseJamuCode(jamu.getCode());
        }
    }
}
