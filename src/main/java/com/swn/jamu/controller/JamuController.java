package com.swn.jamu.controller;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.DoseDTO;
import com.swn.jamu.dto.JamuDTO;
import com.swn.jamu.service.JamuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jamu")
public class JamuController {

    private final JamuService jamuService;

    @Autowired
    public JamuController(JamuService jamuService) {
        this.jamuService = jamuService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        JamuDTO jamuDTO = new JamuDTO();

        List<BaseJamuDTO> baseJamuDTOS = jamuService.getAllBaseJamu();
        List<DoseDTO> dtoList = new ArrayList<>();
        baseJamuDTOS.forEach(jamu -> {
            DoseDTO dto = new DoseDTO();
            dto.setBaseJamuId(jamu.getId());
            dtoList.add(dto);
        });
        jamuDTO.setDose(dtoList);

        model.addAttribute("jamu", jamuDTO);
        model.addAttribute("baseJamus", baseJamuDTOS);
        return "jamu-register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("jamu") JamuDTO jamuDTO){
        jamuService.saveJamu(jamuDTO);
        return "redirect:/jamu/jamus";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        JamuDTO jamu = jamuService.findById(id);

        List<BaseJamuDTO> baseJamuDTOS = jamuService.getAllBaseJamu();
        Map<Long, Long> map = jamu.getDose().stream().collect(Collectors.toMap(DoseDTO::getBaseJamuId,DoseDTO::getBaseJamuId));
        baseJamuDTOS.forEach(baseJamu -> {
            baseJamu.setSelected(map.containsKey(baseJamu.getId()));
        });

        model.addAttribute("jamu", jamu);
        model.addAttribute("baseJamus", baseJamuDTOS);
        return "/jamu-edit";
    }

    @PostMapping("/edit/save/{id}")
    public String edit(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("jamu") JamuDTO jamuDTO,
                               BindingResult result){
        if (result.hasErrors()) {
            jamuDTO.setId(id);
            return "/jamu-edit";
        }
        jamuService.editJamu(jamuDTO, id);
        return "redirect:/jamu/jamus";
    }

    @GetMapping("/jamus")
    public String jamuPage(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<JamuDTO> jamus = jamuService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("jamus", jamus);
        if (jamus.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, jamus.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "jamu";
    }

    @GetMapping("/delete/{id}")
    public String deactivate(@PathVariable("id") Long id) {
        jamuService.deactivate(id);
        return "redirect:/jamu/jamus";
    }
}
