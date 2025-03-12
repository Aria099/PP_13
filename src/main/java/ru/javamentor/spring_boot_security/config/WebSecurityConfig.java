package ru.javamentor.spring_boot_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.javamentor.spring_boot_security.security.SecurityUserDetailsService;
import ru.javamentor.spring_boot_security.service.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SecurityUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public WebSecurityConfig(SecurityUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                             AuthenticationSuccessHandler authenticationSuccessHandler,
                             LoginSuccessHandler loginSuccessHandler) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Доступ только для ADMIN
                        //.requestMatchers("/user").hasAnyRole("USER", "ADMIN") // Только для пользователей с ролью USER
                        //.anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                        .requestMatchers("/auth/login", "/", "/error", "/auth/registration").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .formLogin(form -> form
                        .successHandler(loginSuccessHandler)
                        .loginPage("/auth/login")

                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/process_login")
                        .failureUrl("/auth/login?error")


                        //.defaultSuccessUrl("/admin", true)
                        //.successHandler(authenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth/login") // Перенаправление после выхода
                        .permitAll()
                );
        return http.build();
    }
}