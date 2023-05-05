package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchSaleDTO;
import com.swn.jamu.dto.BranchSaleDetailDTO;
import com.swn.jamu.model.BranchSale;
import com.swn.jamu.model.BranchSaleDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface BranchSaleMapper {

    @Mapping(target = "id", ignore = true)
    BranchSale toBranchSale(BranchSaleDTO branchSaleDTO);

    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "branchName", source = "branch.name")
    BranchSaleDTO toBranchSaleDTO(BranchSale branchSale);

    @AfterMapping
    default void afterBranchSaleDetailListToBranchSaleDetailDTOList(@MappingTarget BranchSaleDetailDTO branchSaleDetailDTO,
                                                                    BranchSaleDetail branchSaleDetail) {
        if (branchSaleDetail.getJamu() != null) {
            branchSaleDetailDTO.setJamuId(branchSaleDetail.getJamu().getId());
            branchSaleDetailDTO.setJamuCode(branchSaleDetail.getJamu().getCode());
            branchSaleDetailDTO.setJamuName(branchSaleDetail.getJamu().getName());
        }
        if (branchSaleDetail.getBranchSale() != null) {
            branchSaleDetailDTO.setSaleDate(DateTimeFormatter.ISO_LOCAL_DATE.format( branchSaleDetail.getBranchSale().getSaleDate()));
            branchSaleDetailDTO.setBranchSaleId(branchSaleDetail.getBranchSale().getId());
        }
    }
}
