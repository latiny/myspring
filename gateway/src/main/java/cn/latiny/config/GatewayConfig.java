package cn.latiny.config;

import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("product_service", r -> r.path("/product/**").uri("http://localhost:8082/"))
                .route("user_service", r -> r.path("/user/**").uri("http://localhost:8083/"))
                .build();
    }
}

