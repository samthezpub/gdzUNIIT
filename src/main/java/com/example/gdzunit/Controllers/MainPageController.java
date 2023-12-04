package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        List<Subject> subjectList = subjectService.findAll();
        model.addAttribute("subjects", subjectList);

        return "home";
    }
}
