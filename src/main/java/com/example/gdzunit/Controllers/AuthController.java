package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Services.impl.RoleServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import com.example.gdzunit.Services.impl.VariantServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AuthController {

    private final UserServiceImpl userService;
    private final VariantServiceImpl variantService;
    private final RoleServiceImpl roleService;

    public AuthController(UserServiceImpl userService, VariantServiceImpl variantService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.variantService = variantService;
        this.roleService = roleService;
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
    public String getRegistrationPage(@RequestParam(required = false) String success ,Model model){
        // Вытягиваем варианты из бд и помещаем их в модель
        List<Variant> variants = variantService.findAll();
        model.addAttribute("variants", variants);

        if (success != null){
            model.addAttribute("success", success);
        }


        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user")User user){
        user.setEnabled(true);
        user.setRoles(roleService.getUserRole());
        userService.saveUser(user);




        return "redirect:/registration?success";
    }

}
