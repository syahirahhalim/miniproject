package springboot.projects.OrderInterface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import springboot.projects.OrderInterface.Cart;
import springboot.projects.OrderInterface.services.CurrencyAPI;
import springboot.projects.OrderInterface.services.DBConnection;
import springboot.projects.OrderInterface.models.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @RequestMapping("/payment")
    public String getPayment(Model model) {
        HashMap<Products, Integer> cartItems = Cart.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", "SGD" + Cart.totalPrice());
        return "payment";
    }

    @RequestMapping(value="/payment", method=RequestMethod.POST)
    public String setCurrency(@RequestParam(value = "currency") String currency, Model model) {
        HashMap<Products, Integer> cartItems = Cart.getCartItems();
        model.addAttribute("cartItems", cartItems);
        double totalPrice = CurrencyAPI.convertCurrency(Cart.totalPrice(), "SGD", currency);
        model.addAttribute("totalPrice", currency + totalPrice);
        return "payment";
    }

    @RequestMapping(value="/confirm", method=RequestMethod.POST)
    public RedirectView confirmPayment(@RequestParam(value = "fullname") String fullname, @RequestParam(value = "countryCode") Integer countryCode) throws SQLException {
        System.out.println("Confirm Payment");

        // Check user
        Integer userID = null;
        userID = checkUser(fullname);

        // Add user
        if (userID == null) {
            System.out.println("Adding new user");
            userID = addUser(fullname, countryCode);
        }

        if (userID == null) System.out.println("userID is null!");

        // Orders
        Integer orderID = addOrder(userID);

        if (orderID == null) System.out.println("orderID is null");

        // OrderItems
        addOrderItems(orderID);

        // Payment (Done in local currency)
        String currencyType;
        switch (countryCode) {
            case 49: {
                currencyType = "EUR";
                break;
            }
            case 65: {
                currencyType = "SGD";
                break;
            }
            default: {
                currencyType = "SGD";
            }
        }
        double payment = CurrencyAPI.convertCurrency(Cart.totalPrice(), "SGD", currencyType);
        addPayment(countryCode, payment);

        // Clear cart
        Cart.clearCart();

        return new RedirectView("/");
    }

    private Integer checkUser(String fullname) throws SQLException {
        String query = "SELECT * FROM users WHERE full_name = ?;";
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullname);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return null;
    }

    private Integer addUser(String fullname, Integer countryCode) throws SQLException {
        String query = "INSERT INTO users(full_name, created_at, country_code) VALUES(?, ?, ?);";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullname);
            stmt.setString(2, dtf.format(currentDateTime));
            stmt.setInt(3, countryCode);
            try {
                stmt.executeUpdate();
                ResultSet rs = stmt.executeQuery("SELECT last_insert_id() as last_id;");
                rs.next();
                return rs.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Integer addOrder(Integer userID) throws SQLException {
        String status = "orderPlaced";
        String query = "INSERT INTO orders(user_id, status, created_at) VALUES(?, ?, ?);";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);
            stmt.setString(2, status);
            stmt.setString(3, dtf.format(currentDateTime));
            try {
                stmt.executeUpdate();
                ResultSet rs = stmt.executeQuery("SELECT last_insert_id() as last_id;");
                rs.next();
                return rs.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void addOrderItems(Integer orderID) throws SQLException {
        HashMap<Products, Integer> cartItems = Cart.getCartItems();
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO order_items VALUES(?, ?, ?);");
            int i = 0;
            for (Map.Entry<Products, Integer> entry : cartItems.entrySet()) {
                Products product = entry.getKey();
                Integer quantity = entry.getValue();
                stmt.setInt(1, orderID);
                stmt.setInt(2, product.getId());
                stmt.setInt(3, quantity);
                stmt.addBatch();
                i++;

                if (i == cartItems.size()) {
                    stmt.executeBatch();
                }
            }
        }
    }

    private void addPayment(Integer countryCode, Double currencyAmount) throws SQLException {
        String query = "INSERT INTO payment(country_code, currency, created_at) VALUES(?, ?, ?);";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        try (Connection conn = DBConnection.createDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, countryCode);
            stmt.setDouble(2, currencyAmount);
            stmt.setString(3, dtf.format(currentDateTime));
            try {
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
