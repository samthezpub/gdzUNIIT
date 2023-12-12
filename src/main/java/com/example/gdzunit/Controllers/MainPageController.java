package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        List<Subject> subjectList = subjectService.findAll();
        User currentUser = userService.getCurrentUser();

        model.addAttribute("subjects", subjectList);
        model.addAttribute("user", currentUser);

        return "home";
    }
}
