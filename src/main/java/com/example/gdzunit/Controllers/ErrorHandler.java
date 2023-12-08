package com.example.gdzunit.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandler {

    @GetMapping("/error404")
    public String error404(){
        return "404";
    }
}
