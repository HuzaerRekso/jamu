package com.swn.jamu.controller;

import com.swn.jamu.dto.BranchSaleDTO;
import com.swn.jamu.dto.BranchSaleDetailDTO;
import com.swn.jamu.dto.JamuDTO;
import com.swn.jamu.service.BranchSaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/branch-sale")
public class BranchSaleController {

    private final BranchSaleService branchSaleService;

    @Autowired
    public BranchSaleController(BranchSaleService branchSaleService) {
        this.branchSaleService = branchSaleService;
    }

    @GetMapping("/branch/add-daily-sale")
    public String showRegistrationForm(Model model){
        List<JamuDTO> jamus = branchSaleService.getAllJamu();
        List<BranchSaleDetailDTO> details = new ArrayList<>();
        jamus.forEach(jamu -> {
            BranchSaleDetailDTO detailDTO = new BranchSaleDetailDTO();
            detailDTO.setSelected(false);
            detailDTO.setJamuId(jamu.getId());
            details.add(detailDTO);
        });

        BranchSaleDTO branchSaleDTO = new BranchSaleDTO();
        branchSaleDTO.setDetails(details);
        model.addAttribute("sale", branchSaleDTO);
        model.addAttribute("jamus", jamus);
        return "branch-sale-create";
    }

    @PostMapping("/branch/add-daily-sale/save")
    public String registration(@Valid @ModelAttribute("sale") BranchSaleDTO saleDTO,
                               @AuthenticationPrincipal User userDetail,
                               Model model){
        try {
            branchSaleService.saveBranchSale(saleDTO, userDetail.getUsername());
            return "redirect:/branch-sale/branch/sales";
        } catch (IllegalArgumentException e) {
            model.addAttribute("jamus", branchSaleService.getAllJamu());
            model.addAttribute("error", e.getMessage());
            return "branch-sale-create";
        }
    }

    @GetMapping("/branch/sales")
    public String findSales(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @AuthenticationPrincipal User userDetail) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchSaleDTO> sales = branchSaleService.findPaginated(PageRequest.of(currentPage-1, pageSize), userDetail.getUsername());
        model.addAttribute("sales", sales);
        if (sales.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, sales.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-sale";
    }

    @GetMapping("/branch/detail/{saleId}")
    public String showDetail(@PathVariable("saleId") Long saleId, Model model){
        BranchSaleDTO saleDTO = branchSaleService.findBranchSale(saleId);
        model.addAttribute("sale", saleDTO);
        return "branch-sale-detail";
    }

    /* CONTROLLER FOR DISTRIBUTOR/ADMIN */

    @GetMapping("/distributor/sales/{branchId}")
    public String findSalesAdmin(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @PathVariable("branchId") Long branchId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchSaleDTO> sales = branchSaleService.findPaginated(PageRequest.of(currentPage-1, pageSize), branchId);
        model.addAttribute("sales", sales);
        if (sales.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, sales.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-sale";
    }

    @GetMapping("/distributor/detail/{saleId}")
    public String showDetailAdmin(@PathVariable("saleId") Long saleId, Model model){
        BranchSaleDTO saleDTO = branchSaleService.findBranchSale(saleId);
        model.addAttribute("sale", saleDTO);
        return "branch-sale-detail";
    }
}
