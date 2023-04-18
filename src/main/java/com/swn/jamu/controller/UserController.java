package com.swn.jamu.controller;

import com.swn.jamu.dto.UserDTO;
import com.swn.jamu.model.User;
import com.swn.jamu.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        model.addAttribute("branches", userService.findAllBranches());
        return "user-register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByUsernameAndActive(userDTO.getUsername());

        if(existingUser != null){
            result.rejectValue("username", "J001",
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDTO);
            return "user-register";
        }

        userService.saveUser(userDTO);
        return "redirect:/user/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("branches", userService.findAllBranches());
        return "/user-edit";
    }

    @PostMapping("/edit/save/{id}")
    public String registration(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               Model model){
        if (result.hasErrors()) {
            userDTO.setId(id);
            return "/user-edit";
        }
        userService.editUser(id, userDTO);
        return "redirect:/user/users";
    }

//    @GetMapping("/users")
//    public String users(Model model){
//        List<UserDTO> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }

    @GetMapping("/users")
    public String usersPage(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            String name) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<UserDTO> users = userService.findPaginated(PageRequest.of(currentPage-1, pageSize), name);
        model.addAttribute("users", users);
        if (users.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, users.getTotalPages()).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             Model model) {
        userService.deleteUser(id);
        return "redirect:/user/users";
    }
}
