package com.swn.jamu.mapper;

import com.swn.jamu.dto.BranchDTO;
import com.swn.jamu.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    Branch toBranch(BranchDTO branchDTO);

    BranchDTO toBranchDTO(Branch branch);
}
