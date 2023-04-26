package com.swn.jamu.service;

import com.swn.jamu.constant.ProcurementStatusConstant;
import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.BranchProcurementDTO;
import com.swn.jamu.mapper.BranchProcurementDetailMapper;
import com.swn.jamu.mapper.BranchProcurementMapper;
import com.swn.jamu.model.BranchProcurement;
import com.swn.jamu.model.BranchProcurementDetail;
import com.swn.jamu.repository.BranchProcurementDetailRepository;
import com.swn.jamu.repository.BranchProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchProcurementService {

    private final BranchProcurementRepository branchProcurementRepository;
    private final BranchProcurementDetailRepository branchProcurementDetailRepository;
    private final BaseJamuService baseJamuService;
    private final UserService userService;
    private final BranchService branchService;
    private final BranchStockService branchStockService;
    private final BranchProcurementMapper branchProcurementMapper;
    private final BranchProcurementDetailMapper branchProcurementDetailMapper;

    @Autowired
    public BranchProcurementService(BranchProcurementRepository branchProcurementRepository,
                                    BranchProcurementDetailRepository branchProcurementDetailRepository,
                                    BaseJamuService baseJamuService,
                                    UserService userService,
                                    BranchService branchService,
                                    BranchStockService branchStockService,
                                    BranchProcurementMapper branchProcurementMapper,
                                    BranchProcurementDetailMapper branchProcurementDetailMapper) {
        this.branchProcurementRepository = branchProcurementRepository;
        this.branchProcurementDetailRepository = branchProcurementDetailRepository;
        this.baseJamuService = baseJamuService;
        this.userService = userService;
        this.branchService = branchService;
        this.branchStockService = branchStockService;
        this.branchProcurementMapper = branchProcurementMapper;
        this.branchProcurementDetailMapper = branchProcurementDetailMapper;
    }

    public void requestBranchProcurement(BranchProcurementDTO procurementDTO, String userName) {
        BranchProcurement procurement = branchProcurementMapper.toBranchProcurement(procurementDTO);
        procurement.setRequestDate(LocalDate.now());
        procurement.setStatus(ProcurementStatusConstant.WAITING);

        List<BranchProcurementDetail> details = new ArrayList<>();
        procurementDTO.getDetails().forEach(detailDTO -> {
            if (detailDTO.getSelected()) {
                BranchProcurementDetail detail = new BranchProcurementDetail();
                detail.setBranchProcurement(procurement);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setBaseJamu(baseJamuService.findBaseJamu(detailDTO.getBaseJamuId()));
                details.add(detail);
            }
        });

        procurement.setBranch(userService.findUserBranch(userName));
        procurement.setDetails(details);
        branchProcurementRepository.save(procurement);
    }

    public void approveBranchProcurement(long id, BranchProcurementDTO procurementDTO) {
        BranchProcurement procurement = branchProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't approve this Procurement status");
        }

        procurement.setDistributorNotes(procurementDTO.getDistributorNotes());
        procurement.setStatus(ProcurementStatusConstant.APPROVED);
        procurement.setApprovedDate(LocalDate.now());

        branchProcurementRepository.save(procurement);
    }

    public void rejectBranchProcurement(long id, BranchProcurementDTO procurementDTO) {
        BranchProcurement procurement = branchProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't reject this Procurement status");
        }

        procurement.setDistributorNotes(procurementDTO.getDistributorNotes());
        procurement.setStatus(ProcurementStatusConstant.REJECTED);
        procurement.setRejectDate(LocalDate.now());

        branchProcurementRepository.save(procurement);
    }

    public void acceptBranchProcurement(long id) {
        BranchProcurement procurement = branchProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.APPROVED)) {
            throw new IllegalArgumentException("Can't accept this Procurement status");
        }

        procurement.setStatus(ProcurementStatusConstant.ACCEPTED);
        procurement.setAcceptDate(LocalDate.now());

        branchStockService.addStock(procurement.getBranch(), procurement.getDetails());

        branchProcurementRepository.save(procurement);
    }

    public void cancelBranchProcurement(long id) {
        BranchProcurement procurement = branchProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't cancel this Procurement status");
        }

        procurement.setStatus(ProcurementStatusConstant.CANCELED);
        procurement.setCancelDate(LocalDate.now());

        branchProcurementRepository.save(procurement);
    }

    public Page<BranchProcurementDTO> findPaginated(Pageable pageable) {
        Page<BranchProcurement> procurementPage = branchProcurementRepository.findPaginated(pageable);

        List<BranchProcurementDTO> dtoList = procurementPage.getContent().stream().map(branchProcurementMapper::toBranchProcurementDTO).toList();
        return new PageImpl<>(dtoList, pageable, procurementPage.getTotalElements());
    }

    public Page<BranchProcurementDTO> findPaginatedBranch(Pageable pageable, String userName) {
        long branchId = userService.findUserBranchId(userName);
        Page<BranchProcurement> procurementPage = branchProcurementRepository.findPaginatedBranch(branchId, pageable);

        List<BranchProcurementDTO> dtoList = procurementPage.getContent().stream().map(branchProcurementMapper::toBranchProcurementDTO).toList();
        return new PageImpl<>(dtoList, pageable, procurementPage.getTotalElements());
    }

    public List<BaseJamuDTO> getBaseJamuDTOS() {
        return baseJamuService.findAllBaseJamu();
    }

    public BranchProcurementDTO findBranchProcurement(long id) {
        return branchProcurementMapper.toBranchProcurementDTO(branchProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement not found")));
    }
}
