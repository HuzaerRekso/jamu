package com.swn.jamu.controller;

import com.swn.jamu.dto.DashboardSaleDTO;
import com.swn.jamu.service.BranchSaleService;
import com.swn.jamu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final BranchSaleService branchSaleService;
    private final UserService userService;

    @Autowired
    public WebController(BranchSaleService branchSaleService,
                         UserService userService) {
        this.branchSaleService = branchSaleService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String home(Model model, @AuthenticationPrincipal User userDetail) {
        DashboardSaleDTO dto = branchSaleService.getDashboardData();
        model.addAttribute("labels", dto.getLabels());
        model.addAttribute("data", dto.getDatasets().getData());
        model.addAttribute("backgroundColor", dto.getDatasets().getBackgroundColor());
        model.addAttribute("testData", "WOOOYY");
        model.addAttribute("fullName", userService.getFullName(userDetail.getUsername()));
        return "dashboard";
    }
}
