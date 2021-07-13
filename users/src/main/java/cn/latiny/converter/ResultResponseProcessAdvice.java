package cn.latiny.converter;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 已经找到问题所在的，这个ResponseBodyAdvice只支持@ResponseBody注解的controller方法，
 * 同样，RequestBodyAdvice只支持带有@RequestBody注解的controller方法参数的方法，
 * 同时上报的数据必须是jsonorxml也就是说，想要用这两个实现http内容的加密解密，是很有局限性的。并不支持表单post请求
 */


//@RestControllerAdvice
public class ResultResponseProcessAdvice implements ResponseBodyAdvice<Object> {
    public ResultResponseProcessAdvice() {
    }

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Jackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body == null ? Result.newBuilder().success().build() : body;
    }
}