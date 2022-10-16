package com.example.onlineshop;

import com.example.onlineshop.dto.*;
import com.example.onlineshop.service.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class OnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            SignUpDto signUpDto = new SignUpDto();
            signUpDto.setFirstName("Super");
            signUpDto.setLastName("User");
            signUpDto.setUsername("admin");
            signUpDto.setPassword("123456");
            authService.registerUser(signUpDto);
        };
    }
}
