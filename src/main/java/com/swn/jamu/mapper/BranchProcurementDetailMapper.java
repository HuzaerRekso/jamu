package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchProcurementDetailDTO;
import com.swn.jamu.model.BranchProcurementDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchProcurementDetailMapper {

    @Mapping(target = "id", ignore = true)
    BranchProcurementDetail toBranchProcurementDetail(BranchProcurementDetailDTO branchProcurementDetailDTO);

    BranchProcurementDetailDTO toBranchProcurementDetailDTO(BranchProcurementDetail branchProcurementDetail);
}
