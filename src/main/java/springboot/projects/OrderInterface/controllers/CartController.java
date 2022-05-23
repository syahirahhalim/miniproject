package springboot.projects.OrderInterface.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import springboot.projects.OrderInterface.Cart;
import springboot.projects.OrderInterface.models.Products;
import springboot.projects.OrderInterface.services.CurrencyAPI;

@Controller
public class CartController {

    @RequestMapping("/cart")
    public String getCart(Model model) {
        HashMap<Products, Integer> cartItems = Cart.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", "SGD" + Cart.totalPrice());
        return "cart";
    }

    @RequestMapping(value="/cart", method=RequestMethod.POST)
    public String setCurrency(@RequestParam(value="currency") String currency, Model model) {
        HashMap<Products, Integer> cartItems = Cart.getCartItems();
        model.addAttribute("cartItems", cartItems);
        double totalPrice = CurrencyAPI.convertCurrency(Cart.totalPrice(), "SGD", currency);
        model.addAttribute("totalPrice", currency + totalPrice);
        return "cart";
    }

    @RequestMapping(value="/cart/edit", method=RequestMethod.POST, params="update")
    public RedirectView updateProductInCart(@RequestParam(value="id") Integer id, @RequestParam(value="quantity") Integer quantity) {
        Cart.updateCart(id, quantity);
        return new RedirectView("/cart");
    }

    @RequestMapping(value="/cart/edit", method=RequestMethod.POST, params="remove")
    public RedirectView removeProductFromCart(@RequestParam(value="id") Integer id) {
        Cart.removeFromCart(id);
        return new RedirectView("/cart");
    }
}