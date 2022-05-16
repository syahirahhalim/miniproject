package springboot.projects.OrderInterface.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springboot.projects.OrderInterface.models.User;

@Controller
public class PaymentController {

    @RequestMapping("/payment")
    public String getPayment(Model model) {
        return "payment";
    }

    @RequestMapping(name="/confirm", method=RequestMethod.POST,
            consumes="application/x-www-form-urlencoded",
            produces="text/html"
    )
    public String confirmPayment(@ModelAttribute User user, Model model) {
        // TODO: Create user if confirmation done

        model.addAttribute("confirmation", true);
        return "payment";
    }
}
