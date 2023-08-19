package com.example.bookstore.controller;

import com.example.bookstore.domain.*;
import com.example.bookstore.security.JwtUtil;
import com.example.bookstore.service.RoleService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.service.WishlistService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final WishlistService wishlistService;
    private final ShoppingCartService shoppingCartService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


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
    public void logoutUser(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromHeader(request.getHeader("Authorization"));
        jwtUtil.invalidateToken(token);
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerNewUser(@RequestBody @Valid Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || userService.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.badRequest().build();

        ShoppingCart sc = new ShoppingCart();
        Wishlist ws = new Wishlist();
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

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
