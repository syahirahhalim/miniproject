package springboot.projects.OrderInterface.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void displayProducts() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("quantity", "1");
        this.mvc.perform(MockMvcRequestBuilders.post("/addProduct/1")
                        .accept(MediaType.TEXT_HTML)
                        .params(params))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    void addToCart() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"));
    }
}