package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrendsController {

   @GetMapping("/trends")
   public String trends() {
       return "trends";
   }
}
