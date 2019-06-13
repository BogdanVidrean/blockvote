package com.blockvote.bootstrap.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.blockvote.bootstrap.controllers"})
public class BootstrapServer {

    public static void main(String[] args) {
        SpringApplication.run(BootstrapServer.class, args);
    }

}
