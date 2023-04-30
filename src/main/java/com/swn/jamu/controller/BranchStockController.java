package com.swn.jamu.controller;

import com.swn.jamu.dto.BranchStockDTO;
import com.swn.jamu.dto.BranchStockHistoryDTO;
import com.swn.jamu.service.BranchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
@RequestMapping("/branch-stock")
public class BranchStockController {

    private final BranchStockService branchStockService;

    @Autowired
    public BranchStockController(BranchStockService branchStockService) {
        this.branchStockService = branchStockService;
    }

    @GetMapping("/branch/stocks")
    public String findStock(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        String name,
                                        @AuthenticationPrincipal User userDetail) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchStockDTO> stocks = branchStockService.findPaginated(PageRequest.of(currentPage-1, pageSize),
                name, userDetail.getUsername());
        model.addAttribute("stocks", stocks);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-stock";
    }

    @GetMapping("/branch/stock-history/{branchStockId}")
    public String findStockHistory(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @PathVariable("branchStockId") Long branchStockId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchStockHistoryDTO> stocks = branchStockService.findPaginatedHistory(PageRequest.of(
                currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "stockDate")), branchStockId);
        model.addAttribute("stocks", stocks);
        model.addAttribute("branchStockId", branchStockId);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-stock-history";
    }
}
