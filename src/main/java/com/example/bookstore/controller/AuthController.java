package com.example.bookstore.controller;

import com.example.bookstore.domain.*;
import com.example.bookstore.security.JwtUtil;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    @GetMapping("/u")
    public ResponseEntity<String> user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No account is associated with these credentials.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if(jwtUtil.isInvalid(token))
            return ResponseEntity.badRequest().body("You're not logged in.");
        jwtUtil.invalidateToken(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("You've logged out successfully.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Valid Users user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String s = "";
            for(int i = 0 ; i < bindingResult.getErrorCount(); i++)
                s+=bindingResult.getAllErrors().get(i).getDefaultMessage() + "\n";
            return ResponseEntity.badRequest().body(s);
        }

        if (userService.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("Credentials are invalid.");

        Users newUser = userService.register(user);

        mailService.sendWelcomeEmail(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("You've registered successfully!");
    }

}
