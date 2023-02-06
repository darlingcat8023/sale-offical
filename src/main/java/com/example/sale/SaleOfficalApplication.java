package com.example.sale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class SaleOfficalApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(SaleOfficalApplication.class, args);
    }

}
