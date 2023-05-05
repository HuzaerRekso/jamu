package com.swn.jamu.controller;

import com.swn.jamu.dto.DistributorStockDTO;
import com.swn.jamu.dto.DistributorStockHistoryDTO;
import com.swn.jamu.service.DistributorStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/distributor-stock")
public class DistributorStockController {

    private final DistributorStockService distributorStockService;

    @Autowired
    public DistributorStockController(DistributorStockService distributorStockService) {
        this.distributorStockService = distributorStockService;
    }

    @GetMapping("/distributor/stocks")
    public String findStock(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<DistributorStockDTO> stocks = distributorStockService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("stocks", stocks);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "distributor-stock";
    }

    @GetMapping("/distributor/stock-history/{distributorStockId}")
    public String findStockHistory(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @PathVariable("distributorStockId") Long distributorStockId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<DistributorStockHistoryDTO> stocks = distributorStockService.findPaginatedHistory(PageRequest.of(
                currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "stockDate")), distributorStockId);
        model.addAttribute("stocks", stocks);
        model.addAttribute("distributorStockId", distributorStockId);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "distributor-stock-history";
    }
}
