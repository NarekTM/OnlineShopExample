package com.example.onlineshop.service;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.domain.enums.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.repository.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasketService basketService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       BasketService basketService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.basketService = basketService;
    }

    @Transactional
    public ResponseEntity<String> registerUser(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username exists.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        if (savedUser.getId().equals(1L)) {
            savedUser.setRole(Role.ADMIN);
        }
        Basket basket = new Basket();
        savedUser.addBasket(basket);

        return new ResponseEntity<>("User successfully registered.", HttpStatus.OK);
    }

    public ResponseEntity<String> authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User successfully authenticated.", HttpStatus.OK);
    }
}
