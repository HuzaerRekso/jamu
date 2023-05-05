package com.swn.jamu.service;

import com.swn.jamu.dto.BranchStockDTO;
import com.swn.jamu.dto.BranchStockHistoryDTO;
import com.swn.jamu.mapper.BranchStockHistoryMapper;
import com.swn.jamu.mapper.BranchStockMapper;
import com.swn.jamu.model.*;
import com.swn.jamu.repository.BranchStockHistoryRepository;
import com.swn.jamu.repository.BranchStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchStockService {

    private final BranchStockRepository branchStockRepository;
    private final BranchStockHistoryRepository branchStockHistoryRepository;
    private final UserService userService;
    private final BranchStockMapper branchStockMapper;
    private final BranchStockHistoryMapper branchStockHistoryMapper;

    @Autowired
    public BranchStockService(BranchStockRepository branchStockRepository,
                              BranchStockHistoryRepository branchStockHistoryRepository,
                              UserService userService,
                              BranchStockMapper branchStockMapper,
                              BranchStockHistoryMapper branchStockHistoryMapper) {
        this.branchStockRepository = branchStockRepository;
        this.branchStockHistoryRepository = branchStockHistoryRepository;
        this.userService = userService;
        this.branchStockMapper = branchStockMapper;
        this.branchStockHistoryMapper = branchStockHistoryMapper;
    }

    public void addStock(Branch branch, List<BranchProcurementDetail> details) {
        List<BranchStockHistory> branchStockHistoryList = new ArrayList<>();
        List<BranchStock> branchStocks = new ArrayList<>();
        details.forEach(detail -> {
            long finalQuantity = 0;
            long previousQuantity = 0;

            BranchStock stock = branchStockRepository.findByBranchIdAndAndBaseJamuId(branch.getId(), detail.getBaseJamu().getId());
            if (stock == null) {
                finalQuantity = detail.getQuantity();
                stock = new BranchStock();
                stock.setBranch(branch);
                stock.setBaseJamu(detail.getBaseJamu());
                stock.setQty(finalQuantity);
            } else {
                previousQuantity = stock.getQty();
                finalQuantity = previousQuantity + detail.getQuantity();
                stock.setQty(finalQuantity);
            }
            branchStocks.add(stock);

            BranchStockHistory branchStockHistory = new BranchStockHistory();
            branchStockHistory.setBranchStock(stock);
            branchStockHistory.setStockDate(LocalDate.now());
            branchStockHistory.setPreviousQty(previousQuantity);
            branchStockHistory.setQty(finalQuantity);
            branchStockHistoryList.add(branchStockHistory);
        });

        branchStockRepository.saveAll(branchStocks);
        branchStockHistoryRepository.saveAll(branchStockHistoryList);
    }

    public Page<BranchStockDTO> findPaginated(Pageable pageable, String name, long branchId) {
        return findPaginatedCore(pageable, name, branchId);
    }

    public Page<BranchStockDTO> findPaginated(Pageable pageable, String name, String userName) {
        long branchId = userService.findUserBranchId(userName);
        return findPaginatedCore(pageable, name, branchId);
    }

    private Page<BranchStockDTO> findPaginatedCore(Pageable pageable, String name, long branchId) {
        Page<BranchStock> stock;
        if (StringUtils.hasLength(name)) {
            stock = branchStockRepository.findByBranchIdAndAndBaseJamuNameContaining(branchId, name, pageable);
        } else {
            stock = branchStockRepository.findByBranchId(branchId, pageable);
        }

        List<BranchStockDTO> dtoList = stock.getContent().stream().map(branchStockMapper::toBranchStockDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public Page<BranchStockHistoryDTO> findPaginatedHistory(Pageable pageable, long branchStockId) {
        Page<BranchStockHistory> stock = branchStockHistoryRepository.findByBranchStockId(branchStockId, pageable);

        List<BranchStockHistoryDTO> dtoList = stock.getContent().stream().map(branchStockHistoryMapper::toBranchStockHistoryDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public void decreaseStock(Branch branch, List<BranchSaleDetail> details) {
        List<BranchStockHistory> branchStockHistoryList = new ArrayList<>();
        List<BranchStock> branchStocks = new ArrayList<>();

        Map<Long, BaseJamu> baseJamuMap = new HashMap<>();
        Map<Long, Long> minusDoseMap = new HashMap<>();
        details.forEach(detail -> {
            if (detail.getJamu() != null && detail.getJamu().getDose() != null && detail.getJamu().getDose().size() > 0) {
                detail.getJamu().getDose().forEach(dose -> {
                    if (baseJamuMap.containsKey(dose.getBaseJamu().getId())) {
                        long currentDose = dose.getDose() * detail.getQty();
                        long minusDose = minusDoseMap.get(dose.getBaseJamu().getId()) + currentDose;
                        minusDoseMap.put(dose.getBaseJamu().getId(), minusDose);
                    } else {
                        long currentDose = dose.getDose() * detail.getQty();
                        baseJamuMap.put(dose.getBaseJamu().getId(), dose.getBaseJamu());
                        minusDoseMap.put(dose.getBaseJamu().getId(), currentDose);
                    }
                });
            }
        });

        for (Map.Entry<Long, BaseJamu> entry : baseJamuMap.entrySet()) {
            long finalQuantity;
            long previousQuantity;

            BranchStock stock = branchStockRepository.findByBranchIdAndAndBaseJamuId(branch.getId(), entry.getKey());
            if (stock == null) {
                throw new IllegalArgumentException("Stock doesn't exist");
            } else {
                previousQuantity = stock.getQty();
                finalQuantity = previousQuantity - minusDoseMap.get(entry.getKey());
                if (finalQuantity < 0) {
                    throw new IllegalArgumentException("Stock is not enough");
                }
                stock.setQty(finalQuantity);
            }
            branchStocks.add(stock);

            BranchStockHistory branchStockHistory = new BranchStockHistory();
            branchStockHistory.setBranchStock(stock);
            branchStockHistory.setStockDate(LocalDate.now());
            branchStockHistory.setPreviousQty(previousQuantity);
            branchStockHistory.setQty(finalQuantity);
            branchStockHistoryList.add(branchStockHistory);
        }

        branchStockRepository.saveAll(branchStocks);
        branchStockHistoryRepository.saveAll(branchStockHistoryList);
    }
}
