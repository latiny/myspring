package cn.latiny.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    @GetMapping("/product/findAll")
    public Map<String, Object> findAll(){
        Map<String, Object> map = new HashMap<>();
        map.put("1", "Latiny");
        map.put("2", "Jack");
        return map;
    }
}
