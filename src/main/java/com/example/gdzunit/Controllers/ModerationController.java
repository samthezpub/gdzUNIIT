package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Exceptions.UserNotFoundException;
import com.example.gdzunit.Services.impl.RoleServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/moderation")
public class ModerationController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public ModerationController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path="")
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

    @GetMapping(path="/user")
    public String userInfo(@RequestParam(value = "id") Long id, Model model) {
        try {
            model.addAttribute("user", userService.getUser(id));

            if (userService.getUser(id).isEnabled()) {
                model.addAttribute("isUserEnabled", true);
            } else {
                model.addAttribute("isUserEnabled", false);
            }

            model.addAttribute("userVariant", userService.getUser(id).getVariant());
            return "userinfo";
        } catch (UserNotFoundException e) {
            return "404";
        }
    }

    @GetMapping(path = "/user/{id}/edit")
    public String editUserInfoPage(@PathVariable Long id, Model model){
        User user = null;
        try {
            user = userService.getUser(id);
            model.addAttribute("user", user);

            return "editUserInfoPage";
        } catch (UserNotFoundException e) {
            return "redirect:/404";
        }


    }

    @PostMapping(path = "/user/edit/confirm")
    public String editUserInfo(@ModelAttribute User user){
        try {
            userService.updateUser(user);
        } catch (UserNotFoundException e) {
            return "404";
        }

        return "redirect:/moderation";
    }
}
