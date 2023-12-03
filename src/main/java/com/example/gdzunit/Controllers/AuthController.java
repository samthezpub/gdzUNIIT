package com.example.gdzunit.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

}
