package com.swn.jamu.controller;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.DistributorProcurementDTO;
import com.swn.jamu.dto.DistributorProcurementDetailDTO;
import com.swn.jamu.service.DistributorProcurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/distributor-procurement")
public class DistributorProcurementController {

    private final DistributorProcurementService distributorProcurementService;

    @Autowired
    public DistributorProcurementController(DistributorProcurementService distributorProcurementService) {
        this.distributorProcurementService = distributorProcurementService;
    }

    /* CONTROLLER FOR ALL */

    @GetMapping("/distributor")
    public String findProcurementDistributorDistributor(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<DistributorProcurementDTO> po = distributorProcurementService.findPaginated(PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "requestDate")));
        model.addAttribute("procurements", po);
        if (po.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, po.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "distributor-procurement";
    }

    @GetMapping("/distributor/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model){
        DistributorProcurementDTO procurementDTO = distributorProcurementService.findDistributorProcurement(id);
        model.addAttribute("procurement", procurementDTO);
        return "distributor-procurement-detail";
    }

    /* CONTROLLER FOR SUPPLIER */

    @PostMapping("/supplier/approve/{id}")
    public String approveProcurement(@PathVariable("id") Long id,
                                     @ModelAttribute("procurement") DistributorProcurementDTO procurementDTO){
        distributorProcurementService.approveDistributorProcurement(id, procurementDTO);
        return "redirect:/distributor-procurement/distributor";
    }

    @PostMapping("/supplier/reject/{id}")
    public String rejectProcurement(@PathVariable("id") Long id,
                                    @ModelAttribute("procurement") DistributorProcurementDTO procurementDTO){
        distributorProcurementService.rejectDistributorProcurement(id, procurementDTO);
        return "redirect:/distributor-procurement/distributor";
    }

    /* CONTROLLER FOR DISTRIBUTOR/ADMIN */

    /*@GetMapping("/distributor")
    public String findProcurementDistributor(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        @AuthenticationPrincipal User userDetail) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<DistributorProcurementDTO> po = distributorProcurementService.findPaginatedDistributor(PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Direction.DESC, "requestDate")), userDetail.getUsername());
        model.addAttribute("procurements", po);
        if (po.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, po.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "distributor-procurement";
    }*/

    @GetMapping("/distributor/register")
    public String showRegistrationForm(Model model){

        List<BaseJamuDTO> baseJamuDTOS = distributorProcurementService.getBaseJamuDTOS();
        List<DistributorProcurementDetailDTO> details = new ArrayList<>();
        baseJamuDTOS.forEach(dto -> {
            DistributorProcurementDetailDTO detailDTO = new DistributorProcurementDetailDTO();
            detailDTO.setBaseJamuId(dto.getId());
            detailDTO.setSelected(false);
            details.add(detailDTO);
        });

        DistributorProcurementDTO procurementDTO = new DistributorProcurementDTO();
        procurementDTO.setDetails(details);
        model.addAttribute("procurement", procurementDTO);
        model.addAttribute("baseJamus", baseJamuDTOS);
        return "distributor-procurement-register";
    }

    @PostMapping("/distributor/register/save")
    public String registration(@Valid @ModelAttribute("procurement") DistributorProcurementDTO procurementDTO){
        distributorProcurementService.requestDistributorProcurement(procurementDTO);
        return "redirect:/distributor-procurement/distributor";
    }

    @PostMapping("/distributor/accept/{id}")
    public String acceptProcurement(@PathVariable("id") Long id){
        distributorProcurementService.acceptDistributorProcurement(id);
        return "redirect:/distributor-procurement/distributor";
    }

    @PostMapping("/distributor/cancel/{id}")
    public String cancelProcurement(@PathVariable("id") Long id){
        distributorProcurementService.cancelDistributorProcurement(id);
        return "redirect:/distributor-procurement/distributor";
    }
}
