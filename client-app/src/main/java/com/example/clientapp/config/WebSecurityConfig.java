package com.example.clientapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITELIST_URLS = {
            "/register",
            "/verify",
            "/helloworld"
    };

    private static final String[] AUTHENTICATED_URLS = {
            "/try"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/register", "/verify").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login(oauth2login -> oauth2login.loginPage("/oauth2/authorization/app-client-oidc").defaultSuccessUrl("/api/helloworld"))
                .oauth2Client(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
