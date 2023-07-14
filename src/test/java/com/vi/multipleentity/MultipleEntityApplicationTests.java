package com.vi.multipleentity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vi.multipleentity.Models.Cart;
import com.vi.multipleentity.Models.CartProduct;
import com.vi.multipleentity.Models.Product;
import com.vi.multipleentity.Models.User;
import com.vi.multipleentity.Repo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class MultipleEntityApplicationTests {
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CartProductRepo cpRepo;


    @Test
    @Order(1)
    void test1() throws JsonProcessingException, ParseException {
        String[] categories = { "Fashion", "Electronics", "Books", "Groceries", "Medicines" };
        for (ListIterator<?> it = ((JSONArray) new JSONParser().parse(new ObjectMapper().writeValueAsString(categoryRepo.findAll()))).listIterator(); it.hasNext();)
            assertEquals(categories[it.nextIndex()], ((JSONObject) it.next()).get("categoryName").toString());

    }
    @Test
    @Order(2)
    public void dbUserDefaultData() throws Exception {
        String[] users = { "jack", "bob", "apple", "glaxo" };
        for (ListIterator<?> it = ((JSONArray) new JSONParser()
                .parse(new ObjectMapper().writeValueAsString(userRepo.findAll()))).listIterator(); it.hasNext();) {
            assertEquals(users[it.nextIndex()], ((JSONObject) it.next()).get("username").toString());
            assertEquals("pass_word", ((JSONObject) it.next()).get("password").toString());
        }
    }

    @Test
    @Order(3)
    public void dbProductDefaultData() throws Exception {
        String[] products = { "apple ipad", "crocin" };
        String[] prices = { "29190", "10" };
        for (ListIterator<?> it = ((JSONArray) new JSONParser()
                .parse(new ObjectMapper().writeValueAsString(productRepo.findAll()))).listIterator(); it.hasNext();) {
            assertEquals(products[it.nextIndex()],
                    ((JSONObject) it.next()).get("productName").toString().toLowerCase());
            assertEquals(prices[it.nextIndex()],
                    String.valueOf(Math.round((Double) ((JSONObject) it.next()).get("price"))));
        }
    }
    @Test
    @Order(4)
    public void dbCartDefaultData() throws Exception {
        List<Cart> arr = cartRepo.findAll();

        assertEquals(2, arr.size());
        assertEquals("20.0", arr.get(0).getTotalAmount().toString());
        assertEquals("0.0", arr.get(1).getTotalAmount().toString());
        assertEquals("2", arr.get(0).getCartProduct().get(0).getProduct().getProductId().toString());
        assertEquals("2", arr.get(0).getCartProduct().get(0).getQuantity().toString());
        assertEquals("10.0", arr.get(0).getCartProduct().get(0).getProduct().getPrice().toString());
        assertEquals("crocin",
                arr.get(0).getCartProduct().get(0).getProduct().getProductName().toString());
        assertEquals("5", arr.get(0).getCartProduct().get(0).getProduct().getCategory().getCategoryId().toString());
        assertEquals("Medicines",
                arr.get(0).getCartProduct().get(0).getProduct().getCategory().getCategoryName().toString());
        assertEquals(0, arr.get(1).getCartProduct().size());
    }

    @Test
    @Order(5)
    public void updateUser() {
        User user = userRepo.findById(1).get();
        assertEquals("jack", user.getUsername());
        user.setUsername("jackie");
        userRepo.save(user);

        user = userRepo.findById(3).get();
        assertEquals("apple", user.getUsername());
        user.setUsername("apple inc");
        userRepo.save(user);
    }
    @Test
    @Order(6)
    public void checkUpdatedUsers() throws Exception {
        String[] users = { "jackie", "bob", "apple inc", "glaxo" };
        for (ListIterator<?> it = ((JSONArray) new JSONParser()
                .parse(new ObjectMapper().writeValueAsString(userRepo.findAll()))).listIterator(); it.hasNext();) {
            assertEquals(users[it.nextIndex()], ((JSONObject) it.next()).get("username").toString());
            assertEquals("pass_word", ((JSONObject) it.next()).get("password").toString());
        }
    }
    @Test
    @Order(7)
    public void updateProduct() {
        Product p = productRepo.findById(1).get();
        assertEquals("apple ipad", p.getProductName().toString().toLowerCase());
        assertEquals("29190", String.valueOf(Math.round((Double) p.getPrice())));
        p.setProductName("apple iphone");
        p.setPrice(30000.0);
        productRepo.save(p);
    }
    @Test
    @Order(8)
    public void checkUpdatedProducts() throws Exception {
        String[] products = { "apple iphone" };
        String[] prices = { "30000", "10" };
        for (ListIterator<?> it = ((JSONArray) new JSONParser()
                .parse(new ObjectMapper().writeValueAsString(productRepo.findAll()))).listIterator(); it.hasNext();) {
            assertEquals(products[it.nextIndex()],
                    ((JSONObject) it.next()).get("productName").toString().toLowerCase());
            assertEquals(prices[it.nextIndex()],
                    String.valueOf(Math.round((Double) ((JSONObject) it.next()).get("price"))));
        }
    }
    @Test
    @Order(9)
    public void compareUserAndCartOwner() {
        Cart c = cartRepo.findById(1).get();
        User u = userRepo.findById(1).get();
        assertEquals(u.getUsername(), c.getUser().getUsername());
        assert (u.getRoles().toString().contains("CONSUMER"));
        assert (!u.getRoles().toString().contains("SELLER"));
    }
    static CartProduct cp;

    @Test
    @Order(10)
    public void removeProductFromCart() {
        Cart c = cartRepo.findById(1).get();
        assertEquals(1, c.getCartProduct().size());
        c.getCartProduct().remove(0);
        cartRepo.save(c);
        cp = cpRepo.findById(1).get();
        cpRepo.deleteById(1);

    }

    @Test
    @Order(11)
    public void checkProductRemovedFromCart() {
        Cart c = cartRepo.findById(1).get();
        c = cartRepo.findById(1).get();
        assertEquals(0, c.getCartProduct().size());
    }
    @Test
    @Order(12)
    public void addCartProduct() {
        assertEquals("crocin", cp.getProduct().getProductName().toString().toLowerCase());
    }

    @Test
    @Order(13)
    public void addNewProduct() {
        cp.setProduct(productRepo.findById(1).get());
        cpRepo.save(cp);
        List<CartProduct> cps = cpRepo.findAll();
        assertEquals(1, cps.size());
    }

    @Test
    @Order(14)
    public void checkUserCart() {
        Cart c = cartRepo.findById(1).get();
        assertEquals("1", c.getUser().getUserId().toString());
        assertEquals("jackie", c.getUser().getUsername().toString());
    }

}
