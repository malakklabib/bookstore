//package com.example.bookstore.security;
//
//import com.example.bookstore.domain.Role;
//import com.example.bookstore.domain.Users;
//import com.example.bookstore.repo.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collection;
//import java.util.stream.*;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
////    @Override
////    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////        Optional<Users> u = userRepository.findByEmail(email);
////        if(!u.isPresent())
////            throw new UsernameNotFoundException(email);
////        Users user = u.get();
////        return new User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
////
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Simulate loading user data from a database
//        if ("admin@example.com".equals(username)) {
//            return User.withUsername("admin@example.com")
//                    .password("password") // You should hash the password in a real scenario
//                    .roles("ADMIN")
//                    .build();
//        }
//        throw new UsernameNotFoundException("User not found");
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                .collect(Collectors.toList());
//    }
//}
