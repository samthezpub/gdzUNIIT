package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<Subject> subjectList = subjectService.findAll();
        User currentUser = userService.getCurrentUser();

        model.addAttribute("subjects", subjectList);
        model.addAttribute("user", currentUser);

        return "home";
    }

    @GetMapping("/")
    public String getMainPage(){
        if (isAuthenticated()){
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/termsofuse")
    public String getTermsOfUse(){
        return "termsofuse";
    }


//    Проверка авторизован ли пользователь
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
