package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.impl.RoleServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModerationController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public ModerationController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/moderation")
    public String showModerPage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);

        Role adminRole = roleService.getAdminRole();
        if (currentUser.getRoles().contains(adminRole)){
            model.addAttribute("isUserHaveAdminRole", true);
            List<User> userList = userService.getAllUsers();
            model.addAttribute("allUsersList", userList);
            model.addAttribute(currentUser);
            return "moderation";
        }
        else {
            return "403";
        }
    }
}
