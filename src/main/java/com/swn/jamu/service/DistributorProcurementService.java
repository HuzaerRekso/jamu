package com.swn.jamu.service;

import com.swn.jamu.constant.ProcurementStatusConstant;
import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.DistributorProcurementDTO;
import com.swn.jamu.mapper.DistributorProcurementDetailMapper;
import com.swn.jamu.mapper.DistributorProcurementMapper;
import com.swn.jamu.model.DistributorProcurement;
import com.swn.jamu.model.DistributorProcurementDetail;
import com.swn.jamu.repository.DistributorProcurementDetailRepository;
import com.swn.jamu.repository.DistributorProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistributorProcurementService {

    private final DistributorProcurementRepository distributorProcurementRepository;
    private final DistributorProcurementDetailRepository distributorProcurementDetailRepository;
    private final BaseJamuService baseJamuService;
    private final DistributorStockService distributorStockService;
    private final DistributorProcurementMapper distributorProcurementMapper;
    private final DistributorProcurementDetailMapper distributorProcurementDetailMapper;

    @Autowired
    public DistributorProcurementService(DistributorProcurementRepository distributorProcurementRepository,
                                         DistributorProcurementDetailRepository distributorProcurementDetailRepository,
                                         BaseJamuService baseJamuService,
                                         DistributorStockService distributorStockService,
                                         DistributorProcurementMapper distributorProcurementMapper,
                                         DistributorProcurementDetailMapper distributorProcurementDetailMapper) {
        this.distributorProcurementRepository = distributorProcurementRepository;
        this.distributorProcurementDetailRepository = distributorProcurementDetailRepository;
        this.baseJamuService = baseJamuService;
        this.distributorStockService = distributorStockService;
        this.distributorProcurementMapper = distributorProcurementMapper;
        this.distributorProcurementDetailMapper = distributorProcurementDetailMapper;
    }

    public void requestDistributorProcurement(DistributorProcurementDTO procurementDTO) {
        DistributorProcurement procurement = distributorProcurementMapper.toDistributorProcurement(procurementDTO);
        procurement.setRequestDate(LocalDate.now());
        procurement.setStatus(ProcurementStatusConstant.WAITING);

        List<DistributorProcurementDetail> details = new ArrayList<>();
        procurementDTO.getDetails().forEach(detailDTO -> {
            if (detailDTO.getSelected()) {
                DistributorProcurementDetail detail = new DistributorProcurementDetail();
                detail.setDistributorProcurement(procurement);
                detail.setQuantity(detailDTO.getQuantity() * 1000);
                detail.setBaseJamu(baseJamuService.findBaseJamu(detailDTO.getBaseJamuId()));
                details.add(detail);
            }
        });

        procurement.setDetails(details);
        distributorProcurementRepository.save(procurement);
    }

    public void approveDistributorProcurement(long id, DistributorProcurementDTO procurementDTO) {
        DistributorProcurement procurement = distributorProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't approve this Procurement status");
        }

        procurement.setSupplierNotes(procurementDTO.getSupplierNotes());
        procurement.setStatus(ProcurementStatusConstant.APPROVED);
        procurement.setApprovedDate(LocalDate.now());

        distributorProcurementRepository.save(procurement);
    }

    public void rejectDistributorProcurement(long id, DistributorProcurementDTO procurementDTO) {
        DistributorProcurement procurement = distributorProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't reject this Procurement status");
        }

        procurement.setSupplierNotes(procurementDTO.getSupplierNotes());
        procurement.setStatus(ProcurementStatusConstant.REJECTED);
        procurement.setRejectDate(LocalDate.now());

        distributorProcurementRepository.save(procurement);
    }

    public void acceptDistributorProcurement(long id) {
        DistributorProcurement procurement = distributorProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.APPROVED)) {
            throw new IllegalArgumentException("Can't accept this Procurement status");
        }

        procurement.setStatus(ProcurementStatusConstant.ACCEPTED);
        procurement.setAcceptDate(LocalDate.now());

        distributorStockService.addStock(procurement.getDetails());
        distributorProcurementRepository.save(procurement);
    }

    public void cancelDistributorProcurement(long id) {
        DistributorProcurement procurement = distributorProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement Request not found"));
        if (!procurement.getStatus().equals(ProcurementStatusConstant.WAITING)) {
            throw new IllegalArgumentException("Can't cancel this Procurement status");
        }

        procurement.setStatus(ProcurementStatusConstant.CANCELED);
        procurement.setCancelDate(LocalDate.now());

        distributorProcurementRepository.save(procurement);
    }

    public Page<DistributorProcurementDTO> findPaginated(Pageable pageable) {
        Page<DistributorProcurement> procurementPage = distributorProcurementRepository.findPaginated(pageable);

        List<DistributorProcurementDTO> dtoList = procurementPage.getContent().stream().map(distributorProcurementMapper::toDistributorProcurementDTO).toList();
        return new PageImpl<>(dtoList, pageable, procurementPage.getTotalElements());
    }

    public List<BaseJamuDTO> getBaseJamuDTOS() {
        return baseJamuService.findAllBaseJamu();
    }

    public DistributorProcurementDTO findDistributorProcurement(long id) {
        return distributorProcurementMapper.toDistributorProcurementDTO(distributorProcurementRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Procurement not found")));
    }
}
