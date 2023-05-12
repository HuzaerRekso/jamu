package com.swn.jamu.controller;

import com.swn.jamu.dto.SupplierStockDTO;
import com.swn.jamu.dto.SupplierStockHistoryDTO;
import com.swn.jamu.dto.SupplierStockListDTO;
import com.swn.jamu.service.SupplierStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/supplier-stock")
public class SupplierStockController {

    private final SupplierStockService supplierStockService;

    @Autowired
    public SupplierStockController(SupplierStockService supplierStockService) {
        this.supplierStockService = supplierStockService;
    }

    @GetMapping("/stocks/add")
    public String showAddStockForm(Model model) {
        SupplierStockDTO stock = new SupplierStockDTO();
        model.addAttribute("stock", stock);
        model.addAttribute("baseJamus", supplierStockService.getAllBaseJamu());
        return "supplier-stock-add";
    }

    @PostMapping("/stocks/add/save")
    public String addStocks(@Valid @ModelAttribute("stock") SupplierStockDTO supplierStockDTO) {
        supplierStockService.addNewBaseJamuStock(supplierStockDTO);
        return "redirect:/supplier-stock/stocks";
    }

    @GetMapping("/stocks/edit")
    public String showEditStocksForm(Model model) {
        model.addAttribute("stocks", supplierStockService.showEditForms());
        return "supplier-stock-edit";
    }

    @PostMapping("/stocks/edit/save")
    public String editStocks(@Valid @ModelAttribute("stocks") SupplierStockListDTO supplierStockListDTO) {
        supplierStockService.editStocks(supplierStockListDTO);
        return "redirect:/supplier-stock/stocks";
    }

    @GetMapping("/stocks")
    public String findStock(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<SupplierStockDTO> stocks = supplierStockService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("stocks", stocks);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "supplier-stock";
    }

    @GetMapping("/stock-history/{supplierStockId}")
    public String findStockHistory(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @PathVariable("supplierStockId") Long supplierStockId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<SupplierStockHistoryDTO> stocks = supplierStockService.findPaginatedHistory(PageRequest.of(
                currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "stockDate")), supplierStockId);
        model.addAttribute("stocks", stocks);
        model.addAttribute("supplierStockId", supplierStockId);
        if (stocks.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, stocks.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "supplier-stock-history";
    }
}
