package cn.latiny.client.order;

import cn.latiny.client.order.input.OrderInput;
import cn.latiny.client.order.output.OrderOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("order")
public interface OrderClient {

    @PostMapping("/order/getOrder")
    OrderOutput getOrder(@RequestParam("orderId") Long orderId);

}
