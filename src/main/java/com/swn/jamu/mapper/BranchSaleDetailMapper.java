package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchSaleDetailDTO;
import com.swn.jamu.model.BranchSaleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchSaleDetailMapper {

    @Mapping(target = "id", ignore = true)
    BranchSaleDetail toBranchSaleDetail(BranchSaleDetailDTO branchSaleDetailDTO);

    @Mapping(target = "branchSaleId", source = "branchSale.id")
    @Mapping(target = "saleDate", source = "branchSale.saleDate")
    BranchSaleDetailDTO toBranchSaleDetailDTO(BranchSaleDetail branchSaleDetail);
}
