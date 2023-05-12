package com.swn.jamu.service;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.SupplierStockDTO;
import com.swn.jamu.dto.SupplierStockHistoryDTO;
import com.swn.jamu.dto.SupplierStockListDTO;
import com.swn.jamu.mapper.SupplierStockHistoryMapper;
import com.swn.jamu.mapper.SupplierStockMapper;
import com.swn.jamu.model.DistributorProcurementDetail;
import com.swn.jamu.model.SupplierStock;
import com.swn.jamu.model.SupplierStockHistory;
import com.swn.jamu.repository.SupplierStockHistoryRepository;
import com.swn.jamu.repository.SupplierStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierStockService {

    private final SupplierStockRepository supplierStockRepository;
    private final SupplierStockHistoryRepository supplierStockHistoryRepository;
    private final BaseJamuService baseJamuService;
    private final SupplierStockMapper supplierStockMapper;
    private final SupplierStockHistoryMapper supplierStockHistoryMapper;

    @Autowired
    public SupplierStockService(SupplierStockRepository supplierStockRepository,
                                SupplierStockHistoryRepository supplierStockHistoryRepository,
                                BaseJamuService baseJamuService,
                                SupplierStockMapper supplierStockMapper,
                                SupplierStockHistoryMapper supplierStockHistoryMapper) {
        this.supplierStockRepository = supplierStockRepository;
        this.supplierStockHistoryRepository = supplierStockHistoryRepository;
        this.baseJamuService = baseJamuService;
        this.supplierStockMapper = supplierStockMapper;
        this.supplierStockHistoryMapper = supplierStockHistoryMapper;
    }

    public void addNewBaseJamuStock(SupplierStockDTO supplierStockDTO) {
        long qty = supplierStockDTO.getQty() * 1000;

        SupplierStock stock = new SupplierStock();
        stock.setBaseJamu(baseJamuService.findBaseJamu(supplierStockDTO.getBaseJamuId()));
        stock.setQty(qty);

        SupplierStockHistory supplierStockHistory = new SupplierStockHistory();
        supplierStockHistory.setSupplierStock(stock);
        supplierStockHistory.setStockDate(LocalDate.now());
        supplierStockHistory.setQty(qty);
        supplierStockHistory.setPreviousQty(0L);

        supplierStockRepository.save(stock);
        supplierStockHistoryRepository.save(supplierStockHistory);
    }

    public SupplierStockListDTO showEditForms() {
        SupplierStockListDTO listDTO = new SupplierStockListDTO();
        listDTO.setStocks(supplierStockRepository.findAll().stream().map(supplierStockMapper::toSupplierStockDTO).collect(Collectors.toList()));
        return listDTO;
    }

    public void editStocks(SupplierStockListDTO supplierStockListDTO) {
        List<Long> stockIds = new ArrayList<>();
        Map<Long, Long> qtyStockMap = new HashMap<>();
        supplierStockListDTO.getStocks().forEach(stock -> {
            stockIds.add(stock.getId());
            qtyStockMap.put(stock.getId(), stock.getQty() * 1000);
        });

        List<SupplierStock> stocks = supplierStockRepository.findByIdIn(stockIds);

        if (stocks != null && stocks.size() > 0) {
            List<SupplierStock> changedStock = new ArrayList<>();
            List<SupplierStockHistory> histories = new ArrayList<>();
            stocks.forEach(stock -> {
                if (!Objects.equals(stock.getQty(), qtyStockMap.get(stock.getId()))) {
                    SupplierStockHistory history = new SupplierStockHistory();
                    history.setSupplierStock(stock);
                    history.setStockDate(LocalDate.now());
                    history.setPreviousQty(stock.getQty());
                    history.setQty(qtyStockMap.get(stock.getId()));
                    histories.add(history);

                    stock.setQty(qtyStockMap.get(stock.getId()));
                    changedStock.add(stock);
                }
            });
            supplierStockRepository.saveAll(changedStock);
            supplierStockHistoryRepository.saveAll(histories);
        }
    }

    public Page<SupplierStockDTO> findPaginated(Pageable pageable, String name) {
        Page<SupplierStock> stock;
        if (StringUtils.hasLength(name)) {
            stock = supplierStockRepository.findByBaseJamuNameContaining(name, pageable);
        } else {
            stock = supplierStockRepository.findAll(pageable);
        }

        List<SupplierStockDTO> dtoList = stock.getContent().stream().map(supplierStockMapper::toSupplierStockDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public Page<SupplierStockHistoryDTO> findPaginatedHistory(Pageable pageable, long supplierStockId) {
        Page<SupplierStockHistory> stock = supplierStockHistoryRepository.findBySupplierStockId(supplierStockId, pageable);

        List<SupplierStockHistoryDTO> dtoList = stock.getContent().stream().map(supplierStockHistoryMapper::toSupplierStockHistoryDTO).toList();
        return new PageImpl<>(dtoList, pageable, stock.getTotalElements());
    }

    public void decreaseStock(List<DistributorProcurementDetail> details) {
        List<SupplierStockHistory> supplierStockHistoryList = new ArrayList<>();
        List<SupplierStock> supplierStocks = new ArrayList<>();

        details.forEach(detail -> {
            long finalQuantity;
            long previousQuantity;

            SupplierStock stock = supplierStockRepository.findByBaseJamuId(detail.getBaseJamu().getId());
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
            supplierStocks.add(stock);

            SupplierStockHistory supplierStockHistory = new SupplierStockHistory();
            supplierStockHistory.setSupplierStock(stock);
            supplierStockHistory.setStockDate(LocalDate.now());
            supplierStockHistory.setPreviousQty(previousQuantity);
            supplierStockHistory.setQty(finalQuantity);
            supplierStockHistoryList.add(supplierStockHistory);
        });

        supplierStockRepository.saveAll(supplierStocks);
        supplierStockHistoryRepository.saveAll(supplierStockHistoryList);
    }

    public List<BaseJamuDTO> getAllBaseJamu() {
        return baseJamuService.findAllBaseJamu();
    }
}
