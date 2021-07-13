package cn.latiny.client.product;

import cn.latiny.client.product.impl.ProductClientFallBack;
import cn.latiny.client.product.input.ProductParamInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(serviceId = "products")
public interface ProductClient {

    @GetMapping("/product/hystrix")
    String testHystrix(int id);

    @GetMapping("/product/findAll")
    Map<String, Object> findAll();

    @GetMapping("/product/findOne")
    Map<String, Object> findOne(@RequestParam("productId") String productId);

    @PostMapping("/product/findTwo")
    Map<String, Object> findTwo(@RequestBody ProductParamInput paramInput);
}
