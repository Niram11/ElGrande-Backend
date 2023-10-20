package com.codecool.gastro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GastroApplication {
    //todo: SessionNotRegisteredException handler
    //  EmailNotFoundException after deleting customer
    public static void main(String[] args) {
        SpringApplication.run(GastroApplication.class, args);
    }

}
