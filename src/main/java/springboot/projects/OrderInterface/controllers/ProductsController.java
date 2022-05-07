package springboot.projects.OrderInterface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductsController {

    @RequestMapping("/")
    public String getMenu() {
        return "products";
    }
}
