package com.example.bookstore.controller;

import com.example.bookstore.domain.*;
import com.example.bookstore.security.JwtUtil;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final WishlistService wishlistService;
    private final ShoppingCartService shoppingCartService;
    private final MailService mailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    @GetMapping("/u")
    public ResponseEntity<String> user() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(a.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

//        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("ROLE_USER");

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromHeader(request.getHeader("Authorization"));
        jwtUtil.invalidateToken(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("You've logged out successfully!");
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerNewUser(@RequestBody @Valid Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || userService.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.badRequest().build();

        ShoppingCart sc = new ShoppingCart(new ArrayList<>());
        Wishlist ws = new Wishlist(new ArrayList<>());
        Role role = roleService.findByName("ROLE_USER");
        String secret = encoder.encode(user.getPassword());

        Users newUser = new Users();

        newUser.setRole(role);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(secret);
        newUser.setConfirmPassword(secret);
        newUser.setWishlist(ws);
        newUser.setShoppingCart(sc);

        shoppingCartService.save(sc);
        wishlistService.save(ws);
        userService.save(newUser);

        mailService.sendWelcomeEmail(newUser);


        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
