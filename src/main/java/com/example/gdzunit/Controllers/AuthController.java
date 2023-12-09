package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import com.example.gdzunit.Services.impl.VariantServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private final UserServiceImpl userService;
    private final VariantServiceImpl variantService;

    public AuthController(UserServiceImpl userService, VariantServiceImpl variantService) {
        this.userService = userService;
        this.variantService = variantService;
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @ModelAttribute("user")
    public User getDefaultUser() {
        // Создаем объект User и устанавливаем как атрибут "user"
        return new User();
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        List<Variant> variants = variantService.findAll();
        model.addAttribute("variants", variants);

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user")User user){
        userService.saveUser(user);

        return "redirect:/registration?success";
    }

}
