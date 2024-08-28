package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.controller;
import org.springframework.web.bind.annotation.GetMapping;

@controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}