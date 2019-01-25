package me.zbl.gates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatesApplication.class, args);
    }

    @Value("${auth-url}")
    private String authUrl;
    @Value("${message-url}")
    private String messageUrl;
    @Value("${user-url}")
    private String userUrl;

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("auth", p -> p.path("/oauth/**").uri(authUrl))
                .route("user", p -> p.path("/user/**").uri(userUrl))
                .route("message", p -> p.path("/message/**").uri(messageUrl))
                .build();
    }
}

