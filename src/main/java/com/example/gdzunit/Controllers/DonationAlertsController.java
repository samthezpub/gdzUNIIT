package com.example.gdzunit.Controllers;

import com.example.gdzunit.Services.impl.DonationAlertsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dalerts")
public class DonationAlertsController {

    @Autowired
    private DonationAlertsServiceImpl donationAlertsService;

    // https://www.donationalerts.com/oauth/authorize?client_id=12025&redirect_uri=http://localhost:8080/dalerts/getcode&response_type=code&scope=oauth-donation-index
    @GetMapping("/getcode")
    public String getCode(@RequestParam("code") String code){
        System.out.println(code);
        return "redirect:/";
    }
}
