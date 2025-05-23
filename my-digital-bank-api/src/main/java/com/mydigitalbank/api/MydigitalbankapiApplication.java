package com.mydigitalbank.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(title = "my-digital-bank-api", version = "1.0.0", description = "API for managing digital bank operations."),
    servers = { @Server(url = "/", description = "Default Server URL") }
)
@SpringBootApplication
public class MydigitalbankapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MydigitalbankapiApplication.class, args);
    }

}
