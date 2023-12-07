package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import com.example.gdzunit.Services.impl.VariantServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final VariantServiceImpl variantService;

    public UserController(UserServiceImpl userService, VariantServiceImpl variantService) {
        this.userService = userService;
        this.variantService = variantService;
    }

    @GetMapping("/user")
    public String userInfo(@RequestParam(value = "id", defaultValue = "0") Long id, Model model){
        model.addAttribute("user", userService.getUser(id));
        return "userinfo";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        List<Variant> allVariants = variantService.findAll();
        model.addAttribute("allVariants", allVariants);
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String confirmAddUser(@ModelAttribute User user) {
        userService.saveUser(user);

        return "redirect:/user/add"; // Перенаправление на GET-метод
    }
}
