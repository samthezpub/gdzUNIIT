package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModerationController {

    private final UserServiceImpl userService;

    public ModerationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/moderation")
    public String showModerPage(Model model){
         List<User> userList = userService.getAllUsers();
         model.addAttribute("allUsersList", userList);
        return "moderation";
    }
}
