package lk.ijse.gdse.springboot_practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTMLController {
    @GetMapping("/")
    public String index() {
        return "forward:/index.html" ;
    }
}
