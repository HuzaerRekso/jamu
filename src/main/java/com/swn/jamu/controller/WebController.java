package com.swn.jamu.controller;

import com.swn.jamu.dto.DashboardSaleDTO;
import com.swn.jamu.service.BranchSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final BranchSaleService branchSaleService;

    @Autowired
    public WebController(BranchSaleService branchSaleService) {
        this.branchSaleService = branchSaleService;
    }

    @GetMapping("/dashboard")
    public String home(Model model) {
        DashboardSaleDTO dto = branchSaleService.getDashboardData();
        model.addAttribute("labels", dto.getLabels());
        model.addAttribute("data", dto.getDatasets().getData());
        model.addAttribute("backgroundColor", dto.getDatasets().getBackgroundColor());
        model.addAttribute("testData", "WOOOYY");
        return "dashboard";
    }
}
