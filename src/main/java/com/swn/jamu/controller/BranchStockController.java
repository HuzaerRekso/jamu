package com.swn.jamu.controller;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.service.BaseJamuService;
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
@RequestMapping("/base-jamu")
public class BranchStockController {

    private final BaseJamuService baseJamuService;

    @Autowired
    public BranchStockController(BaseJamuService baseJamuService) {
        this.baseJamuService = baseJamuService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        BaseJamuDTO baseJamuDTO = new BaseJamuDTO();
        model.addAttribute("jamu", baseJamuDTO);
        return "base-jamu-register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("jamu") BaseJamuDTO baseJamuDTO){
        baseJamuService.saveBaseJamu(baseJamuDTO);
        return "redirect:/base-jamu/jamus";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        BaseJamuDTO jamu = baseJamuService.findById(id);
        model.addAttribute("jamu", jamu);
        return "/base-jamu-edit";
    }

    @PostMapping("/edit/save/{id}")
    public String edit(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("jamu") BaseJamuDTO baseJamuDTO,
                               BindingResult result){
        if (result.hasErrors()) {
            baseJamuDTO.setId(id);
            return "/base-jamu-edit";
        }
        baseJamuService.editBaseJamu(baseJamuDTO, id);
        return "redirect:/base-jamu/jamus";
    }

    @GetMapping("/jamus")
    public String baseJamuPage(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<BaseJamuDTO> jamus = baseJamuService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("jamus", jamus);
        if (jamus.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, jamus.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "base-jamu";
    }

    @GetMapping("/delete/{id}")
    public String deactivate(@PathVariable("id") Long id) {
        baseJamuService.deactivate(id);
        return "redirect:/base-jamu/jamus";
    }
}
