package com.company.router;

import com.company.webflux.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> routeHelloHandler(HelloHandler helloHandler){
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8))
                ,helloHandler::hello2);

    }
}
