package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open/dashboard")
public class OpenController {

    @GetMapping
    public String dashboard() {
        return "pages/open/dashboard";
    }
}
