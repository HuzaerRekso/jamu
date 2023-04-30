package com.swn.jamu.service;

import com.swn.jamu.dto.BranchSaleDTO;
import com.swn.jamu.dto.JamuDTO;
import com.swn.jamu.mapper.BranchSaleDetailMapper;
import com.swn.jamu.mapper.BranchSaleMapper;
import com.swn.jamu.model.Branch;
import com.swn.jamu.model.BranchSale;
import com.swn.jamu.model.BranchSaleDetail;
import com.swn.jamu.model.Jamu;
import com.swn.jamu.repository.BranchSaleDetailRepository;
import com.swn.jamu.repository.BranchSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BranchSaleService {

    private final BranchSaleRepository branchSaleRepository;
    private final BranchSaleDetailRepository branchSaleDetailRepository;
    private final UserService userService;
    private final JamuService jamuService;
    private final BranchStockService branchStockService;
    private final BranchSaleMapper branchSaleMapper;
    private final BranchSaleDetailMapper branchSaleDetailMapper;

    @Autowired
    public BranchSaleService(BranchSaleRepository branchSaleRepository,
                             BranchSaleDetailRepository branchSaleDetailRepository,
                             UserService userService,
                             JamuService jamuService,
                             BranchStockService branchStockService,
                             BranchSaleMapper branchSaleMapper,
                             BranchSaleDetailMapper branchSaleDetailMapper) {
        this.branchSaleRepository = branchSaleRepository;
        this.branchSaleDetailRepository = branchSaleDetailRepository;
        this.userService = userService;
        this.jamuService = jamuService;
        this.branchStockService = branchStockService;
        this.branchSaleMapper = branchSaleMapper;
        this.branchSaleDetailMapper = branchSaleDetailMapper;
    }

    public Page<BranchSaleDTO> findPaginated(Pageable pageable, long branchId) {
        return findPaginatedCore(pageable, branchId);
    }

    public Page<BranchSaleDTO> findPaginated(Pageable pageable, String userName) {
        long branchId = userService.findUserBranchId(userName);
        return findPaginatedCore(pageable, branchId);
    }

    private Page<BranchSaleDTO> findPaginatedCore(Pageable pageable, long branchId) {
        Page<BranchSale> sales = branchSaleRepository.findByBranchId(branchId, pageable);

        List<BranchSaleDTO> dtoList = sales.getContent().stream().map(branchSaleMapper::toBranchSaleDTO).toList();
        return new PageImpl<>(dtoList, pageable, sales.getTotalElements());
    }

    public List<JamuDTO> getAllJamu() {
        return jamuService.getAllJamu();
    }

    public void saveBranchSale(BranchSaleDTO saleDTO, String userName) {
        Branch branch = userService.findUserBranch(userName);
        BranchSale sale = branchSaleMapper.toBranchSale(saleDTO);
        sale.setBranch(branch);

        AtomicLong totalSales = new AtomicLong(0);
        List<BranchSaleDetail> details = new ArrayList<>();
        saleDTO.getDetails().forEach(detailDTO -> {
            if (detailDTO.getSelected()) {
                Jamu jamu = jamuService.findJamu(detailDTO.getJamuId());
                long price = jamu.getPrice() * detailDTO.getQty();

                BranchSaleDetail detail = new BranchSaleDetail();
                detail.setBranchSale(sale);
                detail.setJamu(jamu);
                detail.setPrice(price);
                detail.setPricePerQty(jamu.getPrice());
                detail.setQty(detailDTO.getQty());

                details.add(detail);

                totalSales.addAndGet(price);
            }
        });
        sale.setDetails(details);
        sale.setTotalSale(totalSales.get());

        branchStockService.decreaseStock(branch, details);
        branchSaleRepository.save(sale);
    }

    public BranchSaleDTO findBranchSale(long id) {
        return branchSaleMapper.toBranchSaleDTO(branchSaleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found")));
    }
}
