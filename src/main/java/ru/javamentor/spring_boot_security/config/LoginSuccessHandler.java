package ru.javamentor.spring_boot_security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Получаем роли пользователя
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                        grantedAuthority.getAuthority().equals("ROLE_USER"));

        // Перенаправляем в зависимости от роли
        if (isAdmin) {
            response.sendRedirect("/admin");
        } else if (isUser) {
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/"); // По умолчанию, если роль не определена
        }
    }
}