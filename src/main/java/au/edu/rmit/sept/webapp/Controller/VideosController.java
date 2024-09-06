package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideosController {

    @GetMapping("/videos")
    public String videos() {
        return "videos";  // This must match the template name "videos.html"
    }
}
