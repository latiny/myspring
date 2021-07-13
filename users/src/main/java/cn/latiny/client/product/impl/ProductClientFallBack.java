package cn.latiny.client.product.impl;

import cn.latiny.client.product.ProductClient;
import cn.latiny.client.product.input.ProductParamInput;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductClientFallBack implements ProductClient {

    @Override
    public String testHystrix(int id) {
        return "当前服务已降级。";
    }

    @Override
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("status","false");
        map.put("msg","当前查询服务不可用，已被降级。");
        return map;
    }

    @Override
    public Map<String, Object> findOne(String productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("status","false");
        map.put("msg","findOne 查询服务不可用，已被降级。");
        return map;
    }

    @Override
    public Map<String, Object> findTwo(ProductParamInput paramInput) {
        return null;
    }
}
