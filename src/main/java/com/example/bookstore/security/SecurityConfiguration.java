//package com.example.bookstore.security;
//
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
//                .antMatchers("/").permitAll()
////                .antMatchers("/admin").hasRole("ADMIN")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .usernameParameter("email")
//                .and()
//                .logout()
//                .and()
////                .csrf().disable()
//                .headers().frameOptions().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//}
