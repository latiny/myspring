package cn.latiny.converter;


import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.concurrent.TimeUnit;

//@Configuration
public class FeignConfig {

    private int connectTimeoutMillis = 5000;
    private int readTimeoutMillis = 5000;

    private ObjectFactory<HttpMessageConverters> messageConverters = () -> {
        return new HttpMessageConverters(new HttpMessageConverter[]{new MappingJackson2HttpMessageConverter()});
    };

    public FeignConfig() {
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConverters));
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(this.messageConverters);
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(this.connectTimeoutMillis, TimeUnit.MILLISECONDS, this.readTimeoutMillis, TimeUnit.MILLISECONDS, true);
    }
}

