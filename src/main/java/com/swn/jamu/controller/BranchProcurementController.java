package com.swn.jamu.controller;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.BranchProcurementDTO;
import com.swn.jamu.dto.BranchProcurementDetailDTO;
import com.swn.jamu.service.BranchProcurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/branch-procurement")
public class BranchProcurementController {

    private final BranchProcurementService branchProcurementService;

    @Autowired
    public BranchProcurementController(BranchProcurementService branchProcurementService) {
        this.branchProcurementService = branchProcurementService;
    }

    /* CONTROLLER FOR ADMIN */

    @GetMapping("/distributor")
    public String findProcurementBranchDistributor(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchProcurementDTO> po = branchProcurementService.findPaginated(PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "requestDate")));
        model.addAttribute("procurements", po);
        if (po.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, po.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-procurement-distributor";
    }

    @GetMapping("/distributor/{id}")
    public String showDetailDistributor(@PathVariable("id") Long id, Model model){
        BranchProcurementDTO procurementDTO = branchProcurementService.findBranchProcurement(id);
        model.addAttribute("procurement", procurementDTO);
        return "branch-procurement-distributor-detail";
    }

    @PostMapping("/distributor/approve/{id}")
    public String approveProcurement(@PathVariable("id") Long id,
                                     @ModelAttribute("procurement") BranchProcurementDTO procurementDTO,
                                     Model model){
        try {
            branchProcurementService.approveBranchProcurement(id, procurementDTO);
            return "redirect:/branch-procurement/distributor";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "branch-procurement-distributor-detail";
        }
    }

    @PostMapping("/distributor/reject/{id}")
    public String rejectProcurement(@PathVariable("id") Long id,
                                    @ModelAttribute("procurement") BranchProcurementDTO procurementDTO,
                                    Model model){
        try {
            branchProcurementService.rejectBranchProcurement(id, procurementDTO);
            return "redirect:/branch-procurement/distributor";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "branch-procurement-distributor-detail";
        }
    }

    /* CONTROLLER FOR BRANCH */

    @GetMapping("/branch")
    public String findProcurementBranch(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        @AuthenticationPrincipal User userDetail) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchProcurementDTO> po = branchProcurementService.findPaginatedBranch(PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "requestDate")), userDetail.getUsername());
        model.addAttribute("procurements", po);
        if (po.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, po.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch-procurement";
    }

    @GetMapping("/branch/register")
    public String showRegistrationForm(Model model){

        List<BaseJamuDTO> baseJamuDTOS = branchProcurementService.getBaseJamuDTOS();
        List<BranchProcurementDetailDTO> details = new ArrayList<>();
        baseJamuDTOS.forEach(dto -> {
            BranchProcurementDetailDTO detailDTO = new BranchProcurementDetailDTO();
            detailDTO.setBaseJamuId(dto.getId());
            detailDTO.setSelected(false);
            details.add(detailDTO);
        });

        BranchProcurementDTO procurementDTO = new BranchProcurementDTO();
        procurementDTO.setDetails(details);
        model.addAttribute("procurement", procurementDTO);
        model.addAttribute("baseJamus", baseJamuDTOS);
        return "branch-procurement-register";
    }

    @PostMapping("/branch/register/save")
    public String registration(@Valid @ModelAttribute("procurement") BranchProcurementDTO procurementDTO,
                               @AuthenticationPrincipal User userDetail,
                               Model model){
        try {
            branchProcurementService.requestBranchProcurement(procurementDTO, userDetail.getUsername());
            return "redirect:/branch-procurement/branch";
        } catch (IllegalArgumentException e) {
            model.addAttribute("baseJamus", branchProcurementService.getBaseJamuDTOS());
            model.addAttribute("error", e.getMessage());
            return "branch-procurement-register";
        }
    }

    @GetMapping("/branch/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model){
        BranchProcurementDTO procurementDTO = branchProcurementService.findBranchProcurement(id);
        model.addAttribute("procurement", procurementDTO);
        return "branch-procurement-detail";
    }

    @PostMapping("/branch/accept/{id}")
    public String acceptProcurement(@PathVariable("id") Long id,
                                    Model model){
        try {
            branchProcurementService.acceptBranchProcurement(id);
            return "redirect:/branch-procurement/branch";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "branch-procurement-detail";
        }
    }

    @PostMapping("/branch/cancel/{id}")
    public String cancelProcurement(@PathVariable("id") Long id,
                                    Model model){
        try {
            branchProcurementService.cancelBranchProcurement(id);
            return "redirect:/branch-procurement/branch";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "branch-procurement-detail";
        }
    }
}
