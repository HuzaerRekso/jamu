package com.swn.jamu.service;

import com.swn.jamu.dto.BranchDTO;
import com.swn.jamu.mapper.BranchMapper;
import com.swn.jamu.model.Branch;
import com.swn.jamu.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Autowired
    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    public void saveBranch(BranchDTO branchDTO) {
        if (branchRepository.existsByCodeAndActive(branchDTO.getCode(), true)) {
            throw new IllegalArgumentException("code already exist");
        }

        Branch branch = branchMapper.toBranch(branchDTO);
        branch.setActive(true);

        branch.setCode(generateBranchCode());
        branchRepository.save(branch);
    }

    public Page<BranchDTO> findPaginated(Pageable pageable, String name) {
        Page<Branch> branchPage;
        if (StringUtils.hasLength(name)) {
            branchPage = branchRepository.findByNameContainingAndActive(name, true, pageable);
        } else {
            branchPage = branchRepository.findByActive(true, pageable);
        }

        List<BranchDTO> dtoList = branchPage.getContent().stream().map(branchMapper::toBranchDTO).toList();
        return new PageImpl<>(dtoList, pageable, branchPage.getTotalElements());
    }

    public BranchDTO findById(long id) {
        return branchMapper.toBranchDTO(branchRepository.findById(id).orElse(null));
    }


    public Branch findBranch(long id) {
        return branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
    }

    public void editBranch(BranchDTO branchDTO, long id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        branch.setAddress(branchDTO.getAddress());
        branch.setName(branchDTO.getName());
        branch.setJoinDate(LocalDate.parse(branchDTO.getJoinDate()));
        branch.setPhoneNumber(branchDTO.getPhoneNumber());
        branchRepository.save(branch);
    }

    public void deactivateBranch(long id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        branch.setActive(false);
        branchRepository.save(branch);
    }

    public List<BranchDTO> findAllBranch() {
        return branchRepository.findByActive(true).stream().map(branchMapper::toBranchDTO).toList();
    }

    private String generateBranchCode() {
        Branch branch = branchRepository.findLatest();
        long latestCodeSeq = Long.parseLong(branch.getCode().substring(branch.getCode().length()-3));
        latestCodeSeq++;
        String seq = ("000" + latestCodeSeq).substring(String.valueOf(latestCodeSeq).length());
        return "BR"+seq;
    }
}
