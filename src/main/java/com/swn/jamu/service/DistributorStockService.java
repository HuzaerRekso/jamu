package com.swn.jamu.service;

import com.swn.jamu.dto.DistributorStockDTO;
import com.swn.jamu.dto.DistributorStockHistoryDTO;
import com.swn.jamu.mapper.DistributorStockHistoryMapper;
import com.swn.jamu.mapper.DistributorStockMapper;
import com.swn.jamu.model.BranchProcurementDetail;
import com.swn.jamu.model.DistributorProcurementDetail;
import com.swn.jamu.model.DistributorStock;
import com.swn.jamu.model.DistributorStockHistory;
import com.swn.jamu.repository.DistributorStockHistoryRepository;
import com.swn.jamu.repository.DistributorStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistributorStockService {

    private final DistributorStockRepository distributorStockRepository;
    private final DistributorStockHistoryRepository distributorStockHistoryRepository;
    private final UserService userService;
    private final DistributorStockMapper distributorStockMapper;
    private final DistributorStockHistoryMapper distributorStockHistoryMapper;

    @Autowired
    public DistributorStockService(DistributorStockRepository distributorStockRepository,
                                   DistributorStockHistoryRepository distributorStockHistoryRepository,
                                   UserService userService,
                                   DistributorStockMapper distributorStockMapper,
                                   DistributorStockHistoryMapper distributorStockHistoryMapper) {
        this.distributorStockRepository = distributorStockRepository;
        this.distributorStockHistoryRepository = distributorStockHistoryRepository;
        this.userService = userService;
        this.distributorStockMapper = distributorStockMapper;
        this.distributorStockHistoryMapper = distributorStockHistoryMapper;
    }

    public void addStock(List<DistributorProcurementDetail> details) {
        List<DistributorStockHistory> distributorStockHistoryList = new ArrayList<>();
        List<DistributorStock> distributorStocks = new ArrayList<>();
        details.forEach(detail -> {
            long finalQuantity = 0;
            long previousQuantity = 0;

            DistributorStock stock = distributorStockRepository.findByBaseJamuId(detail.getBaseJamu().getId());
            if (stock == null) {
                finalQuantity = detail.getQuantity();
                stock = new DistributorStock();
                stock.setBaseJamu(detail.getBaseJamu());
                stock.setQty(finalQuantity);
            } else {
                previousQuantity = stock.getQty();
                finalQuantity = previousQuantity + detail.getQuantity();
                stock.setQty(finalQuantity);
            }
            distributorStocks.add(stock);

            DistributorStockHistory distributorStockHistory = new DistributorStockHistory();
            distributorStockHistory.setDistributorStock(stock);
            distributorStockHistory.setStockDate(LocalDate.now());
            distributorStockHistory.setPreviousQty(previousQuantity);
            distributorStockHistory.setQty(finalQuantity);
            distributorStockHistoryList.add(distributorStockHistory);
        });

        distributorStockRepository.saveAll(distributorStocks);
        distributorStockHistoryRepository.saveAll(distributorStockHistoryList);
    }

    public Page<DistributorStockDTO> findPaginated(Pageable pageable, String name) {
        Page<DistributorStock> stock;
        if (StringUtils.hasLength(name)) {
            stock = distributorStockRepository.findByBaseJamuNameContaining(name, pageable);
        } else {
            stock = distributorStockRepository.findAll(pageable);
        }

        List<DistributorStockDTO> dtoList = stock.getContent().stream().map(distributorStockMapper::toDistributorStockDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public Page<DistributorStockHistoryDTO> findPaginatedHistory(Pageable pageable, long distributorStockId) {
        Page<DistributorStockHistory> stock = distributorStockHistoryRepository.findByDistributorStockId(distributorStockId, pageable);

        List<DistributorStockHistoryDTO> dtoList = stock.getContent().stream().map(distributorStockHistoryMapper::toDistributorStockHistoryDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public void decreaseStock(List<BranchProcurementDetail> details) {
        List<DistributorStockHistory> distributorStockHistoryList = new ArrayList<>();
        List<DistributorStock> distributorStocks = new ArrayList<>();

        details.forEach(detail -> {
            long finalQuantity;
            long previousQuantity;

            DistributorStock stock = distributorStockRepository.findByBaseJamuId(detail.getBaseJamu().getId());
            if (stock == null) {
                throw new IllegalArgumentException("Stock doesn't exist");
            } else {
                previousQuantity = stock.getQty();
                finalQuantity = previousQuantity - detail.getQuantity();
                if (finalQuantity < 0) {
                    throw new IllegalArgumentException("Stock is not enough");
                }
                stock.setQty(finalQuantity);
            }
            distributorStocks.add(stock);

            DistributorStockHistory distributorStockHistory = new DistributorStockHistory();
            distributorStockHistory.setDistributorStock(stock);
            distributorStockHistory.setStockDate(LocalDate.now());
            distributorStockHistory.setPreviousQty(previousQuantity);
            distributorStockHistory.setQty(finalQuantity);
            distributorStockHistoryList.add(distributorStockHistory);
        });

        distributorStockRepository.saveAll(distributorStocks);
        distributorStockHistoryRepository.saveAll(distributorStockHistoryList);
    }
}
