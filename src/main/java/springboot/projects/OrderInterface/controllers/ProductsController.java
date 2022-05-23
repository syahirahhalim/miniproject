package springboot.projects.OrderInterface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import springboot.projects.OrderInterface.Cart;
import springboot.projects.OrderInterface.services.DBConnection;
import springboot.projects.OrderInterface.models.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductsController {

    @RequestMapping("/")
    public String displayProducts(Model model) {
        List<Products> products = null;
        try {
            products = getProducts(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/addProduct/{productID}")
    public RedirectView addToCart(@PathVariable(value = "productID") String id, @RequestParam Integer quantity) {
        try {
            Products product = getProducts(id).get(0);
            addProduct(product, quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/");
    }

    private List<Products> getProducts(String id) throws SQLException {
        List<Products> productList = new ArrayList<>();
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt;
            if (id == null) {
                stmt = conn.prepareStatement("SELECT * FROM products;");
            } else {
                stmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?;");
                stmt.setString(1, id);
            }
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    productList.add(new Products(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("status"),
                            resultSet.getString("created_at")));
                }
            }
        }
        return productList;
    }

    private void addProduct(Products product, Integer quantity) {
        Cart.addToCart(product, quantity);
    }
}