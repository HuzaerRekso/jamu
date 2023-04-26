package com.swn.jamu.service;

import com.swn.jamu.model.Branch;
import com.swn.jamu.model.BranchProcurementDetail;
import com.swn.jamu.model.BranchStock;
import com.swn.jamu.model.BranchStockHistory;
import com.swn.jamu.repository.BranchStockHistoryRepository;
import com.swn.jamu.repository.BranchStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchStockService {

    private final BranchStockRepository branchStockRepository;
    private final BranchStockHistoryRepository branchStockHistoryRepository;

    @Autowired
    public BranchStockService(BranchStockRepository branchStockRepository,
                              BranchStockHistoryRepository branchStockHistoryRepository) {
        this.branchStockRepository = branchStockRepository;
        this.branchStockHistoryRepository = branchStockHistoryRepository;
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
}
