package cn.latiny.web;

import cn.latiny.client.order.OrderClient;
import cn.latiny.client.order.output.OrderOutput;
import cn.latiny.client.product.ProductClient;
import cn.latiny.client.product.input.ProductParamInput;
import cn.latiny.web.input.OrderInput;
import cn.latiny.web.output.UserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderClient orderClient;
    @Value("${server.port}")
    private String port;

    @GetMapping("/findAll")
    public Map<String, Object> findAll() {
        return productClient.findAll();
    }

    @GetMapping("/findOne")
    public Map<String, Object> findOne(@RequestParam("productId") String productId) {
        Map<String, Object> map = productClient.findOne(productId);
        return map;
    }

    @PostMapping("/findTwo")
    public @ResponseBody
    UserOutput findTwo() {
        ProductParamInput paramInput = new ProductParamInput();
        paramInput.setId(101L);
        paramInput.setProductName("Car");
        paramInput.setProductDesc("One");
        //Map<String, Object> map = productClient.findTwo(paramInput);
        UserOutput user = new UserOutput();
        user.setId(101);
        user.setName("Latiny");
        user.setSex("Male");
        user.setPort(port);
        return user;
    }

    @PostMapping("/getOrder")
    public @ResponseBody
    OrderOutput getOrder(@RequestBody OrderInput input) {
        OrderOutput order = orderClient.getOrder(input.getOrderId());
        return order;
    }
}
