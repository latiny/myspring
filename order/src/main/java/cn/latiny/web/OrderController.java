package cn.latiny.web;

import cn.latiny.web.output.OrderOutput;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("order")
public class OrderController {

    @PostMapping("/getOrder")
    public OrderOutput getOrder(@RequestParam("orderId") Long orderId){
        OrderOutput order = new OrderOutput();
        order.setId(orderId);
        order.setName("peach");
        order.setPrice(new BigDecimal(10.00));
        return order;
    }
}
