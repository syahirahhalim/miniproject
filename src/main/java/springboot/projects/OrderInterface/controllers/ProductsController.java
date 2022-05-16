package springboot.projects.OrderInterface.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.projects.OrderInterface.models.Products;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/")
    public String getMenu() {
        List<Products> products = null;
        try {
//            products = getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        model.addAttribute(products);
        return "products";
    }

    private List<Products> getProducts() throws SQLException {
        if (dataSource == null) return null;

        List<Products> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products;")) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        productList.add(new Products(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("status"),
                                resultSet.getString("createdAt")));
                    }
                }
            }
        }
        return productList;
    }
}
