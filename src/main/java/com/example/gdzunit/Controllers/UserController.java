package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Entity.Variant;
import com.example.gdzunit.Exceptions.UserNotFoundException;
import com.example.gdzunit.Services.impl.RoleServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import com.example.gdzunit.Services.impl.VariantServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final VariantServiceImpl variantService;
    private final RoleServiceImpl roleService;

    public UserController(UserServiceImpl userService, VariantServiceImpl variantService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.variantService = variantService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String userInfo(@RequestParam(value = "id", defaultValue = "0") Long id, Model model){
        try {
            model.addAttribute("user", userService.getUser(id));

            if (userService.getUser(id).isEnabled()) {
                model.addAttribute("isUserEnabled", "Да");
            } else {
                model.addAttribute("isUserEnabled", "Нет");
            }

            model.addAttribute("userVariant", userService.getUser(id).getVariant());
            return "userinfo";
        } catch (UserNotFoundException e) {
            return "404";
        }
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);

        Role adminRole = roleService.getAdminRole();
        model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));


        List<Variant> allVariants = variantService.findAll();
        model.addAttribute("allVariants", allVariants);
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/user/add")
    public String confirmAddUser(@ModelAttribute User user) {
        userService.saveUser(user);

        return "redirect:/user/add"; // Перенаправление на GET-метод
    }

    @GetMapping("/me")
    public String showProfile(@RequestParam(required = false) String success, @RequestParam(required = false) String fileSizeError,  MultipartFile file, Model model){
        User currentUser = userService.getCurrentUser();

        model.addAttribute("user", currentUser);
        model.addAttribute("isUserEnabled", currentUser.isEnabled());
        model.addAttribute("userVariant", currentUser.getVariant());
        model.addAttribute("avatar", currentUser.getAvatarURL());

        model.addAttribute("user", currentUser);

        Role adminRole = roleService.getAdminRole();
        model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

        if (success != null) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("fileSizeError", fileSizeError);
        }

        return "myProfileInfo";
    }
}
