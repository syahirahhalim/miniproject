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
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getCart() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("cart"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cartItems"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPrice"));
    }

    @Test
    void setCurrency() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("currency", "EUR");
        this.mvc.perform(MockMvcRequestBuilders.post("/cart")
                .accept(MediaType.TEXT_HTML)
                .params(params))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("cart"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("cartItems"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("totalPrice"));
    }

    @Test
    void updateProductInCart() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("quantity", "2");
        params.add("update", "");
        this.mvc.perform(MockMvcRequestBuilders.post("/cart/edit")
                        .accept(MediaType.TEXT_HTML)
                        .params(params))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cart"));
    }

    @Test
    void removeProductFromCart() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("remove", "");
        this.mvc.perform(MockMvcRequestBuilders.post("/cart/edit")
                        .accept(MediaType.TEXT_HTML)
                        .params(params))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cart"));
    }
}