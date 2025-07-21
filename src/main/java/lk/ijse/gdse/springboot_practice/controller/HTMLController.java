package lk.ijse.gdse.springboot_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {
    @GetMapping("/")
    public String index() {
        return "forward:/index.html" ;
    }
}
