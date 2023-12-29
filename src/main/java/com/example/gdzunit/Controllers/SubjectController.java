package com.example.gdzunit.Controllers;

import com.example.gdzunit.Entity.Role;
import com.example.gdzunit.Entity.Subject;
import com.example.gdzunit.Entity.User;
import com.example.gdzunit.Services.impl.RoleServiceImpl;
import com.example.gdzunit.Services.impl.SubjectServiceImpl;
import com.example.gdzunit.Services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectServiceImpl subjectService;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public SubjectController(SubjectServiceImpl subjectService, UserServiceImpl userService, RoleServiceImpl roleService) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/add")
    public String getAddSubjectPage(Model model){
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);

        Role adminRole = roleService.getAdminRole();
        model.addAttribute("isUserHaveAdminRole", currentUser.getRoles().contains(adminRole));

        model.addAttribute("subject", new Subject());

        return "addSubject";
    }

}
