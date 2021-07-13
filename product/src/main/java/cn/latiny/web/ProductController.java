package cn.latiny.web;

import cn.latiny.error.BusinessException;
import cn.latiny.web.input.ProductParamInput;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @GetMapping("hystrix")
    @HystrixCommand(defaultFallback = "defaultFallBack")
    public String testHystrix(int id){
        if (id < 0) {
            throw new BusinessException(100, "数据不合法");
        }
        return "当前商品id:" + id;
    }

    public String testFallBack(int id){
        return "商品参数：" + id + "不合法。";
    }

    public String defaultFallBack(){
        return "服务发生异常，熔断中。";
    }

    @GetMapping("/findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "访问商品服务成功，端口号："+ port);
        map.put("status", "success");
        return map;
    }

    @GetMapping("/findOne")
    public Map<String, Object> findOne(String productId){
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "调用商品查询信息");
        map.put("status", "success");
        map.put("id", productId);
        return map;
    }

    @PostMapping("/findTwo")
    public Map<String, Object> findTwo(@RequestBody ProductParamInput paramInput){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "调用商品查询信息");
        map.put("status", "success");
        map.put("data", paramInput);
        return map;
    }

}
