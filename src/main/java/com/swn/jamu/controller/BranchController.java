package com.swn.jamu.controller;

import com.swn.jamu.dto.BranchDTO;
import com.swn.jamu.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        BranchDTO branch = new BranchDTO();
        model.addAttribute("branch", branch);
        return "branch-register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("branch") BranchDTO branchDTO,
                               BindingResult result,
                               Model model){
//        User existingUser = userService.findUserByUsernameAndActive(branchDTO.getUsername());
//
//        if(existingUser != null){
//            result.rejectValue("username", "J001",
//                    "There is already an account registered with the same username");
//        }

//        if(result.hasErrors()){
//            model.addAttribute("user", branchDTO);
//            return "user-register";
//        }

        branchService.saveBranch(branchDTO);
        return "redirect:/branch/branches";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        BranchDTO branch = branchService.findById(id);
        model.addAttribute("branch", branch);
        return "/branch-edit";
    }

    @PostMapping("/edit/save/{id}")
    public String registration(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("branch") BranchDTO branchDTO,
                               BindingResult result){
        if (result.hasErrors()) {
            branchDTO.setId(id);
            return "/branch-edit";
        }
        branchService.editBranch(branchDTO, id);
        return "redirect:/branch/branches";
    }

    @GetMapping("/branches")
    public String branchesPage(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BranchDTO> branches = branchService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("branches", branches);
        if (branches.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, branches.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "branch";
    }

    @GetMapping("/delete/{id}")
    public String deleteBranch(@PathVariable("id") Long id,
                             Model model) {
        branchService.deactivateBranch(id);
        return "redirect:/branch/branches";
    }
}
