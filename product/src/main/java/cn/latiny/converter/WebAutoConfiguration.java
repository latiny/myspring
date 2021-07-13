package cn.latiny.converter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class WebAutoConfiguration {
    public WebAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters() {
        return new HttpMessageConverters(new HttpMessageConverter[]{new Jackson2HttpMessageConverter()});
    }

}