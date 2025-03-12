package ru.javamentor.spring_boot_security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
//@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user");
        }







        // Получаем роли пользователя
//        boolean isAdmin = authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority ->
//                        grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
//        boolean isUser = authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority ->
//                        grantedAuthority.getAuthority().equals("ROLE_USER"));
//
//        // Перенаправляем в зависимости от роли
//        if (isAdmin) {
//            response.sendRedirect("/admin");
//        } else if (isUser) {
//            response.sendRedirect("/user");
//        } else {
//            response.sendRedirect("/"); // По умолчанию, если роль не определена
//        }
    }
}